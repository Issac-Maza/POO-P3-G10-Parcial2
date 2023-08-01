package com.mycompany.millonariogameapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {


    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
