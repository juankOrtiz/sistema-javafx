module com.juan.sistema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.juan.sistema to javafx.fxml;
    exports com.juan.sistema;

    opens com.juan.sistema.controllers to javafx.fxml;
    exports com.juan.sistema.controllers;

    opens com.juan.sistema.models to javafx.fxml;
    exports com.juan.sistema.models;
}