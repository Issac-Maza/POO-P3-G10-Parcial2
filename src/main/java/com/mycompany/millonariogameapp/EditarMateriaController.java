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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class EditarMateriaController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnModificar;
    @FXML
    private Label textCodigo;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textNivel;
    @FXML
    private TableColumn<Materia, String> columnaCodigo;
    @FXML
    private TableColumn<Materia, String> columnaNombre;
    @FXML
    private TableColumn<Materia, Integer> columnaNivel;
    @FXML
    private TableColumn<Materia, String> columnaPreguntas;
    @FXML
    private TableColumn<Materia, String> columnaParalelos;
    
    private ObservableList<Materia> materias;
    @FXML
    private TableView<Materia> tablaMateria;

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
        
        this.columnaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.columnaNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.columnaNivel.setCellValueFactory(new PropertyValueFactory("cantidadNiveles"));
        this.columnaParalelos.setCellValueFactory(cellData -> {
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
        this.columnaPreguntas.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            boolean tienePreguntas = !materia.getLstOrdenadasxNivel().isEmpty();
            String respuesta = tienePreguntas ? "Sí" : "No";
            return new ReadOnlyStringWrapper(respuesta);
        });
        // TODO
        
        btnVolver.setOnAction(eh ->{
            try {
                guardarListaEnArchivo(materias);
                
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    

    @FXML
    private void seleccionarMateria(MouseEvent event) {
        
        Materia mat = this.tablaMateria.getSelectionModel().getSelectedItem();
        
        if(mat!=null){
            this.textCodigo.setText(mat.getCodigo());
            this.textNombre.setText(mat.getNombre());
            this.textNivel.setText(String.valueOf(mat.getCantidadNiveles()));
        }
        
    }

    @FXML
    private void editarMateria(ActionEvent event) {
        Materia mat = this.tablaMateria.getSelectionModel().getSelectedItem();
        
        if(mat == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una Materia");
            alert.showAndWait();
        }else{
            try{
                String codigo = this.textCodigo.getText();
                String nombre = this.textNombre.getText();
                String niveltexto = this.textNivel.getText();
                
                int nivel;
                try {
                    nivel = Integer.parseInt(niveltexto);
                } catch (NumberFormatException e) {
                    mostrarAlerta("Formato incorrecto", "El nivel debe ser un número entero.", Alert.AlertType.ERROR);
                    return; // Sale del método sin guardar la materia
                }
                
                Materia aux = new Materia(codigo,nombre,nivel);
                
                if(App.materias.contains(aux)){
                    mat.setCodigo(codigo);
                    mat.setNombre(nombre);
                    mat.setCantidadNiveles(nivel);
                    
                    this.tablaMateria.refresh();
                    
                    mostrarAlerta("Info","Materia modificada",Alert.AlertType.INFORMATION);
                    
                }else{
                    mostrarAlerta("Error","La Materia ya existe",Alert.AlertType.ERROR);
                }
            }catch (NumberFormatException e) {
                mostrarAlerta("Error","Formato incorrecto",Alert.AlertType.ERROR);
                
            }
        }
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
