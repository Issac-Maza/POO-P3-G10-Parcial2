/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class IngresarMateriaController implements Initializable {

    @FXML
    private TextField textfieldcodigo;
    @FXML
    private TextField textfieldNombre;
    @FXML
    private TextField textfieldNivel;
    @FXML
    private Button btnguardar;
    @FXML
    private Button btnVolver;
    @FXML
    private TableColumn<Materia, String> columCodigo;
    @FXML
    private TableColumn<Materia, String> columNombre;
    @FXML
    private TableColumn<Materia, Integer> columNivel;
    @FXML
    private TableColumn<Materia, String> columParalelos;
    @FXML
    private TableColumn<Materia, String> columPreguntas;
    @FXML
    private TableView<Materia> tablaMateria;
    
    private ObservableList<Materia> materias;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materias = FXCollections.observableArrayList();
        
        File file = new File(App.rutaMateria);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                materias.addAll((List<Materia>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        
        
        tablaMateria.setItems(materias);
        
        this.columCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columNivel.setCellValueFactory(new PropertyValueFactory("cantidadNiveles"));
        this.columParalelos.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            ArrayList<Paralelo> paralelos = materia.getParalelos();
            StringBuilder paraleloString = new StringBuilder();
            if(!paralelos.isEmpty()){  
                for(int i = 0; i<paralelos.size();i++){
                    if(i>0){
                        paraleloString.append("P-").append(paralelos.get(i).getNumero()).append("; ");
                        
                        
                    }else{
                        paraleloString.append("P-").append(paralelos.get(i).getNumero());
                        //texto+=previo;
                    }
                }
            }else{
                return new ReadOnlyStringWrapper("NO POSEE PARALELOS");
            }
            
            //String numeroParalelo = "P-" + paralelo.getNumero();
            return new ReadOnlyStringWrapper(paraleloString.toString());
        });
        this.columPreguntas.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            boolean tienePreguntas = !materia.getLstOrdenadasxNivel().isEmpty();
            String respuesta = tienePreguntas ? "Sí" : "No";
            return new ReadOnlyStringWrapper(respuesta);
        });
        // TODO
        
        btnVolver.setOnAction(eh->{
            try {
                guardarListaEnArchivo(materias);
                
                App.setRoot("menuConfiguracion");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    

    @FXML
    private void agregarMateria(ActionEvent event) {
        String codigo = textfieldcodigo.getText();
        String nombre = textfieldNombre.getText();
        String nivelText = textfieldNivel.getText();
        Integer nivel = Integer.parseInt(textfieldNivel.getText());
        
        if (codigo.isEmpty() || nombre.isEmpty() || nivelText.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor complete todos los campos.", Alert.AlertType.ERROR);
            return; // Sale del método sin guardar la materia
        }

        
        Materia materiaPD = new Materia(codigo,nombre,nivel);
        
        if(!this.materias.contains(materiaPD)){
            this.materias.add(materiaPD);
            this.tablaMateria.setItems(materias);
            mostrarAlerta("Materia ingresada", "La materia ha sido ingresada exitosamente.", Alert.AlertType.INFORMATION);
            
        }else{
            mostrarAlerta("Ya existe esa materia", "La materia que ha ingresado ya se registro.", Alert.AlertType.WARNING);
        }
        // Limpiar los campos del formulario
        textfieldcodigo.clear();
        textfieldNombre.clear();
        textfieldNivel.clear();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void guardarListaEnArchivo(ObservableList<Materia> listaMaterias) {
        ArrayList<Materia> lista = (ArrayList<Materia>) listaMaterias.stream().collect(Collectors.toList());
        
        try (ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream(App.rutaMateria))) {
            out.writeObject(lista);
            App.materias = lista;
            out.close();
            

            System.out.println("Lista de materias guardada exitosamente en " + App.rutaMateria);
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de materias en el archivo: " + e.getMessage());
        }
    }
    
}
