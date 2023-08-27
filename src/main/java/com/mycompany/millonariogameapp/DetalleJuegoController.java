/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class DetalleJuegoController implements Serializable {

    @FXML
    private Label fechaLbl;
    @FXML
    private Label participanteLbl;
    @FXML
    private Label compaLbl;
    @FXML
    private Label nivelMaxLbl;
    @FXML
    private Label tiempoLbl;
    @FXML
    private Label premioLbl;
    @FXML
    private VBox enunciadosVB;
    @FXML
    private VBox nivelesVB;
    @FXML
    private VBox comodinesVB;
    
    private Reporte reporte; //atributo reporte

    /**
     * Initializes the controller class.
     */

    //Metodo que que inicializa los metodos
    public void initialize() {
        deserializarReporte();
        rellenarInfo();
    }    
    
    //Metodo que deserializa el reporte seleccionado del menu anterior
    public void deserializarReporte(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/reporteSeleccionado.ser"))){
            reporte = (Reporte) in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    //Metodo que muestra por pantalla la informacion del reporte seleccionado
    public void rellenarInfo(){
        fechaLbl.setText(reporte.getFecha());
        participanteLbl.setText(reporte.getEstudianteParticipante());
        compaLbl.setText(reporte.getEstudianteApoyo());
        nivelMaxLbl.setText(""+reporte.getNivelMaximoAlcanzado());
        tiempoLbl.setText(reporte.getTiempoPasados());
        premioLbl.setText(reporte.getPremio());
        
        //atributos para controlar las preguntas y niveles
        int numPregunta = 1;
        int nivel = 1;
        int controlador = 1;
        //for de los nombres de los comodines usados
        for(String comodin: reporte.getComodinesUsados()){
            enunciadosVB.getChildren().add(new Label("Pregunta "+numPregunta));
            comodinesVB.getChildren().add(new Label(comodin));
            nivelesVB.getChildren().add(new Label(""+nivel));
            //Condicional para controlar las sgts preguntas y niveles
            if(reporte.getPregXNivel() == controlador){
                nivel++;
                controlador = 1;
            } else controlador++;
            numPregunta++;
        }    
    }
    
    //Metodo que regresa al menu anterior
    @FXML
    public void regresarMenuAnterior() throws IOException {
        App.setRoot("reporte");
    }
}
