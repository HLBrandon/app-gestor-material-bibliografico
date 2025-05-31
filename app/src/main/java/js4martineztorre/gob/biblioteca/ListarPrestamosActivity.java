package js4martineztorre.gob.biblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import js4martineztorre.gob.biblioteca.model.LibroAdapter;
import js4martineztorre.gob.biblioteca.model.Prestamo;
import js4martineztorre.gob.biblioteca.model.PrestamoAdapter;
import js4martineztorre.gob.biblioteca.model.Utilidades;

public class ListarPrestamosActivity extends AppCompatActivity {

    private ImageButton btnVolver, btnSc;
    private EditText txtBuscar;

    private RecyclerView prestamoRecyclerView;
    private PrestamoAdapter prestamoAdapter;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    // Reload Recycler View
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_prestamos);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Inicializar todo lo relacionado con el recyclerView
        prestamoRecyclerView = findViewById(R.id.recyclerViewPrestamos);
        prestamoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prestamoAdapter = new PrestamoAdapter();
        prestamoRecyclerView.setAdapter(prestamoAdapter);

        btnVolver = findViewById(R.id.btnVolver);
        btnSc = findViewById(R.id.btnSc);

        txtBuscar = findViewById(R.id.txtBuscar);

        // Relacionar el reload
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        listar_prestamos();

        btnVolver.setOnClickListener(v -> finish());

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String buscar = txtBuscar.getText().toString().trim();

                if (buscar.equals("")) {
                    listar_prestamos();
                    return;
                }

                buscar_prestamo(buscar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSc.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(ListarPrestamosActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });

        // Hacer el reload
        swipeRefreshLayout.setOnRefreshListener(() -> {
            String buscar = txtBuscar.getText().toString().trim();

            if (!buscar.equals("")) {
                buscar_prestamo(buscar);
                swipeRefreshLayout.setRefreshing(false);
                return;
            }

            listar_prestamos();
            swipeRefreshLayout.setRefreshing(false);

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void listar_prestamos () {
        String url = Utilidades.LISTAR_PRESTAMOS;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("prestamos");

                        List<Prestamo> prestamos = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("id", 0);
                                    String titulo = jsonObject.optString("libro_titulo", "Desconocido");
                                    String isbn = jsonObject.optString("libro_isbn", "Desconocido");
                                    String nombre = jsonObject.optString("lector_nombre", "Desconocido");
                                    String apellido = jsonObject.optString("lector_apellido", "Desconocido");
                                    String correo = jsonObject.optString("lector_correo", "Desconocido");
                                    Boolean retraso = jsonObject.optBoolean("retraso", false);

                                    Prestamo prestamo = new Prestamo(id, nombre, apellido, correo, titulo, isbn, retraso);
                                    prestamos.add(prestamo);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            prestamoAdapter.setPrestamos(prestamos);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarPrestamosActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }

    private void buscar_prestamo (String buscar) {
        String url = Utilidades.BUSCAR_PRESTAMO + "?lector_id=" + buscar;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = response.optJSONArray("prestamos");

                        List<Prestamo> prestamos = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    int id = jsonObject.optInt("id", 0);
                                    String titulo = jsonObject.optString("libro_titulo", "Desconocido");
                                    String isbn = jsonObject.optString("libro_isbn", "Desconocido");
                                    String nombre = jsonObject.optString("lector_nombre", "Desconocido");
                                    String apellido = jsonObject.optString("lector_apellido", "Desconocido");
                                    String correo = jsonObject.optString("lector_correo", "Desconocido");
                                    Boolean retraso = jsonObject.optBoolean("retraso", false);

                                    Prestamo prestamo = new Prestamo(id, nombre, apellido, correo, titulo, isbn, retraso);
                                    prestamos.add(prestamo);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            prestamoAdapter.setPrestamos(prestamos);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarPrestamosActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
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
                txtBuscar.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}