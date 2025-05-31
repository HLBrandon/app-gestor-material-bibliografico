package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import js4martineztorre.gob.biblioteca.model.Utilidades;

public class VerLibroActivity extends AppCompatActivity {

    private ImageButton btnVolver;

    private TextView txtTitulo, txtISBN, txtAutor, txtAnioPublicacion, txtEditorial, txtCategoria, txtPasillo, txtExistencias;
    private Button btnModificar, btnPrestar;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    private String isbn_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_libro);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar Relacionar botones
        btnVolver = findViewById(R.id.btnVolver);
        btnModificar = findViewById(R.id.btnModificar);
        // btnPrestar = findViewById(R.id.btnPrestar);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.main);

        // Relacionar text
        txtTitulo = findViewById(R.id.txtTitulo);
        txtISBN = findViewById(R.id.txtISBN);
        txtAutor = findViewById(R.id.txtAutor);
        txtAnioPublicacion = findViewById(R.id.txtAnioPublicacion);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtPasillo = findViewById(R.id.txtPasillo);
        txtExistencias = findViewById(R.id.txtExistencias);

        // Obtener el clave del documento de la ficha del intent
        int libro_id = getIntent().getIntExtra("libro_id", 0); // Usa 0 como valor por defecto si no se encuentra
        int role_id = getIntent().getIntExtra("role_id", 0);

        if (role_id == 2) {
            btnModificar.setVisibility(View.GONE);
        }

        if (libro_id > 0) {
            cargar_datos(libro_id);
        }

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnModificar.setOnClickListener(v -> {
            Intent view = new Intent(VerLibroActivity.this, ModificarLibroActivity.class);
            view.putExtra("libro_id", libro_id);
            startActivity(view);
        });

        /*
        btnPrestar.setOnClickListener(v -> {
            Intent view = new Intent(VerLibroActivity.this, RegistrarPrestamoActivity.class);
            view.putExtra("isbn_libro", isbn_enviar);
            startActivity(view);
        }); */

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            cargar_datos(libro_id);
            swipeRefreshLayout.setRefreshing(false);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_LIBRO + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        JSONObject jsonObject = response.optJSONObject("libro");

                        if (jsonObject != null) {

                            String titulo = jsonObject.optString("titulo", "Desconocido");
                            String isbn = jsonObject.optString("isbn", "Desconocido");
                            String autor_nombre = jsonObject.optString("autor_nombre", "Desconocido");
                            String anio_publicacion = jsonObject.optString("anio_publicacion", "Desconocido");
                            String editorial_nombre = jsonObject.optString("editorial_nombre", "Desconocido");
                            String categoria_nombre = jsonObject.optString("categoria_nombre", "Desconocido");
                            String pasillo = jsonObject.optString("pasillo", "Desconocido");
                            int existencias = jsonObject.optInt("stoke", 0);


                            // Asignar valores a los EditText
                            txtTitulo.setText(titulo);
                            txtISBN.setText("ISBN: " + isbn);
                            txtAutor.setText("Autor: " + autor_nombre);
                            txtAnioPublicacion.setText("Año de publicación: " + anio_publicacion);
                            txtEditorial.setText("Editorial: " + editorial_nombre);
                            txtCategoria.setText("Categoría: " + categoria_nombre);
                            txtPasillo.setText("Pasillo: " + pasillo);
                            txtExistencias.setText("Existencias: " + existencias);

                            isbn_enviar = isbn;

                            if (existencias == 0) {
                                btnPrestar.setEnabled(false);
                                Toast.makeText(VerLibroActivity.this, "No hay libros disponibles para prestar", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(VerLibroActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerLibroActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        request.add(jsonObjectRequest);
    }
}