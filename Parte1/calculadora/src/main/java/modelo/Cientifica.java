package modelo;

public class Cientifica {
    // OPERACIONES TRIGONOMÉTRICAS (se asume que el ángulo está en grados)

    public double calcularSeno(double angulo) {
        return Math.sin(Math.toRadians(angulo));
    }
    public double calcularCoseno(double angulo) {
        return Math.cos(Math.toRadians(angulo));
    }
    public double calcularTangente(double angulo) {
        return Math.tan(Math.toRadians(angulo));
    }
    public double calcularLogaritmoBase10(double valor) {
        // Verifica que el valor sea mayor a 0, de lo contrario lanza una excepción.
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a 0 para calcular el logaritmo base 10");
        }
        // Calcula y retorna el logaritmo base 10 utilizando Math.log10.
        return Math.log10(valor);
    }
}


