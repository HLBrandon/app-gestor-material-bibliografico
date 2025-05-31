package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ModificarLibroActivity extends AppCompatActivity {

    private Button btnVolver, btnCrear;
    private ImageButton btnSc;

    private EditText txtISBN, txtTitulo, txtAutor, txtEditorial, txtAnioPublicacion, txtCat, txtStoke;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String [] opciones_categorias;
    private String [] opciones_autores;
    private String [] opciones_editoriales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar_libro);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar datos de las cat, autores y edit
        int libro_id = getIntent().getIntExtra("libro_id", 0);
        if (libro_id > 0) {
            cargar_datos(libro_id);
        }

        listar_categorias();
        listar_autores();
        listar_editoriales();

        // Relacionar botones
        btnVolver = findViewById(R.id.btnVolver);
        btnSc = findViewById(R.id.btnSc);
        btnCrear = findViewById(R.id.btnCrear);

        // Relacionar txt
        txtISBN = findViewById(R.id.txtISBN);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtAnioPublicacion = findViewById(R.id.txtAnioPublicacion);
        txtCat = findViewById(R.id.txtCat);
        txtStoke = findViewById(R.id.txtStoke);

        // Eventos Click
        btnVolver.setOnClickListener(v -> {
            finish();
        });

        btnSc.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(ModificarLibroActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });

        txtCat.setOnClickListener(v -> {
            alertar_opciones(opciones_categorias, txtCat);
        });

        txtAutor.setOnClickListener(v -> {
            alertar_opciones(opciones_autores, txtAutor);
        });

        txtEditorial.setOnClickListener(v -> {
            alertar_opciones(opciones_editoriales, txtEditorial);
        });

        txtAnioPublicacion.setOnClickListener(v -> {
            alerta_anio(txtAnioPublicacion);
        });

        btnCrear.setOnClickListener(v -> {

            String isbn = txtISBN.getText().toString().trim();
            String titulo = txtTitulo.getText().toString().trim();
            String autor = txtAutor.getText().toString().trim();
            String edit = txtEditorial.getText().toString().trim();
            String anio = txtAnioPublicacion.getText().toString().trim();
            String cat = txtCat.getText().toString().trim();
            String stoke = txtStoke.getText().toString().trim();

            if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() ||
                    edit.isEmpty() || anio.isEmpty() || cat.isEmpty() || stoke.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(stoke) == 0) {
                Toast.makeText(this, "Debe haber por lo menos un libro en existencia.", Toast.LENGTH_SHORT).show();
                return;
            }

            modificar_libro(isbn, titulo, autor, edit, anio, cat, stoke, libro_id);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargar_datos (int id) {
        String url = Utilidades.VER_LIBRO + "?id=" + id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obtener el objeto "editorial"
                        JSONObject jsonObject = response.optJSONObject("libro");

                        if (jsonObject != null) {

                            String titulo = jsonObject.optString("titulo", "Desconocido");
                            String isbn = jsonObject.optString("isbn", "Desconocido");
                            String autor_nombre = jsonObject.optString("autor_nombre", "Desconocido");
                            String anio_publicacion = jsonObject.optString("anio_publicacion", "Desconocido");
                            String editorial_nombre = jsonObject.optString("editorial_nombre", "Desconocido");
                            String categoria_nombre = jsonObject.optString("categoria_nombre", "Desconocido");
                            int existencias = jsonObject.optInt("stoke", 0);


                            // Asignar valores a los EditText
                            txtTitulo.setText(titulo);
                            txtISBN.setText(isbn);
                            txtAutor.setText(autor_nombre);
                            txtAnioPublicacion.setText(anio_publicacion);
                            txtEditorial.setText(editorial_nombre);
                            txtCat.setText(categoria_nombre);
                            txtStoke.setText("" + existencias);

                        } else {
                            Toast.makeText(ModificarLibroActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarLibroActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        request.add(jsonObjectRequest);
    }

    private void modificar_libro (String isbn, String titulo, String autor, String edit, String anio, String cat, String stoke, int id) {

        String url = Utilidades.ACTUALIZAR_LIBRO + "?isbn="+isbn+"&titulo=\"" + titulo + "\"&autor=\"" + autor + "\"&edit=\"" + edit + "\"&anio=" + anio + "&cat=\"" + cat + "\"&stoke=" + stoke + "&id=" + id;
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ModificarLibroActivity.this, "Modificado con exito", Toast.LENGTH_LONG).show();
                        limpiar();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarLibroActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Escaneo exitoso", Toast.LENGTH_LONG).show();
                txtISBN.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void listar_categorias () {
        String url = Utilidades.LISTAR_CATEGORIAS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("categorias");

                        ArrayList<String> nombresCategorias = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");
                                    nombresCategorias.add(nombre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // Convertir ArrayList en String[]
                        opciones_categorias = nombresCategorias.toArray(new String[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarLibroActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void listar_autores () {
        String url = Utilidades.LISTAR_AUTORES;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray = response.optJSONArray("autores");

                        ArrayList<String> campo = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");
                                    campo.add(nombre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // Convertir ArrayList en String[]
                        opciones_autores = campo.toArray(new String[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarLibroActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void listar_editoriales () {
        String url = Utilidades.LISTAR_EDITORIALES;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray = response.optJSONArray("editoriales");

                        ArrayList<String> campo = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String nombre = jsonObject.optString("nombre", "Desconocido");
                                    campo.add(nombre);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // Convertir ArrayList en String[]
                        opciones_editoriales = campo.toArray(new String[0]);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModificarLibroActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void alertar_opciones (String [] opciones, EditText txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar opción:");
        builder.setItems(opciones, (dialog, which) -> {
            txt.setText(opciones[which]);
        });
        builder.show();
    }

    private void alerta_anio(EditText textView) {

        int anioActual = 2000;

        // Crear un NumberPicker en lugar de DatePicker
        NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(0); // Rango de años (100 años atrás)
        numberPicker.setMaxValue(9999);  // Rango de años (10 años adelante)
        numberPicker.setValue(anioActual); // Año predeterminado

        // Crear un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(numberPicker);
        builder.setTitle("Seleccionar Año");

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            // Guardar el año seleccionado en el EditText
            textView.setText(String.valueOf(numberPicker.getValue()));
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void limpiar() {
        txtISBN.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAnioPublicacion.setText("");
        txtCat.setText("");
        txtStoke.setText("");
    }

}