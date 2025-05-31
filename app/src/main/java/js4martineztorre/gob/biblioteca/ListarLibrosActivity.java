package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import js4martineztorre.gob.biblioteca.model.Editorial;
import js4martineztorre.gob.biblioteca.model.EditorialAdapter;
import js4martineztorre.gob.biblioteca.model.Libro;
import js4martineztorre.gob.biblioteca.model.LibroAdapter;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ListarLibrosActivity extends AppCompatActivity {

    private Button btnAgregar, btnVolver;
    private EditText txtBuscar;

    private RecyclerView libroRecyclerView;
    private LibroAdapter libroAdapter;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_libros);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar todo lo relacionado con el recyclerView
        libroRecyclerView = findViewById(R.id.recyclerViewLibros);
        libroRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        libroAdapter = new LibroAdapter();
        libroRecyclerView.setAdapter(libroAdapter);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        // Relacionar vistas con botones
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);
        txtBuscar = findViewById(R.id.txtBuscar);

        // Obtener el id y el role_id
        int id = getIntent().getIntExtra("id", 0);
        int role_id = getIntent().getIntExtra("role_id", 0);

        if (role_id == 2) {
            btnAgregar.setVisibility(View.GONE);
        }

        listar_libros(role_id);

        btnAgregar.setOnClickListener(v -> {
            Intent agregar = new Intent(ListarLibrosActivity.this, RegistrarLibroActivity.class);
            startActivity(agregar);
        });

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String buscar = txtBuscar.getText().toString().trim();
                if (buscar.equals("")) {
                    listar_libros(role_id);
                    return;
                }

                buscar_libro(buscar, role_id);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listar_libros(role_id);
            swipeRefreshLayout.setRefreshing(false);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void listar_libros (int role) {
        String url = Utilidades.LISTAR_LIBROS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("libros");

                        List<Libro> libros = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("libro_id", 0);
                                    String titulo = jsonObject.optString("titulo", "Desconocido");
                                    String autor_nombre = jsonObject.optString("autor_nombre", "Desconocido");

                                    Libro libro = new Libro(id, titulo, autor_nombre, role);
                                    libros.add(libro);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            libroAdapter.setLibros(libros);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarLibrosActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void buscar_libro (String buscar, int role) {
        String url = Utilidades.BUSCAR_LIBRO + "?buscar=" + buscar;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("libros");

                        List<Libro> libros = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("libro_id", 0);
                                    String titulo = jsonObject.optString("titulo", "Desconocido");
                                    String autor_nombre = jsonObject.optString("autor_nombre", "Desconocido");

                                    Libro libro = new Libro(id, titulo, autor_nombre, role);
                                    libros.add(libro);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            libroAdapter.setLibros(libros);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarLibrosActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

}