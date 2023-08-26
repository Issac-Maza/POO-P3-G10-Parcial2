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
    
    private ArrayList<TerminoAcademico> lstTerm;
    private ObservableList<TerminoAcademico> termMostrar= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deserializarTerminos();
        almacenarTerminos();
        tablaTerminos.setItems(termMostrar);
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
    private void guardarCambios() {
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
                    if(!(termMostrar.contains(newt))){
                        /*lstTerm.add(newt);
                        termMostrar.add(newt);
                        lstTerm.remove(t);
                        termMostrar.remove(t);*/
                        lstTerm.set(lstTerm.indexOf(t), newt);
                        termMostrar.set(termMostrar.indexOf(t), newt);
                        serializarTerminos();
                        tablaTerminos.setItems(termMostrar);
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
    
    public void almacenarTerminos(){
        for (TerminoAcademico t: lstTerm){
            termMostrar.add(t);
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
    
    public void serializarTerminos(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("archivos/terminos.ser"))){
            out.writeObject(lstTerm);
            out.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}