/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.millonariogameapp;

import static com.mycompany.millonariogameapp.MenuIngresarTerminoController.lstTerminos;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    private ObservableList<TerminoAcademico> termMostrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        termMostrar=FXCollections.observableArrayList();
        /*for(TerminoAcademico t: lstTerminos){
            termMostrar.add(t);
        }*/
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
         
    }
    
}
