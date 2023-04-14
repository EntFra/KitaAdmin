package com.example.kitaadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity login, permite al usuario realizar login en la app en caso de poseer credenciales, en caso contrario se informará con un mensaje
 */
public class LoginActivity extends AppCompatActivity {
    static Usuarios usuarioLogin;
    static String rolUsuario;
    TextInputLayout etPassword, etUsername;
    Button btnLogin;

    public static String getRol() {
        return rolUsuario;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciaListeners();
        onClickListeners();


    }
    //Método que devuelve el usuario logueado
    public static Usuarios getUsuario() {
        return usuarioLogin;
    }

    void onClickListeners() {
        btnLogin.setOnClickListener(v -> {
            login();
        });
    }
    //Método que realiza el login
    public void login() {
        if (validaUsuario() && validaContrasenia()) {
            Usuarios usuario = new Usuarios(etUsername.getEditText().getText().toString(), etPassword.getEditText().getText().toString());

            ApiService apiService = Network.getInstance().create(ApiService.class);
            apiService.getUsuario(usuario).enqueue(new Callback<Usuarios>() {
                @Override
                public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                    if (response.body() != null) {
                        usuarioLogin = response.body();
                        rolUsuario = usuarioLogin.getRol();
                        Toast.makeText(LoginActivity.this, "¡Bienvenido/a!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("rol", rolUsuario);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, R.string.usuarioNoValido, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuarios> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, R.string.usuarioNoValido, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    //Valida la entrada del campo usuario
    boolean validaUsuario() {
        if (TextUtils.isEmpty(etUsername.getEditText().getText().toString())) {
            etUsername.setError(getResources().getString(R.string.usuarioVacio));
            etUsername.requestFocus();
            return false;
        }
        return true;
    }

    //Valida la entrada del campo contraseña
    boolean validaContrasenia() {
        if (TextUtils.isEmpty(etPassword.getEditText().getText().toString())) {
            etPassword.setError(getResources().getString(R.string.contraseniaVacia));
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

    public void setEtPassword(String password) {
        etPassword.getEditText().setText(password);
    }

    public void setEtUsername(String username) {
        etUsername.getEditText().setText(username);
    }

}