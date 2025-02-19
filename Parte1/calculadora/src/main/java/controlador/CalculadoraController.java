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

public class CalculadoraController {
    private String memoria = "";
    private boolean usandoMemoria = false;
    @FXML
    private Label Pantalla;
    @FXML
    private Label Operaciones; // Label para mostrar el historial

    private String operador = "";
    private double primerNumero = 0;
    private boolean esOperacionRealizada = false;
    private boolean esperandoSegundoOperando = false;
    private String historialOperacion = "";

    private ModeloCalculadora modelo = new ModeloCalculadora();

    @FXML
    public void initialize() {
        Pantalla.setText("");
        Operaciones.setText("");
    }

    @FXML
    protected void onClickCalcular() {
        String current = Pantalla.getText().trim();
        if (current.isEmpty()) {
            mostrarAdvertencia("No hay número para calcular");
            return;
        }
        // Si no se ha seleccionado ningún operador, se muestra el mismo número en el historial con '='
        if (operador.isEmpty()) {
            Operaciones.setText(current + "=");
            // Se mantiene el número en el display
            return;
        }
        // En caso de que se haya seleccionado un operador, se actualiza el historial y se realiza la operación
        Operaciones.setText(historialOperacion + current + "=");
        realizarOperacion();
    }


    /**
     * Maneja el uso del botón de paréntesis para ingresar números negativos en el segundo operando.
     * Solo se permite cuando se está esperando el segundo operando y el display está vacío.
     */
    @FXML
    public void onClickParentesis() {
        String pantalla = Pantalla.getText();

        // Si ya se inició el número negativo y no está cerrado, permitimos cerrar
        if (pantalla.startsWith("(-") && !pantalla.endsWith(")")) {
            if (pantalla.length() > 2) {  // Debe haber al menos un dígito después de "(-"
                Pantalla.setText(pantalla + ")");
                Operaciones.setText(historialOperacion + Pantalla.getText());
            } else {
                mostrarAdvertencia("Ingrese el número negativo antes de cerrar el paréntesis.");
            }
            return;
        }

        // Si se está en modo de espera para el segundo operando, permitimos abrir el paréntesis
        if (esperandoSegundoOperando) {
            if (pantalla.isEmpty() || pantalla.equals("0")) {
                Pantalla.setText("(-");
                Operaciones.setText(historialOperacion + "(-");
            } else {
                mostrarAdvertencia("El número ya está iniciado. Para números negativos, inícielo con '(-'.");
            }
        } else {
            // En cualquier otro caso (por ejemplo, en el primer operando) mostramos el mensaje de error
            mostrarAdvertencia("Para número negativo en el primer operando, use el signo '-' al inicio.");
        }
    }

