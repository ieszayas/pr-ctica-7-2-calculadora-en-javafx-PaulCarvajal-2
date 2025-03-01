package modelo;

import java.util.Locale;

public class Formateo {

    public static String formatResult(double result) {
        String formatted;
        if (result == (long) result) {
            // Formatea sin decimales
            formatted = String.format(Locale.US, "%.0f", result);
        } else {
            // Formatea con 5 decimales y elimina ceros finales (y un punto final si es necesario)
            formatted = String.format(Locale.US, "%.5f", result).replaceAll("\\.?0+$", "");
        }
        return formatted;
    }
}