package com.example.kitaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Response.ResponseClass;
import com.example.kitaadmin.Response.ResponseRegisterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
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
                ResponseRegisterClass responseRegisterClass = new ResponseRegisterClass(etUsername.getText().toString(), etPassword.getText().toString());

                ApiService apiService = Network.getInstance().create(ApiService.class);
                apiService.getUsuario(responseRegisterClass).enqueue(new Callback<ResponseClass>() {
                    @Override
                    public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                        if (response.body() != null) {
                            Toast.makeText(LoginActivity.this, "¡Bienvenido/a!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("rol", response.body().getRol());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseClass> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña no válidos", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
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