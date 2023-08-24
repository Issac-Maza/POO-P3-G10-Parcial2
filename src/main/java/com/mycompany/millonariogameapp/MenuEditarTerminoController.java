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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class MenuEditarTerminoController implements Initializable {

    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtTermino;
    @FXML
    private Button btnVolver;
    @FXML
    private TableView<TerminoAcademico> tablaTerminos;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colTermino;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablaTerminos.setItems(MenuIngresarTerminoController.termMostrar);
        this.colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        this.colTermino.setCellValueFactory(new PropertyValueFactory("numero"));
    }    

    @FXML
    private void volver(ActionEvent event)  throws IOException{
        App.setRoot("menuAdministrarTerminos");
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        TerminoAcademico t=this.tablaTerminos.getSelectionModel().getSelectedItem();
        if(t!=null){
            this.txtAnio.setText(t.getAnio()+"");
            this.txtTermino.setText(t.getNumero()+"");
        }
    }

    @FXML
    private void guardarCambios(ActionEvent event) throws IOException {
         TerminoAcademico t=this.tablaTerminos.getSelectionModel().getSelectedItem();
        if(t==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un término");
        }else{
            try{
            int anio=Integer.parseInt(txtAnio.getText());
            int numero=Integer.parseInt(txtTermino.getText());
            boolean bool=MenuIngresarTerminoController.validarAnio(anio,numero);
            TerminoAcademico newt=new TerminoAcademico(anio,numero);
            if(bool){
                if(!(MenuIngresarTerminoController.termMostrar.contains(newt))){
                    MenuIngresarTerminoController.termMostrar.add(newt);
                    MenuIngresarTerminoController.termMostrar.remove(t);
                    Alert alert =new Alert(AlertType.INFORMATION);
                    alert.setTitle("Operación Exitosa");
                    alert.setContentText(null);
                    alert.setHeaderText("Se ha ingresado el Término correctamente");
                    alert.showAndWait();
                }else{
                    Alert alert =new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("El Término ya existe");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }else{
                Alert alert =new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se ha ingresado el Término");
                alert.setHeaderText("Valores no permitidos");
                alert.showAndWait();
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
            Alert alert =new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ingrese correctamente los datos solicitados");
            alert.setHeaderText("No se ha ingresado el Término");
            alert.showAndWait();
        }
    }
    
}
}