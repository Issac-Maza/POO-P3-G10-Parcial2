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
    
    //Creacion de atributos necesarios para el juego
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
        //Se les asigna a algunos atributos elementos predefinidos
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

    //Metodo que inicia el juego
    @FXML
    public void iniciar(){
        tiempo();
        programaPrincipal3();
    }
    
    //Metodo que crea un hilo desde una clase interna
    public void tiempo(){
        Temporizador temp = new Temporizador();
        temp.start();
    }

    //Metodo que desearializa el juego que se va a jugar
    public void deserializarJuego() {
        //Busqueda de archivo juego e implementacion en el atributo juego
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
    
    //Metodo que mostrara en la pantalla cuantas preguntas se jugara en el juego
    public void rellenarPreguntasVB(){
        int i = 1;
        for(ArrayList<Pregunta> lst: preguntasParaJuego){
            for(Pregunta p: lst){
                preguntasVB.getChildren().add(new Label("Preguntas "+i));
                i++;
            }
        }
        numPregMax = i-1; //Indica cuantas preguntas maximas habra en el atributo
    }
    
    //Metodo que extraera las preguntas de forma aleatoria segun cuntas preguntas por nivel habra querido el usuario
    public void ajustesParaPreguntas(){
        int cont = 0;
        int ind = 0;
        ArrayList<Pregunta> preguntasUsadas = new ArrayList<>(); //Lista para almacenar las preguntas sin repetirse
        ArrayList<ArrayList<Pregunta>> lstAjustes = new ArrayList<>(); //Lista que almacena las preguntas seleccionadas por nivel
        for (ArrayList<Pregunta> lstnivel : juego.getMateria().getLstOrdenadasxNivel()){
            lstAjustes.add(new ArrayList<>());
            for(int i = 0; i<juego.getPreguntasPorNivel();i++){
                do{
                    ind = (int)(lstnivel.size()*Math.random()); 
                } while(preguntasUsadas.contains(lstnivel.get(ind))); //Un do while que evita que haya preguntas repetidas
                
                lstAjustes.get(cont).add(lstnivel.get(ind));
                preguntasUsadas.add(lstnivel.get(ind));
            }
            cont += 1;
        }

        preguntasParaJuego = lstAjustes; //Asignacion al atributo preguntas para juego
    }
    
    //Metodo que verifica si el participante contesto bien o mal
    public void contestoBien(){
        if(literalSeleccionado.equals(literalVerdadero)) correcto = true; //Si es contesto bien el atrito correcto sera true y viceversa
        else correcto = false;
    }
    
    
    //Metodo que se llama si se escoge la opcion A
    @FXML
    public void seleccionA(){
        literalSeleccionado = "a";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    //Metodo que se llama si se escoge la opcion B
    @FXML
    public void seleccionB(){
        literalSeleccionado = "b";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    //Metodo que se llama si se escoge la opcion C
    @FXML
    public void seleccionC(){
        literalSeleccionado = "c";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }
    
    //Metodo que se llama si se escoge la opcion D
    @FXML
    public void seleccionD(){
        literalSeleccionado = "d";
        deteccionDeComodines();
        contestoBien();
        programaPrincipal3();
    }

    //Metodo que tiene como parametro un objeto tipo pregunta que cambia la pregunta si contesto bien
    public void cambioDePregunta2(Pregunta p){
        //Se sustituye la pregunta anterior y sus opciones por la que sigue
        pregunta.setText(p.getEnunciado());
        opcionA.setText("a. "+p.getPosiblesRespuestas().get(0));
        opcionB.setText("b. "+p.getPosiblesRespuestas().get(1));
        opcionC.setText("c. "+p.getPosiblesRespuestas().get(2));
        opcionD.setText("d. "+p.getPosiblesRespuestas().get(3));
        
        //Condicional que llena el atributo literalVerdadero por la respuesta correcta de la pregunta actual
        if(p.getPosiblesRespuestas().get(0).equals(p.getRespuestaCorrecta())) literalVerdadero = "a";
        else if (p.getPosiblesRespuestas().get(1).equals(p.getRespuestaCorrecta())) literalVerdadero = "b";
        else if (p.getPosiblesRespuestas().get(2).equals(p.getRespuestaCorrecta())) literalVerdadero = "c";
        else if (p.getPosiblesRespuestas().get(3).equals(p.getRespuestaCorrecta())) literalVerdadero = "d";
        
        //Condicional que indica que pregunta se encuentra 
        if(numPregActual == 0){
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-background-color: yellow;");
        }
        else{
            preguntasVB.getChildren().get(numPregActual-1).setStyle("-fx-background-color: lightGreen;");
            preguntasVB.getChildren().get(numPregActual).setStyle("-fx-background-color: yellow;");
        }
    }
    
    //Metodo que indica si ha contestado bien, o si es que ha ganado, o si ha contestado mal
    public void programaPrincipal3(){
        if(correcto && numPregMax != numPregActual){ //Si contesto bien pero aun no termina
            tiempoI = 60; //Se reinicia el tiempo
            cambioDePregunta2(preguntasParaJuego.get(posNivelActual).get(posNumPregArray)); //Se cambia la pregunta
            
            //Condicional que maneja la posicion de la pregunta
            if(posNumPregArray == (juego.getPreguntasPorNivel()-1)){
                posNumPregArray = 0;
                posNivelActual++;
            } else posNumPregArray++;
            
            numPregActual++; 
        }
        else if(correcto && numPregMax == numPregActual){ //Si contesto bien y no quedan más preguntas
            tiempoSigue = false; // Se desactiva el tiempo
            //Alerta que indica que ganaste
            mostrarAlerta(Alert.AlertType.INFORMATION,"GANASTE!!!!! \n Ahora el Profe eligira el premio"); 
            creacionPremio(); //Creacion para el premio
        }
        else if(correcto == false){ //si perdiste
            try {
                tiempoSigue = false; //se desactiva tiempo
                //alerta de perdiste
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
    
    //Metodo de un solo uso que elimina dos respuestas incorrectas
    @FXML
    public void comodin50_50(){
        //Condicional indicando si ya se uso el metodo
        if(boolean50_50){
            String[] resp = {"a","b","c","d"}; //Respuestas posibles
            ArrayList<String> respEliminar = new ArrayList<>(); //Respuestas que se eliminaran
            
            //For para elegir las dos respuestas que se eliminara
            int i;
            for(int j = 0; j<2; j++){
                do{
                    i = (int) (resp.length*Math.random()); //atributo que marca la posicion que se eliminara la resp
                } while(resp[i].equals(literalVerdadero) || respEliminar.contains(resp[i]));  // do while para que la respueta no sea la correca y que no se repita
                respEliminar.add(resp[i]); //Se añade las respuestas que se eliminaran
            }
            
            //For que elimina las dos respuestas incorrectas
            for(String literal: respEliminar){
                if(literal.equals("a")) opcionA.setText("");
                else if(literal.equals("b")) opcionB.setText("");
                else if(literal.equals("c")) opcionC.setText("");
                else if(literal.equals("d")) opcionD.setText("");
            }
            contadorComodines += 1; //se suma el uso del comodin
            boolean50_50 = false; //indica que ya no se podra usar el comodin
            nombreComodin = "50/50"; // atributo que indica que el comodin se esta usando en X pregunta
        }
    }
    
    //Metodo que revela cual es la respuesta correcta
    @FXML
    public void comodinLlamada(){
        //Se verifica si el comodin sea haya usado antes
        if(booleanLlamada){
            contadorComodines += 1; //se suma el uso del comodin
            booleanLlamada = false; //indica que ya no se podra usar el comodin
            nombreComodin = "Llamada"; // atributo que indica que el comodin se esta usando en X pregunta
            //Muestra mediante una alerta la respuesta correcta
            mostrarAlerta(Alert.AlertType.INFORMATION,juego.getAcompañante().getNombre()+"\n esta seguro que la respuesta es el literal "+literalVerdadero);
        }
    }
    
    //Metodo que muestra el porcentaje de personas que votado por cada pregunta
    @FXML
    public void comodinGrupo(){
        //Se verifica si el comodin sea haya usado antes
        if(booleanGrupo){
            //Creacion de las posibles respuestas y arrays de porcentajes
            String[] resp = {"a","b","c","d"};
            int[] porc1 = {70,20,5,5};
            int[] porc2 = {40,30,10,10};
            int[] porc3 = {50,20,20,10};
            ArrayList<int[]> baraja = new ArrayList<>(); //lista que que almacena arrays int
            baraja.add(porc1);
            baraja.add(porc2);
            baraja.add(porc3);
            
            //Se indica cual array se usara
            int pos = (int) (baraja.size()*Math.random());
            int[] porcUsar = baraja.get(pos);
            ArrayList<String> porcMostrar = new ArrayList<>();
            
            //Se le asigna a cada literal un porcentaje
            int cont = 1;
            for(String opcion: resp){
                if(opcion.equals(literalVerdadero)){ //A la resp correcta se le asigna el porcentaje mayor
                    porcMostrar.add(opcion+" - "+porcUsar[0]+"%");
                }
                else{
                    porcMostrar.add(opcion+" - "+porcUsar[cont]+"%");
                    cont++;
                }
            }
            contadorComodines += 1; //se suma el uso del comodin
            booleanGrupo = false; //indica que ya no se podra usar el comodin
            nombreComodin = "Público"; // atributo que indica que el comodin se esta usando en X pregunta
            //Muestra mediante una alerta el porcentaje de cada respuesta
            mostrarAlerta(Alert.AlertType.INFORMATION,"Este es el porcentaje de personas que ha escogido cada \n opcion: "+porcMostrar.get(0)+" / "+porcMostrar.get(1)+" / "+porcMostrar.get(2)+" / "+porcMostrar.get(3));
        }
    }
    
    //Este metodo almacena el nombre del comodin que se esta usando, si no esta usando entonces es vacio
    public void deteccionDeComodines(){
        comodinesUsadosJuego.add(nombreComodin);
        nombreComodin = "";
    }
    
    //Metodo que crea un label, textfield y un boton para asiganr el premio ganador
    public void creacionPremio(){
        Label l = new Label("Escriba el premio: "); //creacion del label
        l.setStyle("-fx-background: yellow;"); //cambio de color
        contenedorPremio.getChildren().add(l); //Se añade el label
        TextField text = new TextField(); //creacion del textfield
        contenedorPremio.getChildren().add(text); //se lo añade
        Button b = new Button("Aceptar"); //creacion del boton para aceptar
        b.setStyle("-fx-background: yellow;"); //cambio color
        //se le asigna una accion al boton
        b.setOnAction(eh -> {
            try {
                premio = (String)text.getText().trim(); //se llena el atributo premio
                deserializarReportes();
                creacionReporte();
                serializarReporte();
                App.setRoot("menuInicio"); //devuelve al menu Inicio
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        contenedorPremio.getChildren().add(b); // se añade el boton
    }
    
    //Metodo que crea el reporte del juego actual
    public void creacionReporte(){
        reporte = new Reporte(fecha(),juego.getParticipante().getNombre(),posNivelActual,premio);
        //Se añade a ciertos atributos necesarios
        reporte.setEstudianteApoyo(juego.getAcompañante().getNombre());
        reporte.setNumPregAlcanzada(numPregActual);
        reporte.setPregXNivel(juego.getPreguntasPorNivel());
        reporte.setContadorComodines(contadorComodines);
        reporte.setTiempoPasados(minutosPasados+":"+segundosPasados);
        reporte.setPuntaje(puntaje);
        reporte.setComodinesUsados(comodinesUsadosJuego);
        lstReportes.add(reporte); //se implementa el reporte creado a una lista junto a otros repotes
    }
    
    //metodo que serializa la lista de reportes con todos los reportes obtenidos junto al recien creado
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
    
    //Metodo que deserializa la lista de reportes con los otros reportes para posteriormente actualizar
    public void deserializarReportes(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/reportes.ser"))){
            lstReportes = (ArrayList<Reporte>) in.readObject(); //asignacion del atributo
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    //Metodo que indica la fecha actual
    public String fecha(){
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String año = Integer.toString(c.get(Calendar.YEAR));
        
        return dia+"/"+mes+"/"+año;
    }
    
    //Metodo para mostrarnos una alerta
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
    
    //Creacion de clase interna que extiende de Thread
    class Temporizador extends Thread{
        @Override
        public void run(){
            do{
                tiempo.setText(String.valueOf(tiempoI)); //Cambio de tiempo por pantalla al pasar un segundo
                
                //el hilo dormira un segundo
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                //Condicional que alamacena el tiempo total transcurrido
                if(segundosPasados == 60){
                    segundosPasados = 0;
                    minutosPasados++;
                } else segundosPasados++;

                tiempoI--; //Se resta un segundo al tiempo
                
                //Condicional que se activa cuando el tiempo es 0, indicando que perdio
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
            } while(tiempoI > 0 && tiempoSigue); //do while que repite hasta que el tiempo es 0 o haya contestado mal         
        }
    }
}
