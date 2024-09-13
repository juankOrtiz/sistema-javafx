package com.juan.sistema.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import com.juan.sistema.util.SesionUsuario;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private TextField tfUsuario;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    public void manejarLogin() {
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();

        if(autenticar(usuario, password)) {
            try {
                // Redirigir al Dashboard
                Parent root = FXMLLoader.load(getClass().getResource("/com/juan/sistema/views/Dashboard.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean autenticar(String usuario, String password) {
        // Conectarnos a la BD
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/programacion3", "root", "");
                PreparedStatement pstm = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?")
        ) {
            pstm.setString(1, usuario);
            ResultSet rs = pstm.executeQuery();

            if(rs.next()) {
                String passwordAlmacenado = rs.getString("password");
                String email = rs.getString("email");
                // Comprobar las contraseñas
                if(BCrypt.checkpw(password, passwordAlmacenado)) {
                    // Guardar datos en la sesion de usuario
                    SesionUsuario sesion = SesionUsuario.getInstance();
                    sesion.setUsuario(usuario);
                    sesion.setEmail(email);
                    return true;
                } else {
                    // No coiciden las contraseñas
                    mostrarError("Las credenciales son inválidas");
                }
            } else {
                // No existe el nombre de usuario
                mostrarError("El usuario no existe");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de login");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
