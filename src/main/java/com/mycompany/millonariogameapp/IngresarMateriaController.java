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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class IngresarMateriaController implements Initializable {

    @FXML
    private TextField textfieldcodigo;
    @FXML
    private TextField textfieldNombre;
    @FXML
    private TextField textfieldNivel;
    @FXML
    private Button btnguardar;
    @FXML
    private Button btnVolver;
    @FXML
    private TableColumn<Materia, String> columCodigo;
    @FXML
    private TableColumn<Materia, String> columNombre;
    @FXML
    private TableColumn<Materia, Integer> columNivel;
    @FXML
    private TableColumn<Materia, String> columParalelos;
    @FXML
    private TableColumn<Materia, String> columPreguntas;
    @FXML
    private TableView<Materia> tablaMateria;
    
    private ObservableList<Materia> materias;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materias = FXCollections.observableArrayList();
        
        //Aqui en este codigo vamos a ver si existe el archivo materias.ser para deserializarlo
        File file = new File("archivos/materias.ser");
        //si existe lo deserializara y lo guardara en la lista obserbalble "materia"
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                materias.addAll((List<Materia>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        //una vez deserializado y guardado en la lista observable lo agregamos en la de tabla que maneja objetos de materia
        tablaMateria.setItems(materias);
        //A partir de la tabla Materia, le agremaos los valores en cada columna, usando setCellValueFactory e 
        //internamente creamos un objeto PropertyValueFactory, el cual dentro de un objeto Materia
        //buscara el nombre del atributo, en columCodigo, buscara en Materia una variable de nombre codigo
        this.columCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        //columNombre, buscara en Materia una variable de nombre "nombre"
        this.columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        //columNivel, buscara en Materia una variable de  nombre "cantidadNiveles"
        this.columNivel.setCellValueFactory(new PropertyValueFactory("cantidadNiveles"));
        //columParalelos, buscara en Materia una variable de nombre "paralelos"
        //Pero en este caso en la tabla mostraremos si dicha materia posee paralelos si o no, entonces usando una expresion lamba
        //en caso de que existe Paralelos usara un formato string, ejemplo "P-3,P-4..."
        //En caso de que no exista mostrara un mensaje "NO POSEE PARALELOS"
        this.columParalelos.setCellValueFactory(cellData -> {
            //con el objeto cellData se hara un casting de tipo materia
            Materia materia = cellData.getValue();
            //con ese obejto materia obtendra una lista de lista de paralelos
            ArrayList<Paralelo> paralelos = materia.getParalelos();
            //creamos un obejto de tipo StringBuilder
            StringBuilder paraleloString = new StringBuilder();
            //Si la lista de paralelos no esta vacio, entoces comenzara un ciclo for
            if(!paralelos.isEmpty()){ 
                //Realizamos un for para la lista
                for(int i = 0; i<paralelos.size();i++){
                    //Aqui buscamos por indice la cantidad de paralelos 
                    //En caso de "i" sea mayor que 0 
                    //nos indica de que existe más de un elemento 
                    //Entonces agregara una coma por cada elemento agregado en el StringBuilder
                    if(i>0){
                       
                        paraleloString.append(";P-").append(paralelos.get(i).getNumero());
                        
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
        this.columPreguntas.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            boolean tienePreguntas = !materia.getLstOrdenadasxNivel().isEmpty();
            String respuesta = tienePreguntas ? "Sí" : "No";
            return new ReadOnlyStringWrapper(respuesta);
        });
        // TODO
    }  
    
    /*Aqui se hace el metodo agregarMateria con el boton agregar*/

    @FXML
    private void agregarMateria(ActionEvent event) {
        /*Obtenmos los textos de los textField*/
        String codigo = textfieldcodigo.getText();
        String nombre = textfieldNombre.getText();
        
        String nivelText = textfieldNivel.getText();
        //Integer nivel = Integer.parseInt(txtNivel.getText());
        //En caso de que falte uno o más textos vacios mostrara una alarma de tipo error
        if (codigo.isEmpty() || nombre.isEmpty() || nivelText.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor complete todos los campos.", Alert.AlertType.ERROR);
            return; // Sale del método sin guardar la materia
        }
        //Vamos a confirmar que el textField de nivel
        int nivel;
        //Haremos un try catch donde verificaremos que agregamos un dato de tipo  int
        try {
            nivel = Integer.parseInt(textfieldNivel.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "El nivel debe ser un número entero.", Alert.AlertType.ERROR);
            return; // Sale del método sin guardar la materia
        }
        //Todo esto se guarda en un objeto Materia
        Materia materiaPD = new Materia(codigo,nombre,nivel);
        //Un if que verificara si la materia ya existe o o no
        if(!this.materias.contains(materiaPD)){
            //En caso de no exista la materia se agrega a la lista materias y despues a la tabla Materia
            App.materias.add(materiaPD);
            this.materias.add(materiaPD);   
            this.tablaMateria.setItems(materias);
            //Finalmente muestra un mensaje de  "La materia ha sido ingresada exitosamente."
            mostrarAlerta("Materia ingresada", "La materia ha sido ingresada exitosamente.", Alert.AlertType.INFORMATION);
            
            
            //Luego finalmente limpia los textField
            textfieldcodigo.clear();
            textfieldNombre.clear();
            textfieldNivel.clear();
        //Caso de que la materia existe solo se muestra un mensaje    
        }else{
            mostrarAlerta("Materia existente", "La materia ya existe en el sistema.", Alert.AlertType.ERROR);
            
            textfieldcodigo.clear();
            textfieldNombre.clear();
            textfieldNivel.clear();
            return;
        }
        
        
        /*btnGuardar.setOnAction(e -> {
            
        });*/
        // Limpiar los campos del formulario
        
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
    @FXML
    private void cerrarPrograma(ActionEvent event) throws IOException {
        
        guardarListaEnArchivo(materias);

        App.setRoot("menuMateriaYParalelo");
  
    }
    
    //Aqui es metodo guardar, el cual guarda la lista observable de materias y lo serializa en un archivo
    private void guardarListaEnArchivo(ObservableList<Materia> listaMaterias) {        
        //Converitmos la lista observable en una lista común
        ArrayList<Materia> lista = (ArrayList<Materia>) listaMaterias.stream().collect(Collectors.toList());
        App.materias = lista;
        try (ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("archivos/materias.ser"))) {
            out.writeObject(App.materias);
            
            out.close();
            

            System.out.println("Lista de materias guardada exitosamente en " + "materias.ser");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de materias en el archivo: " + e.getMessage());
        }
    }
    
}
