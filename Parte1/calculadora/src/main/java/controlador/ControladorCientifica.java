package controlador;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Cientifica;
import modelo.Formateo;
import modelo.ModeloCalculadora;

public class ControladorCientifica {
    private String memoria = "";
    private boolean usandoMemoria = false;
    @FXML
    private Label pantalla;
    @FXML
    private Label operaciones; // Label para mostrar el historial

    private String operador = "";
    private double primerNumero = 0;
    private boolean esOperacionRealizada = false;
    private boolean esperandoSegundoOperando = false;
    private boolean operacionLogaritmo = false;
    private double multiplicador = 1;

    private String historialOperacion = "";

    private ModeloCalculadora modelo = new ModeloCalculadora();

    // Instancia de la clase que contiene los métodos científicos
    private Cientifica cienti = new Cientifica();
    private ControladorMain controladorMain;



    @FXML
    public void initialize() {
        pantalla.setText("");
        operaciones.setText("");
    }

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
        // Si se está realizando una operación unaria (log, tan, sin o cos) y no se ha cerrado, añade ")"
        if ((pantalla.startsWith("log(") || pantalla.startsWith("tan(") ||
                pantalla.startsWith("sin(") || pantalla.startsWith("cos("))
                && !pantalla.endsWith(")")) {
            this.pantalla.setText(pantalla + ")");
            operaciones.setText(this.pantalla.getText());
            return;
        }
        // Manejo para números negativos con paréntesis
        if (pantalla.startsWith("(-") && !pantalla.endsWith(")")) {
            if (pantalla.length() > 2) {
                this.pantalla.setText(pantalla + ")");
                operaciones.setText(historialOperacion + this.pantalla.getText());
            } else {
                mostrarAdvertencia("Ingrese el número negativo antes de cerrar el paréntesis.");
            }
            return;
        }
        // Si se está esperando el segundo operando, permitimos abrir el paréntesis para un negativo
        if (esperandoSegundoOperando) {
            if (pantalla.isEmpty() || pantalla.equals("0")) {
                this.pantalla.setText("(-");
                operaciones.setText(historialOperacion + "(-");
            } else {
                mostrarAdvertencia("El número ya está iniciado. Para números negativos, inícielo con '(-'.");
            }
        } else {
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
        // Se obtiene el texto actual del display
        String text = pantalla.getText();

        // --------------------------
        // CASO ESPECÍFICO: LOGARITMO (base 10)
        // --------------------------
        if ("logaritmo".equals(operador)) {
            // Verifica que el texto tenga el formato correcto: debe iniciar con "log(" y terminar con ")"
            if (!text.startsWith("log(") || !text.endsWith(")")) {
                pantalla.setText("Error: Falta cerrar paréntesis");
                return;
            }
            // Extrae el contenido dentro de los paréntesis
            String inside = text.substring(4, text.length() - 1);
            // Convierte la cadena a número, reemplazando coma por punto
            double valor = modelo.convertirNumero(inside.replace(',', '.'));
            double resultado;
            try {
                // Calcula el logaritmo en base 10 utilizando el modelo Cientifica
                resultado = cienti.calcularLogaritmoBase10(valor);
            } catch (IllegalArgumentException e) {
                pantalla.setText("Error: " + e.getMessage());
                return;
            }
            // Aplica el multiplicador (en caso de que se haya ingresado un valor previo)
            resultado = multiplicador * resultado;
            // Formatea el resultado a cadena
            String resultStr = Formateo.formatResult(resultado);
            // Muestra el resultado en el display
            pantalla.setText(resultStr);

            // Construye la cadena completa de la operación para el historial, por ejemplo "5*log(10)=1.00"
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            // Agrega la operación al historial global si se dispone de controladorMain
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }

            // Reinicia variables de estado para continuar
            operador = "";
            esOperacionRealizada = true;
            esperandoSegundoOperando = false;
            historialOperacion = "";
            multiplicador = 1;
            return;
        }

