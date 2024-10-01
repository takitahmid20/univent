module com.univent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports com.univent.controller;
    exports database;
    exports com.univent.services;
    exports utils;

    opens com.univent to javafx.graphics, javafx.fxml;
    opens com.univent.controller to javafx.fxml;
}
