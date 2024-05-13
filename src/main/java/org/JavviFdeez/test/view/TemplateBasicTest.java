package org.JavviFdeez.test.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.JavviFdeez.controller.view.TemplateController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ContactDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class TemplateBasicTest extends Application {
    public void start(Stage primaryStage) throws IOException {
        try {
            // Cargar el archivo FXML de la pantalla principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/templatesCVV/TemplateBasic.fxml"));

            // Cargar el controlador asociado al archivo FXML
            Parent root = loader.load();

            // Obtener el controlador del archivo FXML
            TemplateController controller = loader.getController();

            // Configurar la escena
            Scene scene = new Scene(root, 793.7, 1122.5);

            // Establecer ícono de la aplicación con un tamaño específico
            Image appIcon = new Image(getClass().getResourceAsStream("/org/JavviFdeez/images/Logo.png"));
            primaryStage.getIcons().add(appIcon);

            // Establecer título de la ventana
            primaryStage.setTitle("Generator CVV");

            // Maximizar la ventana
            //primaryStage.setMaximized(true);

            // Establecer la escena y mostrar la pantalla principal
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}