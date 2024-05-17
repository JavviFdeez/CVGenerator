package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.dao.UsersDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    // ===========
    // Atributos
    // ===========
    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonLogIn;

    private UsersController usersController;

    private UsersDAO usersDAO;

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // =============================
    // Constructor
    // =============================
    public LogInController() {
        this.usersController = new UsersController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleController();
        buttonLogIn.setOnAction(event -> handleLogIn());
    }

    private void handleController() {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> emailTextField.getParent().requestFocus());
    }

    private void handleLogIn() {
        String email = emailTextField.getText().trim();
        String password = passwordField.getText().trim();

        System.out.println("Email ingresado: " + email);
        System.out.println("Password ingresado: " + password);
        try {
            boolean isAuthenticated = usersController.authenticate(email, password);

            if (isAuthenticated) {
                showAlert("Éxito", "Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
                // Cambiar a la escena de inicio de sesión después de guardar el usuario
                // changeSceneToLogIn();
            } else {
                showAlert("Error", "Correo electrónico o contraseña incorrectos.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ruta/a/LogIn.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Stage stage = (Stage) emailTextField.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert("Error", "No se pudo cargar la pantalla de inicio de sesión.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleButtonRegister(javafx.event.ActionEvent event) {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/Register.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el evento
            Scene scene = ((Node) event.getSource()).getScene();

            // Cambiar la escena en el escenario
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert("Error", "No se pudo cargar la pantalla de Register.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String error, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}