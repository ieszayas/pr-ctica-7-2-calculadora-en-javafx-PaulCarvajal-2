package controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/vista/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Operaciones sencillas");

        // Cargar el archivo CSS
        scene.getStylesheets().add(getClass().getResource("/vista/styles.css").toExternalForm());

        // Agregar el icono de la aplicación
        Image icono = new Image(getClass().getResourceAsStream("/vista/icono.png"));
        stage.getIcons().add(icono);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}