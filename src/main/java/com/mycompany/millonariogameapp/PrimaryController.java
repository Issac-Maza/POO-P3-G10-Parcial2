package com.mycompany.millonariogameapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button configuraciones;
    @FXML
    private Button nuevoJuego;
    @FXML
    private Button reporte;
    @FXML
    private Button salir;

    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
