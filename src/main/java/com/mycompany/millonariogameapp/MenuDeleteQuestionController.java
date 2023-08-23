/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuDeleteQuestionController implements Serializable {

    @FXML
    private ComboBox<String> materiaCMB;
    @FXML
    private VBox preguntasVB;
    
    private ComboBox<String> combo;
    private ArrayList<Materia> lstMaterias; 
    private int posMateria;
    private ArrayList<Pregunta> lstPreguntas;

    /**
     * Initializes the controller class.
     */

    public void initialize() {
        importarMaterias();
    }    
    
    public void importarMaterias() {
        lstMaterias = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))) {
            lstMaterias = (ArrayList<Materia>)in.readObject();
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
    
    public void buscarMateria() {   
        int cont = 0;
        for(Materia m: lstMaterias){
            if(m.getNombre().equalsIgnoreCase((String)materiaCMB.getValue())) posMateria = cont;
            cont++;
        }
    }
    
    public void preguntas() {
        lstPreguntas = new ArrayList<>();
        for(ArrayList<Pregunta> lst: lstMaterias.get(posMateria).getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                lstPreguntas.add(p);
            }
        }
    }
    
    @FXML
    public void mostrarPreguntas() {
        buscarMateria();
        preguntas();
        combo = new ComboBox();
        Button b = new Button("Eliminar");
        int cont = 1;
        
        for(Pregunta p: lstPreguntas){
            preguntasVB.getChildren().add(new Label(cont+". "+p.getEnunciado()));
            combo.getItems().add(""+cont);
            cont++;
        }
        b.setOnAction(eh -> {
            eliminarPregunta();
        });
        
        HBox contenedor = new HBox(10);
        contenedor.getChildren().add(combo);
        contenedor.getChildren().add(b);
        preguntasVB.getChildren().add(contenedor);
        preguntasVB.setSpacing(10);
    }
    
    public void eliminarPregunta() {
        int posNivel = 0;
        int numSeleccionado = Integer.parseInt(combo.getValue());
        int numPregActual = 1;
        for(ArrayList<Pregunta> lst: lstMaterias.get(posMateria).getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                if(numPregActual == numSeleccionado){
                    lstMaterias.get(posMateria).getLstOrdenadasxNivel().get(posNivel).remove(p);
                } else{
                    numPregActual++;
                }
            }
            posNivel++;
        }
        actualizarMateria();
        importarMaterias();
        preguntasVB.getChildren().clear();
    }
    
    public void actualizarMateria(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/materias.ser"))){
            out.writeObject(lstMaterias);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    public void regresarMenuAnterior() throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
}
