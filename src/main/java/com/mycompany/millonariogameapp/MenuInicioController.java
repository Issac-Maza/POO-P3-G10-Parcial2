/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class MenuInicioController implements Initializable {

    @FXML
    private Button btnConfiguraciones;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnJuego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void mostrarMenuConfiguraciones(ActionEvent event) throws IOException{
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void NuevoJuego(ActionEvent event) throws IOException{
        App.setRoot("nuevoJuego");
    }

    @FXML
    private void obtnerReporte(ActionEvent event) throws IOException{
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void salirDelAplicacion(ActionEvent event) throws IOException{
        // Crear un Alert de tipo INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gracias por usar esta aplicación");
        alert.setHeaderText(null);
        alert.setContentText("¡Gracias por utilizar esta aplicación!\n¡Hasta luego!");
        
        // Mostrar el Alert y esperar a que el usuario lo cierre
        alert.showAndWait();
        
        Platform.exit();
        System.exit(0);
    }
    
}
