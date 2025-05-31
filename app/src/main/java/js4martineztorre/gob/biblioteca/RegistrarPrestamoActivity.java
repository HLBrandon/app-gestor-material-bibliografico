package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import js4martineztorre.gob.biblioteca.model.Libro;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class RegistrarPrestamoActivity extends AppCompatActivity {

    private EditText txtISBN, txtLectorId;
    private ImageButton btnSc, btnSc2;
    private Button btnVolver, btnPrestar;

    private CardView card_libro, card_lector;

    private TextView lbTitulo, lbAutor, lbAnioPublicacion, lbEditorial, lbNombre, lbCorreo, lbTelefono;

    private static final int REQUEST_CODE_ISBN = 1;
    private static final int REQUEST_CODE_ID = 2;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_prestamo);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        txtISBN = findViewById(R.id.txtISBN);
        txtLectorId = findViewById(R.id.txtLectorId);

        lbTitulo = findViewById(R.id.lbTitulo);
        lbAutor = findViewById(R.id.lbAutor);
        lbAnioPublicacion = findViewById(R.id.lbAnioPublicacion);
        lbEditorial = findViewById(R.id.lbEditorial);
        lbNombre = findViewById(R.id.lbNombre);
        lbCorreo = findViewById(R.id.lbCorreo);
        lbTelefono = findViewById(R.id.lbTelefono);


        btnSc = findViewById(R.id.btnSc);
        btnSc2 = findViewById(R.id.btnSc2);
        btnVolver = findViewById(R.id.btnVolver);
        btnPrestar = findViewById(R.id.btnPrestar);

        card_libro = findViewById(R.id.card_libro);
        card_lector = findViewById(R.id.card_lector);

        // Obtener el id y el role_id
        int id = getIntent().getIntExtra("id", 0);
        String isbn_enviado = getIntent().getStringExtra("isbn_libro");

        // Ocultar las tarjetas
        ocultar_tarjetas(card_libro, card_lector);

        // Validar si el valor es null antes de usar isEmpty()
        if (isbn_enviado != null && !isbn_enviado.isEmpty()) {
            txtISBN.setText(isbn_enviado);
            buscar_libro(isbn_enviado);
        }

        btnVolver.setOnClickListener(v -> finish());

        // Escaner para ISBN
        btnSc.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(RegistrarPrestamoActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setRequestCode(REQUEST_CODE_ISBN);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
        // Escáner para ID
        btnSc2.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(RegistrarPrestamoActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.setRequestCode(REQUEST_CODE_ID);
            integrator.initiateScan();
        });

        txtISBN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String buscar = txtISBN.getText().toString().trim();
                if ("".equals(buscar)) {
                    card_libro.setVisibility(View.GONE);
                    return;
                }
                buscar_libro(buscar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtLectorId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String buscar = txtLectorId.getText().toString().trim();
                if ("".equals(buscar)) {
                    card_lector.setVisibility(View.GONE);
                    return;
                }
                buscar_lector(Integer.parseInt(buscar));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnPrestar.setOnClickListener(v -> {
            String isbn = txtISBN.getText().toString().trim();
            String lector = txtLectorId.getText().toString().trim();

            if (isbn.isEmpty() || lector.isEmpty()) {
                Toast.makeText(RegistrarPrestamoActivity.this, "Debes completar todos los campos", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                int lector_id = Integer.parseInt(lector); // Convertir a entero
                guardar(isbn, lector_id, id);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Error: Lector ID no es un número válido", Toast.LENGTH_SHORT).show();
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String scannedData = result.getContents();

                // Verificar qué botón activó el escáner y asignar el resultado
                if (requestCode == REQUEST_CODE_ISBN) {
                    txtISBN.setText(scannedData);
                } else if (requestCode == REQUEST_CODE_ID) {
                    txtLectorId.setText(scannedData);
                }
            } else {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void buscar_libro (String buscar) {
        String url = Utilidades.BUSCAR_LIBRO + "?buscar=" + buscar;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el array "libros"
                        JSONArray jsonArray = response.optJSONArray("libros");

                        if (jsonArray != null && jsonArray.length() > 0) {
                            // Obtener el primer elemento del array
                            JSONObject jsonObject = jsonArray.optJSONObject(0);

                            if (jsonObject != null) {

                                card_libro.setVisibility(View.VISIBLE);
                                btnPrestar.setEnabled(true);

                                String titulo = jsonObject.optString("titulo", "Desconocido");
                                String autor_nombre = jsonObject.optString("autor_nombre", "Desconocido");
                                String anio_publicacion = jsonObject.optString("anio_publicacion", "Desconocido");
                                String editorial_nombre = jsonObject.optString("editorial_nombre", "Desconocido");
                                int existencias = jsonObject.optInt("stoke", 0);

                                // Asignar valores a los EditText
                                lbTitulo.setText(titulo);
                                lbAutor.setText("Autor: " + autor_nombre);
                                lbAnioPublicacion.setText("Año de publicación: " + anio_publicacion);
                                lbEditorial.setText("Editorial: " + editorial_nombre);

                                if (existencias == 0) {
                                    btnPrestar.setEnabled(false);
                                    Toast.makeText(RegistrarPrestamoActivity.this, "No hay libros disponibles para prestar", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(RegistrarPrestamoActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarPrestamoActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void buscar_lector (int id) {
        String url = Utilidades.VER_LECTOR + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = response.optJSONObject("lector");

                        if (jsonObject != null) {

                            int id = jsonObject.optInt("id", 0);
                            String nombre = jsonObject.optString("nombre", "Desconocido");
                            String apellido = jsonObject.optString("apellido", "Desconocido");
                            String telefono = jsonObject.optString("telefono", "Desconocido");
                            String correo = jsonObject.optString("correo", "Desconocido");
                            int estatus = jsonObject.optInt("estatus", 0);

                            if (id == 0) {
                                card_lector.setVisibility(View.GONE);
                                Toast.makeText(RegistrarPrestamoActivity.this, "Lector no encontrado", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (estatus == 0) {
                                card_lector.setVisibility(View.GONE);
                                Toast.makeText(RegistrarPrestamoActivity.this, "Este lector no puede recibir prestamos", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            card_lector.setVisibility(View.VISIBLE);
                            // Asignar valores a los EditText
                            lbNombre.setText(nombre + " " + apellido);
                            lbTelefono.setText(telefono);
                            lbCorreo.setText(correo);


                        } else {
                            Toast.makeText(RegistrarPrestamoActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarPrestamoActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void ocultar_tarjetas (CardView card1, CardView card2) {
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
    }

    private void guardar (String isbn, int lector_id, int usuario_id) {
        String url = Utilidades.CREAR_PRESTAMO + "?isbn=" + isbn + "&lector_id=" + lector_id + "&usuario_id=" + usuario_id;
        url.replace("","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean status = response.optBoolean("status", false);
                        String mensaje = response.optString("mensaje", "Mensaje no disponible");
                        Toast.makeText(RegistrarPrestamoActivity.this, mensaje, Toast.LENGTH_LONG).show();
                        if (status) {
                            limpiar();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarPrestamoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void limpiar () {
        txtLectorId.setText("");
        txtISBN.setText("");
        card_lector.setVisibility(View.GONE);
        card_libro.setVisibility(View.GONE);
    }

}