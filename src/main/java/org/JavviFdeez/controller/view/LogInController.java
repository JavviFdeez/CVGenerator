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
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;

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

    private Session session;

    private UsersController usersController;

    private ContactController contactController;
    private AcademiesDAO academiesDAO;
    private ContactDAO contactDAO;


    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // =============================
    // Constructor
    // =============================
    public LogInController() {
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        this.contactController = new ContactController();
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
        this.usersController = new UsersController();
        this.session = Session.getInstance();
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

    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de buscar un contacto por ID en la base de datos
     */
    public Contact getIDContact() {
        try {
            // Obtener el email del usuario autenticado desde el campo de texto
            String email = emailTextField.getText();

            // Obtener el contactId del usuario autenticado
            Integer contactId = contactController.getContactIdByEmail(email);

            // Verificar si se obtuvo correctamente el contactId
            if (contactId != null) {
                // Buscar el contacto en la base de datos utilizando el contactId
                Contact foundContact = contactDAO.findById(contactId);

                if (foundContact != null) {
                    // Si la búsqueda es exitosa, mostrar mensaje de éxito
                    System.out.println("✅ Contacto encontrado exitosamente: " + foundContact);
                    return foundContact;
                } else {
                    // Si no se encuentra el contacto, mostrar mensaje de advertencia
                    System.out.println("⚠️ No se encontró ningún contacto con el ID proporcionado: " + contactId);
                }
            } else {
                // Si no se obtiene el contactId, mostrar un mensaje de advertencia
                System.out.println("⚠️ No se pudo obtener el ID del contacto.");
            }
        } catch (SQLException e) {
            // En caso de error, mostrar mensaje de error
            System.err.println("❌ Error al buscar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Academies getIDAcademies() {
        try {
            // Obtener el email del usuario autenticado desde el campo de texto
            String email = emailTextField.getText();

            // Obtener el contactId del usuario autenticado
            Integer contactId = contactController.getContactIdByEmail(email);

            // Verificar si se obtuvo correctamente el contactId
            if (contactId != null && contactId != -1) {
                // Buscar la academia en la base de datos utilizando el contactId
                Academies foundAcademy = academiesDAO.getIDContact(contactId);

                if (foundAcademy != null) {
                    // Si la búsqueda es exitosa, mostrar mensaje de éxito
                    System.out.println("✅ Academia encontrada exitosamente: " + foundAcademy);
                    return foundAcademy;
                } else {
                    // Si no se encuentra la academia, mostrar mensaje de advertencia
                    System.out.println("⚠️ No se encontró ninguna academia con el ID proporcionado: " + contactId);
                }
            } else {
                // Si no se obtiene el contactId, mostrar un mensaje de advertencia
                System.out.println("⚠️ No se pudo obtener el ID del contacto.");
            }
        } catch (SQLException e) {
            // En caso de error, mostrar mensaje de error
            System.err.println("❌ Error al buscar la academia: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private void handleLogIn() {
        String email = emailTextField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            // Verificar la autenticación del usuario
            boolean isAuthenticated = usersController.authenticate(email, password);

            if (isAuthenticated) {
                showAlert("Éxito", "Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
                // Cambiar a la escena de inicio de sesión después de guardar el usuario
                changeSceneToFormData();
                // Obtener el contactId del usuario autenticado
                int contactId = Session.getInstance().getContactId();

                // Obtener el contacto utilizando el contactId
                Contact contact = getIDContact();

                // Obtener la academia utilizando el contactId
                Academies academy = getIDAcademies();

                // Si el contacto se encuentra, guardar el contactId en la sesión
                if (contact != null) {
                    // Mostrar mensaje de éxito
                    showAlert("Éxito", "Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);

                    // Guardar el contactId en la sesión
                    Session.getInstance().setContactId(contactId);

                    // Cambiar a la escena de formulario de contacto
                    changeSceneToFormData();
                } else {
                    // Mostrar mensaje de advertencia si no se encuentra el contacto
                    showAlert("Advertencia", "No se encontró ningún contacto con el ID proporcionado.", Alert.AlertType.WARNING);
                }
            } else {
                // Mostrar mensaje de error
                showAlert("Error", "Correo electrónico o contraseña incorrectos.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            // Manejar errores de SQL
            e.printStackTrace();
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }




    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataContact.fxml"));
            Parent root = loader.load();

            FormDataContactController formDataContactController = loader.getController();
            formDataContactController.setLogInController(this);

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) emailTextField.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert("Error", "No se pudo cargar la pantalla de contacto.", Alert.AlertType.ERROR);
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