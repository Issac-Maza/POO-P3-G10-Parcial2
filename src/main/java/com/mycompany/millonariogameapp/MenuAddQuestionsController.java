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
import javafx.event.ActionEvent;
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
    
    @FXML
    public void registrarPregunta() throws Exception{
        boolean permiso = condicionNivel();
        Materia materia = buscarMateria();
        
        
        if(permiso){
            String enunciado = pregunta.getText().trim();
            int nivel = Integer.parseInt(nivelPreg.getText().trim());
            String respC = rCorrecta.getText().trim();
            String respI1 = rIncorrecta_1.getText().trim();
            String respI2 = rIncorrecta_2.getText().trim();
            String respI3 = rIncorrecta_3.getText().trim();
            
            Pregunta preg = new Pregunta(enunciado,nivel,respC,respI1,respI2,respI3);
            
            materia.getLstOrdenadasxNivel().get(nivel-1).add(preg);
            
        }else{
            nivelPreg.setText(null);
            mostrarAlerta(Alert.AlertType.ERROR, "Nivel invalido pruebe poner desde el 1 hasta el "+materia.getCantidadNiveles());
        }
        
    }
    
    public boolean condicionNivel() throws Exception{
        int nivel = Integer.parseInt(nivelPreg.getText().trim());
        boolean permiso = true;
        Materia m = buscarMateria();

        if(nivel > m.getCantidadNiveles() || nivel < 1){
            permiso = false;
        }
        
        return permiso;
    }
    
    public Materia buscarMateria() throws Exception{
        Materia mVerdadera = new Materia("","",0);
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.txt"))){
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
    
    @FXML
    public void borrar(){
        materiaCMB.setValue(null);
        pregunta.setText(null);
        nivelPreg.setText(null);
        rCorrecta.setText(null);
        rIncorrecta_2.setText(null);
        rIncorrecta_3.setText(null);
    }
    
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    } 
}
