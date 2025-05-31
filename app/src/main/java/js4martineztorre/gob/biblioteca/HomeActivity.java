package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class HomeActivity extends AppCompatActivity {

    private CardView card_usuarios, card_libros, card_categorias, card_editoriales, card_autores;
    private CardView card_lectores, card_salir, card_prestamos, card_devoluciones, card_alertas;
    private CardView card_historial;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        card_usuarios = findViewById(R.id.card_usuarios);
        card_libros = findViewById(R.id.card_libros);
        card_categorias = findViewById(R.id.card_categorias);
        card_editoriales = findViewById(R.id.card_editoriales);
        card_autores = findViewById(R.id.card_autores);
        card_lectores = findViewById(R.id.card_lectores);
        card_salir = findViewById(R.id.card_salir);
        card_prestamos = findViewById(R.id.card_prestamos);
        card_devoluciones = findViewById(R.id.card_devoluciones);
        card_alertas = findViewById(R.id.card_alertas);
        card_historial = findViewById(R.id.card_historial);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.main);

        // Obtener el id y el role_id
        int id = getIntent().getIntExtra("id", 0);
        int role_id = getIntent().getIntExtra("role_id", 0);

        cargar_datos(role_id);
        comprobar_retrasos();

        card_libros.setOnClickListener(v -> {
            Intent libros = new Intent(HomeActivity.this, ListarLibrosActivity.class);
            libros.putExtra("id", id);
            libros.putExtra("role_id", role_id);
            startActivity(libros);
        });

        card_usuarios.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarUsuariosActivity.class);
            startActivity(view);
        });

        card_categorias.setOnClickListener(v -> {
            Intent cat = new Intent(HomeActivity.this, ListarCategoriasActivity.class);
            startActivity(cat);
        });

        card_editoriales.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarEditorialesActivity.class);
            startActivity(view);
        });

        card_autores.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarAutoresActivity.class);
            startActivity(view);
        });

        card_lectores.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarLectoresActivity.class);
            startActivity(view);
        });

        card_prestamos.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, RegistrarPrestamoActivity.class);
            view.putExtra("id", id);
            startActivity(view);
        });

        card_devoluciones.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarPrestamosActivity.class);
            startActivity(view);
        });

        card_alertas.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarRetrasosActivity.class);
            startActivity(view);
        });

        card_historial.setOnClickListener(v -> {
            Intent view = new Intent(HomeActivity.this, ListarHistorialActivity.class);
            startActivity(view);
        });

        card_salir.setOnClickListener(v -> cerrar_sesion());

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            cargar_datos(role_id);
            comprobar_retrasos();
            swipeRefreshLayout.setRefreshing(false);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void cargar_datos (int role_id) {
        if (role_id == 2) {
            card_autores.setVisibility(View.GONE);
            card_categorias.setVisibility(View.GONE);
            card_editoriales.setVisibility(View.GONE);
            card_usuarios.setVisibility(View.GONE);
            card_lectores.setVisibility(View.GONE);
        }

        card_alertas.setVisibility(View.GONE);
    }

    private void cerrar_sesion () {
        Intent view = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(view);
        finish();
    }

    private void comprobar_retrasos () {
        String url = Utilidades.VERIFICAR_RETRASOS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        Boolean status = response.optBoolean("status", false);
                        String mensaje = response.optString("message", "Mensaje");
                        // JSONObject jsonObject = response.optJSONObject("atrasos");

                        if (status) {
                            card_alertas.setVisibility(View.VISIBLE);
                            alerta_eliminar(mensaje);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        request.add(jsonObjectRequest);
    }

    private void alerta_eliminar(String mensaje) {
        // Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta!!");
        builder.setMessage(mensaje);

        // Botón para eliminar
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            dialog.dismiss();
        });

        // Mostrar el diálogo
        builder.show();
    }


}