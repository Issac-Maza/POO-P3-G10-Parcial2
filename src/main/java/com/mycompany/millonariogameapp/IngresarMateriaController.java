/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import java.net.URL;
import java.util.ResourceBundle;
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
    private AnchorPane anchorPane;
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
    private TableColumn<Materia, ?> columParalelos;
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
        this.columCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columNivel.setCellValueFactory(new PropertyValueFactory("nivel"));
        this.columParalelos.setCellValueFactory(new PropertyValueFactory("paralelos"));
        this.columPreguntas.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            boolean tienePreguntas = !materia.getLstOrdenadasxNivel().isEmpty();
            String respuesta = tienePreguntas ? "Sí" : "No";
            return new ReadOnlyStringWrapper(respuesta);
        });
        // TODO
    }    

    @FXML
    private void agregarMateria(ActionEvent event) {
        String codigo = textfieldcodigo.getText();
        String nombre = textfieldNombre.getText();
        Integer nivel = Integer.parseInt(textfieldNivel.getText());
        
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
    
}
