package controlador;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ControladorMain {
    @FXML
    private BorderPane borderPane;

    @FXML
    public void cargarCalculadoraNormal() {
        try {
            Parent normal = FXMLLoader.load(getClass().getResource("/vista/vista.fxml"));
            borderPane.setCenter(normal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarCalculadoraCientifica() {
        try {
            Parent cientifica = FXMLLoader.load(getClass().getResource("/vista/Cientifica.fxml"));
            borderPane.setCenter(cientifica);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