        // --------------------------
        // CASO ESPECÍFICO: TANGENTE
        // --------------------------
        if ("tangente".equals(operador)) {
            // Verifica que el texto tenga el formato correcto: debe iniciar con "tan(" y terminar con ")"
            if (!text.startsWith("tan(") || !text.endsWith(")")) {
                pantalla.setText("Error: Falta cerrar paréntesis");
                return;
            }
            // Extrae el contenido entre "tan(" y ")"
            String inside = text.substring(4, text.length() - 1);
            // Convierte la cadena a número (ángulo) reemplazando coma por punto
            double angulo = modelo.convertirNumero(inside.replace(',', '.'));
            // Calcula la tangente multiplicada por el multiplicador
            double resultado = multiplicador * cienti.calcularTangente(angulo);
            // Formatea el resultado
            String resultStr = Formateo.formatResult(resultado);
            // Muestra el resultado en el display
            pantalla.setText(resultStr);

            // Construye la cadena completa de la operación
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            // Agrega la operación al historial global si se dispone de controladorMain
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }

            // Reinicia las variables de la operación
            operador = "";
            esOperacionRealizada = true;
            esperandoSegundoOperando = false;
            historialOperacion = "";
            multiplicador = 1;
            return;
        }

        // --------------------------
        // CASO ESPECÍFICO: SENO
        // --------------------------
        if ("seno".equals(operador)) {
            // Verifica que el texto tenga el formato correcto: debe iniciar con "sin(" y terminar con ")"
            if (!text.startsWith("sin(") || !text.endsWith(")")) {
                pantalla.setText("Error: Falta cerrar paréntesis");
                return;
            }
            // Extrae el contenido entre "sin(" y ")"
            String inside = text.substring(4, text.length() - 1);
            // Convierte la cadena a número (ángulo) con reemplazo de coma por punto
            double angulo = modelo.convertirNumero(inside.replace(',', '.'));
            // Calcula el seno multiplicado por el multiplicador
            double resultado = multiplicador * cienti.calcularSeno(angulo);
            // Formatea el resultado
            String resultStr = Formateo.formatResult(resultado);
            // Muestra el resultado en el display
            pantalla.setText(resultStr);

            // Construye la operación completa para el historial
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }

            // Reinicia las variables de estado
            operador = "";
            esOperacionRealizada = true;
            esperandoSegundoOperando = false;
            historialOperacion = "";
            multiplicador = 1;
            return;
        }

        // --------------------------
        // CASO ESPECÍFICO: COSENO
        // --------------------------
        if ("coseno".equals(operador)) {
            // Verifica el formato correcto: debe iniciar con "cos(" y terminar con ")"
            if (!text.startsWith("cos(") || !text.endsWith(")")) {
                pantalla.setText("Error: Falta cerrar paréntesis");
                return;
            }
            // Extrae el contenido entre "cos(" y ")"
            String inside = text.substring(4, text.length() - 1);
            // Convierte la cadena a número (ángulo)
            double angulo = modelo.convertirNumero(inside.replace(',', '.'));
            // Calcula el coseno multiplicado por el multiplicador
            double resultado = multiplicador * cienti.calcularCoseno(angulo);
            // Formatea el resultado
            String resultStr = Formateo.formatResult(resultado);
            // Muestra el resultado en el display
            pantalla.setText(resultStr);

            // Construye la operación completa para el historial
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }

            // Reinicia variables de estado
            operador = "";
            esOperacionRealizada = true;
            esperandoSegundoOperando = false;
            historialOperacion = "";
            multiplicador = 1;
            return;
        }

        // --------------------------
        // CASO GENERAL: OPERACIONES BINARIAS (suma, resta, multiplicación, división, exponencial, etc.)
        // --------------------------
        // Maneja el caso en que se ingresa un número negativo entre paréntesis sin cerrar
        if (text.startsWith("(-") && !text.endsWith(")")) {
            mostrarAdvertencia("Cierre el paréntesis para el operando negativo.");
            return;
        }
        // Si el número negativo ya está cerrado, lo convierte a formato negativo simple
        if (text.startsWith("(-") && text.endsWith(")")) {
            text = "-" + text.substring(2, text.length() - 1);
        }
        // Reemplaza la coma por punto para la conversión numérica
        text = text.replace(',', '.');
        // Convierte el texto a un número para el segundo operando
        double segundoNumero = modelo.convertirNumero(text);
        double resultado;
        // Si el operador es "exponencial", se calcula la potencia
        if ("exponencial".equals(operador)) {
            resultado = Math.pow(primerNumero, segundoNumero);
        } else {
            // Para otros operadores, se delega el cálculo en el modelo
            resultado = modelo.calcular(primerNumero, segundoNumero, operador);
        }
        // Si el resultado es un número válido, se formatea y se muestra
        if (!Double.isNaN(resultado)) {
            String resultStr = Formateo.formatResult(resultado);
            pantalla.setText(resultStr);
            // Construye la cadena completa de la operación, por ejemplo "5+3=8"
            String operacionCompleta = historialOperacion + text + "=" + resultStr;
            // Agrega la operación al historial global si está disponible el controlador principal
            if (controladorMain != null) {
                controladorMain.agregarOperacion(operacionCompleta);
            }
            // Si se está utilizando la memoria, se actualiza con el nuevo resultado
            if (usandoMemoria) {
                memoria = resultStr;
                usandoMemoria = false;
            }
        } else {
            pantalla.setText("Error");
        }
        // Reinicia las variables de la operación
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

        primerNumero = modelo.convertirNumero(currentText);
        if (Double.isNaN(primerNumero)) {
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

        // Si la pantalla ya muestra alguna función unaria, se concatena el dígito
        if (pantalla.getText().startsWith("log(") || pantalla.getText().startsWith("tan(") ||
                pantalla.getText().startsWith("sin(") || pantalla.getText().startsWith("cos(")) {
            pantalla.setText(pantalla.getText() + numero);
            operaciones.setText(pantalla.getText());
            return;
        }
        // Resto de la lógica para ingreso normal de números...
        if (esperandoSegundoOperando) {
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
        primerNumero = 0;
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
        if (current.isEmpty()) {
            return;
        }

        String parsedNumber = current;
        // Si el número está en formato con paréntesis, por ejemplo "(-3)", lo convertimos a "-3"
        if (current.startsWith("(-") && current.endsWith(")")) {
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

        } catch (NumberFormatException e) {
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


    /**
     * Método para calcular el seno del ángulo ingresado (en grados).
     */
    @FXML
    public void onClickSeno() {
        // Inicia la operación de seno mostrando "sin(" en pantalla
        pantalla.setText("sin(");
        operador = "seno";
    }


    /**
     * Método para calcular el coseno del ángulo ingresado (en grados).
     */
    @FXML
    public void onClickCoseno() {
        // Inicia la operación de coseno mostrando "cos(" en pantalla
        pantalla.setText("cos(");
        operador = "coseno";
    }


    /**
     * Método para calcular la tangente del ángulo ingresado (en grados).
     */
    @FXML
    public void onClickTangente() {
        pantalla.setText("tan(");
        operador = "tangente";
    }


    /**
     * Método para calcular la exponencial de un número (e^x).
     */
    @FXML
    public void onClickExponencial() {
        if (pantalla.getText().isEmpty()) {
            pantalla.setText("Error: Ingrese la base");
            return;
        }
        try {
            // Se guarda la base ingresada
            primerNumero = modelo.convertirNumero(pantalla.getText().replace(',', '.'));
            // Se asigna el operador para identificar la exponenciación
            operador = "exponencial";
            // Se actualiza el historial para mostrar la operación en curso (por ejemplo, "9^")
            historialOperacion = pantalla.getText() + "^";
            operaciones.setText(historialOperacion);
            // Se limpia el display para que el usuario ingrese el exponente
            pantalla.setText("");
            esperandoSegundoOperando = true;
        } catch (NumberFormatException e) {
            pantalla.setText("Error: Entrada inválida");
        }
    }
    /**
     * Método para calcular el logaritmo natural (ln) del valor ingresado.
     */
    @FXML
    public void onClickLogaritmo() {
        // Muestra "log(" en la pantalla para que el usuario ingrese el valor
        pantalla.setText("log(");
        operador = "logaritmo";
        operacionLogaritmo = true;
    }
}