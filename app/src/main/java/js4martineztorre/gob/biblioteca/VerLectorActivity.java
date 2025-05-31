package js4martineztorre.gob.biblioteca;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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

public class VerLectorActivity extends AppCompatActivity {

    private Button btnVolver, btnCrear, btnActivar, btnDesactivar;

    private EditText txtNombre, txtApellido, txtTelefono, txtCorreo, txtDireccion;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_lector);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        btnCrear = findViewById(R.id.btnCrear);
        btnVolver = findViewById(R.id.btnVolver);
        btnActivar = findViewById(R.id.btnActivar);
        btnDesactivar = findViewById(R.id.btnDesactivar);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDireccion = findViewById(R.id.txtDireccion);

        // Obtener el clave del documento de la ficha del intent
        int lector_id = getIntent().getIntExtra("lector_id", 0); // Usa 0 como valor por defecto si no se encuentra

        if (lector_id > 0) {
            cargar_datos(lector_id);
        } else {
            Toast.makeText(VerLectorActivity.this, "No encontrado", Toast.LENGTH_LONG).show();
            btnCrear.setEnabled(false);
        }

        // Botones
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

            actualizar_lector(nombre, apellido, telefono, correo, direccion, lector_id);

        });

        btnActivar.setOnClickListener(v -> activar(lector_id));

        btnDesactivar.setOnClickListener(v -> desactivar(lector_id));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_LECTOR + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = response.optJSONObject("lector");

                        if (jsonObject != null) {

                            String nombre = jsonObject.optString("nombre", "Desconocido");
                            String apellido = jsonObject.optString("apellido", "Desconocido");
                            String telefono = jsonObject.optString("telefono", "Desconocido");
                            String correo = jsonObject.optString("correo", "Desconocido");
                            String direccion = jsonObject.optString("direccion", "Desconocido");
                            int estatus = jsonObject.optInt("estatus", 0);


                            // Asignar valores a los EditText
                            txtNombre.setText(nombre);
                            txtApellido.setText(apellido);
                            txtTelefono.setText(telefono);
                            txtCorreo.setText(correo);
                            txtDireccion.setText(direccion);

                            if (estatus == 1) {
                                btnActivar.setEnabled(false);
                                btnActivar.setVisibility(View.GONE);
                            } else {
                                btnDesactivar.setEnabled(false);
                                btnDesactivar.setVisibility(View.GONE);
                            }


                        } else {
                            Toast.makeText(VerLectorActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerLectorActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void actualizar_lector(String nombre, String apellido, String telefono, String correo, String direccion, int id) {
        String url = Utilidades.ACTUALIZAR_LECTOR + "?nombre=\"" + nombre + "\"&apellido=\"" + apellido + "\"&telefono=\"" + telefono + "\"&correo=\"" + correo + "\"&direccion=\"" + direccion + "\"&id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerLectorActivity.this, "Creado con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerLectorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void activar(int id) {
        String url = Utilidades.ACTIVAR_LECTOR + "?id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerLectorActivity.this, "Este Lector fue desactivado", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerLectorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void desactivar(int id) {
        String url = Utilidades.DESACTIVAR_LECTOR + "?id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerLectorActivity.this, "Este Lector fue activado", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerLectorActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

}