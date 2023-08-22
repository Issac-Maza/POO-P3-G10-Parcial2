package com.mycompany.millonariogameapp;

import com.mycompany.millonariogameapp.modelo.Estudiante;
import com.mycompany.millonariogameapp.modelo.Juego;
import com.mycompany.millonariogameapp.modelo.Materia;
import com.mycompany.millonariogameapp.modelo.Paralelo;
import com.mycompany.millonariogameapp.modelo.Pregunta;
import com.mycompany.millonariogameapp.modelo.Reporte;
import com.mycompany.millonariogameapp.modelo.TerminoAcademico;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Scene scene;
    public static ArrayList<Materia> materias;
    public static ArrayList<Paralelo> paralelos;
    public static ArrayList<Pregunta> preguntas;
    public static ArrayList<Estudiante> estudiantes;
    public static ArrayList<Juego> juegos;
    public static ArrayList<Reporte> reportes;
    public static ArrayList<TerminoAcademico> terminosAcademico;
    public static String rutaMateria = "src/main/java/archivos/materias.txt";
    public static String rutaParalelo = "src/main/java/archivos/paralelos.txt";
    public static String rutaPregunta = "src/main/java/archivos/preguntas.txt";
    public static String rutaEstudiante = "src/main/java/archivos/";
    public static String rutaJuego = "src/main/java/archivos/juegos.txt";
    public static String rutaReporte = "src/main/java/archivos/reportes.txt";
    public static String rutaTerminoAcademico = "src/main/java/archivos/terminoAcademico.txt";

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

}