module com.example.calculadora {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens controlador to javafx.fxml;
    exports controlador;
}