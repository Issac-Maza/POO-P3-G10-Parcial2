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
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuAddQuestionsController implements Serializable {

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
    
    //Atributos necesarios para la clase
    private ArrayList<Materia> lstMaterias;
    private int posMateria;

    /**
     * Initializes the controller class.
     */

    //Metodo que inicializa el metodo
    public void initialize() {
        importarMaterias();     
    }
    
    //Metodo que llena el CMB de materias con los nombres de las materias del archivo materias
    public void importarMaterias() {     
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
    
    //Metodo que crea la pregunta y la almacena y junto a las demas preguntas de la materia
    @FXML
    public void registrarPregunta() {
        buscarMateria();
        boolean permiso = condicionNivel();

        if(permiso){ //Condicional que veifica si el nivel ingresado es permitido
            String enunciado = pregunta.getText().trim();
            int nivel = Integer.parseInt(nivelPreg.getText().trim());
            String respC = rCorrecta.getText().trim();
            String respI1 = rIncorrecta_1.getText().trim();
            String respI2 = rIncorrecta_2.getText().trim();
            String respI3 = rIncorrecta_3.getText().trim();
            
            Pregunta preg = new Pregunta(enunciado,nivel,respC,respI1,respI2,respI3);
            
            lstMaterias.get(posMateria).getLstOrdenadasxNivel().get(nivel-1).add(preg);
            
            mostrarAlerta(Alert.AlertType.INFORMATION,"La datos se ha guardado correctamente");
            actualizarMateria();
            borrar();   
        }else{
            nivelPreg.setText(null);
            mostrarAlerta(Alert.AlertType.ERROR, "Nivel invalido pruebe poner desde el 1 hasta el "+lstMaterias.get(posMateria).getCantidadNiveles());
        }   
    }
    
    //Metodo que verifica que el nivel ingresado es permitido
    public boolean condicionNivel() {
        int nivel = Integer.parseInt(nivelPreg.getText().trim());
        boolean permiso = true;

        if(nivel > lstMaterias.get(posMateria).getCantidadNiveles() || nivel < 1){
            permiso = false;
        }
        
        return permiso;
    }
    
    //Metodo que encuentra la posicion de la materia seleccionada del CMB en la lista
    public void buscarMateria() {
        int cont = 0;
        for(Materia m: lstMaterias){
            if(m.getNombre().equalsIgnoreCase((String)materiaCMB.getValue())) posMateria = cont;
            cont++;
        }
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
    
    //Metodo que borra todo texto seleccionado o escrito
    @FXML
    public void borrar(){
        materiaCMB.setValue(null);
        pregunta.setText(null);
        nivelPreg.setText(null);
        rCorrecta.setText(null);
        rIncorrecta_1.setText(null);
        rIncorrecta_2.setText(null);
        rIncorrecta_3.setText(null);
    }
    
    //Metodo que regresa al menu anterior
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
    
    //Metodo que muestra una alerta
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    } 
}
