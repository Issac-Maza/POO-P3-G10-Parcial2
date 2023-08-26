/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.*;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class MenuInicioController implements Serializable {

    @FXML
    private Button btnConfiguraciones;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnJuego;
    
    Materia POO = new Materia("002","Programacion Orientada a Objetos",3);   
    TerminoAcademico t3 = new TerminoAcademico(2023,01);
    Paralelo P3 = new Paralelo(POO,t3,3);
    ArrayList<Materia> lstMaterias = new ArrayList<>();
    ArrayList<Paralelo> lstParalelos = new ArrayList<>();
    ArrayList<TerminoAcademico> lstTerminos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */

    public void initialize(){
     
    }
    
    @FXML
    public void resetear(){
        cargaPorDefecto();       
        lstMaterias.add(POO);
        lstParalelos.add(P3);
        lstTerminos.add(t3);
        serializarMateria();
        serializarPreguntas();
        serializarParalelo();
        serializarTermino();
    }

    @FXML
    private void mostrarMenuConfiguraciones(ActionEvent event) throws IOException{
        App.setRoot("menuConfiguracion");
    }

    @FXML
    private void NuevoJuego(ActionEvent event) throws IOException{
        App.setRoot("datosJuego");
    }

    @FXML
    private void obtnerReporte(ActionEvent event) throws IOException{
        App.setRoot("reporte");
    }
    
    private void cargaPorDefecto(){
        //POO = new Materia("002","Programacion Orientada a Objetos",3);
        POO.creacionDeNiveles();
        //t3 = new TerminoAcademico(2023,03);
        //P3 = new Paralelo(POO,t3,3);
        POO.getParalelos().add(P3);
        
        //PREGUNTAS PARA LA MATERIA PROGRAMACION ORIENTADA A OBJETOS
        //Nivel 1 - Facil
        Pregunta p1_POO = new Pregunta("¿Cuál de estos datos es primitivo?",1,"int","Interger","Double","Float");
        Pregunta p2_POO = new Pregunta("¿Cuál de estos modificadores es el más restrictivo?",1,"private","protected","public","default");
        Pregunta p3_POO = new Pregunta("Complete: Los metodos estaticos solo pueden usar variables y metodos ____.",1,"estaticos","instancia","private","public");
        
        POO.getLstOrdenadasxNivel().get(0).add(p1_POO);
        POO.getLstOrdenadasxNivel().get(0).add(p2_POO);
        POO.getLstOrdenadasxNivel().get(0).add(p3_POO);
        
        //Nivel 2 - Medio
        Pregunta p4_POO = new Pregunta("Complete: En la herencia la clase _____ hereda atributos y metodos de la clase ______.",2,"hija - padre","padre - hija","super - subsuper","instancia - estatico");
        Pregunta p5_POO = new Pregunta("Indetifique cual enunciado es incorrecto:",2,"Una clase abstracta obligatoriamente debe tener abstractos sus metodos","El nivel de acceso no puede ser más restrictivo que el método que se sobrescribe","@Override verifica que estés sobrescribiendo el método, de acuerdo a las reglas","Instanceof nos permite comprobar si un objeto es de cierto tipo o una subclase de ese tipo");
        Pregunta p6_POO = new Pregunta("¿Qué es la sobrecarga?",2,"Es la creacion de varios constructores/metodos con mismos nombres y diferentes parametros","Es la creacion de varios constructores/metodos con diferentes nombres y diferentes parametros","Es la creacion de varios constructores/metodos con mismos nombres y mismos parametros","Es la creacion de varios constructores/metodos con diferentes nombres y mismos parametros");
        
        POO.getLstOrdenadasxNivel().get(1).add(p4_POO);
        POO.getLstOrdenadasxNivel().get(1).add(p5_POO);
        POO.getLstOrdenadasxNivel().get(1).add(p6_POO);
        
        //Nivel 3 - Dificil
        Pregunta p7_POO = new Pregunta("¿Cuál metodo crea un objeto wrapper a partir de un tipo primitivo?",3,"valueOf()","xxxValue()","parseXxx()","toString()");
        Pregunta p8_POO = new Pregunta("Cuál de los argumentos del for es mejor para recorrer una lista",3,"for(double d: lstDouble)","for(int i = 0; i<lstDouble.size(); i++)","for(int i = 0; i<lstDouble.length; i++)","for(int i = 0; i<6;i++)");
        Pregunta p9_POO = new Pregunta("¿Cuál metodo del ArrayList devuelve true o false si contiene al objeto requerido?",4,"contains()","set()","indexOf()","remove()");
        
        POO.getLstOrdenadasxNivel().get(2).add(p7_POO);
        POO.getLstOrdenadasxNivel().get(2).add(p8_POO);
        POO.getLstOrdenadasxNivel().get(2).add(p9_POO);
        
        try(BufferedReader bf = new BufferedReader(new FileReader("archivos/estudiantes.txt"))){
            String linea;
            while((linea = bf.readLine()) != null){
                String[] datos = linea.split(",");
                P3.getEstudiantes().add(new Estudiante(datos[0],datos[1],datos[2]));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void serializarMateria() {
        
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("archivos/materias.ser"))) {
            
            out1.writeObject(this.lstMaterias);
            out1.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void serializarTermino() {
        
        try (ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream("archivos/terminos.ser"))) {
            out3.writeObject(this.lstTerminos);
            out3.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void serializarParalelo() {
        try (ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("archivos/paralelos.ser"))) {
            out2.writeObject(this.lstParalelos);
            out2.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void serializarPreguntas() {
        for(Materia mate : this.lstMaterias){
            try (ObjectOutputStream out0 = new ObjectOutputStream(new FileOutputStream("archivos/"+mate.getCodigo()+".ser"))) {
                out0.writeObject(POO.getLstOrdenadasxNivel());
                out0.flush();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }

    @FXML
    private void salirDelAplicacion(ActionEvent event) throws IOException{
        // Crear un Alert de tipo INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gracias por usar esta aplicación");
        alert.setHeaderText(null);
        alert.setContentText("¡Gracias por utilizar esta aplicación!\n¡Hasta luego!");
        
        // Mostrar el Alert y esperar a que el usuario lo cierre
        alert.showAndWait();
        
        Platform.exit();
        System.exit(0);
    }
}
