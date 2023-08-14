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
public class MenuMateriaYParaleloController implements Initializable {

    @FXML
    private Button btnIngresarMateria;
    @FXML
    private Button btnEditarMateria;
    @FXML
    private Button btnAddParalelo;
    @FXML
    private Button btnDeleteParalelo;
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
    private void ingresarMateria(ActionEvent event) throws IOException {
        App.setRoot("ingresarMateria");
    }

    @FXML
    private void editarMateria(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void addParalelo(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void deleteParalelo(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }
    
}
