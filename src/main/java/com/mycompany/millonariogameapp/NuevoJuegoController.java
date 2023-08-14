/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class NuevoJuegoController implements Initializable {

    @FXML
    private Button opcionA;
    @FXML
    private Button opcionC;
    @FXML
    private Button opcionB;
    @FXML
    private Button opcionD;
    @FXML
    private Label pregunta;
    @FXML
    private VBox preguntasVB;
    
    private Juego juego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void deserializarJuego() throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/juegos.ser"));
        Juego j;
        while((j = (Juego)in.readObject()) != null){
            juego = j;
        }
    }
    
    public void rellenarPreguntasVB(){
        int i = 1;
        for(ArrayList<Pregunta> lst: juego.getMateria().getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                preguntasVB.getChildren().add(new Label("Preguntas "+i));
                i++;
            }
        }
    }

}
