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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import js4martineztorre.gob.biblioteca.model.Utilidades;

public class VerCategoriaActivity extends AppCompatActivity {

    private Button btnVolver, btnGuardar;
    private EditText txtNombre, txtPasillo;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_categoria);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar botones
        btnVolver = findViewById(R.id.btnVolver);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Relacionar Txt
        txtNombre = findViewById(R.id.txtNombre);
        txtPasillo = findViewById(R.id.txtPasillo);

        // Obtener el clave del documento de la ficha del intent
        int categoria_id = getIntent().getIntExtra("categoria_id", 0); // Usa 0 como valor por defecto si no se encuentra

        if (categoria_id > 0) {
            cargar_datos(categoria_id);
        } else {
            Toast.makeText(VerCategoriaActivity.this, "No encontrado", Toast.LENGTH_LONG).show();
            btnGuardar.setEnabled(false);
        }

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnGuardar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();
            String pasillo = txtPasillo.getText().toString().trim();

            if (nombre.isEmpty() && pasillo.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            actualizar(categoria_id, nombre, pasillo);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_CATEGORIA + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "categoria"
                        JSONObject jsonObject = response.optJSONObject("categoria");

                        if (jsonObject != null) {
                            String nombre = jsonObject.optString("nombre", "Desconocido");
                            String pasillo = jsonObject.optString("pasillo", "Desconocido");

                            // Asignar valores a los EditText
                            txtNombre.setText(nombre);
                            txtPasillo.setText(pasillo);
                        } else {
                            Toast.makeText(VerCategoriaActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerCategoriaActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void actualizar (int id, String nombre, String pasillo) {

        String url = Utilidades.ACTUALIZAR_CATEGORIA + "?id="+ id + "&nombre=\"" + nombre + "\"&pasillo=" + pasillo;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerCategoriaActivity.this, "Cambios guardados con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerCategoriaActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

}