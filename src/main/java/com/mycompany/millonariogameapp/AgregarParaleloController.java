/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class AgregarParaleloController implements Initializable {

    @FXML
    private ComboBox<Materia> comboMaterias;
    @FXML
    private TextField textTerminoAcademico;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<TerminoAcademico> comboTermino;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TextFormatter<String> textFormatter= new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
            return change;
            }
            return null;
        });
        
        textTerminoAcademico.setTextFormatter(textFormatter);
          
        comboMaterias.setItems((ObservableList<Materia>) App.materias);
        
        comboTermino.setItems((ObservableList<TerminoAcademico>) App.terminosAcademico);
        
        btnVolver.setOnAction(eh -> {
            try {
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnGuardar.setOnAction(eh ->{
            if (comboMaterias.getValue()!= null && comboTermino.getValue()!= null && !textTerminoAcademico.getText().isEmpty()) {
                boolean existe= false;
                Materia selectedMateria = comboMaterias.getValue();
                TerminoAcademico selectedTermino = comboTermino.getValue();
                int numero = Integer.parseInt(textTerminoAcademico.getText());
                Paralelo pnuevo= new Paralelo(selectedMateria, selectedTermino, numero);
                
                for (Paralelo p: selectedMateria.getParalelos()) {
                    if(p.equals(pnuevo)) {
                    existe=true;
                    }
                }
                if (existe) {
                mostrarAlerta("Paralelo Ya Existente","El paralelo ingresado ya existe.",Alert.AlertType.ERROR);
                }else{
                selectedMateria.getParalelos().add(pnuevo);
                comboMaterias.setValue(null);
                comboTermino.setValue(null);
                textTerminoAcademico.setText(null);
                mostrarAlerta("Confirmación","Un nuevo paralelo ha sido creado.",Alert.AlertType.INFORMATION);
                }    
            }else{
                mostrarAlerta("Información No Completada","No se ha elegido ninguna materia",Alert.AlertType.ERROR);
            }
        });
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
       
    
}
