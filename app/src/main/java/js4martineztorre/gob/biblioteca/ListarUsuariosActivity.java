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

import js4martineztorre.gob.biblioteca.model.Editorial;
import js4martineztorre.gob.biblioteca.model.EditorialAdapter;
import js4martineztorre.gob.biblioteca.model.Usuario;
import js4martineztorre.gob.biblioteca.model.UsuarioAdapter;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ListarUsuariosActivity extends AppCompatActivity {

    private Button btnAgregar, btnVolver;

    private RecyclerView usuariolRecyclerView;
    private UsuarioAdapter usuarioAdapter;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_usuarios);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar todo lo relacionado con el recyclerView
        usuariolRecyclerView = findViewById(R.id.recyclerViewUsuarios);
        usuariolRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usuarioAdapter = new UsuarioAdapter();
        usuariolRecyclerView.setAdapter(usuarioAdapter);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        listar_usuarios();

        // Botones
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnAgregar.setOnClickListener(v -> {
            Intent crear = new Intent(ListarUsuariosActivity.this, RegistrarUsuarioActivity.class);
            startActivity(crear);
        });

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listar_usuarios();
            swipeRefreshLayout.setRefreshing(false);
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void listar_usuarios () {
        String url = Utilidades.LISTAR_USUARIOS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("usuarios");

                        List<Usuario> usuarios = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("id", 0);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");
                                    String apellido = jsonObject.optString("apellido", "Desconocido");
                                    String role = jsonObject.optString("role_nombre", "Desconocido");

                                    Usuario usuario = new Usuario(apellido, nombre, id, role);
                                    usuarios.add(usuario);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            usuarioAdapter.setUsuarios(usuarios);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarUsuariosActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
}