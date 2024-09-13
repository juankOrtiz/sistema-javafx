package com.juan.sistema.controllers;

import com.juan.sistema.models.Usuario;
import com.juan.sistema.util.GestionEscena;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class UsuarioGestionController {
    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, String> columnaUsuarios;

    @FXML
    private TableColumn<Usuario, String> columnaPassword;

    @FXML
    private TableColumn<Usuario, String> columnaEmail;

    @FXML
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    private Button btnVolverAlDashboard;

    @FXML
    private void initialize() {
        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tablaUsuarios.setItems(listaUsuarios);
        cargarUsuarios();
    }

    // Obtener la lista de usuarios de la BD y modificar listaUsuarios
    private void cargarUsuarios() {
        listaUsuarios.clear();
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/programacion3", "root", "");
                PreparedStatement pstm = conn.prepareStatement("SELECT * FROM usuarios")
                ) {
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                listaUsuarios.add(new Usuario(rs.getString("nombre"), rs.getString("password"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void manejarVolverAlDashboard(ActionEvent actionEvent) {
        Stage stage = (Stage) btnVolverAlDashboard.getScene().getWindow();
        GestionEscena.cargar(stage, "/com/juan/sistema/views/Dashboard.fxml", "Mi sistema");
    }

    public void manejarCrear(ActionEvent actionEvent) {
        abrirFormUsuario(null);
    }

    public void manejarActualizar(ActionEvent actionEvent) {

    }

    private void abrirFormUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/juan/sistema/views/UsuarioForm.fxml"));
            Parent root = loader.load();

            UsuarioFormController controlador = loader.getController();
            controlador.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setTitle(usuario == null ? "Crear usuario" : "Actualizar usuario");
            stage.setScene(new Scene(root, 300, 250));
            stage.show();
            // Refrescar la lista de usuarios cuando se cierre el formulario
            stage.setOnHidden(e -> cargarUsuarios());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manejarEliminar(ActionEvent actionEvent) {
        Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if(usuarioSeleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Seleccion incorrecta");
            alerta.setContentText("Por favor selecciona un usuario para eliminar.");
            alerta.showAndWait();
            return;
        }
        // Si seleccionaste un usuario, proseguimos con el dialogo de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estas seguro que deseas eliminar este usuario?");
        confirmacion.setContentText("Esta acción no se puede revertir.");

        ButtonType btnConfirmacion = new ButtonType("Eliminar");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btnConfirmacion, btnCancelar);

        if(confirmacion.showAndWait().orElse(btnCancelar) == btnConfirmacion) {
            // Procedemos a eliminar el usuario
            eliminarUsuario(usuarioSeleccionado);
        }
    }

    private void eliminarUsuario(Usuario usuario) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/programacion3", "root", "");
                PreparedStatement pstm = conn.prepareStatement("DELETE FROM usuarios WHERE nombre = ?")
                ) {
            pstm.setString(1, usuario.getNombre());
            pstm.executeUpdate();
            // opcional, comprobar cantidad de filas afectadas
            listaUsuarios.remove(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
