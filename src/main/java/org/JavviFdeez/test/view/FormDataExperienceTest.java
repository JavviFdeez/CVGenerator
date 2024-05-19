package org.JavviFdeez.test.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.JavviFdeez.controller.view.FormDataAcademiesController;
import org.JavviFdeez.controller.view.FormDataExperienceController;

import java.io.IOException;

public class FormDataExperienceTest extends Application {
    public void start(Stage primaryStage) throws IOException {
        try {
            // Cargar el archivo FXML de la pantalla principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataExperience.fxml"));

            // Cargar el controlador asociado al archivo FXML
            Parent root = loader.load();

            // Obtener el controlador del archivo FXML
            FormDataExperienceController controller = loader.getController();

            // Establecer ícono de la aplicación con un tamaño específico
            Image appIcon = new Image(getClass().getResourceAsStream("/org/JavviFdeez/images/Logo.png"));
            primaryStage.getIcons().add(appIcon);

            // Establecer título de la ventana
            primaryStage.setTitle("CVV Generator");

            // Maximizar la ventana
            primaryStage.setMaximized(true);

            // Establecer la escena y mostrar la pantalla principal
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
