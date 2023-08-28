/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class EditarMateriaController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnModificar;
    @FXML
    private Label textCodigo;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textNivel;
    @FXML
    private TableColumn<Materia, String> columnaCodigo;
    @FXML
    private TableColumn<Materia, String> columnaNombre;
    @FXML
    private TableColumn<Materia, Integer> columnaNivel;
    @FXML
    private TableColumn<Materia, String> columnaPreguntas;
    @FXML
    private TableColumn<Materia, String> columnaParalelos;
    
    private ObservableList<Materia> materias;
    @FXML
    private TableView<Materia> tablaMateria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materias = FXCollections.observableArrayList();
        //Buscamos si existe un archivo searilizado de materias y los agregamos a la lista observable "materias"
        File file = new File("archivos/materias.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                materias.addAll((List<Materia>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        //Una vez desializado agregamos todos estos items a la tabla Materia
        tablaMateria.setItems(materias);
        
        //A partir de la tabla Materia, le agremaos los valores en cada columna, usando setCellValueFactory e 
        //internamente creamos un objeto PropertyValueFactory, el cual dentro de un objeto Materia
        //buscara el nombre del atributo, en columCodigo, buscara en Materia una variable de nombre codigo
        this.columnaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        
        //columNombre, buscara en Materia una variable de nombre "nombre"
        this.columnaNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        //columNivel, buscara en Materia una variable de  nombre "cantidadNiveles"
        this.columnaNivel.setCellValueFactory(new PropertyValueFactory("cantidadNiveles"));
        
        //columParalelos, buscara en Materia una variable de nombre "paralelos"
        //Pero en este caso en la tabla mostraremos si dicha materia posee paralelos si o no, entonces usando una expresion lamba
        //en caso de que existe Paralelos usara un formato string, ejemplo "P-3,P-4..."
        //En caso de que no exista mostrara un mensaje "NO POSEE PARALELOS"
        this.columnaParalelos.setCellValueFactory(cellData -> {
            //con el objeto cellData se hara un casting de tipo materia
            Materia materia = cellData.getValue();
            //con ese obejto materia obtendra una lista de lista de paralelos
            ArrayList<Paralelo> paralelos = materia.getParalelos();
            //creamos un obejto de tipo StringBuilder
            StringBuilder paraleloString = new StringBuilder();
            //Si la lista de paralelos no esta vacio, entoces comenzara un ciclo for
            if(!paralelos.isEmpty()){  
                for(int i = 0; i<paralelos.size();i++){
                    //Aqui buscamos por indice la cantidad de paralelos 
                    //En caso de "i" sea mayor que 0 
                    //nos indica de que existe más de un elemento 
                    //Entonces agregara una coma por cada elemento agregado en el StringBuilder
                    if(i>0){
                        paraleloString.append(";").append("P-").append(paralelos.get(i).getNumero());
                        
                    //En caso de que solo exista un solo elemento  solo 
                    //agregara P y el numero del paralelos
                        
                    }else{
                        paraleloString.append("P-").append(paralelos.get(i).getNumero());
                        //texto+=previo;
                    }
                }
                //Caso de que no existe elementos en la lista entonces mostrara un mensaje
            }else{
                return new ReadOnlyStringWrapper("NO POSEE PARALELOS");
            }
            
            //String numeroParalelo = "P-" + paralelo.getNumero();
            return new ReadOnlyStringWrapper(paraleloString.toString());
        });
        
        /*En la columna Preguntas se buscara el atributo 
        "preguntas" en la  Mtaeria en caso de que exista al menos una pregunta
        entonces mostrara el mensaje de Si en la tabla.
        Caso contrario mostrara No*/
        this.columnaPreguntas.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            boolean tienePreguntas = !materia.getLstOrdenadasxNivel().isEmpty();
            String respuesta = tienePreguntas ? "Sí" : "No";
            return new ReadOnlyStringWrapper(respuesta);
        });
        // TODO
        
        //Aqui realizamos una expresion lamba con el boton de volver, donde activamos el metodo guardarListaEnArchiv 
        //Y volvemos al menu Anterior
        btnVolver.setOnAction(eh ->{
            try {
                //Activamos el metodo guardar lista donde serializamos en unarchivo la lista 
                guardarListaEnArchivo(materias);
                
                //Y posteriormente volvemos al menu anterior
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    
    
    //En tabla de Materias podemos seleccionar una fila el cua contiene un objeto de tipo Materia
    @FXML
    private void seleccionarMateria(MouseEvent event) {
        //Con los metods getSelectionModel y getSelectedItem() seleccionados un obejto materia y los guardamos en la variable 
        // "mat"
        Materia mat = this.tablaMateria.getSelectionModel().getSelectedItem();
        
        //En caso de mat no sea vacio
        if(mat!=null){
            //Obtenemos el codigo,nombre y nivel de esa materia y los agregamos en los textfield de la ventana para poder editarla, 
            //conexcepcion de codigo ya que esa se mantendra inmute , ya que esta no se guarda en un textfield
            //sino que se guarda en un objeto Lambel
            this.textCodigo.setText(mat.getCodigo());
            this.textNombre.setText(mat.getNombre());
            this.textNivel.setText(String.valueOf(mat.getCantidadNiveles()));
        }
        
    }

    
    //Aqui es donde editamos la materia
    @FXML
    private void editarMateria(ActionEvent event) {
        //Creamos una lista de materias utilizando la lista observable materias
        ArrayList<Materia> lista = (ArrayList<Materia>) materias.stream().collect(Collectors.toList());
        //Seleccionamos un objeto materia
        Materia mat = this.tablaMateria.getSelectionModel().getSelectedItem();
        
        //Si no elegimos una materia dando click en un elemento vacio mostrara una Alerta
        if(mat == null){
            //Esta alerta pudo ser optimizada usando el metodo mostrarAlerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una Materia");
            alert.showAndWait();
            //Caso contrario obtenmos los textos de los textfield y lambes
        }else{
            try{
                String codigo = this.textCodigo.getText();
                String nombre = this.textNombre.getText();
                String niveltexto = this.textNivel.getText();
                
                //Vamos a confirmar que el textField de nivel
                int nivel;
                
                //Haremos un try catch donde verificaremos que agregamos un dato de tipo  int
                try {
                    nivel = Integer.parseInt(niveltexto);
                } catch (NumberFormatException e) {
                    mostrarAlerta("Formato incorrecto", "El nivel debe ser un número entero.", Alert.AlertType.ERROR);
                    return; // Sale del método sin guardar la materia
                }
                
                //Creamos un nuevo objeto materia que posteriemente reemplaza al objeto de materia seleccionado
                Materia aux = new Materia(codigo,nombre,nivel);
                
                //Verificamos que la materia editada no aparezca en la lista de materias para no repetir elementos
                if(!lista.contains(aux)){
                    //editamos la materia seleccionada con los datos nuevo
                    mat.setCodigo(codigo);
                    mat.setNombre(nombre);
                    mat.setCantidadNiveles(nivel);
                    
                    int indice = App.materias.indexOf(mat);
                    App.materias.set(indice, aux);
                    
                    //Refrescamos la tabla
                    this.tablaMateria.refresh();
                    
                    //Utilizamos el metodo mostrarAlerta para indicar que la materia fue editada
                    mostrarAlerta("Info","Materia modificada",Alert.AlertType.INFORMATION);
                    
                }else{
                    mostrarAlerta("Error","La Materia ya existe",Alert.AlertType.ERROR);
                }
            }catch (NumberFormatException e) {
                mostrarAlerta("Error","Formato incorrecto",Alert.AlertType.ERROR);
                
            }
        }
    }
    
    //Este el metodo de mostrarAlerta, tiene el tirulo, un mensje y el tipo de Alerta
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    //Con el boton cerrar simplemete activa el metodo guardarLista y nos regresamos al menu anterior
    private void guardarListaEnArchivo(ObservableList<Materia> listaMaterias) {
        ArrayList<Materia> lista = (ArrayList<Materia>) listaMaterias.stream().collect(Collectors.toList());
        
        
        try (ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("archivos/materias.ser"))) {
            out.writeObject(App.materias);
            
            out.close();
            

            System.out.println("Lista de materias guardada exitosamente en " + "archivos/materias.ser");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de materias en el archivo: " + e.getMessage());
        }
    }
    
}
