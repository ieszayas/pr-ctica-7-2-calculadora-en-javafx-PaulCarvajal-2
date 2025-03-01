package controlador;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorMain implements Initializable {
    @FXML
    private BorderPane borderPane;


    @FXML
    private ListView<String> listHistorial;

    // Lista observable para el historial
    private ObservableList<String> historial = FXCollections.observableArrayList();

    @FXML
    public void cargarCalculadoraNormal() {
        try {
            // Crea un FXMLLoader con la ruta del archivo FXML de la calculadora normal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Calculadora.fxml"));
            // Carga la vista definida en el FXML
            Parent normal = loader.load();

            // Obtiene el controlador asociado al FXML
            ControladorCalculadora calcController = loader.getController();
            // Asigna la referencia del controlador principal para actualizar el historial
            calcController.setControladorMain(this);


            borderPane.setCenter(normal);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarCalculadoraCientifica() {
        try {
            // Crea un FXMLLoader con la ruta del archivo FXML de la calculadora científica.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Cientifica.fxml"));

            // Carga la vista definida en el FXML y la guarda en la variable 'cientifica'
            Parent cientifica = loader.load();

            // Obtiene el controlador asociado al FXML
            ControladorCientifica cientController = loader.getController();

            // Asigna la referencia del controlador principal para que el controlador científico
            // pueda agregar operaciones al historial global.
            cientController.setControladorMain(this);

            // Coloca la vista de la calculadora científica en el centro del BorderPane.
            borderPane.setCenter(cientifica);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarAyuda() {
        try {
            Parent ayudaView = FXMLLoader.load(getClass().getResource("/vista/Ayuda.fxml"));
            borderPane.setCenter(ayudaView);
        } catch (IOException e) {
            e.printStackTrace();
    }

}
    public void agregarOperacion(String operacion) {
        // Agrega la operación a la lista observable para actualizar el historial en la UI
        historial.add(operacion);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asocia la lista observable 'historial' al ListView para que se actualice automáticamente.
        listHistorial.setItems(historial);


        cargarCalculadoraNormal();
    }

    public void cargarConversor(ActionEvent actionEvent) {
        try {
            Parent conversor = FXMLLoader.load(getClass().getResource("/vista/Conversor.fxml"));
            borderPane.setCenter(conversor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda el historial de operaciones en un archivo de texto.
     * <p>
     * Este método utiliza un FileChooser para permitir al usuario seleccionar la ubicación
     * y el nombre del archivo donde se guardará el historial. Luego, recorre la lista observable
     * de operaciones y escribe cada operación en una línea del archivo.
     * </p>
     */
    @FXML
    public void guardarHistorial() {
        // Crea un FileChooser para seleccionar el archivo de destino
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Historial");
        // Agrega un filtro para archivos de texto (*.txt)
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto", "*.txt"));

        // Muestra el diálogo de guardado y obtiene el archivo seleccionado
        File file = fileChooser.showSaveDialog(new Stage());

        // Si el usuario seleccionó un archivo, se procede a guardar el historial
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Recorre cada operación almacenada en el historial y la escribe en una línea del archivo
                for (String operacion : historial) {
                    writer.write(operacion);
                    writer.newLine();
                }
            } catch (IOException e) {
                // Imprime la traza del error en caso de que ocurra un problema al escribir en el archivo
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void borrarHistorial() {
        // Crea un Alert de tipo CONFIRMATION para solicitar la confirmación del usuario
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar Historial");
        alert.setHeaderText("¿Estás seguro de que deseas borrar el historial?");
        alert.setContentText("Se eliminarán todas las operaciones almacenadas.");

        // Muestra el diálogo y espera la respuesta del usuario
        java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        // Si el usuario confirma (presionando OK), se limpia el historial
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            historial.clear();
        }
    }

    @FXML
    public void cargarHistorial() {
        // Crea un FileChooser y configura el título y el filtro para archivos de texto
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Historial");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto", "*.txt"));

        // Muestra el diálogo de apertura y obtiene el archivo seleccionado
        File file = fileChooser.showOpenDialog(new Stage());

        // Si el archivo existe y no es nulo, procede a leer su contenido
        if (file != null && file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                // Limpia el historial actual antes de cargar el nuevo contenido
                historial.clear();
                // Lee el archivo línea por línea y añade cada línea al historial
                while ((line = reader.readLine()) != null) {
                    historial.add(line);
                }
            } catch (IOException e) {
                // En caso de error al leer el archivo, imprime la traza de la excepción
                e.printStackTrace();
            }

            // Crea un Alert de tipo CONFIRMATION para preguntar al usuario si desea abrir el archivo
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            alert.setTitle("Abrir historial");
            alert.setHeaderText("¿Desea abrir el archivo con el historial?");
            alert.setContentText("El archivo seleccionado es:\n" + file.getAbsolutePath());

            // Muestra el diálogo y espera la respuesta del usuario
            java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
                try {
                    // Si la plataforma soporta Desktop, se usa para abrir el archivo con la aplicación predeterminada
                    if (java.awt.Desktop.isDesktopSupported()) {
                        java.awt.Desktop.getDesktop().open(file);
                    } else {
                        // Si Desktop no está soportado, se invoca un método alternativo para mostrar el archivo en una ventana de JavaFX
                        mostrarArchivoEnVentana(file);
                    }
                } catch (IOException e) {
                    // Imprime la traza en caso de error al intentar abrir el archivo
                    e.printStackTrace();
                }
            }
        }
    }

    private void mostrarArchivoEnVentana(File file) {
        // Inicializa un StringBuilder para almacenar el contenido del archivo.
        StringBuilder contenido = new StringBuilder();

        // Intenta abrir y leer el archivo línea por línea.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Lee cada línea del archivo y la añade al StringBuilder, agregando un salto de línea.
            while ((line = reader.readLine()) != null) {
                contenido.append(line).append("\n");
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, imprime la traza del error.
            e.printStackTrace();
        }

        // Crea un TextArea con el contenido leído del archivo.
        javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea(contenido.toString());
        // Configura el TextArea para que no sea editable, permitiendo solo la visualización.
        textArea.setEditable(false);

        // Crea un StackPane que contendrá el TextArea.
        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane(textArea);
        // Crea una nueva Scene con el StackPane, con dimensiones de 600x400 píxeles.
        javafx.scene.Scene scene = new javafx.scene.Scene(root, 600, 400);

        // Crea un nuevo Stage (ventana) para mostrar la Scene.
        javafx.stage.Stage stage = new javafx.stage.Stage();
        // Establece el título de la ventana.
        stage.setTitle("Historial desde archivo");
        // Asigna la Scene al Stage.
        stage.setScene(scene);
        // Muestra la ventana.
        stage.show();
    }

    @FXML
    public void mostrarGrafica() {
        try {
            // Carga la vista definida en Graficas.fxml desde el directorio /vista/
            Parent graphView = FXMLLoader.load(getClass().getResource("/vista/Grafica.fxml"));
            // Establece la vista cargada en el centro del BorderPane para mostrar la gráfica
            borderPane.setCenter(graphView);
        } catch (IOException e) {
            // Imprime la traza del error en caso de que ocurra un problema durante la carga del FXML
            e.printStackTrace();
        }
    }
}
