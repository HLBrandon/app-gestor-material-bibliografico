package js4martineztorre.gob.biblioteca;

import android.os.Bundle;
import android.view.View;
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

public class VerUsuarioActivity extends AppCompatActivity {

    private Button btnVolver, btnGuardar, btnActivar, btnDesactivar;

    private EditText txtNombre, txtApellido, txtTelefono, txtUsuario, txtContrasenia, txtRole;

    private String [] opciones_role = {"Admin", "Empleado"};

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_usuario);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        btnGuardar = findViewById(R.id.btnCrear);
        btnVolver = findViewById(R.id.btnVolver);
        btnActivar = findViewById(R.id.btnActivar);
        btnDesactivar = findViewById(R.id.btnDesactivar);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtRole = findViewById(R.id.txtRole);

        // Obtener el clave del documento de la ficha del intent
        int usuario_id = getIntent().getIntExtra("usuario_id", 0); // Usa 0 como valor por defecto si no se encuentra

        if (usuario_id > 0) {
            cargar_datos(usuario_id);
        } else {
            Toast.makeText(VerUsuarioActivity.this, "No encontrado", Toast.LENGTH_LONG).show();
            btnGuardar.setEnabled(false);
        }

        btnVolver.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString().trim();
            String apellido = txtApellido.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String usuario = txtUsuario.getText().toString().trim();
            String contrasenia = txtContrasenia.getText().toString().trim(); // Contraseña es opcional
            String role = txtRole.getText().toString().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                    usuario.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos marcados *.", Toast.LENGTH_SHORT).show();
                return;
            }

            actualizar_usuario(nombre, apellido, telefono, usuario, contrasenia, role, usuario_id);

        });

        txtRole.setOnClickListener(v -> {
            alertar_opciones(opciones_role, txtRole);
        });

        btnActivar.setOnClickListener(v -> activar(usuario_id));

        btnDesactivar.setOnClickListener(v -> desactivar(usuario_id));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_USUARIO + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        JSONObject jsonObject = response.optJSONObject("usuario");

                        if (jsonObject != null) {

                            String nombre = jsonObject.optString("nombre", "Desconocido");
                            String apellido = jsonObject.optString("apellido", "Desconocido");
                            String telefono = jsonObject.optString("telefono", "Desconocido");
                            String usuario = jsonObject.optString("usuario", "Desconocido");
                            int estatus = jsonObject.optInt("estatus", 0);
                            String role_nombre = jsonObject.optString("role_nombre", "Desconocido");


                            // Asignar valores a los EditText
                            txtNombre.setText(nombre);
                            txtApellido.setText(apellido);
                            txtTelefono.setText(telefono);
                            txtUsuario.setText(usuario);
                            txtRole.setText(role_nombre);

                            if (estatus == 1) {
                                btnActivar.setEnabled(false);
                                btnActivar.setVisibility(View.GONE);
                            } else {
                                btnDesactivar.setEnabled(false);
                                btnDesactivar.setVisibility(View.GONE);
                            }

                        } else {
                            Toast.makeText(VerUsuarioActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerUsuarioActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void alertar_opciones(String[] opcionesRole, EditText txtRole) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar opción:");
        builder.setItems(opcionesRole, (dialog, which) -> {
            txtRole.setText(opcionesRole[which]);
        });
        builder.show();
    }

    private void actualizar_usuario(String nombre, String apellido, String telefono, String usuario, String contrasenia, String role, int id) {
        String url = Utilidades.ACTUALIZAR_USUARIO + "?nombre=\"" + nombre + "\"&apellido=\"" + apellido + "\"&telefono=\"" + telefono + "\"&usuario=\"" + usuario + "\"&contrasenia=\"" + contrasenia + "\"&role=\"" + role + "\"&id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerUsuarioActivity.this, "Creado con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerUsuarioActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void activar(int id) {
        String url = Utilidades.ACTIVAR_USUARIO + "?id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerUsuarioActivity.this, "La Cuenta fue activada con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerUsuarioActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void desactivar(int id) {
        String url = Utilidades.DESACTIVAR_USUARIO + "?id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerUsuarioActivity.this, "La Cuenta fue desactiva con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerUsuarioActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

}