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
    private TextField tiempo;
    
    private Juego juego;
    private ArrayList<ArrayList<Pregunta>> preguntasParaJuego;
    private int puntaje;
    private String literalSeleccionado;
    private int numPregMax;
    private int numPregActual;
    private String premio;
    private boolean correcto;
    private int posNivelActual;
    private Reporte reporte;
    private int posNumPregArray;
    private String literalVerdadero;
    private boolean boolean50_50;
    private boolean booleanLlamada;
    private boolean booleanGrupo;
    private String nombreComodin;
    private ArrayList<String> comodinesUsadosJuego;
    public int tiempoI;
    private boolean tiempoSigue;
    private int contadorComodines;
    private int segundosPasados;
    private int minutosPasados;
    private ArrayList<Reporte> lstReportes;

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        segundosPasados = 0;
        minutosPasados = 0;
        contadorComodines = 0;
        tiempoSigue = true;
        tiempoI = 60;
        nombreComodin = "";
        boolean50_50 = true;
        booleanLlamada = true;
        booleanGrupo = true;
        puntaje = 0;
        posNivelActual = 0;
        numPregActual = 0;
        correcto = true;
        premio = "nada";
        posNumPregArray = 0;
        lstReportes = new ArrayList<>();
        comodinesUsadosJuego = new ArrayList<>();
        deserializarJuego();
        ajustesParaPreguntas();
        rellenarPreguntasVB();
    }

    @FXML
    public void iniciar(){
        tiempo();
        programaPrincipal3();
    }
    
    public void tiempo(){
        Temporizador temp = new Temporizador();
        temp.start();
    }

    public void deserializarJuego() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/juegos.ser"))){
            ArrayList<Juego> lstJuegos = (ArrayList<Juego>)in.readObject();
            juego = lstJuegos.get(lstJuegos.size()-1);
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
       /* for(ArrayList<Pregunta> lst: preguntasParaJuego){
            for(Pregunta p: lst){
                System.out.println(p.getEnunciado());
                System.out.println(p.getRespuestaCorrecta());
                System.out.println("");
            }
        } */   
    }
    
    public void contestoBien(){
        if(literalSeleccionado.equals(literalVerdadero)) correcto = true;
        else correcto = false;
    }
    
    @FXML
    public void seleccionA(){
        literalSeleccionado = "a";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionB(){
        literalSeleccionado = "b";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionC(){
        literalSeleccionado = "c";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    @FXML
    public void seleccionD(){
        literalSeleccionado = "d";
        deteccionDeComodines();
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
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-background-color: yellow;");
        }
        else{
            preguntasVB.getChildren().get(numPregActual-1).setStyle("-fx-background-color: lightGreen;");
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-background-color: yellow;");
        }
    }
    
    public void programaPrincipal3(){
        if(correcto && numPregMax != numPregActual){
            tiempoI = 60;
            cambioDePregunta2(preguntasParaJuego.get(posNivelActual).get(posNumPregArray));
            
            if(posNumPregArray == (juego.getPreguntasPorNivel()-1)){
                posNumPregArray = 0;
                posNivelActual++;
            } else posNumPregArray++;
            
            numPregActual++; 
        }
        else if(correcto && numPregMax == numPregActual){
            tiempoSigue = false;
            mostrarAlerta(Alert.AlertType.INFORMATION,"GANASTE!!!!! \n Ahora el Profe eligira el premio");
            creacionPremio();
        }
        else if(correcto == false){
            try {
                tiempoSigue = false;
                mostrarAlerta(Alert.AlertType.INFORMATION,"Pregunta Incorrecta. Perdiste");
                deserializarReportes();
                creacionReporte();
                serializarReporte();
                App.setRoot("menuInicio");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @FXML
    public void comodin50_50(){
        if(boolean50_50){
            String[] resp = {"a","b","c","d"};
            ArrayList<String> respEliminar = new ArrayList<>();
            int i;
            for(int j = 0; j<2; j++){
                do{
                    i = (int) (resp.length*Math.random());
                } while(resp[i].equals(literalVerdadero) || respEliminar.contains(resp[i]));
                respEliminar.add(resp[i]);
            }
            
            for(String literal: respEliminar){
                if(literal.equals("a")) opcionA.setText("");
                else if(literal.equals("b")) opcionB.setText("");
                else if(literal.equals("c")) opcionC.setText("");
                else if(literal.equals("d")) opcionD.setText("");
            }
            contadorComodines += 1;
            boolean50_50 = false;
            nombreComodin = "50/50";
        }
    }
    
    @FXML
    public void comodinLlamada(){
        if(booleanLlamada){
            contadorComodines += 1;
            booleanLlamada = false;
            nombreComodin = "Llamada";
            mostrarAlerta(Alert.AlertType.INFORMATION,juego.getAcompañante().getNombre()+"\n esta seguro que la respuesta es el literal "+literalVerdadero);
        }
    }
    
    @FXML
    public void comodinGrupo(){
        if(booleanGrupo){
            String[] resp = {"a","b","c","d"};
            int[] porc1 = {70,20,5,5};
            int[] porc2 = {40,30,10,10};
            int[] porc3 = {50,20,20,10};
            ArrayList<int[]> baraja = new ArrayList<>();
            baraja.add(porc1);
            baraja.add(porc2);
            baraja.add(porc3);
            
            int pos = (int) (baraja.size()*Math.random());
            int[] porcUsar = baraja.get(pos);
            ArrayList<String> porcMostrar = new ArrayList<>();
            
            int cont = 1;
            for(String opcion: resp){
                if(opcion.equals(literalVerdadero)){
                    porcMostrar.add(opcion+" - "+porcUsar[0]+"%");
                }
                else{
                    porcMostrar.add(opcion+" - "+porcUsar[cont]+"%");
                    cont++;
                }
            }
            contadorComodines += 1;
            booleanGrupo = false;
            nombreComodin = "Público";
            mostrarAlerta(Alert.AlertType.INFORMATION,"Este es el porcentaje de personas que ha escogido cada \n opcion: "+porcMostrar.get(0)+" / "+porcMostrar.get(1)+" / "+porcMostrar.get(2)+" / "+porcMostrar.get(3));
        }
    }
    
    public void deteccionDeComodines(){
        comodinesUsadosJuego.add(nombreComodin);
        nombreComodin = "";
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
                deserializarReportes();
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
        reporte = new Reporte(fecha(),juego.getParticipante().getNombre(),posNivelActual,premio);
        reporte.setEstudianteApoyo(juego.getAcompañante().getNombre());
        reporte.setNumPregAlcanzada(numPregActual);
        reporte.setPregXNivel(juego.getPreguntasPorNivel());
        reporte.setContadorComodines(contadorComodines);
        reporte.setTiempoPasados(minutosPasados+":"+segundosPasados);
        reporte.setPuntaje(puntaje);
        reporte.setComodinesUsados(comodinesUsadosJuego);
        lstReportes.add(reporte);
    }
    
    public void serializarReporte(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/reportes.ser"))){
            out.writeObject(lstReportes);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void deserializarReportes(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/reportes.ser"))){
            lstReportes = (ArrayList<Reporte>) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public String fecha(){
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
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
    
    class Temporizador extends Thread{
        @Override
        public void run(){
            do{
                tiempo.setText(String.valueOf(tiempoI));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                if(segundosPasados == 60){
                    segundosPasados = 0;
                    minutosPasados++;
                } else segundosPasados++;

                tiempoI--; 
                //System.out.println(tiempoI);
                if(tiempoI == 0){
                    deserializarReportes();
                    creacionReporte();
                    serializarReporte();
                    try {
                        App.setRoot("menuInicio");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }                 
            } while(tiempoI > 0 && tiempoSigue);         
        }
    }
}
