package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

import js4martineztorre.gob.biblioteca.model.Autor;
import js4martineztorre.gob.biblioteca.model.AutorAdapter;
import js4martineztorre.gob.biblioteca.model.Editorial;
import js4martineztorre.gob.biblioteca.model.EditorialAdapter;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ListarAutoresActivity extends AppCompatActivity {

    private Button btnAgregar, btnVolver;

    private RecyclerView autorRecyclerView;
    private AutorAdapter autorAdapter;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_autores);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar todo lo relacionado con el recyclerView
        autorRecyclerView = findViewById(R.id.recyclerViewAutores);
        autorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        autorAdapter = new AutorAdapter();
        autorRecyclerView.setAdapter(autorAdapter);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        listar_autores();

        // Botones
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnAgregar.setOnClickListener(v -> {
            Intent crear = new Intent(ListarAutoresActivity.this, RegistrarAutorActivity.class);
            startActivity(crear);
        });

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listar_autores();
            swipeRefreshLayout.setRefreshing(false);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void listar_autores () {
        String url = Utilidades.LISTAR_AUTORES;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("autores");

                        List<Autor> autores = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("id", 0);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");

                                    Autor autor = new Autor(id, nombre);
                                    autores.add(autor);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            autorAdapter.setAutores(autores);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarAutoresActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
}