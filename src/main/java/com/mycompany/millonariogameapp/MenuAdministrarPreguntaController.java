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

    @FXML
    private void viewQuestions(ActionEvent event) {
        try{
          App.setRoot("menuViewQuestions");  
        }
        catch(IOException io){
            System.out.println("ERROR");
        } 
    }

    @FXML
    private void addQuestions(ActionEvent event) {
    }

    @FXML
    private void deleteQuestions(ActionEvent event) {
    }

    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuConfiguracion");
    }
    
}
