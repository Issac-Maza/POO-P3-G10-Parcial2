/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class MenuAdministrarTerminosController implements Initializable {

    @FXML
    private Button btnIngresarTermino;
    @FXML
    private Button btnEditarTermino;
    @FXML
    private Button btnConfigurarTermino;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ingresarTermino(ActionEvent event) throws IOException {
        App.setRoot("menuIngresarTermino");
    }

    @FXML
    private void editarTermino(ActionEvent event) {
    }

    @FXML
    private void configurarTermino(ActionEvent event) {
    }

    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }
    
}
