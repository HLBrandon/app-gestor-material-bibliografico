package js4martineztorre.gob.biblioteca;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtTelefono, txtUsuario, txtContrasenia, txtRole;

    private Button btnVolver, btnCrear;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String [] opciones_role = {"Admin", "Empleado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        btnCrear = findViewById(R.id.btnCrear);
        btnVolver = findViewById(R.id.btnVolver);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtRole = findViewById(R.id.txtRole);

        btnVolver.setOnClickListener(v -> finish());

        btnCrear.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString().trim();
            String apellido = txtApellido.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String usuario = txtUsuario.getText().toString().trim();
            String contrasenia = txtContrasenia.getText().toString().trim();
            String role = txtRole.getText().toString().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                    usuario.isEmpty() || contrasenia.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            registrar_usuario(nombre, apellido, telefono, usuario, contrasenia, role);

        });

        txtRole.setOnClickListener(v -> {
            alertar_opciones(opciones_role, txtRole);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void alertar_opciones(String[] opcionesRole, EditText txtRole) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar opción:");
        builder.setItems(opcionesRole, (dialog, which) -> {
            txtRole.setText(opcionesRole[which]);
        });
        builder.show();
    }

    private void registrar_usuario(String nombre, String apellido, String telefono, String usuario, String contrasenia, String role) {
        String url = Utilidades.CREAR_USUARIO + "?nombre=\"" + nombre + "\"&apellido=\"" + apellido + "\"&telefono=\"" + telefono + "\"&usuario=\"" + usuario + "\"&contrasenia=\"" + contrasenia + "\"&role=\"" + role + "\"";
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegistrarUsuarioActivity.this, "Creado con exito", Toast.LENGTH_LONG).show();
                        limpiar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarUsuarioActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtRole.setText("");
    }

}