module com.mycompany.millonariogameapp {
    opens com.mycompany.millonariogameapp.modelo to javafx.base, javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.millonariogameapp to javafx.fxml,javafx.base;
    exports com.mycompany.millonariogameapp;
}
