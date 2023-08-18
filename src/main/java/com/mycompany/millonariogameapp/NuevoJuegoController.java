/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.mycompany.millonariogameapp.modelo.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class NuevoJuegoController implements Serializable {

    @FXML
    private Button opcionA;
    @FXML
    private Button opcionC;
    @FXML
    private Button opcionB;
    @FXML
    private Button opcionD;
    @FXML
    private Label pregunta;
    @FXML
    private VBox preguntasVB;
    @FXML
    private HBox contenedorPremio;
    
    private Juego juego;
    private ArrayList<ArrayList<Pregunta>> preguntasParaJuego;
    private int puntaje;
    private Pregunta preguntaActual;
    private String literalSeleccionado;
    private int numPregMax;
    private int numPregActual;
    private int numPregArray;
    private String premio;
    private boolean correcto;
    private boolean noTerminado;
    private int nivelActual;
    private Reporte reporte;

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        puntaje = 0;
        numPregArray = 1;
        nivelActual = 1;
        correcto = true;
        noTerminado = true;
        premio = "nada";
        puntaje = 0;
        deserializarJuego();
        ajustesParaPreguntas();
        rellenarPreguntasVB();
        cambioDePregunta(nivelActual-1,numPregArray-1);
    }    
    
    public void deserializarJuego() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/juegos.ser"))){
            Juego j;
            while((j = (Juego)in.readObject()) != null){
                juego = j;
            }  
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void rellenarPreguntasVB(){
        int i = 1;
        for(ArrayList<Pregunta> lst: preguntasParaJuego){
            for(Pregunta p: lst){
                preguntasVB.getChildren().add(new Label("Preguntas "+i));
                i++;
            }
        }
        numPregMax = i;
    }
    
    public void ajustesParaPreguntas(){
        int cont = 0;
        int ind = 0;
        ArrayList<Pregunta> preguntasUsadas = new ArrayList<>();
        ArrayList<ArrayList<Pregunta>> lstAjustes = new ArrayList<>();
        for (ArrayList<Pregunta> lstnivel : juego.getMateria().getLstOrdenadasxNivel()){
            lstAjustes.add(new ArrayList<>());
            for(int i = 0; i<juego.getPreguntasPorNivel();i++){
                do{
                    ind = (int)(lstnivel.size()*Math.random());
                } while(preguntasUsadas.contains(lstnivel.get(ind)));
                
                lstAjustes.get(cont).add(lstnivel.get(ind));
                preguntasUsadas.add(lstnivel.get(ind));
            }
            cont += 1;
        }
        preguntasParaJuego = lstAjustes;
    }
    
    public boolean contestoBien(){
        boolean esCorrecta = false;
        
        if(literalSeleccionado.equalsIgnoreCase("a")) esCorrecta = CorrectaOIncorrecta(opcionA);
        else if(literalSeleccionado.equalsIgnoreCase("b")) esCorrecta = CorrectaOIncorrecta(opcionB);
        else if(literalSeleccionado.equalsIgnoreCase("c")) esCorrecta = CorrectaOIncorrecta(opcionC);
        else if(literalSeleccionado.equalsIgnoreCase("d")) esCorrecta = CorrectaOIncorrecta(opcionD);
        
        return esCorrecta;
    }
    
    public void seleccionA(){
        literalSeleccionado = "a";
    }
    
    public void seleccionB(){
        literalSeleccionado = "b";
    }
    
    public void seleccionC(){
        literalSeleccionado = "c";
    }
    
    public void seleccionD(){
        literalSeleccionado = "d";
    }
    
    public boolean CorrectaOIncorrecta(Button b){
        String[] extraido = b.getText().trim().split(". ");
        String respuestaJugador = extraido[1];
        boolean esCorrecta = false;
        
        if(respuestaJugador.equalsIgnoreCase(preguntaActual.getRespuestaCorrecta())){
            esCorrecta = true;
        }
        
        return esCorrecta;
    }
    
    public void cambioDePregunta(int posNivel, int posPreg){
        Pregunta p = preguntasParaJuego.get(posNivel).get(posPreg);
        pregunta.setText(p.getEnunciado());
        opcionA.setText("a. "+p.getPosiblesRespuestas().get(0));
        opcionB.setText("b. "+p.getPosiblesRespuestas().get(1));
        opcionC.setText("c. "+p.getPosiblesRespuestas().get(2));
        opcionD.setText("d. "+p.getPosiblesRespuestas().get(3));
        
        if((numPregActual-1) == 0){
            preguntasVB.getChildren().get(numPregActual-1).setStyle("-fx-control-inner-background: yellow;");
        }
        else{
            preguntasVB.getChildren().get(numPregActual-1).setStyle("-fx-control-inner-background: green;");
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-control-inner-background: yellow;");
        }
    }
    
    public void programaPrincipal(){
        correcto = contestoBien();
        if(correcto){
            if(juego.getPreguntasPorNivel() == numPregArray){
                if(numPregMax == numPregActual){
                    noTerminado = false;
                } else{
                    numPregArray = 0;
                    nivelActual++; 
                }
            }
            if(noTerminado){
                puntaje += (nivelActual*100);
                numPregArray++;
                numPregActual++;
                preguntaActual = preguntasParaJuego.get(nivelActual-1).get(numPregArray-1);
                cambioDePregunta(nivelActual-1,numPregArray-1);
            }
            else{
                mostrarAlerta(Alert.AlertType.INFORMATION,"GANASTE!!!!! \n Ahora el Profe eligira el premio");
                creacionPremio();
            }
        }
        else{
            mostrarAlerta(Alert.AlertType.INFORMATION,"Pregunta Incorrecta. Perdiste");
            creacionReporte();
            serializarReporte();
        }
    }
    
    public void creacionPremio(){
        Label l = new Label("Escriba el premio: ");
        l.setStyle("-fx-control-inner-background: yellow;");
        contenedorPremio.getChildren().add(l);
        TextField text = new TextField();
        contenedorPremio.getChildren().add(text);
        Button b = new Button("Aceptar");
        b.setStyle("-fx-control-inner-background: yellow;");
        b.setOnAction(eh -> {
            premio = (String)text.getText().trim();
            creacionReporte();
            serializarReporte();
        });
        contenedorPremio.getChildren().add(b);
    }
    
    public void creacionReporte(){
        reporte = new Reporte(fecha(),juego.getParticipante().getNombre(),nivelActual,premio);
        reporte.setPuntaje(puntaje);
    }
    
    public void serializarReporte(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/reportes.ser"))){
            out.writeObject(reporte);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public String fecha(){
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH));
        String año = Integer.toString(c.get(Calendar.YEAR));
        
        return dia+"/"+mes+"/"+año;
    }
    
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
