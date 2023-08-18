package com.mycompany.millonariogameapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

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

    private static Scene scene;

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