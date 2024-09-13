package com.juan.sistema.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionEscena {
    public static void cargar(Stage stage, String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(GestionEscena.class.getResource(rutaFxml));
            Parent root = loader.load();

            Scene escena = new Scene(root, 800, 600);
            stage.setScene(escena);
            stage.setTitle(titulo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
