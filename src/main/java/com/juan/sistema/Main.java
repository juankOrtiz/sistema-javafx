package com.juan.sistema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/juan/sistema/views/Login.fxml"));
        primaryStage.setTitle("Mi sistema");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
