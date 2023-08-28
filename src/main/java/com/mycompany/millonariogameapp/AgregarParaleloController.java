/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * FXML Controller class
 *
 * @author maza-
 */
public class AgregarParaleloController implements Initializable {

    @FXML
    private ComboBox<Materia> comboMaterias;
    @FXML
    private TextField textTerminoAcademico;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<TerminoAcademico> comboTermino;
    /*private Button btnCargarArchivo;
    private TextField labelRutaArchivo;*/
    
    //Este eran variables que hiba a usar para crear una tabla de Mtaeria, Termino Academico y Paralelo
    ObservableList<Materia> materiaObservableList;
    ObservableList<TerminoAcademico> terminoObservableList;
    ObservableList<Paralelo> paraleloObservableList;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Aqui hiba a inicializar esa lista observable para crear las tablas
        materiaObservableList = FXCollections.observableArrayList(App.materias);
        terminoObservableList = FXCollections.observableArrayList(App.terminosAcademico);
        paraleloObservableList =  FXCollections.observableArrayList();
        
        
        // TODO
        
        /*estás creando un TextFormatter para el campo de texto textTerminoAcademico. 
        La función lambda que pasaste al constructor del TextFormatter se ejecutará cada 
        vez que se realice un cambio en el texto del campo.*/
        /*estás creando un TextFormatter para el campo de texto textTerminoAcademico. 
        La función lambda que pasaste al constructor del TextFormatter se ejecutará cada vez 
        que se realice un cambio en el texto del campo.*/
        /* Si el nuevo texto cumple con esta expresión regular (es decir, contiene solo dígitos), 
        se permite el cambio (return change;),*/
        /*En resumen, este TextFormatter se utiliza para asegurarse de que solo se ingresen números en el campo textTerminoAcademico, 
        lo cual es útil para evitar que se ingresen valores no válidos en ese campo.*/
        TextFormatter<String> textFormatter= new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
            return change;
            }
            return null;
        });
        
        textTerminoAcademico.setTextFormatter(textFormatter);
        
        //
        //En una lista de materias que tenemos en la clase App realizamos un for each y los agreamos en un combobox
        for(Materia m: App.materias) {
            comboMaterias.getItems().add(m);
        }
        
        //En una lista de Termino Academico que tenemos en la clase App realizamos un for each y los agreamos en un combobox
        for(TerminoAcademico t: App.terminosAcademico) {
            comboTermino.getItems().add(t);
        }
          
        /*comboMaterias.setItems(materiaObservableList);
        
        comboTermino.setItems(terminoObservableList);*/
        
        
        //Aqui realizamos una expresion lamba con el boton de volver, donde activamos el metodo guardarListaEnArchivo 
        //Y volvemos al menu Anterior
        btnVolver.setOnAction(eh -> {
            try {
                guardarListaEnArchivo(paraleloObservableList);
                App.setRoot("menuMateriaYParalelo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        //Aqui usamos una Expresion Lamba 
        btnGuardar.setOnAction(eh ->{
            //Aqui vereficamos que no tenga datos los texfield
            if (comboMaterias.getValue()!= null && comboTermino.getValue()!= null && !textTerminoAcademico.getText().trim().isEmpty()) {
                //una expresion boolean para verificar si no existe ese paralelo
                boolean existe= false;
                //obtenemos un objeto materia apartir del combobox
                Materia melegida=comboMaterias.getValue();
                //obtenemos un objeto TerminoAcademico apartir del combobox
                TerminoAcademico telegido=comboTermino.getValue();
                //Ingresamos el numero del paralelo
                int numPar= Integer.parseInt(textTerminoAcademico.getText());
                //Creamos el paralelo
                Paralelo pnuevo= new Paralelo(melegida, telegido, numPar);
                //verificamos que la materia escogida no esta vacia
                if(!melegida.getParalelos().isEmpty()){
                    //Caso de que no este vacia realizamos un for earch 
                    for (Paralelo p: melegida.getParalelos()) {
                        //Veridicamos que si esa materia ya no tiene ese paralelo
                        //Sea ese caso entonces cambiara a existe true
                        if(p.equals(pnuevo)) {
                            existe=true;
                        }
                    }
                }
                //Si boolean existe es true manda un mensaje de paralelo ya existente
                if (existe) {
                    mostrarAlerta("Paralelo Ya Existente", "El paralelo ingresado ya existe.", Alert.AlertType.WARNING);
                }
                //Caso contrario se agrega ese paralelo a esa Materia
                else{
                    melegida.getParalelos().add(pnuevo);/*: En esta línea, se agrega un nuevo objeto Paralelo (pnuevo) a la lista de paralelos asociados a la materia seleccionada (melegida). 
                    Esto significa que se está agregando un nuevo paralelo a la materia elegida por el usuario.*/
                    
                    /*Aquí, se está agregando el mismo objeto Paralelo (pnuevo) a una lista global llamada paralelos, que parece estar definida en la clase App.*/
                    App.paralelos.add(pnuevo);
                    
                    /*Esta línea establece el valor seleccionado en el combo box comboMaterias como nulo, 
                    lo que efectivamente deselecciona cualquier materia que esté actualmente seleccionada.*/
                    comboMaterias.setValue(null);
                    
                    /*De manera similar a la línea anterior, esta establece el valor seleccionado en el combo box 
                    comboTermino como nulo, deseleccionando cualquier término académico que esté actualmente seleccionado.*/
                    comboTermino.setValue(null);
                    
                    /*Esta línea establece el contenido del campo de texto textTerminoAcademico como nulo, 
                    lo que significa que se borra el valor que el usuario podría haber ingresado.*/
                    textTerminoAcademico.setText(null);
                    mostrarAlerta("Paralelo Creado", "Un nuevo paralelo ha sido creado.", Alert.AlertType.INFORMATION);
                    
                }
            }
            else {
                mostrarAlerta("Información No Completada", "No se ha elegido ninguna materia", Alert.AlertType.WARNING);
            }
        });
        
        
        //En resumen, esta sección de código permitiría a los usuarios cargar información de estudiantes desde un archivo CSV 
        //en un paralelo específico dentro de una materia seleccionada.
        
        /*btnCargarArchivo.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo de estudiantes");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
            
            
            // Mostrar el diálogo de selección de archivo
            java.io.File selectedFile = fileChooser.showOpenDialog(null);
            
            if (selectedFile != null) {
                String rutaArchivo = "archivos/" + comboMaterias.getValue().getCodigo() + "-" + textTerminoAcademico.getText() + ".csv";
                labelRutaArchivo.setText(rutaArchivo);
                
                 ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
                 
                 try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] campos = linea.split(",");
                        if (campos.length >= 3) {
                            String nMatricula = campos[0];
                            String nombre = campos[1];
                            String correo = campos[2];
                            Estudiante estudiante = new Estudiante(nMatricula, nombre, correo);
                            listaEstudiantes.add(estudiante);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                 
                 // Ahora puedes asignar esta lista de estudiantes al paralelo seleccionado
                Paralelo paraleloSeleccionado = new Paralelo(comboMaterias.getValue(), comboTermino.getValue(), Integer.parseInt(textTerminoAcademico.getText()));
                paraleloSeleccionado.setEstudiantes(listaEstudiantes);

                // Actualiza la lista de paralelos en la materia
                
                comboMaterias.getValue().getParalelos().add(paraleloSeleccionado);

                // Limpia los campos de la interfaz
                

                // Aquí puedes copiar el archivo a la ruta definida usando la clase Files
                // Por ejemplo: Files.copy(selectedFile.toPath(), Paths.get(rutaDefinida, nombreArchivo));
            }else{
                mostrarAlerta("Ningún Archivo Seleccionado", "No se ha seleccionado ningún archivo.", Alert.AlertType.WARNING);
            }
            
            comboMaterias.setValue(null);
            comboTermino.setValue(null);
            textTerminoAcademico.setText(null);
            labelRutaArchivo.setText("");
        });*/
    }
    
    //Este el metodo de mostrarAlerta, tiene el tirulo, un mensje y el tipo de Alerta
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    //Aqui es metodo guardar, el cual guarda la lista observable de materias y lo serializa en un archivo
    private void guardarListaEnArchivo(ObservableList<Paralelo> listaMaterias) {
        ArrayList<Paralelo> lista = (ArrayList<Paralelo>) listaMaterias.stream().collect(Collectors.toList());
        App.paralelos = lista;
        
        try (ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("archivos/paralelos.ser"))) {
            out.writeObject(App.paralelos);
            
            out.close();
            

            System.out.println("Lista de materias guardada exitosamente en " + "paralelos.ser");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista de materias en el archivo: " + e.getMessage());
        }
    }
   
}
