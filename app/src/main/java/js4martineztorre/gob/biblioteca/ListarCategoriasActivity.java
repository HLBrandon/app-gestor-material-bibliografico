package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import js4martineztorre.gob.biblioteca.model.Categoria;
import js4martineztorre.gob.biblioteca.model.CategoriaAdapter;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ListarCategoriasActivity extends AppCompatActivity {

    private Button btnAgregar, btnVolver;

    private RecyclerView categoriaRecyclerView;
    private CategoriaAdapter categoriaAdapter;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_categorias);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar todo lo relacionado con el recyclerView
        categoriaRecyclerView = findViewById(R.id.recyclerViewCategorias);
        categoriaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriaAdapter = new CategoriaAdapter();
        categoriaRecyclerView.setAdapter(categoriaAdapter);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        listar_categorias();


        // Botones
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnAgregar.setOnClickListener(v -> {
            Intent crear = new Intent(ListarCategoriasActivity.this, RegistrarCategoriaActivity.class);
            startActivity(crear);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            listar_categorias();
            swipeRefreshLayout.setRefreshing(false);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void listar_categorias () {
        String url = Utilidades.LISTAR_CATEGORIAS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("categorias");

                        List<Categoria> categorias = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.optInt("id", 0);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");
                                    int pasillo = jsonObject.optInt("pasillo", 0);

                                    Categoria categoria = new Categoria(id, nombre, pasillo);
                                    categorias.add(categoria);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            categoriaAdapter.setCategorias(categorias);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarCategoriasActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

}