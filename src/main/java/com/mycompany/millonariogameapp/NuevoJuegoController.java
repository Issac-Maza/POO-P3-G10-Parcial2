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
import javafx.event.ActionEvent;
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
    @FXML
    private Label tiempo;
    
    private Juego juego;
    private ArrayList<ArrayList<Pregunta>> preguntasParaJuego;
    private int puntaje;
    private String literalSeleccionado;
    private int numPregMax;
    private int numPregActual;
    private String premio;
    private boolean correcto;
    private boolean noTerminado;
    private int posNivelActual;
    private Reporte reporte;
    private int posNumPregArray;
    private String literalVerdadero;
    

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        puntaje = 0;
        posNivelActual = 0;
        numPregActual = 0;
        correcto = true;
        noTerminado = true;
        premio = "nada";
        posNumPregArray = 0;
        deserializarJuego();
        ajustesParaPreguntas();
        rellenarPreguntasVB();
    }

    @FXML
    public void iniciar(){
        programaPrincipal3();
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
        numPregMax = i-1;
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
        for(ArrayList<Pregunta> lst: preguntasParaJuego){
            for(Pregunta p: lst){
                System.out.println(p.getEnunciado());
                System.out.println(p.getRespuestaCorrecta());
                System.out.println("");
            }
        }
        
        
    }
    
    public void contestoBien(){

        if(literalSeleccionado.equals(literalVerdadero)) correcto = true;
        else correcto = false;

    }
    
    @FXML
    public void seleccionA(){
        literalSeleccionado = "a";
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionB(){
        literalSeleccionado = "b";
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionC(){
        literalSeleccionado = "c";
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionD(){
        literalSeleccionado = "d";
        contestoBien();
        programaPrincipal3();
    }

    public void cambioDePregunta2(Pregunta p){
        pregunta.setText(p.getEnunciado());
        opcionA.setText("a. "+p.getPosiblesRespuestas().get(0));
        opcionB.setText("b. "+p.getPosiblesRespuestas().get(1));
        opcionC.setText("c. "+p.getPosiblesRespuestas().get(2));
        opcionD.setText("d. "+p.getPosiblesRespuestas().get(3));
        
        if(p.getPosiblesRespuestas().get(0).equals(p.getRespuestaCorrecta())) literalVerdadero = "a";
        else if (p.getPosiblesRespuestas().get(1).equals(p.getRespuestaCorrecta())) literalVerdadero = "b";
        else if (p.getPosiblesRespuestas().get(2).equals(p.getRespuestaCorrecta())) literalVerdadero = "c";
        else if (p.getPosiblesRespuestas().get(3).equals(p.getRespuestaCorrecta())) literalVerdadero = "d";
        
        if(numPregActual == 0){
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-control-inner-background: yellow;");
        }
        else{
            preguntasVB.getChildren().get(numPregActual-1).setStyle("-fx-control-inner-background: green;");
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-control-inner-background: yellow;");
        }
    }
    
    /*public void programaPrincipal(){
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
    }*/
    
    /*public void programaPrincipal2(){
        for(ArrayList<Pregunta> arrayNivel:preguntasParaJuego){
            if(correcto && noTerminado){
                for(Pregunta p: arrayNivel){
                    if(correcto){
                        preguntaActual = p;
                        cambioDePregunta2(p);
                        int i = 60;
                        for(i = 60; i>0; i--){
                            tiempo.setText(""+i);
                            try{
                                if(correcto && i>0){
                                   Thread.sleep(1000);
                                }
                                else{
                                    mostrarAlerta(Alert.AlertType.INFORMATION,"Pregunta Incorrecta. Perdiste");
                                    creacionReporte();
                                    serializarReporte();
                                    App.setRoot("menuInicio");
                                }

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        
                        
                        class Temporizador extends Thread{
                            boolean tiempoAgotado = false;
                            @Override
                            public void run(){
                                int i;
                                for(i = 60; i>0; i--){
                                    if(correcto){
                                        tiempo.setText(""+i);
                                        try{
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                    }   
                                }
                                
                                if(i == 0) tiempoAgotado = true;
                            }
                        }
                        
                        Temporizador temp = new Temporizador();
                        temp.start();
                        
                        if(!correcto){
                            try {
                                mostrarAlerta(Alert.AlertType.INFORMATION,"Pregunta Incorrecta. Perdiste");
                                creacionReporte();
                                serializarReporte();
                                App.setRoot("menuInicio");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(temp.tiempoAgotado){
                            try {
                                correcto = false;
                                mostrarAlerta(Alert.AlertType.INFORMATION,"Se te acabo el tiempo. Perdiste");
                                creacionReporte();
                                serializarReporte();
                                App.setRoot("menuInicio");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            numPregActual++;
                            puntaje += (nivelActual*10); 
                        }
                    } 
                }
            }
            
            if((numPregActual == numPregMax) && correcto){
                mostrarAlerta(Alert.AlertType.INFORMATION,"GANASTE!!!!! \n Ahora el Profe eligira el premio");
                creacionPremio();
            } else{
                nivelActual++;
            }
        }
    }*/
    
    public void programaPrincipal3(){
        if(correcto && numPregMax != numPregActual){
            cambioDePregunta2(preguntasParaJuego.get(posNivelActual).get(posNumPregArray));
            
            if(posNumPregArray == (juego.getPreguntasPorNivel()-1)){
                posNumPregArray = 0;
                posNivelActual++;
            } else posNumPregArray++;
            
            numPregActual++; 
        }
        else if(correcto && numPregMax == numPregActual){
            mostrarAlerta(Alert.AlertType.INFORMATION,"GANASTE!!!!! \n Ahora el Profe eligira el premio");
            creacionPremio();
        }
        else if(correcto == false){
            try {
                mostrarAlerta(Alert.AlertType.INFORMATION,"Pregunta Incorrecta. Perdiste");
                creacionReporte();
                serializarReporte();
                App.setRoot("menuInicio");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
            try {
                premio = (String)text.getText().trim();
                creacionReporte();
                serializarReporte();
                App.setRoot("menuInicio");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        contenedorPremio.getChildren().add(b);
    }
    
    public void creacionReporte(){
        reporte = new Reporte(fecha(),juego.getParticipante().getNombre(),posNivelActual+1,premio);
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
    
    public void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuAdministrarPregunta");
    }
}
