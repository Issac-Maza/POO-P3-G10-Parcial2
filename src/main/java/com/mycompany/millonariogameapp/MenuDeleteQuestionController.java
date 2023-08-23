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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;


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
    
    Materia materia = new Materia("","",0);

    /**
     * Initializes the controller class.
     */

    public void initialize() {
        try{
            importarMaterias();
        }
        catch(Exception e){
            System.out.println("ERROR");
        }
        
    }    
    
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
    
    public Materia buscarMateria() {
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
    
    public ArrayList<Pregunta> preguntas() {
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        materia = buscarMateria();
        
        for(ArrayList<Pregunta> lst: materia.getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                preguntas.add(p);
            }
        }
        return preguntas;
    }
    
    @FXML
    public void mostrarPreguntas() {
        ArrayList<Pregunta> lstpreguntas = preguntas();
        
        for(Pregunta p: lstpreguntas){
            CheckBox check = new CheckBox(p.getEnunciado());
            preguntasVB.getChildren().add(check);
        }
        preguntasVB.setSpacing(10);
    }
    
    @FXML
    public void eliminarPreguntas() {
        for(int i = 0; i<preguntas().size(); i++){
            CheckBox check = (CheckBox)preguntasVB.getChildren().get(i);
            
            if(check.isSelected()){
                int pos = 0;
                for(ArrayList<Pregunta> lst: materia.getLstOrdenadasxNivel()){
                    if(lst.contains(preguntas().get(i))){
                        materia.getLstOrdenadasxNivel().get(pos).remove(preguntas().get(i));
                    }
                    pos++;
                }
            }
        }
        preguntasVB.getChildren().clear();
        mostrarPreguntas();
    }
    
    
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
}
