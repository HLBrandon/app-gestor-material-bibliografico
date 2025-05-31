package js4martineztorre.gob.biblioteca;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class VerPrestamoActivity extends AppCompatActivity {

    private TextView lbCodigo, lbFechaPrestamo, lbFechaDevolucion, lbEstatus;
    private TextView lbTitulo, lbISBN, lbAutor;
    private TextView lbNombreLector, lbCorreoLector, lbTelefonoLector, lbDireccionLector;
    private TextView lbNombreUsuario;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private ImageButton btnVolver;
    private Button btnCancelar, btnDevolver, btnEnviarCorreo, btnPerder;

    private int codigo_prestamo;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_prestamo);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar text con los ID del XML
        lbCodigo = findViewById(R.id.lb_codigo);
        lbFechaPrestamo = findViewById(R.id.lb_fecha_prestamo);
        lbFechaDevolucion = findViewById(R.id.lb_fecha_devolucion);
        lbEstatus = findViewById(R.id.lb_estatus);

        lbTitulo = findViewById(R.id.lb_titulo);
        lbISBN = findViewById(R.id.lb_isbn);
        lbAutor = findViewById(R.id.lb_autor);

        lbNombreLector = findViewById(R.id.lb_nombre_lector);
        lbCorreoLector = findViewById(R.id.lb_correo_lector);
        lbTelefonoLector = findViewById(R.id.lb_telefono_lector);
        lbDireccionLector = findViewById(R.id.lb_direccion_lector);

        lbNombreUsuario = findViewById(R.id.lb_nombre_usuario);

        // botones
        btnCancelar = findViewById(R.id.btnCancelar);
        btnDevolver = findViewById(R.id.btnDevolver);
        btnVolver = findViewById(R.id.btnVolver);
        btnPerder = findViewById(R.id.btnPerder);
        btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.main);

        // Obtener el clave del documento de la ficha del intent
        int prestamo_id = getIntent().getIntExtra("prestamo_id", 0);
        Boolean retraso = getIntent().getBooleanExtra("retraso", false);

        if (prestamo_id > 0) {
            cargar_datos(prestamo_id);
        }

        if (retraso) {
            btnCancelar.setEnabled(false);
            btnPerder.setVisibility(View.VISIBLE);
            btnEnviarCorreo.setVisibility(View.VISIBLE);
        }

        btnVolver.setOnClickListener(v -> finish());

        btnCancelar.setOnClickListener(v -> cancelar(prestamo_id));

        btnDevolver.setOnClickListener(v -> devolver(prestamo_id));

        btnEnviarCorreo.setOnClickListener(v -> {
            notificar_retraso(codigo_prestamo);
        });

        btnPerder.setOnClickListener(v -> {
            perder(codigo_prestamo);
        });

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            cargar_datos(prestamo_id);
            swipeRefreshLayout.setRefreshing(false);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos (int id)  {
        String url = Utilidades.VER_PRESTAMO + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        // Obtener el objeto "prestamo"
                        JSONObject jsonObject = response.optJSONObject("prestamo");

                        if (jsonObject != null) {
                            // Extraer valores del JSON
                            codigo_prestamo = jsonObject.optInt("id", 0);
                            String fechaPrestamo = jsonObject.optString("fecha_prestamo", "Desconocido");
                            String fechaDevuelto = jsonObject.optString("fecha_devolucion", "Desconocido");
                            int sta = jsonObject.optInt("estatus", 0);
                            String estatus = jsonObject.optString("estatus", "Desconocido").equals("0") ? "Devuelto" : "Prestado";

                            String titulo = jsonObject.optString("libro_titulo", "Desconocido");
                            String isbn = jsonObject.optString("libro_isbn", "Desconocido");
                            String autor = jsonObject.optString("autor_nombre", "Desconocido");

                            String nombreLector = jsonObject.optString("lector_nombre", "Desconocido") + " " + jsonObject.optString("lector_apellido", "");
                            String correoLector = jsonObject.optString("lector_correo", "Desconocido");
                            String telefonoLector = jsonObject.optString("lector_telefono", "Desconocido");
                            String direccionLector = jsonObject.optString("lector_direccion", "Desconocido");

                            String nombreUsuario = jsonObject.optString("usuario_nombre", "Desconocido");
                            String apellidoUsuario = jsonObject.optString("usuario_apellido", "Desconocido");


                            // Asignar valores a los TextView
                            lbCodigo.setText("Cod: " + codigo_prestamo);
                            lbFechaPrestamo.setText("Fecha de préstamo: " + fechaPrestamo);
                            lbFechaDevolucion.setText("Fecha de devolución: " + fechaDevuelto);
                            lbEstatus.setText("Estado: " + estatus);

                            lbTitulo.setText("Título: " + titulo);
                            lbISBN.setText("ISBN: " + isbn);
                            lbAutor.setText("Autor: " + autor);

                            lbNombreLector.setText("Nombre: " + nombreLector);
                            lbCorreoLector.setText("Correo: " + correoLector);
                            lbTelefonoLector.setText("Teléfono: " + telefonoLector);
                            lbDireccionLector.setText("Dirección: " + direccionLector);

                            lbNombreUsuario.setText("Usuario: " + nombreUsuario + " " + apellidoUsuario);

                        } else {
                            Toast.makeText(VerPrestamoActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerPrestamoActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        request.add(jsonObjectRequest);
    }

    private void cancelar (int id) {
        String url = Utilidades.CANCELAR_PRESTAMO + "?id=" + id;
        url.replace("","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerPrestamoActivity.this, "El prestamo fue cancelado con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerPrestamoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void devolver (int id) {
        String url = Utilidades.DEVOLVER_PRESTAMO + "?id=" + id;
        url.replace("","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(VerPrestamoActivity.this, "Se regresó el libro con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerPrestamoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void notificar_retraso (int id) {
        String url = Utilidades.NOTIFICAR_RETRASOS + "?prestamo_id=" + id;
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
                        progressDialog.dismiss();
                        String mesnaje = response.optString("message", "No recibido");
                        Toast.makeText(VerPrestamoActivity.this, mesnaje, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerPrestamoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void perder (int id) {
        String url = Utilidades.PERDER + "?id=" + id;
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
                        progressDialog.dismiss();
                        String mesnaje = response.optString("mensaje", "No recibido");
                        Toast.makeText(VerPrestamoActivity.this, mesnaje, Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerPrestamoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }


}