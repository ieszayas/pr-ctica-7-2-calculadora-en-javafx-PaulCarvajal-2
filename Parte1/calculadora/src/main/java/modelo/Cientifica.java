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

    // OPERACIONES EXPONENCIALES

    public double calcularExponencial(double exponente) {
        return Math.exp(exponente);
    }

    public double calcularLogaritmo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a 0 para calcular el logaritmo");
        }
        return Math.log(valor);
    }
}


