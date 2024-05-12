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
import java.sql.Connection;

public class TemplateBasicTest extends Application {
    public void start(Stage primaryStage) throws IOException {
        try {
            // Crear una instancia de ContactDAO con la conexión a la base de datos
            Connection connection = ConnectionMariaDB.getConnection();
            ContactDAO contactDAO = new ContactDAO(connection);

            // Cargar el archivo FXML de la pantalla principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/templatesCVV/TemplateBasic.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del archivo FXML y inicializarlo
            TemplateController controller = loader.getController();
            controller.initialize(contactDAO);

            // Configurar la escena
            Scene scene = new Scene(root, 1080, 1920);

            // Establecer ícono de la aplicación con un tamaño específico
            Image appIcon = new Image(getClass().getResourceAsStream("/org/JavviFdeez/images/logo.png"));
            primaryStage.getIcons().add(appIcon);

            // Establecer título de la ventana
            primaryStage.setTitle("Generator CVV");

            // Maximizar la ventana
            primaryStage.setMaximized(true);

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