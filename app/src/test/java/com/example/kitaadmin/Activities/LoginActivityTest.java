package com.example.kitaadmin.Activities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.kitaadmin.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    LoginActivity loginActivity;

    @Before
    public void configurar() {
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();
    }

    /**
     * No debería iniciar sesión y mostrar un error cuando el nombre de usuario esté vacío
     */
    @Test
    public void inicioSesionCuandoNombreUsuarioVacio() {
        loginActivity.setEtUsername("");
        loginActivity.setEtPassword("contraseña");

        assertFalse(loginActivity.validaUsuario());
        assertEquals(
                loginActivity.getResources().getString(R.string.usuarioVacio),
                loginActivity.etUsername.getError());
    }

    /**
     * No debería iniciar sesión y mostrar un error cuando la contraseña esté vacía
     */
    @Test
    public void inicioSesionCuandoContraseniaVacia() {
        loginActivity.setEtUsername("nombreUsuarioPrueba");
        loginActivity.setEtPassword("");
        assertFalse(loginActivity.validaContrasenia());
        assertEquals(
                loginActivity.getResources().getString(R.string.contraseniaVacia),
                loginActivity.etPassword.getError());
    }

    @Test
    public void validaUsuarioCuandoNombreUsuarioNoVacio() { // Establece un nombre
        loginActivity.setEtUsername("nombreUsuarioPrueba");

        // Llama a validaUsuario
        boolean resultado = loginActivity.validaUsuario();

        // Afirmar que el resultado es verdadero
        assertTrue(resultado, "Se espera que devuelva true si el usuario no está vacío");
    }

    @Test
    public void validaUsuarioCuandoNombreUsuarioVacio() {
        String nombreUsuarioVacio = "";
        loginActivity.setEtUsername(nombreUsuarioVacio);

        boolean resultado = loginActivity.validaUsuario();

        assertFalse(resultado, "Se espera que devuelva falso si está vacío");
        assertEquals(
                loginActivity.etUsername.getError(),
                loginActivity.getResources().getString(R.string.usuarioVacio),
                "Mensaje de error esperado");
    }
}