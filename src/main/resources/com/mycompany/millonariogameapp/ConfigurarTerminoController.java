/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*for(TerminoAcademico t: lstTerminos){
            termMostrar.add(t);
        }*/
        tblTérminos.setItems(MenuIngresarTerminoController.termMostrar);
        this.colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        this.colPao.setCellValueFactory(new PropertyValueFactory("numero"));
    }    
    
    @FXML
    private void seleccionar(MouseEvent event) {
        TerminoAcademico t=this.tblTérminos.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void volver(ActionEvent event)  throws IOException{
        App.setRoot("menuAdministrarTerminos");
    }

    @FXML
    private void cargar(ActionEvent event) {
        TerminoAcademico t=this.tblTérminos.getSelectionModel().getSelectedItem();
        if(t==null){
            
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un término");
        }else{
            int anio=t.getAnio();
            int numero=t.getNumero();
            if(MenuIngresarTerminoController.validarAnio(anio,numero)){
                //se serializa el término para proceder a cargarlo
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

    

}
