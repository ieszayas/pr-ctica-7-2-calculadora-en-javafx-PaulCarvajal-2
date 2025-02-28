package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControladorConversor {
    private static final String API_KEY = "e75f26ec5adfeef703859094";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    @FXML
    private TextField cantidad;

    @FXML
    private ComboBox<String> currencyFrom;

    @FXML
    private ComboBox<String> currencyTo;

    @FXML
    private Label resultado;


    ObservableList<String> monedas;
    // Método initialize para configurar los ComboBox con monedas
    @FXML
    public void initialize() {
        iniciarMonedas();
        // Puedes agregar más monedas según necesites
        currencyFrom.getItems().addAll(monedas);
        currencyTo.getItems().addAll(monedas);
        // Valores por defecto
        currencyFrom.setValue("USD");
        currencyTo.setValue("EUR");
    }

    // Método que realiza la solicitud a la API y obtiene el tipo de cambio
    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            URL url = new URL(BASE_URL + fromCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject conversionRates = jsonObject.getJSONObject("conversion_rates");

            return conversionRates.getDouble(toCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Método que se invoca al pulsar el botón "Convertir"
    @FXML
    public void convertir(ActionEvent event) {
        try {
            String amountStr = cantidad.getText();
            double amount = Double.parseDouble(amountStr.replace(',', '.'));
            String from = currencyFrom.getValue();
            String to = currencyTo.getValue();

            double rate = getExchangeRate(from, to);
            if (rate < 0) {
                resultado.setText("Error en la conversión");
            } else {
                double converted = amount * rate;
                resultado.setText(String.format("%.2f", converted));
            }
        } catch (NumberFormatException e) {
            resultado.setText("Seleccciona una cantidad antes de convertir");
        }
    }
    private void iniciarMonedas(){
        monedas = FXCollections.observableArrayList();
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
