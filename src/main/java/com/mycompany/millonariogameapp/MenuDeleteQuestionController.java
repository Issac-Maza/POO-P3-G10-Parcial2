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
import java.util.Iterator;
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
    
    //Atributos necesarios para la clase
    private ComboBox<String> combo;
    private ArrayList<Materia> lstMaterias; 
    private int posMateria;
    private ArrayList<Pregunta> lstPreguntas;

    /**
     * Initializes the controller class.
     */

    //Metodo que inicializa el metodo
    public void initialize() {
        importarMaterias();
    }    
    
    //Metodo que llena el CMB de materias y deserializa la lista de materias
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
    
    //Metodo que encuentra la posicion de la materia seleccionada del CMB en la lista
    public void buscarMateria() {   
        int cont = 0;
        for(Materia m: lstMaterias){
            if(m.getNombre().equalsIgnoreCase((String)materiaCMB.getValue())) posMateria = cont;
            cont++;
        }
    }
    
    //Metodo que almacena las preguntas de la materia seleccionada para luego mostrar
    public void preguntas() {
        lstPreguntas = new ArrayList<>();
        for(ArrayList<Pregunta> lst: lstMaterias.get(posMateria).getLstOrdenadasxNivel()){
            for(Pregunta p: lst){
                lstPreguntas.add(p);
            }
        }
    }
    
    //Metodo que muestra las preguntas 
    @FXML
    public void mostrarPreguntas() {
        preguntasVB.getChildren().clear();
        buscarMateria();
        preguntas();
        
        combo = new ComboBox(); //Creacion de un CMB
        Button b = new Button("Eliminar"); //Creacion del boton eliminar
        int cont = 1;
        
        //For que muestra las preguntas en la pantalla
        for(Pregunta p: lstPreguntas){
            preguntasVB.getChildren().add(new Label(cont+". "+p.getEnunciado()));
            combo.getItems().add(""+cont); //se añade al comboBox el numero de preguntas
            cont++;
        }
        
        b.setOnAction(eh -> { //Se le añade una accion que llama a un metodo
            eliminarPregunta();
        });
        
        HBox contenedor = new HBox(10); //creacion de un HBox
        contenedor.getChildren().add(combo); //se añade el comboBox
        contenedor.getChildren().add(b); //se añade el boton
        preguntasVB.getChildren().add(contenedor); //se lo muestra por pantalla
        preguntasVB.setSpacing(10); // se agrega espacio
    }
    
    //Metodo que elimina una pregunta
    public void eliminarPregunta() {
        int posNivel = 0; //posicion del nivel
        int numSeleccionado = Integer.parseInt(combo.getValue()); // el numero de pregunta a eliminar
        int numPregActual = 1; //el numero de pregunta en donde se encuentra
        for(ArrayList<Pregunta> lst: lstMaterias.get(posMateria).getLstOrdenadasxNivel()){
            for(Pregunta p: lst) {
                if(numPregActual == numSeleccionado){ //Condicional
                    lstMaterias.get(posMateria).getLstOrdenadasxNivel().get(posNivel).remove(p); // remueve la pregunta de la lista de materias
                } else{
                    numPregActual++; //avanza de pregunta si el condicional es falso
                }
            }
            posNivel++;
        }
        
        //Llamada de metodos
        actualizarMateria();
        importarMaterias(); 
    }
    
    //Metodo que serializa las materias junto a los cambios recientes
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
    
    //Metodo que regresa al menu anterior
    @FXML
    public void regresarMenuAnterior() throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
}
