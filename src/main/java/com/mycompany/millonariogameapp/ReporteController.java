/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Reporte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class ReporteController implements Serializable {

    @FXML
    private VBox fechasVB;
    @FXML
    private VBox participantesVB;
    @FXML
    private VBox nivelMaxVB;
    @FXML
    private VBox tiempoVB;
    @FXML
    private VBox pregAlcVB;
    @FXML
    private VBox contComodinVB;
    @FXML
    private VBox premiosVB;
    @FXML
    private VBox opcionesVB;
    
    //Atributo lista reportes
    private ArrayList<Reporte> lstReportes;

    /**
     * Initializes the controller class.
     */
    
    //Se inicializa los metodos deseados
    public void initialize() {
        lstReportes = new ArrayList<>();
        deserializarReportes();
        rellenarVBs();
    }    
    
    //Metodo que deseriliza todos los reportes
    public void deserializarReportes() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/reportes.ser"))){
            lstReportes = (ArrayList<Reporte>)in.readObject(); // asignacion al atributo
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    //Metodo para mostrar la informacion de los reportes
    public void rellenarVBs(){
        for(Reporte r: lstReportes){
            fechasVB.getChildren().add(new Label(r.getFecha()));
            participantesVB.getChildren().add(new Label(r.getEstudianteParticipante()));
            nivelMaxVB.getChildren().add(new Label(""+r.getNivelMaximoAlcanzado()));
            tiempoVB.getChildren().add(new Label(r.getTiempoPasados()));
            pregAlcVB.getChildren().add(new Label(""+r.getNumPregAlcanzada()));
            contComodinVB.getChildren().add(new Label(""+r.getContadorComodines()));
            premiosVB.getChildren().add(new Label(r.getPremio()));
            Button b = new Button("Ver más"); //Creacion de un boton
            b.setOnAction(eh -> { //Se le asigna un accion especifica junto al reporte del for
                serializarReporteSeleccionado(r);
                try {
                    App.setRoot("detalleJuego");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            opcionesVB.getChildren().add(b);
        }
    }
    
    //Metodo que serializa el reporte seleccionado
    public void serializarReporteSeleccionado(Reporte r){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/reporteSeleccionado.ser"))){
            out.writeObject(r);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //Metodo que regresa al menu anterior
    @FXML
    private void regresarMenuAnterior(ActionEvent event) throws IOException {
        App.setRoot("menuInicio");
    }
}
