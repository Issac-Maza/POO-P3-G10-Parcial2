package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Estudiante;
import com.mycompany.millonariogameapp.modelo.Juego;
import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import com.mycompany.millonariogameapp.modelo.Pregunta;
import com.mycompany.millonariogameapp.modelo.Reporte;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Scene scene;
    public static ArrayList<Materia> materias = new ArrayList<>();
    public static ArrayList<Paralelo> paralelos= new ArrayList<>();   
    public static ArrayList<TerminoAcademico> terminosAcademico= new ArrayList<>();

    /**
     * @return the scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * @param aScene the scene to set
     */
    public static void setScene(Scene aScene) {
        scene = aScene;
    }

    static void setScene(String menuInicio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void start(Stage stage) throws IOException {
        setScene(new Scene(loadFXML("menuInicio"), 640, 480));
        stage.setScene(getScene());
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        getScene().setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void desealarizarMaterias(){
        try (FileInputStream fileIn = new FileInputStream("archivos/materias.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            App.materias = (ArrayList<Materia>) in.readObject();
            for(Materia m: App.materias ){
                System.out.println(m);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void desealarizarTermino(){
        try (FileInputStream fileIn = new FileInputStream("archivos/terminos.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            App.terminosAcademico = (ArrayList<TerminoAcademico>) in.readObject();
            for(TerminoAcademico m: App.terminosAcademico ){
                System.out.println(m);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void desealarizarParalelo(){
        try (FileInputStream fileIn = new FileInputStream("archivos/paralelos.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            App.paralelos = (ArrayList<Paralelo>) in.readObject();
            for(Paralelo m: App.paralelos ){
                System.out.println(m);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    

}