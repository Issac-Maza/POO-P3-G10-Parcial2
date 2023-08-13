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

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuDatosJuegoController implements Initializable {

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
    private ArrayList<Estudiante> estudiantesUtilizados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        asignacionTermino();
        
        try{
            importarParaleloMateria();
        }
        catch(Exception e){
            System.out.println("ERROR");
        }
        
    }
    
    @FXML
    public void asignacionTermino(){
        
        
    }
    
    @FXML
    public void importarParaleloMateria() throws Exception{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/paralelos.ser"))){
            Paralelo p;
            while((p = (Paralelo)in.readObject()) != null){
                if(terminoSeleccionado.equals(p.getTermino())){
                    paraleloMateriaCMB.getItems().add("P"+p.getNumero()+" - "+p.getMateria().getNombre());
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
    }
    
    public void seleccionMateriaParalelo() throws Exception{
        String datos = (String) paraleloMateriaCMB.getValue();
        String[] paraleloMateria = datos.split(" - ");
        String num = paraleloMateria[0].replace("P", "");
        int numP = Integer.parseInt(num);
        String nomMat = paraleloMateria[1];
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/paralelos.ser"))){
            Paralelo p;
            while((p = (Paralelo)in.readObject()) != null){
                if((p.getNumero() == numP) && (p.getMateria().getNombre().equalsIgnoreCase(nomMat))){
                    paraleloSeleccionado = p;
                    materiaSeleccionada = p.getMateria();
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("Error. No se encuentra el archivo");
        }
        catch(IOException io){
            System.out.println("Error al abrir el archivo");
        }
    }
    
    public boolean validacionNivel(){
        int nivel = Integer.parseInt(numPregxNivel.getText().trim());
        boolean esValido = true;
        for(ArrayList<Pregunta> preguntas: materiaSeleccionada.getLstOrdenadasxNivel()){
                if(preguntas.size() >= nivel && nivel >= 1){
                    esValido = false;
            }
        }
        return esValido;
    }
    
    @FXML
    public void importarEstudianteMatricula(){
        for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
            participanteCMB.getItems().add(e.getnMatricula());
            acompCMB.getItems().add(e.getnMatricula());
        }
    }
    
    public Estudiante estudianteMatricula(){
        String codigo = (String)participanteCMB.getValue();
        for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
            if((codigo.equalsIgnoreCase(e.getnMatricula())) && !(estudiantesUtilizados.contains(e))){
                estudiantesUtilizados.add(e);
                return e;
            }
        }
        return null;
    }
    
    @FXML
    public void estudianteAleatorio(){
        boolean conseguido = false;
        while (!conseguido){
            int posE = (int)(paraleloSeleccionado.getEstudiantes().size()*Math.random());
            if (!estudiantesUtilizados.contains(paraleloSeleccionado.getEstudiantes().get(posE))){
                participanteCMB.setValue((String)participanteCMB.getItems().get(posE));
                conseguido = true;
            }
        }
    }
    
    public void seleccionEstudiantes(){
        jugadorPrincipal = estudianteMatricula();
        jugadorSecundario = estudianteMatricula();
    }
    
    @FXML
    public void comenzarJuego() throws Exception{
        seleccionMateriaParalelo();
        if(validacionNivel()){
            seleccionEstudiantes();
            if(jugadorPrincipal != null && jugadorSecundario != null){
                App.setRoot("nuevoJuego");
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
    
    public TerminoAcademico getTerminoSeleccionado() {
        return terminoSeleccionado;
    }

    public Paralelo getParaleloSeleccionado() {
        return paraleloSeleccionado;
    }

    public Materia getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public Estudiante getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public Estudiante getJugadorSecundario() {
        return jugadorSecundario;
    }
    
    
    
}
