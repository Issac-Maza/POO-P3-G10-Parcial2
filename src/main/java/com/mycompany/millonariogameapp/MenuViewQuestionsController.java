/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.*;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuViewQuestionsController implements Serializable {

    @FXML
    private VBox preguntasVB;
    @FXML
    private ComboBox<String> materiaCMB;

    /**
     * Initializes the controller class.
     */

    //Metodo que inicializa el metodo
    public void initialize(){
        importarMaterias(); 
    }    
    
    //Metodo que almacena el cmb el nombre de las materias
    public void importarMaterias() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))) {
            ArrayList<Materia> lstMaterias = (ArrayList<Materia>)in.readObject();
            for(Materia m: lstMaterias){
                materiaCMB.getItems().add(m.getNombre());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
    //Metodo que muestra por pantalla las preguntas de la materia
    @FXML
    public void mostrarPreguntas(){
        int numPreg = 0;
        int numNivel = 0;
        Materia matVerdadera = buscarMateria();
        
        for(ArrayList<Pregunta> lista: matVerdadera.getLstOrdenadasxNivel()){
            preguntasVB.getChildren().add(new Label("NIVEL "+(numNivel+1)));
            for(Pregunta pregunta: lista){
                preguntasVB.getChildren().add(new Label("Pregunta n°"+ (numPreg+1)+ " " + pregunta.getEnunciado()));
                numPreg++;
            }
            numNivel++;
            preguntasVB.getChildren().add(new Label(""));
        }
    }
    
    //Metodo que muestra una alerta
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    //Metodo que busca la materia seleccionada del cmb
    public Materia buscarMateria(){
        Materia mVerdadera = new Materia("","",0);        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))) {
            ArrayList<Materia> lstMaterias = (ArrayList<Materia>)in.readObject();
            for(Materia m: lstMaterias){
                if(m.getNombre().equalsIgnoreCase((String)materiaCMB.getValue())){
                    mVerdadera = m;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return mVerdadera;  
    }
    
    //Metodo que regresa al menu anterior
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
}
