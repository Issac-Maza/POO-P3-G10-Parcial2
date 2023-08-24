/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    static ObservableList<TerminoAcademico> termMostrar= FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardar(ActionEvent event) throws IOException{
        try{
            int anio=Integer.parseInt(txtAnio.getText());
            int numero=Integer.parseInt(txtNumero.getText());
            boolean bool=validarAnio(anio,numero);
            if(bool){
                TerminoAcademico t= new TerminoAcademico(anio,numero);
                if(!(termMostrar.contains(t))){
                    termMostrar.add(t);
                    Alert alert =new Alert(AlertType.INFORMATION);
                    alert.setTitle("Operación Exitosa");
                    alert.setContentText(null);
                    alert.setHeaderText("Se ha ingresado el Término correctamente");
                    alert.showAndWait();
                }else{
                    Alert alert =new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("El Término ya existe");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }else{
                Alert alert =new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se ha ingresado el Término");
                alert.setHeaderText("Valores no permitidos");
                alert.showAndWait();
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
            Alert alert =new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ingrese correctamente los datos solicitados");
            alert.setHeaderText("No se ha ingresado el Término");
            alert.showAndWait();
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
    
    static boolean validarAnio(int anio,int numero){
        Calendar fecha = Calendar.getInstance();
        if(anio>fecha.get(Calendar.YEAR))return false;
        if(!(numero>0 && numero<3)){return false;}
        if(anio==2023 && numero==2){return false;}
        if(anio<1958){return false;}
        return true;
    }
    
}
