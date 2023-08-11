/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.*;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuViewQuestionsController implements Initializable {

    @FXML
    private TextField nomMateria;
    @FXML
    private VBox materiasVB;
    @FXML
    private VBox preguntasVB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
           mostrarMaterias(); 
        }
        catch(Exception e){
            System.out.println("ERROR");
        }
        
    }    
    
    public void mostrarMaterias() throws Exception{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.txt"));){
            int cont = 1;
            Materia m;
            while((m = (Materia) in.readObject()) != null){
                materiasVB.getChildren().add(new Label(cont+". "+m.getNombre()));
                cont++;
            }
            materiasVB.getChildren().add(new Label("Buenas"));
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
    }
    
    @FXML
    public void mostrarPreguntas() throws Exception{
        String nombre = nomMateria.getText().trim().toLowerCase();
        boolean condicion = false;
        int numPreg = 0;
        int numNivel = 0;
        Materia matVerdadera = new Materia("","",0);
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.txt"));){
            Materia m;
            while((m = (Materia) in.readObject()) != null){
                if(m.getNombre().equalsIgnoreCase(nombre)){
                    condicion = true;
                    matVerdadera = m;
                }
            }
            
            if(condicion){
                    for(ArrayList<Pregunta> lista: matVerdadera.getLstOrdenadasxNivel()){
                        preguntasVB.getChildren().add(new Label("NIVEL "+(numNivel+1)));
                        for(Pregunta pregunta: lista){
                            preguntasVB.getChildren().add(new Label("Pregunta n°"+ (numPreg+1)+ " " + pregunta.getEnunciado()));
                            numPreg++;
                        }
                        numNivel++;
                    }
            }
            else{
                mostrarAlerta(Alert.AlertType.ERROR, "ERROR. Materia Ingresada no valida. Intente de Nuevo.");
                nomMateria.setText(null);
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
    }
    
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
}
