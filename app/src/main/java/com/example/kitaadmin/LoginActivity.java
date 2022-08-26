package com.example.kitaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    private static String rolUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciaListeners();
        onClickListeners();
    }


    private void onClickListeners() {
        btnLogin.setOnClickListener(v -> {
            if (validaUsuario() && validaContrasenia()) {
                Usuarios usuario = new Usuarios(etUsername.getText().toString(), etPassword.getText().toString());

                ApiService apiService = Network.getInstance().create(ApiService.class);
                apiService.getUsuario(usuario).enqueue(new Callback<Usuarios>() {
                    @Override
                    public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                        if (response.body() != null) {
                            rolUsuario = response.body().getRol();
                            Toast.makeText(LoginActivity.this, "¡Bienvenido/a!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("rol", rolUsuario);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuarios> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña no válidos", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }


    public static String getRol() {
        return rolUsuario;
    }

    //Valida la entrada del campo usuario
    private boolean validaUsuario() {
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError("El usuario no puede estar vacío");
            etUsername.requestFocus();
            return false;
        }
        return true;
    }

    //Valida la entrada del campo contraseña
    private boolean validaContrasenia() {
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("La contraseña no puede estar vacía");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void iniciaListeners() {
        etUsername = findViewById(R.id.loginUser);
        etPassword = findViewById(R.id.loginPass);
        btnLogin = findViewById(R.id.buttonLogin);
    }
}