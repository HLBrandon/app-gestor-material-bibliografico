package js4martineztorre.gob.biblioteca;

import android.app.ProgressDialog;
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

public class RegistrarLectorActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtTelefono, txtCorreo, txtDireccion;

    private Button btnVolver, btnCrear;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_lector);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        btnCrear = findViewById(R.id.btnCrear);
        btnVolver = findViewById(R.id.btnVolver);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDireccion = findViewById(R.id.txtDireccion);

        btnVolver.setOnClickListener(v -> finish());

        btnCrear.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString().trim();
            String apellido = txtApellido.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String direccion = txtDireccion.getText().toString().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                    correo.isEmpty() || direccion.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            registrar_lector(nombre, apellido, telefono, correo, direccion);

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void registrar_lector(String nombre, String apellido, String telefono, String correo, String direccion) {
        String url = Utilidades.CREAR_LECTOR + "?nombre=\"" + nombre + "\"&apellido=\"" + apellido + "\"&telefono=\"" + telefono + "\"&correo=\"" + correo + "\"&direccion=\"" + direccion + "\"";
        url.replace("","%20");

        // Mostrar un ProgressDialog mientras se realiza el inicio de sesión
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Procesando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegistrarLectorActivity.this, "Creado con exito", Toast.LENGTH_LONG).show();
                        limpiar();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarLectorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }
}