    @FXML
    public void onClickDecimal(ActionEvent event) {
        limpiarSiError();
        String textoActual = Pantalla.getText();
        if (textoActual.isEmpty()) {
            Pantalla.setText("0.");
            return;
        }
        if (!textoActual.contains(".")) {
            Pantalla.setText(textoActual + ".");
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                Operaciones.setText(historialOperacion + Pantalla.getText());
            }
        }
    }

    /**
     * Realiza la operación, considerando la posibilidad de que el segundo operando
     * se haya ingresado entre paréntesis (por ejemplo, "(-3)").
     */
    private void realizarOperacion() {
        String text = Pantalla.getText();

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
            Pantalla.setText("Error");
            return;
        }

        double resultado = modelo.calcular(primerNumero, segundoNumero, operador);
        if (!Double.isNaN(resultado)) {
            String resultStr = CalculadoraUtils.formatResult(resultado);
            Pantalla.setText(resultStr);
            // Solo se actualiza la memoria si se usó el botón de memoria en la operación.
            if (usandoMemoria) {
                memoria = resultStr;
                usandoMemoria = false;  // Reiniciamos la bandera para futuras operaciones.
            }
        } else {
            Pantalla.setText("Error");
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
        if (Pantalla.getText().isEmpty() && oper.equals("-")) {
            Pantalla.setText("-");
            return;
        }

        // Si ya se está esperando el segundo operando, se interpreta que se desea cambiar el operador
        if (esperandoSegundoOperando) {
            operador = oper;
            if (!historialOperacion.isEmpty()) {
                // Reemplaza el último carácter (el operador anterior) por el nuevo.
                historialOperacion = historialOperacion.substring(0, historialOperacion.length() - 1) + operador;
            } else {
                historialOperacion = Pantalla.getText() + operador;
            }
            Operaciones.setText(historialOperacion);
            return;
        }

        if (esOperacionRealizada) {
            esOperacionRealizada = false;
        }

        String currentText = Pantalla.getText().trim();
        if (currentText.equals("Error") || currentText.isEmpty() || !currentText.matches("[+-]?\\d*([.,]\\d+)?")) {
            currentText = "0";
        } else {
            currentText = currentText.replace(',', '.');
        }

        primerNumero = modelo.convertirNumero(currentText);
        if (Double.isNaN(primerNumero)) {
            mostrarAdvertencia("Valor inválido para la operación");
            return;
        }

        operador = oper;
        // Actualizamos el historial con el operando actual y el operador
        historialOperacion = currentText + operador;
        Operaciones.setText(historialOperacion);
        // Limpiamos el display para que el segundo operando empiece vacío
        Pantalla.setText("");
        esperandoSegundoOperando = true;
    }


    public void onClickNumero(ActionEvent event) {
        limpiarSiError();
        String numero = ((Button) event.getSource()).getText();
        if (esperandoSegundoOperando) {
            // Si el display ya contiene "(-", no lo reemplazamos sino que concatenamos el dígito.
            if (Pantalla.getText().startsWith("(-")) {
                Pantalla.setText(Pantalla.getText() + numero);
            } else {
                Pantalla.setText(numero);
            }
            esperandoSegundoOperando = false;
            Operaciones.setText(historialOperacion + Pantalla.getText());
        } else {
            if (esOperacionRealizada) {
                Pantalla.setText(numero);
                Operaciones.setText("");
                esOperacionRealizada = false;
            } else {
                if (Pantalla.getText().equals("0") || Pantalla.getText().isEmpty()) {
                    Pantalla.setText(numero);
                } else {
                    Pantalla.setText(Pantalla.getText() + numero);
                }
                if (!historialOperacion.isEmpty()) {
                    Operaciones.setText(historialOperacion + Pantalla.getText());
                }
            }
        }
    }


    public void onClickBorrar() {
        String texto = Pantalla.getText();
        if (!texto.isEmpty()) {
            String nuevoTexto = texto.substring(0, texto.length() - 1);
            Pantalla.setText(nuevoTexto);
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                Operaciones.setText(historialOperacion + nuevoTexto);
            }
        }
    }

    public void onClickBorrarTodo() {
        Pantalla.setText("");
        Operaciones.setText("");
        operador = "";
        primerNumero = 0;
        esOperacionRealizada = false;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }

    private void limpiarSiError() {
        if (Pantalla.getText().equals("Error")) {
            Pantalla.setText("");
        }
    }
    @FXML
    public void cambiarSigno(ActionEvent event) {
        String current = Pantalla.getText().trim();

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
            String formatted = CalculadoraUtils.formatResult(toggled);
            Pantalla.setText(formatted);

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
            Stage stage = (Stage) Pantalla.getScene().getWindow();
            popup.show(stage);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> popup.hide());
            delay.play();
        });
    }
    @FXML
    public void cogerMemoria(ActionEvent event) {
        String currentText = Pantalla.getText().trim();

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
            String newMemory = CalculadoraUtils.formatResult(memoryNumber);
            memoria = newMemory;
            usandoMemoria = true;  // Se marca que se está usando la memoria en esta operación.

            // Se actualiza el display y el historial para reflejar la acción.
            Pantalla.setText(newMemory);
            Operaciones.setText("MR = " + newMemory);
            mostrarAdvertencia("Memoria actualizada: " + newMemory);
        } catch (Exception e) {
            mostrarAdvertencia("Error al procesar la memoria.");
        }
    }
    @FXML
    public void resetMemoria(ActionEvent event) {
        memoria = "0";
        Operaciones.setText("MR = 0");
        mostrarAdvertencia("Memoria reiniciada a 0");
    }

    @FXML
    public void recuperarMemoria(ActionEvent event) {
        if (memoria.isEmpty()) {
            mostrarAdvertencia("No hay memoria almacenada.");
            return;
        }

        // Si se está esperando el segundo operando (por ejemplo, después de pulsar un operador)
        if (esperandoSegundoOperando) {
            // Se inserta el valor de la memoria como el segundo operando
            Pantalla.setText(memoria);
            Operaciones.setText(historialOperacion + memoria);
            esperandoSegundoOperando = false; // Se marca que ya se ha ingresado el segundo operando
        } else {
            // Si no se está en medio de una operación, simplemente se muestra la memoria
            Pantalla.setText(memoria);
            Operaciones.setText("MR = " + memoria);
        }
    }



}