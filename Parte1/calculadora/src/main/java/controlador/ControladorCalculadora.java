package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import modelo.Formateo;
import modelo.ModeloCalculadora;

public class ControladorCalculadora {

    private String memoria = "";
    private boolean usandoMemoria = false;
    @FXML
    private Label pantalla;
    @FXML
    private Label operaciones; // Label para mostrar el historial

    private String operador = "";
    private double numero1 = 0;
    private boolean esOperacionRealizada = false;
    private boolean esperandoSegundoOperando = false;
    private String historialOperacion = "";

    private ModeloCalculadora modelo = new ModeloCalculadora();

    private ControladorMain controladorMain; // Asignado cuando se inicia la aplicación

    @FXML
    public void initialize() {
        pantalla.setText("");
        operaciones.setText("");
    }

    /**
     * Asigna la referencia al controlador principal para actualizar el historial global.
     *
     * @param controladorMain La instancia de ControladorMain que se utilizará para agregar operaciones al historial.
     */
    public void setControladorMain(ControladorMain controladorMain) {
        this.controladorMain = controladorMain;
    }


    @FXML
    protected void onClickCalcular() {
        String current = pantalla.getText().trim();
        if (current.isEmpty()) {
            mostrarAdvertencia("No hay número para calcular");
            return;
        }
        // Si no se ha seleccionado ningún operador, se muestra el mismo número en el historial con '='
        if (operador.isEmpty()) {
            operaciones.setText(current + "=");
            // Se mantiene el número en el display
            return;
        }

        // En caso de que se haya seleccionado un operador, se actualiza el historial y se realiza la operación
        operaciones.setText(historialOperacion + current + "=");
        realizarOperacion();
    }


    /**
     * Maneja el uso del botón de paréntesis para ingresar números negativos en el segundo operando.
     * Solo se permite cuando se está esperando el segundo operando y el display está vacío.
     */
    @FXML
    public void onClickParentesis() {
        String pantalla = this.pantalla.getText();

        // Si ya se inició el número negativo y no está cerrado, permitimos cerrar
        if (pantalla.startsWith("(-") && !pantalla.endsWith(")")) {
            if (pantalla.length() > 2) {  // Debe haber al menos un dígito después de "(-"
                this.pantalla.setText(pantalla + ")");
                operaciones.setText(historialOperacion + this.pantalla.getText());
            } else {
                mostrarAdvertencia("Ingrese el número negativo antes de cerrar el paréntesis.");
            }
            return;
        }

        // Si se está en modo de espera para el segundo operando, permitimos abrir el paréntesis
        if (esperandoSegundoOperando) {
            if (pantalla.isEmpty() || pantalla.equals("0")) {
                this.pantalla.setText("(-");
                operaciones.setText(historialOperacion + "(-");
            } else {
                mostrarAdvertencia("El número ya está iniciado. Para números negativos, inícielo con '(-'.");
            }
        } else {
            // En cualquier otro caso (por ejemplo, en el primer operando) mostramos el mensaje de error
            mostrarAdvertencia("Para número negativo en el primer operando, use el signo '-' al inicio.");
        }
    }

