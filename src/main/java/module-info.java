module com.univent.univent {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.univent.univent to javafx.fxml;
    exports com.univent.univent;
}