/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class EliminarParaleloController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private ComboBox<Paralelo> comboParalelos;
    @FXML
    private Button eliminarParalelo;
    ObservableList<Paralelo> paraleloObservableList;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Originalmente hiba a crear una tbla de Paralelos por eso se creamos una lista observable
        paraleloObservableList =  FXCollections.observableArrayList();
        
        //Por cada elemento que esta en App.paralelos los agregamos en el comboBox de Paralelos
        for(Paralelo p: App.paralelos){
            comboParalelos.getItems().add(p);
        }
        // TODO
        
        /*Básicamente, este código permite al usuario seleccionar un paralelo existente y 
        luego eliminarlo de la lista de paralelos asociados a la materia correspondiente.*/
        eliminarParalelo.setOnAction(eh -> {
            Paralelo paraleloAEliminar = comboParalelos.getValue();
            /*Básicamente, este código permite al usuario seleccionar un paralelo existente y luego 
            eliminarlo de la lista de paralelos asociados a la materia correspondiente.*/
            if (paraleloAEliminar != null) {
                // Elimina el paralelo de la lista de paralelos de la materia
                //Si se ha seleccionado un paralelo para eliminar, se obtiene la materia asociada a ese paralelo utilizando el método getMateria().
                Materia materiaDelParalelo = paraleloAEliminar.getMateria();
                //Luego se elimina el paralelo seleccionado de la lista de paralelos de esa materia utilizando el método remove(paraleloAEliminar).
                materiaDelParalelo.getParalelos().remove(paraleloAEliminar);

                // Limpia el ComboBox y muestra una alerta
                
                comboParalelos.setValue(null);
                mostrarAlerta("Paralelo Eliminado", "El paralelo ha sido eliminado exitosamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Selección de Paralelo", "Por favor, selecciona un paralelo para eliminar.", Alert.AlertType.WARNING);
            }
        });
        
        //Con una expresion lamba regresamos al menu anterior
        btnVolver.setOnAction(eh ->{
            try {
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    //Este el metodo de mostrarAlerta, tiene el tirulo, un mensje y el tipo de Alerta
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }   
    
     //Aqui es metodo guardar, el cual guarda la lista observable de materias y lo serializa en un archivo
    private void guardarListaEnArchivo(ObservableList<Paralelo> listaMaterias) {
        ArrayList<Paralelo> lista = (ArrayList<Paralelo>) listaMaterias.stream().collect(Collectors.toList());
        App.paralelos = lista;
        
        try (ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("archivos/paralelos.ser"))) {
            out.writeObject(App.paralelos);
            
            out.close();
            

            System.out.println("Lista de materias guardada exitosamente en " + "paralelos.ser");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de materias en el archivo: " + e.getMessage());
        }
    }
    
}
