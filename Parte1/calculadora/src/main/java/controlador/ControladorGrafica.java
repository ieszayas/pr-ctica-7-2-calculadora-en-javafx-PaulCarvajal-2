package controlador;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.Function;

public class ControladorGrafica {
    @FXML
    private TextField functionField;
    @FXML
    private TextField xMinField;
    @FXML
    private TextField xMaxField;
    @FXML
    private TextField stepField;

    /**
     * Muestra una gráfica de la función matemática ingresada.
     * <p>
     * Este método se invoca cuando el usuario presiona el botón "Ver". Se obtienen los parámetros
     * ingresados (la función, el límite inferior (xMin), el límite superior (xMax) y el incremento (step)).
     * Luego, se utiliza la librería exp4j para construir y evaluar la expresión matemática para cada
     * valor de x en el intervalo especificado. Se crea un gráfico de líneas (LineChart) con los ejes
     * configurados y se añade la serie de datos resultante. Finalmente, se muestra la gráfica en una nueva ventana.
     * En caso de que ocurra alguna excepción (por ejemplo, si faltan valores o la expresión es inválida),
     * se muestra un mensaje de error.
     * </p>
     */
    @FXML
    private void onVerGraph() {
        try {
            // Obtener los parámetros ingresados del usuario y eliminando espacios en blanco.
            String funcStr = functionField.getText().trim();
            double xMin = Double.parseDouble(xMinField.getText().trim());
            double xMax = Double.parseDouble(xMaxField.getText().trim());
            double step = Double.parseDouble(stepField.getText().trim());

            // Construir la expresión matemática utilizando exp4j, que reconoce "^" para la potencia.
            Expression expression = new ExpressionBuilder(funcStr)
                    .variable("x")
                    .build();

            // Configurar el eje X con los límites y el incremento especificados.
            NumberAxis xAxis = new NumberAxis(xMin, xMax, step);
            xAxis.setLabel("x");

            // Configurar el eje Y sin límites fijos (se ajustará automáticamente).
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("f(x)");

            // Crear el gráfico de líneas con los ejes configurados.
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Gráfica de f(x) = " + funcStr);

            // Crear la serie de datos donde se almacenarán los puntos de la gráfica.
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("f(x)");

            // Iterar sobre el intervalo [xMin, xMax] con el incremento definido por 'step'
            for (double x = xMin; x <= xMax; x += step) {
                // Asigna el valor de x a la variable de la expresión.
                expression.setVariable("x", x);
                // Evalúa la función en el valor actual de x.
                double y = expression.evaluate();
                // Añade el punto (x, y) a la serie de datos.
                series.getData().add(new XYChart.Data<>(x, y));
            }
            // Agrega la serie al gráfico.
            lineChart.getData().add(series);

            // Crear una nueva ventana (Stage) para mostrar la gráfica.
            Stage stage = new Stage();
            stage.setTitle("Gráfica de la función");
            // Crea una nueva Scene con el gráfico y establece un tamaño adecuado.
            Scene scene = new Scene(lineChart, 800, 600);
            stage.setScene(scene);
            // Muestra la ventana con la gráfica.
            stage.show();
        } catch (Exception e) {
            // Si ocurre un error (por ejemplo, parámetros inválidos o faltantes),
            // se crea un Alert de error para informar al usuario.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al intentar hacer la gráfica");
            alert.setHeaderText("No se pudo crear la gráfica");
            alert.setContentText("Faltan valores por añadir");
            alert.showAndWait();
        }
    }

}