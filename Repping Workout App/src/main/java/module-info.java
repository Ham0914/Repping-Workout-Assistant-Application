module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires java.sql;

    opens application to javafx.fxml, javafx.base;
    exports application;
    exports model;
    exports controller;
}