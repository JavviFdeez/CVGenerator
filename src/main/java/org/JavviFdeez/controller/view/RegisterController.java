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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.UsersDAO;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;
import org.JavviFdeez.model.entity.User;

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

    private UsersController usersController;
    private ContactController contactController;
    private LogInController logInController;

    private UsersDAO usersDAO;

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // =============================
    // Constructor sin argumentos
    // =============================
    public RegisterController() {
        this.usersController = new UsersController();
        this.usersDAO = new UsersDAO(ConnectionMariaDB.getConnection());
        this.contactController = new ContactController();
        this.conn = ConnectionMariaDB.getConnection();
        this.logInController = new LogInController();
    }

    /**
     * @Author: JavviFdeez
     * Metodo para inicializar el controlador
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleController();

        // Añadir el evento al botón de registro
        buttonRegister.setOnAction(event -> handleSaveUser());
    }

    /**
     * @Author: JavviFdeez
     * Metodo para gestionar el controlador
     */
    private void handleController() {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> emailTextField.getParent().requestFocus());
    }
    /**
     * @Author: JavviFdeez
     * Metodo para registrar un nuevo usuario
     */
    private void handleSaveUser() {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        try {
            // Crear un nuevo objeto Contact con el correo electrónico proporcionado
            Contact contact = new Contact();
            contact.setEmail(email);

            // Guardar el contacto en la base de datos para obtener el contact_id generado
            contactController.saveContact(contact);

            // Obtener el contact_id generado del objeto Contact
            int contactId = contact.getContact_id();
            System.out.println("Contact ID: " + contactId);

            // Crear un nuevo objeto User con el correo electrónico, contraseña y contact_id
            User user = new User(email, password, contactId);

            // Guardar el usuario en la base de datos
            usersController.saveUser(user);

            // Guardar el contact_id en la sesión
            Session.getInstance().setContactId(contactId);
            logInController.showAutoClosingAlert("SUCCESS: User saved successfully", LogInController.AlertType.SUCCESS,  Duration.seconds(1.5));
            changeSceneToLogIn();
            // Guardar el contact_id en la sesión
            Session.getInstance().setContactId(contactId);
            logInController.showAutoClosingAlert("SUCCESS: User saved successfully", LogInController.AlertType.SUCCESS,  Duration.seconds(1.5));
            changeSceneToLogIn();

        } catch (SQLException e) {
            logInController.showAutoClosingAlert("ERROR: No se puede guardar el usuario: " + e.getMessage(), LogInController.AlertType.ERROR,  Duration.seconds(1.5));
        } catch (IllegalArgumentException e) {
            logInController.showAutoClosingAlert("ERROR: " + e.getMessage(), LogInController.AlertType.ERROR,  Duration.seconds(1.5));
        }
    }


    /**
     * @Author: JavviFdeez
     * Metodo para cambiar a la escena LogIn
     */
    private void changeSceneToLogIn() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/LogIn.fxml"));
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
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de datos del formulario.", LogInController.AlertType.ERROR,  Duration.seconds(1.5));
        }
    }

    /**
     * @Author: JavviFdeez
     * Metodo para cambiar a la escena de inicio de sesión
     * @param event
     */
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
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de inicio de sesión.", LogInController.AlertType.ERROR,  Duration.seconds(1.5));
        }
    }
}