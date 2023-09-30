module com.example.ingensql {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ingensql to javafx.fxml;
    exports com.example.ingensql;
}