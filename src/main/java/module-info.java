module com.mycompany.millonariogameapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.millonariogameapp to javafx.fxml;
    exports com.mycompany.millonariogameapp;
}
