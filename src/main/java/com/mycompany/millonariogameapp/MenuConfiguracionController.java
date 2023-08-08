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
public class MenuConfiguracionController implements Initializable {

    @FXML
    private Button btnadmTermino;
    @FXML
    private Button btnAdmMateriaYParalelo;
    @FXML
    private Button btnAdmPregunta;
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
    private void mostrarMenuTermino(ActionEvent event) throws IOException{
        App.setRoot("menuAdministrarTerminos");
    }

    @FXML
    private void mostrarMenuMateriaYParelo(ActionEvent event) throws IOException{
        App.setRoot("menuMateriaYParalelo");
    }

    @FXML
    private void mostrarMenuPregunta(ActionEvent event) throws IOException{
        App.setRoot("menuAdministrarPregunta");
    }

    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException{
        App.setRoot("menuInicio");
    }
    
}
