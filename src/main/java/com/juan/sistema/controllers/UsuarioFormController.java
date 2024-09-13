package com.juan.sistema.controllers;

import com.juan.sistema.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class UsuarioFormController {
    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfEmail;

    private Usuario usuario;

    public void manejarGuardar() {
        // 1) Obtener los datos ingresados en los campos
        String nombre = tfUsuario.getText();
        String password = tfPassword.getText();
        String email = tfEmail.getText();

        // 2) Conectarnos a la DB
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/programacion3", "root", "")
        ) {
            // 3) Segmentar la logica en el caso de creacion o actualizacion
            if(usuario == null) {
                // 3.1) Si es creacion, ejecutamos un INSERT
                try(PreparedStatement pstm = conn.prepareStatement("INSERT INTO usuarios(nombre, password, email) VALUES (?, ?, ?)")) {
                    pstm.setString(1, nombre);
                    pstm.setString(2, com.juan.sistema.util.Usuario.hashPassword(password));
                    pstm.setString(3, email);
                    pstm.executeUpdate();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // 3.2) Si es actualizacion, ejecutamos un UPDATE
                try(PreparedStatement pstm = conn.prepareStatement("UPDATE usuarios SET password = ?, email = ? WHERE nombre = ?")) {
                    pstm.setString(1, com.juan.sistema.util.Usuario.hashPassword(password));
                    pstm.setString(2, email);
                    pstm.setString(3, nombre);
                    pstm.executeUpdate();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            // 4) Cerrar la ventana
            cerrarVentana();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfUsuario.getScene().getWindow();
        stage.close();
    }

    public void manejarCancelar() {
        cerrarVentana();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Si estamos actualizando un usuario existente...
        if(this.usuario != null) {
            tfUsuario.setText(this.usuario.getNombre());
            tfEmail.setText(this.usuario.getEmail());
            // tfPassword.setText(usuario.getPassword());
        }
    }
}
