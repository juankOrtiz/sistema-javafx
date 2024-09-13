package com.juan.sistema.controllers;

import com.juan.sistema.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioFormController {
    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfEmail;

    private Usuario usuario;

    public void manejarGuardar(ActionEvent actionEvent) {
        // 1) Obtener los datos ingresados en los campos
        // 2) Conectarnos a la DB
        // 3) Segmentar la logica en el caso de creacion o actualizacion
        // 3.1) Si es creacion, ejecutamos un INSERT
        // 3.2) Si es actualizacion, ejecutamos un UPDATE
        // 4) Cerrar la ventana
    }

    public void manejarCancelar(ActionEvent actionEvent) {
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Si estamos actualizando un usuario existente...
        if(usuario != null) {
            tfUsuario.setText(usuario.getNombre());
            tfEmail.setText(usuario.getEmail());
        }
    }
}
