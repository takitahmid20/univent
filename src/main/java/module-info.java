module com.univent.univent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.univent.univent to javafx.fxml;
    exports com.univent.univent;
}