    @FXML
    public void onClickPunto(ActionEvent event) {
        limpiarSiError();
        String textoActual = pantalla.getText();
        if (textoActual.isEmpty()) {
            pantalla.setText("0.");
            return;
        }
        if (!textoActual.contains(".")) {
            pantalla.setText(textoActual + ".");
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                operaciones.setText(historialOperacion + pantalla.getText());
            }
        }
    }

    /**
     * Realiza la operación, considerando la posibilidad de que el segundo operando
     * se haya ingresado entre paréntesis (por ejemplo, "(-3)").
     */
    private void realizarOperacion() {
        String text = pantalla.getText();

        // Verificar y completar el manejo de paréntesis para números negativos (si corresponde)
        if (text.startsWith("(-") && !text.endsWith(")")) {
            mostrarAdvertencia("Cierre el paréntesis para el operando negativo.");
            return;
        }
        if (text.startsWith("(-") && text.endsWith(")")) {
            text = "-" + text.substring(2, text.length() - 1);
        }
        text = text.replace(',', '.');

        double segundoNumero = modelo.convertirNumero(text);
        if (Double.isNaN(segundoNumero)) {
            pantalla.setText("Error");
            return;
        }

        double resultado = modelo.calcular(numero1, segundoNumero, operador);
        if (!Double.isNaN(resultado)) {
            String resultStr = Formateo.formatResult(resultado);
            pantalla.setText(resultStr);
            // Solo se actualiza la memoria si se usó el botón de memoria en la operación.
            if (usandoMemoria) {
                memoria = resultStr;
                usandoMemoria = false;  // Reiniciamos la bandera para futuras operaciones.
            }

            // Construye la cadena completa que representa la operación, por ejemplo "5+3=8"
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            // Si se tiene una referencia al controlador principal, se agrega la operación al historial global.
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }
        } else {
            pantalla.setText("Error");
        }

        operador = "";
        esOperacionRealizada = true;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }

    /**
     * Maneja la pulsación de un operador.
     * Para el primer operando, si el display está vacío y se pulsa '-' se tratará como signo negativo.
     */
    public void onClickOperacion(ActionEvent event) {
        limpiarSiError();
        String oper = ((Button) event.getSource()).getText();

        // Si el display está vacío y se pulsa "-" para el primer operando, se trata como signo negativo
        if (pantalla.getText().isEmpty() && oper.equals("-")) {
            pantalla.setText("-");
            return;
        }

        // Si ya se está esperando el segundo operando, se interpreta que se desea cambiar el operador
        if (esperandoSegundoOperando) {
            operador = oper;
            if (!historialOperacion.isEmpty()) {
                // Reemplaza el último carácter (el operador anterior) por el nuevo.
                historialOperacion = historialOperacion.substring(0, historialOperacion.length() - 1) + operador;
            } else {
                historialOperacion = pantalla.getText() + operador;
            }
            operaciones.setText(historialOperacion);
            return;
        }

        if (esOperacionRealizada) {
            esOperacionRealizada = false;
        }

        String currentText = pantalla.getText().trim();
        if (currentText.equals("Error") || currentText.isEmpty() || !currentText.matches("[+-]?\\d*([.,]\\d+)?")) {
            currentText = "0";
        } else {
            currentText = currentText.replace(',', '.');
        }

        numero1 = modelo.convertirNumero(currentText);
        if (Double.isNaN(numero1)) {
            mostrarAdvertencia("Valor inválido para la operación");
            return;
        }

        operador = oper;
        // Actualizamos el historial con el operando actual y el operador
        historialOperacion = currentText + operador;
        operaciones.setText(historialOperacion);
        // Limpiamos el display para que el segundo operando empiece vacío
        pantalla.setText("");
        esperandoSegundoOperando = true;
    }


    public void onClickNumero(ActionEvent event) {
        limpiarSiError();
        String numero = ((Button) event.getSource()).getText();
        if (esperandoSegundoOperando) {
            // Si el display ya contiene "(-", no lo reemplazamos sino que concatenamos el dígito.
            if (pantalla.getText().startsWith("(-")) {
                pantalla.setText(pantalla.getText() + numero);
            } else {
                pantalla.setText(numero);
            }
            esperandoSegundoOperando = false;
            operaciones.setText(historialOperacion + pantalla.getText());
        } else {
            if (esOperacionRealizada) {
                pantalla.setText(numero);
                operaciones.setText("");
                esOperacionRealizada = false;
            } else {
                if (pantalla.getText().equals("0") || pantalla.getText().isEmpty()) {
                    pantalla.setText(numero);
                } else {
                    pantalla.setText(pantalla.getText() + numero);
                }
                if (!historialOperacion.isEmpty()) {
                    operaciones.setText(historialOperacion + pantalla.getText());
                }
            }
        }
    }


    public void onClickBorrar() {
        String texto = pantalla.getText();
        if (!texto.isEmpty()) {
            String nuevoTexto = texto.substring(0, texto.length() - 1);
            pantalla.setText(nuevoTexto);
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                operaciones.setText(historialOperacion + nuevoTexto);
            }
        }
    }

    public void onClickBorrarTodo() {
        pantalla.setText("");
        operaciones.setText("");
        operador = "";
        numero1 = 0;
        esOperacionRealizada = false;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }

    private void limpiarSiError() {
        if (pantalla.getText().equals("Error")) {
            pantalla.setText("");
        }
    }
    @FXML
    public void onClickCambiarSigno(ActionEvent event) {
        String current = pantalla.getText().trim();

        // Si el display está vacío, no hacemos nada
        if(current.isEmpty()) {
            return;
        }

        String parsedNumber = current;
        // Si el número está en formato con paréntesis, por ejemplo "(-3)", lo convertimos a "-3"
        if(current.startsWith("(-") && current.endsWith(")")) {
            parsedNumber = "-" + current.substring(2, current.length() - 1);
        }

        try {
            // Convertimos el número (reemplazamos la coma por punto si fuera necesario)
            double value = Double.parseDouble(parsedNumber.replace(',', '.'));
            double toggled = -value;
            // Formateamos el resultado utilizando tu método de utilidades
            String formatted = Formateo.formatResult(toggled);
            pantalla.setText(formatted);

            // Opcional: si estás mostrando el historial y deseas actualizarlo, puedes hacerlo aquí.
            // Por ejemplo, si el historial contiene el número actual, reemplázalo.
            // En este ejemplo se deja sin modificar el label_historial.

        } catch(NumberFormatException e) {
            mostrarAdvertencia("No se puede cambiar el signo");
        }
    }

    /**
     * Muestra un "toast" no modal que se autodesvanece después de 2 segundos.
     */
    private void mostrarAdvertencia(String mensaje) {
        Platform.runLater(() -> {
            Popup popup = new Popup();
            Label toastLabel = new Label(mensaje);
            toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"
                    + " -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5;");
            popup.getContent().add(toastLabel);
            Stage stage = (Stage) pantalla.getScene().getWindow();
            popup.show(stage);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> popup.hide());
            delay.play();
        });
    }
    @FXML
    public void onClickSumarMemoria(ActionEvent event) {
        String currentText = pantalla.getText().trim();

        if (currentText.isEmpty()) {
            mostrarAdvertencia("No hay nada para guardar en la memoria.");
            return;
        }

        try {
            // Convertir el número actual (reemplazando coma por punto si es necesario)
            double currentNumber = modelo.convertirNumero(currentText.replace(',', '.'));
            double memoryNumber;

            if (memoria.isEmpty()) {
                // Primera vez: se guarda el número actual en memoria.
                memoryNumber = currentNumber;
            } else {
                // Usos posteriores: se suma el número actual con el valor guardado en memoria.
                double storedNumber = modelo.convertirNumero(memoria.replace(',', '.'));
                memoryNumber = storedNumber + currentNumber;
            }

            // Formatear el nuevo valor de memoria.
            String newMemory = Formateo.formatResult(memoryNumber);
            memoria = newMemory;
            usandoMemoria = true;  // Se marca que se está usando la memoria en esta operación.

            // Se actualiza el display y el historial para reflejar la acción.
            pantalla.setText(newMemory);
            operaciones.setText("MR = " + newMemory);
            mostrarAdvertencia("Memoria actualizada: " + newMemory);
        } catch (Exception e) {
            mostrarAdvertencia("Error al procesar la memoria.");
        }
    }
    @FXML
    public void onClickBorrarMemoria(ActionEvent event) {
        memoria = "0";
        operaciones.setText("MR = 0");
        mostrarAdvertencia("Memoria reiniciada a 0");
    }

    @FXML
    public void onClickRecuperarMemoria(ActionEvent event) {
        if (memoria.isEmpty()) {
            mostrarAdvertencia("No hay memoria almacenada.");
            return;
        }

        // Si se está esperando el segundo operando (por ejemplo, después de pulsar un operador)
        if (esperandoSegundoOperando) {
            // Se inserta el valor de la memoria como el segundo operando
            pantalla.setText(memoria);
            operaciones.setText(historialOperacion + memoria);
            esperandoSegundoOperando = false; // Se marca que ya se ha ingresado el segundo operando
        } else {
            // Si no se está en medio de una operación, simplemente se muestra la memoria
            pantalla.setText(memoria);
            operaciones.setText("MR = " + memoria);
        }
    }


    public void onClickDolar(ActionEvent actionEvent) {

    }
}