/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.*;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuAddQuestionsController implements Initializable {

    @FXML
    private ComboBox<String> materiaCMB;
    @FXML
    private TextField pregunta;
    @FXML
    private TextField nivelPreg;
    @FXML
    private TextField rCorrecta;
    @FXML
    private TextField rIncorrecta_1;
    @FXML
    private TextField rIncorrecta_2;
    @FXML
    private TextField rIncorrecta_3;

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
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.txt"))){
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
    
    public void registrarPregunta(){
        
    }
    
    public boolean condicionNivel() throws Exception{
        String texto = nivelPreg.getText().trim();
        int nivel = Integer.parseInt(texto);
        boolean permiso = true;
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.txt"))){
            Materia m;
            while((m = (Materia)in.readObject()) != null){
                if(nivel > m.getCantidadNiveles() || nivel < 1){
                    mostrarAlerta(Alert.AlertType.ERROR, "Nivel invalido pruebe poner desde el 1 hasta el "+(m.getCantidadNiveles()+1));
                    nivelPreg.setText(null);
                    permiso = false;
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
        
        return permiso;
    }
    
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}
