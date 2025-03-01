package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControladorAyuda {

    @FXML
    private TextArea textAreaAyuda;

    @FXML
    public void initialize() {
        String ayudaText = "Bienvenido a la Calculadora.\n\n" +
                "Esta aplicación te permite:\n\n" +
                "1. Realizar operaciones básicas y avanzadas (calculadora normal \n" +
                "y científica).\n" +
                "2. Convertir monedas usando datos en tiempo real de ExchangeRate-API.\n" +
                "3. Graficar funciones matemáticas: ingresa la función, define el rango\n " +
                "y el incremento, y visualiza la gráfica.\n" +
                "4. Consultar y gestionar el historial de operaciones: guarda, carga o\n" +
                " borra el historial desde el menú de Edición.\n\n";
        textAreaAyuda.setText(ayudaText);
    }
}