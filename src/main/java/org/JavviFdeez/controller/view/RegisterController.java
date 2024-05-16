package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.UsersDAO;
import org.JavviFdeez.model.entity.Users;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    // ===========
    // Atributos
    // ===========
    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonLogin;

    private UsersController usersController;

    private UsersDAO usersDAO;

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;


    // =============================
    // Constructor sin argumentos
    // =============================
    public RegisterController() {
        this.usersController = new UsersController(); // Inicializa UsersController
        this.usersDAO = new UsersDAO(ConnectionMariaDB.getConnection());
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleController();

        // Añadir el evento al botón de login
        buttonRegister.setOnAction(event -> handleSaveUser());
    }

    private void handleController() {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> emailTextField.getParent().requestFocus());
    }

    private void handleSaveUser() {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        try {
            Users user = new Users(email, password);
            // Llamada al método del UsersController para guardar el usuario
            usersController.saveUser(user);

            // Mostrar mensaje de éxito
            showAlert("Éxito", "Usuario guardado exitosamente", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            // Mostrar mensaje de error de SQL
            showAlert("Error", "Error al guardar el usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            // Mostrar mensaje de error de correo electrónico no válido
            showAlert("Error", "Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String error, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleButtonLogin(javafx.event.ActionEvent event) {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/LogIn.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el evento
            Scene scene = ((Node) event.getSource()).getScene();

            // Cambiar la escena en el escenario
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert("Error", "No se pudo cargar la pantalla de inicio de sesión.", Alert.AlertType.ERROR);
        }
    }
}