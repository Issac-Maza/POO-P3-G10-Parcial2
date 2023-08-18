/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.millonariogameapp;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuIngresarTerminoController implements Initializable {


    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtNumero;
    @FXML
    private Button botonGuardar;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonVolver;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardar(ActionEvent event) throws IOException{
        try{
            int anio=Integer.parseInt(txtAnio.getText());
            int numero=Integer.parseInt(txtNumero.getText());
            Alert alert =new Alert(AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setContentText("Término Guardado");
            alert.setHeaderText("Se ha guardado el Término ingresado");
        }catch(NumberFormatException e){
            Alert alert =new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ingrese correctamente los datos solicitados");
            alert.setHeaderText("No se ha ingresado el Término");
        }
    }

    @FXML
    private void borrar(ActionEvent event) throws IOException{
        txtNumero.clear();
        txtAnio.clear();
    }

    @FXML
    private void volver(ActionEvent event) throws IOException{
        App.setRoot("menuAdministrarTerminos");
    }
}
