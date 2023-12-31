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
    
    //Creacion de atributos necesarios para la clase
    private TerminoAcademico terminoSeleccionado;
    private Paralelo paraleloSeleccionado;
    private Materia materiaSeleccionada;
    private Estudiante jugadorPrincipal;
    private Estudiante jugadorSecundario;
    public Juego juego;
    private ArrayList<Juego> lstJuegos;
    private ArrayList<Materia> lstMaterias;

    /**
     * Initializes the controller class.
     */

    public void initialize() {
        lstJuegos = new ArrayList<>();
        asignacionTermino();
        if(!termino.getText().equalsIgnoreCase("") && !termino.getText().equalsIgnoreCase(null)){ //Se comprueba si se configuro el termino
            //Se llaman a los metodos que deserializan y llenan los cmbs
            deserializarMaterias();
            deserializarJuego();
            importarParaleloMateria();
        }
        else{
            try {
                mostrarAlerta(Alert.AlertType.ERROR,"Termino No Seleccionado. Por favor vaya a Configuraciones");
                App.setRoot("menuInicio"); //En caso de no haber termino, se regresa automaticamente al menu inicio
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        

    }
      
    @FXML
    public void asignacionTermino(){
        //Se extrae el termino que se utilizara en el juego
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/terminoSeleccionado.ser"))){
            terminoSeleccionado = (TerminoAcademico) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        termino.setText(terminoSeleccionado.nombreTermino()); //Se llena la textfield del termino
        termino.setEditable(false); //bloqueamos el textfield para que no editen
    }
    
    @FXML
    public void importarParaleloMateria() {  
        //Llenamos el cmb del paralelo y la materia de acuerdo con el termino seleccionado
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
        //Se extrae el nombre de la materia y el numero del paralelo que la persona escogio
        String datos = (String) paraleloMateriaCMB.getValue();
        String[] paraleloMateria = datos.split(" - ");
        String num = paraleloMateria[0].replace("P", "");
        int numP = Integer.parseInt(num);
        String nomMat = paraleloMateria[1];
        
        //Se abre el archivo paralelos 
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/paralelos.ser"))) {
            ArrayList<Paralelo> lstParalelos = (ArrayList<Paralelo>)in.readObject();
            for(Paralelo p: lstParalelos){
                // Se busca el paralelo y la materia especifica y se guarda en los atributos
                if((p.getNumero() == numP) && (p.getMateria().getNombre().equalsIgnoreCase(nomMat))){
                    paraleloSeleccionado = p;
                    materiaSeleccionada = lstMaterias.get(lstParalelos.indexOf(p));
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
    
    //Se valida si el usario escogio el numero de preguntas adecuado
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
            participanteCMB.getItems().add(e.getnMatricula()+" - "+e.getNombre());
            acompCMB.getItems().add(e.getnMatricula()+" - "+e.getNombre());
        }
    }
    
    //En este metodo se busca a los estudiantes seleccionados en los cmb
    public ArrayList<Estudiante> buscarEstudiantes(){
        ArrayList<Estudiante> lst = new ArrayList<>();
        String[] codigo = ((String)participanteCMB.getValue()).split(" - "); //SE extrae la info del participante
        String[] codigo2 = ((String)acompCMB.getValue()).split(" - "); //SE extrae el segundo
        if(!codigo[0].equalsIgnoreCase(codigo2[0])){ //Se valida que el usuario no haya escogido el mismo
            for(Estudiante e: paraleloSeleccionado.getEstudiantes()){ 
                if(codigo[0].equalsIgnoreCase(e.getnMatricula())){//Busca al estudiante que participara
                    lst.add(e);
                }
            }
            for(Estudiante e: paraleloSeleccionado.getEstudiantes()){
                if(codigo2[0].equalsIgnoreCase(e.getnMatricula())){//Busca al estudiante de apoyo
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
    
    //Creacion de un metodo para escoger a los estudiantes aleatoriamente
    @FXML
    public void estudianteAleatorio(){
        boolean conseguido = false;
        while (!conseguido){
            int pos1 = (int)(paraleloSeleccionado.getEstudiantes().size()*Math.random());
            int pos2 = (int)(paraleloSeleccionado.getEstudiantes().size()*Math.random());
            if (pos1 != pos2){ //Se valida que estos dos no sean iguales
                //Se pone a los estudiantes como predeterminado en los cmb
                participanteCMB.setValue((String)participanteCMB.getItems().get(pos1));
                acompCMB.setValue((String)participanteCMB.getItems().get(pos2));
                conseguido = true;
            }
        }
    }
    
    //Se le asigna el participante y el apoyo a sus respectivos atributos
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
    
    //Metodo que borra la informacion de los cmbs y textfields
    @FXML
    public void borrar(){
        paraleloMateriaCMB.setValue(null);
        numPregxNivel.setText(null);
        participanteCMB.setValue(null);
        acompCMB.setValue(null);
    }
    
    //Metodo para regresar al menu anterior
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuInicio");
    }
    
    //Metodo para mostrarnos una alerta
    public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);

        alert.setTitle("Resultado de operacion");
        alert.setHeaderText("Notificacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void creacionJuego(){
        //Se crea el atributo juego que jugara el usuario
        int nivel = Integer.parseInt(numPregxNivel.getText().trim());
        juego = new Juego(terminoSeleccionado,paraleloSeleccionado,materiaSeleccionada,nivel,jugadorPrincipal,jugadorSecundario);
        lstJuegos.add(juego); //Se añade ese juego a una lista con otros juegos
    }
    
    public void serializarJuego() {
        //Se serializa la lista Juegos con el nuevo juego recien añadido
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
        //Se extrae todos los juegos realizados anteriormente para posteiormente añadir el más reciente
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
    
    public void deserializarMaterias(){
        //Se exrae todas las materias añadidas
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/materias.ser"))){
            lstMaterias = (ArrayList<Materia>) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
