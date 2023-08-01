module com.mycompany.millonariogameapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.millonariogameapp to javafx.fxml;
    exports com.mycompany.millonariogameapp;
}
