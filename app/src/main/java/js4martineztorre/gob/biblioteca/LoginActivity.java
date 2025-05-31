package js4martineztorre.gob.biblioteca;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, txtPass;
    private Button btnIniciarSesion;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Inicializar Volley
        request = Volley.newRequestQueue(getApplicationContext());

        // Relacionar vista con el codigo
        txtUsername = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPass);
        // Relacionar boton
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);


        btnIniciarSesion.setOnClickListener(v -> {

            String username = txtUsername.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (username.isEmpty() && pass.isEmpty()) {
                Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_LONG).show();
                return;
            }

            if (username.length() < 3) {
                Toast.makeText(this, "El Nombre de usuario debe tener por lo menos 3 caracteres...", Toast.LENGTH_LONG).show();
                return;
            }

            if (pass.length() < 5) {
                Toast.makeText(this, "La Contraseña debe tener por lo menos 5 caracteres...", Toast.LENGTH_LONG).show();
                return;
            }

            iniciarSesion(username, pass);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void iniciarSesion (String username, String pass) {
        String url = Utilidades.LOGIN + "?username=" + username + "&pass=\"" + pass + "\"";
        url.replace("","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        boolean status = response.optBoolean("status", false);
                        String mensaje = response.optString("mensaje", "Mensaje no disponible");

                        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();

                        if (status) {

                            JSONObject usuario = response.optJSONObject("usuario");

                            int id = usuario.optInt("id", 0);
                            int role_id = usuario.optInt("role_id", 0);

                            Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                            home.putExtra("id", id);
                            home.putExtra("role_id", role_id);
                            startActivity(home);

                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
}