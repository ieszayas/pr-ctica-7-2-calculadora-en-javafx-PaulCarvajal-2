package controlador;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ControladorMain {
    @FXML
    private BorderPane rootPane;

    @FXML
    public void cargarCalculadoraNormal() {
        try {
            Parent normal = FXMLLoader.load(getClass().getResource("calculadora.fxml"));
            rootPane.setCenter(normal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarCalculadoraCientifica() {
        try {
            Parent cientifica = FXMLLoader.load(getClass().getResource("calculadoraCientifica.fxml"));
            rootPane.setCenter(cientifica);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
