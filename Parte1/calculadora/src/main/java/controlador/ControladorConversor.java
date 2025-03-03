package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControladorConversor {
    // Constantes para la API: clave de acceso y URL base de la API de ExchangeRate
    private static final String API_KEY = "e75f26ec5adfeef703859094";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    // Elementos de la interfaz gráfica enlazados con FXML
    @FXML
    private TextField cantidad;          // Campo para introducir la cantidad a convertir

    @FXML
    private ComboBox<String> currencyFrom;   // ComboBox para seleccionar la moneda de origen

    @FXML
    private ComboBox<String> currencyTo;     // ComboBox para seleccionar la moneda de destino

    @FXML
    private Label resultado;             // Label para mostrar el resultado de la conversión

    // Lista observable que contendrá los códigos de las monedas
    ObservableList<String> monedas;

    // Método initialize: se ejecuta al cargar la interfaz, configura los ComboBox y asigna valores por defecto
    @FXML
    public void initialize() {
        iniciarMonedas();  // Inicializa la lista de monedas disponibles
        // Agrega todas las monedas al ComboBox de moneda de origen y destino
        currencyFrom.getItems().addAll(monedas);
        currencyTo.getItems().addAll(monedas);
        // Configura los valores por defecto: USD para origen y EUR para destino
        currencyFrom.setValue("USD");
        currencyTo.setValue("EUR");
    }

    // Método para obtener la tasa de cambio entre dos monedas usando la API de ExchangeRate
    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            // Construye la URL para la petición, utilizando la moneda de origen
            URL url = new URL(BASE_URL + fromCurrency);
            // Abre la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Se utiliza BufferedReader para leer la respuesta de la API línea por línea
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            // Acumula cada línea de la respuesta en el StringBuilder
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Convierte la respuesta (en formato JSON) a un objeto JSONObject
            JSONObject jsonObject = new JSONObject(response.toString());
            // Obtiene el objeto que contiene los tipos de cambio (conversion_rates)
            JSONObject conversionRates = jsonObject.getJSONObject("conversion_rates");

            // Devuelve la tasa de cambio correspondiente a la moneda destino
            return conversionRates.getDouble(toCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna -1 en caso de error para indicar fallo en la conversión
            return -1;
        }
    }

    // Metodo que se invoca al pulsar el botón "Convertir" en la interfaz
    @FXML
    public void convertir(ActionEvent event) {
        try {
            // Lee el texto del campo 'cantidad' y reemplaza comas por puntos para convertir a double
            String amountStr = cantidad.getText();
            double amount = Double.parseDouble(amountStr.replace(',', '.'));

            // Validación: si la cantidad es negativa, muestra una alerta y detiene la conversión
            if (amount < 0) {
                mostrarAlerta("Cantidad inválida", "No se permiten valores negativos.");
                return;
            }

            // Obtiene la moneda de origen y destino seleccionadas por el usuario
            String from = currencyFrom.getValue();
            String to = currencyTo.getValue();

            // Llama al metodo getExchangeRate para obtener la tasa de cambio
            double rate = getExchangeRate(from, to);
            if (rate < 0) {
                // Si la tasa es menor que 0, se considera que hubo un error en la conversión
                resultado.setText("Error en la conversión");
            } else {
                // Calcula la cantidad convertida y la muestra formateada con dos decimales y la moneda destino
                double converted = amount * rate;
                resultado.setText(String.format("%.2f %s", converted, to));
            }
        } catch (NumberFormatException e) {
            // Captura la excepción si la entrada no es un número válido y muestra una alerta
            mostrarAlerta("Entrada inválida", "Selecciona una cantidad válida antes de convertir.");
        }
    }

    // Método privado para mostrar una alerta de error o advertencia
    private void mostrarAlerta(String titulo, String mensaje) {
        // Se crea un objeto Alert del tipo WARNING
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);         // Título de la alerta
        alert.setHeaderText(null);        // Sin encabezado para la alerta
        alert.setContentText(mensaje);    // Mensaje de la alerta
        // Se muestra la alerta (se puede usar showAndWait() para bloquear la ejecución, aquí se usa show() para no bloquear)
        //alert.showAndWait();
        alert.show();
    }

    // Método privado para inicializar la lista de monedas disponibles en el conversor
    private void iniciarMonedas(){
        // Se crea una lista observable para manejar dinámicamente la lista de monedas en la interfaz
        monedas = FXCollections.observableArrayList();
        // Se agregan los códigos de moneda a la lista (por ejemplo, "USD", "EUR", "JPY", etc.)
        monedas.add("AED");
        monedas.add("AFN");
        monedas.add("ALL");
        monedas.add("AMD");
        monedas.add("ANG");
        monedas.add("AOA");
        monedas.add("ARS");
        monedas.add("AUD");
        monedas.add("AWG");
        monedas.add("AZN");
        monedas.add("BAM");
        monedas.add("BBD");
        monedas.add("BDT");
        monedas.add("BGN");
        monedas.add("BHD");
        monedas.add("BIF");
        monedas.add("BMD");
        monedas.add("BND");
        monedas.add("BOB");
        monedas.add("BRL");
        monedas.add("BSD");
        monedas.add("BTN");
        monedas.add("BWP");
        monedas.add("BYN");
        monedas.add("BZD");
        monedas.add("CAD");
        monedas.add("CDF");
        monedas.add("CHF");
        monedas.add("CLP");
        monedas.add("CNY");
        monedas.add("COP");
        monedas.add("CRC");
        monedas.add("CUP");
        monedas.add("CVE");
        monedas.add("CZK");
        monedas.add("DJF");
        monedas.add("DKK");
        monedas.add("DOP");
        monedas.add("DZD");
        monedas.add("EGP");
        monedas.add("ERN");
        monedas.add("ETB");
        monedas.add("EUR");
        monedas.add("FJD");
        monedas.add("FKP");
        monedas.add("FOK");
        monedas.add("GBP");
        monedas.add("GEL");
        monedas.add("GGP");
        monedas.add("GHS");
        monedas.add("GIP");
        monedas.add("GMD");
        monedas.add("GNF");
        monedas.add("GTQ");
        monedas.add("GYD");
        monedas.add("HKD");
        monedas.add("HNL");
        monedas.add("HRK");
        monedas.add("HTG");
        monedas.add("HUF");
        monedas.add("IDR");
        monedas.add("ILS");
        monedas.add("IMP");
        monedas.add("INR");
        monedas.add("IQD");
        monedas.add("IRR");
        monedas.add("ISK");
        monedas.add("JEP");
        monedas.add("JMD");
        monedas.add("JOD");
        monedas.add("JPY");
        monedas.add("KES");
        monedas.add("KGS");
        monedas.add("KHR");
        monedas.add("KID");
        monedas.add("KMF");
        monedas.add("KRW");
        monedas.add("KWD");
        monedas.add("KYD");
        monedas.add("KZT");
        monedas.add("LAK");
        monedas.add("LBP");
        monedas.add("LKR");
        monedas.add("LRD");
        monedas.add("LSL");
        monedas.add("LYD");
        monedas.add("MAD");
        monedas.add("MDL");
        monedas.add("MGA");
        monedas.add("MKD");
        monedas.add("MMK");
        monedas.add("MNT");
        monedas.add("MOP");
        monedas.add("MRU");
        monedas.add("MUR");
        monedas.add("MVR");
        monedas.add("MWK");
        monedas.add("MXN");
        monedas.add("MYR");
        monedas.add("MZN");
        monedas.add("NAD");
        monedas.add("NGN");
        monedas.add("NIO");
        monedas.add("NOK");
        monedas.add("NPR");
        monedas.add("NZD");
        monedas.add("OMR");
        monedas.add("PAB");
        monedas.add("PEN");
        monedas.add("PGK");
        monedas.add("PHP");
        monedas.add("PKR");
        monedas.add("PLN");
        monedas.add("PYG");
        monedas.add("QAR");
        monedas.add("RON");
        monedas.add("RSD");
        monedas.add("RUB");
        monedas.add("RWF");
        monedas.add("SAR");
        monedas.add("SBD");
        monedas.add("SCR");
        monedas.add("SDG");
        monedas.add("SEK");
        monedas.add("SGD");
        monedas.add("SHP");
        monedas.add("SLE");
        monedas.add("SOS");
        monedas.add("SRD");
        monedas.add("SSP");
        monedas.add("STN");
        monedas.add("SYP");
        monedas.add("SZL");
        monedas.add("THB");
        monedas.add("TJS");
        monedas.add("TMT");
        monedas.add("TND");
        monedas.add("TOP");
        monedas.add("TRY");
        monedas.add("TTD");
        monedas.add("TVD");
        monedas.add("TWD");
        monedas.add("TZS");
        monedas.add("UAH");
        monedas.add("UGX");
        monedas.add("USD");
        monedas.add("UYU");
        monedas.add("UZS");
        monedas.add("VES");
        monedas.add("VND");
        monedas.add("VUV");
        monedas.add("WST");
        monedas.add("XAF");
        monedas.add("XCD");
        monedas.add("XDR");
        monedas.add("XOF");
        monedas.add("XPF");
        monedas.add("YER");
        monedas.add("ZAR");
        monedas.add("ZMW");
        monedas.add("ZWL");
    }

}
