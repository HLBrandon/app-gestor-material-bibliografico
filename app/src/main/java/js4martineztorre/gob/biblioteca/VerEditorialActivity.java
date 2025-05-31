package js4martineztorre.gob.biblioteca;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import js4martineztorre.gob.biblioteca.model.Utilidades;

public class VerEditorialActivity extends AppCompatActivity {

    private Button btnVolver, btnGuardar;
    private EditText txtNombre;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_editorial);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar botones
        btnVolver = findViewById(R.id.btnVolver);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Relacionar Txt
        txtNombre = findViewById(R.id.txtNombre);

        // Obtener el clave del documento de la ficha del intent
        int categoria_id = getIntent().getIntExtra("editorial_id", 0); // Usa 0 como valor por defecto si no se encuentra

        if (categoria_id > 0) {
            cargar_datos(categoria_id);
        } else {
            Toast.makeText(VerEditorialActivity.this, "No encontrado", Toast.LENGTH_LONG).show();
            btnGuardar.setEnabled(false);
        }

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnGuardar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            actualizar(categoria_id, nombre);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_EDITORIAL + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        JSONObject jsonObject = response.optJSONObject("editorial");

                        if (jsonObject != null) {
                            String nombre = jsonObject.optString("nombre", "Desconocido");

                            // Asignar valores a los EditText
                            txtNombre.setText(nombre);
                        } else {
                            Toast.makeText(VerEditorialActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerEditorialActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void actualizar (int id, String nombre) {

        String url = Utilidades.ACTUALIZAR_EDITORIAL + "?id="+ id + "&nombre=\"" + nombre + "\"";
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerEditorialActivity.this, "Cambios guardados con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerEditorialActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
}