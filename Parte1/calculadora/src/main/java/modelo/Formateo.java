package modelo;

public class Formateo {

    /**
     * Formatea un número decimal eliminando ceros innecesarios.
     * <p>
     * Si el número es un entero (por ejemplo, 5.0), se devuelve sin decimales (ejemplo: "5").
     * Si el número tiene decimales significativos (por ejemplo, 5.12345),
     * se devuelve con hasta 5 decimales eliminando ceros innecesarios al final.
     * </p>
     *
     * @param result El número decimal a formatear.
     * @return Una cadena con el número formateado sin ceros innecesarios.
     */
    public static String formatResult(double result) {
        // Si el número es un entero exacto (por ejemplo, 5.0), se devuelve sin decimales
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            // Formatea el número con hasta 5 decimales y elimina ceros innecesarios
            return String.format("%.5f", result).replaceAll("\\.?0*$", "");
        }
    }

}

