module com.example.calculadora {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens controlador to javafx.fxml;
    exports controlador;
    exports modelo;
    opens modelo to javafx.fxml;
}