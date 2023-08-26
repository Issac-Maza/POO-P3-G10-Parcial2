/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class ConfigurarTerminoController implements Initializable {
    @FXML
    private TableView<TerminoAcademico> tblTérminos;
    @FXML
    private TableColumn<TerminoAcademico,Integer> colAnio;
    @FXML
    private TableColumn<TerminoAcademico,Integer> colPao;
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnvolver;
    
    private TerminoAcademico tSelec;
    private ObservableList<TerminoAcademico> termMostrar= FXCollections.observableArrayList();
    private ArrayList<TerminoAcademico> lstTerm;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deserializarTerminos();
        almacenarTerminos();
        tblTérminos.setItems(termMostrar);
        this.colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        this.colPao.setCellValueFactory(new PropertyValueFactory("numero"));
    }    
    
    @FXML
    private void seleccionar(MouseEvent event) {
        tSelec=this.tblTérminos.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void volver(ActionEvent event)  throws IOException{
        App.setRoot("menuAdministrarTerminos");
    }

    @FXML
    private void cargar(ActionEvent event) {
        tSelec = this.tblTérminos.getSelectionModel().getSelectedItem();
        if(tSelec == null){
            
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un término");
        }else{
            int anio=tSelec.getAnio();
            int numero=tSelec.getNumero();
            if(MenuIngresarTerminoController.validarAnio(anio,numero)){
                //se serializa el término para proceder a cargarlo
                serializarTerminoParaJuego();
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Operación Existosa");
                alert.setHeaderText("Se ha cargado el término");
                alert.showAndWait();
            }else{
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se ha cargado el Término");
                alert.setHeaderText("Valores no permitidos");
                alert.showAndWait();
            }
        }
    }
    
    public void serializarTerminoParaJuego(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/terminoSeleccionado.ser"))){
            out.writeObject(tSelec);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public void deserializarTerminos() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("archivos/terminos.ser"))){
            lstTerm = (ArrayList<TerminoAcademico>)in.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void almacenarTerminos(){
        for (TerminoAcademico t: lstTerm){
            termMostrar.add(t);
        }
    }
}
