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
public class MenuAdministrarPreguntaController implements Initializable {

    @FXML
    private Button btnVizulizarPreguntas;
    @FXML
    private Button btnAddQuestions;
    @FXML
    private Button btnDeleteQuestion;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    //Metodo que muestra el menu ver preguntas
    @FXML
    private void viewQuestions(ActionEvent event) throws IOException {
        App.setRoot("viewQuestions"); 
    }

    //Metodo que muestra el menu añadir pregunta
    @FXML
    private void addQuestions(ActionEvent event) throws IOException {
        App.setRoot("addQuestions");
    }

    //Metodo que muestra el menu eliminar pregunta
    @FXML
    private void deleteQuestions(ActionEvent event) throws IOException{
        App.setRoot("deleteQuestion"); 
    }

    //Metodo regresa al menu anterior
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }
    
}
