package org.JavviFdeez.controller.view;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.controller.UsersController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

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


    /**
     * @param url
     * @param resourceBundle
     * @Author: JavviFdeez
     * Metodo para inicializar el controlador
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleController();
        buttonLogIn.setOnAction(event -> handleLogIn());
    }

    /**
     * @Author: JavviFdeez
     * Metodo para manejar el controlador
     */
    private void handleController() {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> emailTextField.getParent().requestFocus());
        emailTextField.setPromptText("Email *");
        passwordField.setPromptText("Password *");
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


    /**
     * @Author: JavviFdeez
     * Metodo para autenticar al usuario
     */
    private void handleLogIn() {
        String email = emailTextField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            // Verificar las credenciales del usuario
            boolean authenticated = usersController.authenticate(email, password);

            if (authenticated) {
                // Obtener el contact_id de la sesión
                int contactId = Session.getInstance().getContactId();

                // Verificar si el contact_id es válido (por ejemplo, mayor que cero)
                if (contactId > 0) {
                    // Cambiar a la escena de datos de contacto
                    changeSceneToFormData();
                    showAutoClosingAlert("AVISO: Inicio de Sesión Exitoso.", AlertType.SUCCESS, Duration.seconds(1.5));
                } else {
                    showAutoClosingAlert("ERROR: No se pudo obtener el ID de contacto de la sesión.", AlertType.ERROR, Duration.seconds(1.5));
                }

            } else {
                // Mostrar mensaje de error si las credenciales son incorrectas
                showAutoClosingAlert("ERROR: Credenciales incorrectas.", AlertType.ERROR, Duration.seconds(1.5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar mensaje de error si ocurre un error durante la autenticación
            showAutoClosingAlert("ERROR: Ocurrio un error durante la autenticación.", AlertType.ERROR, Duration.seconds(1.5));
        }
    }


    /**
     * @Author: JavviFdeez
     * Metodo para cambiar de escena a la de formulario de contacto
     */
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
            showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de datos de contacto.", AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    /**
     * @param event
     * @Author: JavviFdeez
     * Metodo para cambiar de escena a la de registro
     */
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
            showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Register.", AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    public enum AlertType {
        INFORMATION,
        WARNING,
        ERROR,
        SUCCESS
    }

    public void showAutoClosingAlert(String message, AlertType type, Duration duration) {
        Alert.AlertType alertType;
        String cssStyle = "";
        String imagePath = "";

        switch (type) {
            case INFORMATION:
                alertType = Alert.AlertType.INFORMATION;
                cssStyle = "-fx-background-color: transparent; -fx-text-fill: #000000;";
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsCheckCircleOutline2.png";
                break;
            case WARNING:
                alertType = Alert.AlertType.WARNING;
                cssStyle = "-fx-background-color: transparent; -fx-text-fill: #ffcc00;";
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsErrorOutlineRounded.png";
                break;
            case ERROR:
                alertType = Alert.AlertType.ERROR;
                cssStyle = "-fx-background-color: transparent; -fx-text-fill: #ff0000;";
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsErrorOutlineRounded.png";
                break;
            case SUCCESS:
                alertType = Alert.AlertType.CONFIRMATION;
                cssStyle = "-fx-background-color: transparent; -fx-text-fill: #00ff00;";
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsCheckCircleOutline2.png";
                break;
            default:
                throw new IllegalArgumentException("Unknown alert type: " + type);
        }

        Alert alert = new Alert(alertType);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText(message);


        // Crear el ImageView con el icono y el tamaño deseado
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        // Establecer el icono personalizado como el gráfico de la alerta
        alert.setGraphic(icon);

        Button closeButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        closeButton.setVisible(false);


        DialogPane dialogPane = alert.getDialogPane();


        // Establecer la altura mínima y máxima para controlar el tamaño vertical
        dialogPane.setMinHeight(80);
        dialogPane.setMaxHeight(150);

        dialogPane.getScene().getRoot().setStyle(" -fx-background-radius: 15; -fx-background-color: transparent;");
        dialogPane.setStyle("-fx-font-family: 'Roboto';");
        alert.getDialogPane().getScene().setFill(null);

        alert.getDialogPane().setPrefSize(500, 1);

        alert.show();

        // Configurar la duración de la alerta
        PauseTransition delay = new PauseTransition(duration);
        delay.setOnFinished(event -> alert.close());
        delay.play();
    }
}