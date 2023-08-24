/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
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
    /*private Button btnCargarArchivo;
    private TextField labelRutaArchivo;*/
    
    ObservableList<Materia> materiaObservableList;
    ObservableList<TerminoAcademico> terminoObservableList;
    ObservableList<Paralelo> paraleloObservableList;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materiaObservableList = FXCollections.observableArrayList(App.materias);
        terminoObservableList = FXCollections.observableArrayList(App.terminosAcademico);
        paraleloObservableList =  FXCollections.observableArrayList();
        
        
        // TODO
        TextFormatter<String> textFormatter= new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
            return change;
            }
            return null;
        });
        
        textTerminoAcademico.setTextFormatter(textFormatter);
        
        for(Materia m: App.materias) {
            comboMaterias.getItems().add(m);
        }
        
        for(TerminoAcademico t: App.terminosAcademico) {
            comboTermino.getItems().add(t);
        }
          
        /*comboMaterias.setItems(materiaObservableList);
        
        comboTermino.setItems(terminoObservableList);*/
        
        btnVolver.setOnAction(eh -> {
            try {
                guardarListaEnArchivo(paraleloObservableList);
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnGuardar.setOnAction(eh ->{
            if (comboMaterias.getValue()!= null && comboTermino.getValue()!= null && !textTerminoAcademico.getText().trim().isEmpty()) {
                boolean existe= false;
                Materia melegida=comboMaterias.getValue();
                TerminoAcademico telegido=comboTermino.getValue();
                int numPar= Integer.parseInt(textTerminoAcademico.getText());
                Paralelo pnuevo= new Paralelo(melegida, telegido, numPar);
                if(!melegida.getParalelos().isEmpty()){
                    for (Paralelo p: melegida.getParalelos()) {
                        if(p.equals(pnuevo)) {
                            existe=true;
                        }
                    }
                }
                
                if (existe) {
                    mostrarAlerta("Paralelo Ya Existente", "El paralelo ingresado ya existe.", Alert.AlertType.WARNING);
                }
                else{
                    melegida.getParalelos().add(pnuevo);
                    App.paralelos.add(pnuevo);
                    comboMaterias.setValue(null);
                    comboTermino.setValue(null);
                    textTerminoAcademico.setText(null);
                    mostrarAlerta("Paralelo Creado", "Un nuevo paralelo ha sido creado.", Alert.AlertType.INFORMATION);
                    
                }
            }
            else {
                mostrarAlerta("Información No Completada", "No se ha elegido ninguna materia", Alert.AlertType.WARNING);
            }
        });
        
        /*btnCargarArchivo.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo de estudiantes");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
            
            
            // Mostrar el diálogo de selección de archivo
            java.io.File selectedFile = fileChooser.showOpenDialog(null);
            
            if (selectedFile != null) {
                String rutaArchivo = "archivos/" + comboMaterias.getValue().getCodigo() + "-" + textTerminoAcademico.getText() + ".csv";
                labelRutaArchivo.setText(rutaArchivo);
                
                 ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
                 
                 try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] campos = linea.split(",");
                        if (campos.length >= 3) {
                            String nMatricula = campos[0];
                            String nombre = campos[1];
                            String correo = campos[2];
                            Estudiante estudiante = new Estudiante(nMatricula, nombre, correo);
                            listaEstudiantes.add(estudiante);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                 
                 // Ahora puedes asignar esta lista de estudiantes al paralelo seleccionado
                Paralelo paraleloSeleccionado = new Paralelo(comboMaterias.getValue(), comboTermino.getValue(), Integer.parseInt(textTerminoAcademico.getText()));
                paraleloSeleccionado.setEstudiantes(listaEstudiantes);

                // Actualiza la lista de paralelos en la materia
                
                comboMaterias.getValue().getParalelos().add(paraleloSeleccionado);

                // Limpia los campos de la interfaz
                

                // Aquí puedes copiar el archivo a la ruta definida usando la clase Files
                // Por ejemplo: Files.copy(selectedFile.toPath(), Paths.get(rutaDefinida, nombreArchivo));
            }else{
                mostrarAlerta("Ningún Archivo Seleccionado", "No se ha seleccionado ningún archivo.", Alert.AlertType.WARNING);
            }
            
            comboMaterias.setValue(null);
            comboTermino.setValue(null);
            textTerminoAcademico.setText(null);
            labelRutaArchivo.setText("");
        });*/
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
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
