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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import js4martineztorre.gob.biblioteca.model.Utilidades;

public class VerAutorActivity extends AppCompatActivity {

    private Button btnVolver, btnGuardar;
    private EditText txtNombre;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_autor);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar botones
        btnVolver = findViewById(R.id.btnVolver);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Relacionar Txt
        txtNombre = findViewById(R.id.txtNombre);

        // Obtener el clave del documento de la ficha del intent
        int autor_id = getIntent().getIntExtra("autor_id", 0); // Usa 0 como valor por defecto si no se encuentra

        if (autor_id > 0) {
            cargar_datos(autor_id);
        } else {
            Toast.makeText(VerAutorActivity.this, "No encontrado", Toast.LENGTH_LONG).show();
            btnGuardar.setEnabled(false);
        }

        btnVolver.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            actualizar(autor_id, nombre);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos(int id) {
        String url = Utilidades.VER_AUTOR + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    JSONObject jsonObject = response.optJSONObject("autor");

                    if (jsonObject != null) {
                        String nombre = jsonObject.optString("nombre", "Desconocido");

                        // Asignar valores a los EditText
                        txtNombre.setText(nombre);
                    } else {
                        Toast.makeText(VerAutorActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(VerAutorActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show());
        request.add(jsonObjectRequest);
    }

    private void actualizar(int id, String nombre) {
        String url = Utilidades.ACTUALIZAR_AUTOR + "?id=" + id + "&nombre=\"" + nombre + "\"";
        url.replace("", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Toast.makeText(VerAutorActivity.this, "Cambios guardados con éxito", Toast.LENGTH_LONG).show();
                    finish();
                },
                error -> Toast.makeText(VerAutorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());
        request.add(jsonObjectRequest);
    }

}