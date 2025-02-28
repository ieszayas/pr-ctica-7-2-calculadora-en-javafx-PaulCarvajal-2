package controlador;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorMain implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    public void cargarCalculadoraNormal() {
        try {
            Parent normal = FXMLLoader.load(getClass().getResource("/vista/Calculadora.fxml"));
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarCalculadoraNormal();
    }

    public void cargarConversor(ActionEvent actionEvent) {
        try {
            Parent conversor = FXMLLoader.load(getClass().getResource("/vista/Conversor.fxml"));
            borderPane.setCenter(conversor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
