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
public class MenuDeleteQuestionController implements Initializable {

    @FXML
    private ComboBox<String> materiaCMB;
    @FXML
    private VBox preguntasVB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            importarMaterias();
        }
        catch(Exception e){
            System.out.println("ERROR");
        }
        
    }    
    
    public void importarMaterias() throws Exception{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))){
            Materia m;
            while((m = (Materia)in.readObject()) != null){
                materiaCMB.getItems().add(m.getNombre());
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
    }
    
    public Materia buscarMateria() throws Exception{
        Materia mVerdadera = new Materia("","",0);
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))){
            Materia m;
            while((m = (Materia)in.readObject()) != null){
                if(m.getNombre().equalsIgnoreCase((String)materiaCMB.getValue())){
                    mVerdadera = m;
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
        
        return mVerdadera;  
    }
    
    public ArrayList<Pregunta> preguntas() throws Exception{
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        Materia materia = buscarMateria();
        
        for(ArrayList<Pregunta> lst: materia.getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                preguntas.add(p);
            }
        }
        return preguntas;
    }
    
    @FXML
    public void mostrarPreguntas() throws Exception{
        ArrayList<Pregunta> lstpreguntas = preguntas();
        
        for(Pregunta p: lstpreguntas){
            CheckBox check = new CheckBox(p.getEnunciado());
            preguntasVB.getChildren().add(check);
        }
    }
    
    @FXML
    public void eliminarPreguntas() throws Exception{
        for(int i = 0; i<preguntas().size(); i++){
            CheckBox check = (CheckBox)preguntasVB.getChildren().get(i);
            
            if(check.isSelected()){
                int pos = 0;
                for(ArrayList<Pregunta> lst: buscarMateria().getLstOrdenadasxNivel()){
                    if(lst.contains(preguntas().get(i))){
                        buscarMateria().getLstOrdenadasxNivel().get(pos).remove(preguntas().get(i));
                    }
                    pos++;
                }
            }
        }
    }
    
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
}
