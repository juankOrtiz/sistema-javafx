package com.juan.sistema.controllers;

import com.juan.sistema.util.GestionEscena;
import com.juan.sistema.util.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {
    @FXML
    public Button botonUsuarios;

    @FXML
    public Button botonProductos;

    @FXML
    public Label labelBienvenida;

    @FXML
    private void initialize() {
        // Este metodo es el primero que se ejecuta en un controlador vinculado a una ventana
        SesionUsuario sesion = SesionUsuario.getInstance();
        String usuario = sesion.getUsuario();
        if(usuario != null) {
            labelBienvenida.setText("Bienvenido " + usuario);
        }
    }

    public void manejarCierre() {
        Stage stage = (Stage) botonProductos.getScene().getWindow();
        stage.close();
        // Deberia mostrar el login?
    }

    public void manejarProductos(ActionEvent actionEvent) {
        System.out.println("Abrir ventana productos");
    }

    public void manejarUsuarios(ActionEvent actionEvent) {
        Stage stage = (Stage) botonProductos.getScene().getWindow();
        GestionEscena.cargar(stage, "/com/juan/sistema/views/UsuarioGestion.fxml", "Gestion Usuarios");
    }
}
