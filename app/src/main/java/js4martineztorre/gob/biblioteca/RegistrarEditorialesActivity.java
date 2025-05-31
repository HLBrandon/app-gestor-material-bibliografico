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

public class RegistrarEditorialesActivity extends AppCompatActivity {

    private Button btnVolver, btnCrear;

    private EditText txtNombre;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_editoriales);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        btnCrear = findViewById(R.id.btnCrear);
        btnVolver = findViewById(R.id.btnVolver);

        txtNombre = findViewById(R.id.txtNombre);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnCrear.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString().trim();

            if ( nombre.isEmpty() ) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            registrar_editorial(nombre);

        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registrar_editorial(String nombre) {
        String url = Utilidades.CREAR_EDITORIAL + "?nombre=\"" + nombre + "\"";
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegistrarEditorialesActivity.this, "Creado con exito", Toast.LENGTH_LONG).show();
                        limpiar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarEditorialesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void limpiar () {
        txtNombre.setText("");
    }
}