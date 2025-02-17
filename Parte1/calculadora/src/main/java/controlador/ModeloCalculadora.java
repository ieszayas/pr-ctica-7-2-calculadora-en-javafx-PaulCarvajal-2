package controlador;

public class ModeloCalculadora {
    private int numero1;
    private int numero2;
    private int resultado;

    private String operacion = "";

    public ModeloCalculadora(){

    }

    public int getNumero1() {
        return numero1;
    }

    public int getNumero2() {
        return numero2;
    }

    public int getResultado() {
        return resultado;
    }

    public void setNumero1(int numero1) {
        this.numero1 = numero1;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }


}
