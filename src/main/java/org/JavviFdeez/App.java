package org.JavviFdeez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML de la pantalla principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            // Cargar el ícono de la aplicación
            Image icon = new Image("/resources/images/Logo.jpg");

            // Establecer el ícono de la aplicación
            primaryStage.getIcons().add(icon);

            // Configurar la escena
            Scene scene = new Scene(root, 1080, 1920);

            // Configurar el escenario principal
            primaryStage.setTitle("Generador Curriculums");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}