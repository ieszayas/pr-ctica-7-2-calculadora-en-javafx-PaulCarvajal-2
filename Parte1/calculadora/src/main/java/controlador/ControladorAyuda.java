package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControladorAyuda {

    @FXML
    private TextArea textAreaAyuda;

    @FXML
    public void initialize() {
        String ayudaText =
                "La aplicacion que esta utilizando le permite realizar varias operaciones: \n\n" +
                "1. Operaciones de una calculadora normal y una cientifica.\n" +
                "2. Gracias a una API ver el cambio de las monedas en tiempo real.\n" +
                "3. Poder graficar funciones matematicas\n" +
                "4. Gestionar el historial de operaciones\n\n" +
                "Gracias por utilizar la aplicacion";
        textAreaAyuda.setText(ayudaText);
    }
}