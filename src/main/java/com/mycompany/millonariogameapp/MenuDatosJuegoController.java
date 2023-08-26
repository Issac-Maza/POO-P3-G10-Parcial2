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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.io.*;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuDatosJuegoController implements Serializable {

    @FXML
    private TextField termino;
    @FXML
    private ComboBox<String> paraleloMateriaCMB;
    @FXML
    private TextField numPregxNivel;
    @FXML
    private ComboBox<String> participanteCMB;
    @FXML
    private ComboBox<String> acompCMB;
    
    private TerminoAcademico terminoSeleccionado;
    private Paralelo paraleloSeleccionado;
    private Materia materiaSeleccionada;
    private Estudiante jugadorPrincipal;
    private Estudiante jugadorSecundario;
    public Juego juego;
    private ArrayList<Juego> lstJuegos;

    /**
     * Initializes the controller class.
     */

    public void initialize() {
        lstJuegos = new ArrayList<>();
        asignacionTermino();
        if(!termino.getText().equalsIgnoreCase("") && !termino.getText().equalsIgnoreCase(null)){
            deserializarJuego();
            importarParaleloMateria();
        }
        else{
            try {
                mostrarAlerta(Alert.AlertType.ERROR,"Termino No Seleccionado. Por favor vaya a Configuraciones");
                App.setRoot("menuInicio");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        

    }
    
    @FXML
    public void asignacionTermino(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/terminoSeleccionado.ser"))){
            terminoSeleccionado = (TerminoAcademico) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        termino.setText(terminoSeleccionado.nombreTermino());
        termino.setEditable(false);     
    }
    
    @FXML
    public void importarParaleloMateria() {   
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/paralelos.ser"))) {
            ArrayList<Paralelo> lstParalelos = (ArrayList<Paralelo>)in.readObject();
            for(Paralelo p: lstParalelos){
                if(terminoSeleccionado.equals(p.getTermino())){
                    paraleloMateriaCMB.getItems().add("P"+p.getNumero()+" - "+p.getMateria().getNombre());
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void seleccionMateriaParalelo() {
        String datos = (String) paraleloMateriaCMB.getValue();
        String[] paraleloMateria = datos.split(" - ");
        String num = paraleloMateria[0].replace("P", "");
        int numP = Integer.parseInt(num);
        String nomMat = paraleloMateria[1];
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/paralelos.ser"))) {
            ArrayList<Paralelo> lstParalelos = (ArrayList<Paralelo>)in.readObject();
            for(Paralelo p: lstParalelos){
                if((p.getNumero() == numP) && (p.getMateria().getNombre().equalsIgnoreCase(nomMat))){
                    paraleloSeleccionado = p;
                    materiaSeleccionada = p.getMateria();
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean validacionNivel(){
        int nivel = Integer.parseInt(numPregxNivel.getText().trim());
        boolean esValido = false;
        for(ArrayList<Pregunta> preguntas: materiaSeleccionada.getLstOrdenadasxNivel()){
                if(preguntas.size() >= nivel && nivel >= 1){
                    esValido = true;
            }
        }
        return esValido;
    }
    
    @FXML
    public void importarEstudianteMatricula(){
        seleccionMateriaParalelo();
        for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
            participanteCMB.getItems().add(e.getnMatricula());
            acompCMB.getItems().add(e.getnMatricula());
        }
    }
    
    public ArrayList<Estudiante> buscarEstudiantes(){
        ArrayList<Estudiante> lst = new ArrayList<>();
        String codigo = (String)participanteCMB.getValue();
        String codigo2 = (String)acompCMB.getValue();
        if(!codigo.equalsIgnoreCase(codigo2)){
            for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
                if(codigo.equalsIgnoreCase(e.getnMatricula())){
                    lst.add(e);
                }
            }
            for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
                if(codigo2.equalsIgnoreCase(e.getnMatricula())){
                    lst.add(e);
                }
            }
        }
        else{
            lst.add(null);
            lst.add(null);
        }

        return lst;
    }
    
    @FXML
    public void estudianteAleatorio(){
        boolean conseguido = false;
        while (!conseguido){
            int pos1 = (int)(paraleloSeleccionado.getEstudiantes().size()*Math.random());
            int pos2 = (int)(paraleloSeleccionado.getEstudiantes().size()*Math.random());
            if (pos1 != pos2){
                participanteCMB.setValue((String)participanteCMB.getItems().get(pos1));
                acompCMB.setValue((String)participanteCMB.getItems().get(pos2));
                conseguido = true;
            }
        }
    }
    
    public void seleccionEstudiantes(){
        jugadorPrincipal = buscarEstudiantes().get(0);
        jugadorSecundario = buscarEstudiantes().get(1);
    }
    
    @FXML
    public void comenzarJuego() {
        if(validacionNivel()){
            seleccionEstudiantes();
            if(jugadorPrincipal != null && jugadorSecundario != null){
                creacionJuego();
                serializarJuego();
                try {
                    App.setRoot("nuevoJuego");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(jugadorPrincipal == null || jugadorSecundario == null){
                acompCMB.setValue(null);
                mostrarAlerta(Alert.AlertType.ERROR,"Participante y acompañante son iguales. Por favor cambielo.");
            }
        }
        else{
            numPregxNivel.setText(null);
            mostrarAlerta(Alert.AlertType.ERROR,"Nivel para la pregunta no valido. Intente de nuevo.");
        }
    }
    
    @FXML
    public void borrar(){
        paraleloMateriaCMB.setValue(null);
        numPregxNivel.setText(null);
        participanteCMB.setValue(null);
        acompCMB.setValue(null);
    }
    
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuInicio");
    }
    
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void creacionJuego(){
        int nivel = Integer.parseInt(numPregxNivel.getText().trim());
        juego = new Juego(terminoSeleccionado,paraleloSeleccionado,materiaSeleccionada,nivel,jugadorPrincipal,jugadorSecundario);
        lstJuegos.add(juego);
    }
    
    public void serializarJuego() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/juegos.ser"))) {
            out.writeObject(lstJuegos);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void deserializarJuego(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/juegos.ser"))){
            lstJuegos = (ArrayList<Juego>) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
