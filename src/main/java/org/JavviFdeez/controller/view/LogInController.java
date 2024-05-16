package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.UsersDAO;
import org.JavviFdeez.model.entity.Users;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    // ===========
    // Atributos
    // ===========
    @FXML
    private TextField emailTextField;

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
        this.usersDAO = new UsersDAO(ConnectionMariaDB.getConnection());
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleController();

        // Añadir el evento al botón de login
        //buttonLogIn.setOnAction(event -> handleLogIn());
    }

    private void handleController() {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> emailTextField.getParent().requestFocus());
    }
/*
    private void handleSaveUser() {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        Users user = new Users(email, password);

        // Llamada al método del UsersController para guardar el usuario
        String message = usersController.saveUser(user);

        // Mostrar mensaje de alerta
        if (message.startsWith("Error")) {
            showAlert("Error", message, Alert.AlertType.ERROR);
        } else {
            showAlert("Éxito", message, Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String error, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

 */
}