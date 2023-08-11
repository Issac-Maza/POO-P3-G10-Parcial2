/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.mycompany.millonariogameapp.modelo.*;
/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuAddQuestionsController implements Initializable {


    @FXML
    private ComboBox<Materia> materiaCMB;
    @FXML
    private TextField pregunta;
    @FXML
    private TextField nivelPreg;
    @FXML
    private TextField rCorrecta;
    @FXML
    private TextField rIncorrecta_1;
    @FXML
    private TextField rIncorrecta_2;
    @FXML
    private TextField rIncorrecta_3;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
