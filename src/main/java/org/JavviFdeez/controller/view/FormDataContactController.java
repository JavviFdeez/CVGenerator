package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.JavviFdeez.controller.AcademiesController;
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.dao.ContactDAO;;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;
import org.JavviFdeez.utils.EmailValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;


public class FormDataContactController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailText;

    @FXML
    private TextField LastNameTextField;

    @FXML
    private TextField occupationTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField linkedinTextField;

    @FXML
    private TextField extraTextField;

    @FXML
    private TextField mobileTextField;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView addImage;

    @FXML
    public ImageView backLogIn;

    private String imageRelativePath;

    @FXML
    private Button buttonSaveData;

    private Session session;

    private ContactController contactController;
    private AcademiesController acadController;
    private LogInController logInController;
    private ContactDAO contactDAO;
    private AcademiesDAO academiesDAO;



    public FormDataContactController() {
        this.contactController = new ContactController();
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
        this.acadController = new AcademiesController();
        this.session = Session.getInstance();
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        this.session = Session.getInstance();
        this.logInController = new LogInController();
        this.backLogIn = new ImageView();
    }

    public void setLogInController(LogInController logInController) {
        this.logInController = logInController;
        loadContactData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> nameTextField.getParent().requestFocus());
        handleFormData();
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        backLogIn.setOnMouseClicked(event -> handleSignoff());
    }

    private void handleSignoff() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/LogIn.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) occupationTextField.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la inicio de sesión.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }


    private void loadContactData() {
        if (logInController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener el contacto utilizando el contactId
            Contact contact = logInController.getIDContact();

            if (contact != null) {
                // Mostrar los datos del contacto en los campos de texto
                nameTextField.setText(contact.getName());
                LastNameTextField.setText(contact.getLastname());
                occupationTextField.setText(contact.getOccupation());
                mobileTextField.setText(contact.getMobile());
                emailText.setText(contact.getEmail());

                // Verificar si la dirección de correo electrónico no es nula antes de validarla
                if (contact.getEmail() != null && EmailValidator.isValidEmail(contact.getEmail())) {
                    // La dirección de correo electrónico es válida
                } else {
                    // La dirección de correo electrónico es nula o inválida
                    logInController.showAutoClosingAlert("ADVERTENCIA: La dirección de correo electrónico es inválida.", LogInController.AlertType.WARNING, Duration.seconds(1.5));
                }

            } else {
                // Mostrar un mensaje de advertencia si no se encuentra el contacto
                logInController.showAutoClosingAlert("ADVERTENCIA: No se encontró contacto con el ID proporcionado.", LogInController.AlertType.WARNING, Duration.seconds(1.5));
            }
        } else {
            // Mostrar un mensaje de advertencia si LogInController es null
            logInController.showAutoClosingAlert("ADVERTENCIA: No se pudo obtener la instancia de LogInController.", LogInController.AlertType.WARNING, Duration.seconds(1.5));
        }
    }


    private void handleFormData() {
        // Cargar datos del contacto
        addImage.setOnMouseClicked(event -> handleUploadImage());
        nameTextField.setOnMouseClicked(event -> handleTextFieldClick(nameTextField));
        LastNameTextField.setOnMouseClicked(event -> handleTextFieldClick(LastNameTextField));
        occupationTextField.setOnMouseClicked(event -> handleTextFieldClick(occupationTextField));
        mobileTextField.setOnMouseClicked(event -> handleTextFieldClick(mobileTextField));
        emailText.setOnMouseClicked(event -> handleTextFieldClick(emailText));
        linkedinTextField.setOnMouseClicked(event -> handleTextFieldClick(linkedinTextField));
        extraTextField.setOnMouseClicked(event -> handleTextFieldClick(extraTextField));
        locationTextField.setOnMouseClicked(event -> handleTextFieldClick(locationTextField));
        extraTextField.setOnMouseClicked(event -> handleTextFieldClick(extraTextField));
    }


    private void handleTextFieldClick(TextField textField) {
        textField.clear();
    }

    private void handleFormaDataSave() {
        // Obtener los valores de los campos de texto
        String name = nameTextField.getText().trim();
        String lastName = LastNameTextField.getText().trim();
        String occupation = occupationTextField.getText().trim();
        String mobile = mobileTextField.getText().trim();
        String email = emailText.getText().trim();
        String linkedin = linkedinTextField.getText().trim();
        String location = locationTextField.getText().trim();
        String extra = extraTextField.getText().trim();

        // Verificar si los campos obligatorios están vacíos
        if (name.isEmpty() || lastName.isEmpty() || addImage.getImage() == null || occupation.isEmpty() || mobile.isEmpty() || email.isEmpty() || linkedin.isEmpty() || location.isEmpty()) {
            logInController.showAutoClosingAlert("CAMPOS INCOMPLETOS: Por favor, rellene todos los campos obligatorios.", LogInController.AlertType.WARNING, Duration.seconds(1.5));
            return;
        }

        try {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Crear un nuevo objeto Contact con los datos del formulario
            Contact contact = new Contact();
            contact.setContact_id(contactId);
            contact.setName(name);
            contact.setLastname(lastName);
            contact.setOccupation(occupation);
            contact.setMobile(mobile);
            contact.setEmail(email);
            contact.setLinkedin(linkedin);
            contact.setLocation(location);
            contact.setExtra(extra);
            contact.setImage(imageRelativePath);

            boolean saveDataToDatabase;

            // Verificar si el contacto existe
            if (contactController.getContactById(contactId) != null) {
                // Si el contacto existe, actualizarlo
                saveDataToDatabase = contactController.updateContact(contactId, contact);
                logInController.showAutoClosingAlert("AVISO: Contacto se han guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                changeSceneToFormData();
            } else {
                // Si el contacto no existe, crear un nuevo contacto
                saveDataToDatabase = contactController.saveDataToDatabase(contact);
            }

            if (saveDataToDatabase) {
                logInController.showAutoClosingAlert("AVISO: Contacto se han guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                // Cambiar a la escena de academies después de guardar el contacto
                changeSceneToFormData();
            } else {
                logInController.showAutoClosingAlert("ERROR: No se pudieron guardar los datos.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: No se pudieron guardar los datos.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataAcademies.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            FormDataAcademiesController formDataAcademiesController = loader.getController();
            formDataAcademiesController.setContactController(contactController);

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Scene scene = emailText.getScene();
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                if (stage != null) {
                    // Establecer la nueva escena en el escenario
                    Scene newScene = new Scene(root);
                    stage.setScene(newScene);
                    stage.show();
                } else {
                    System.out.println("");
                }
            } else {
                // Manejar el caso en el que la escena es null
                System.out.println("La escena es null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Academies.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.svg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Leer la imagen seleccionada
                Image image = new Image(new FileInputStream(selectedFile));
                profileImageView.setImage(image);

                // Guardar la imagen en la ruta especificada
                String userImagesDir = "src/main/resources/org/imagesUsers";
                Path outputPath = saveImageToDirectory(selectedFile, userImagesDir);

                // Guardar la ruta relativa
                imageRelativePath = outputPath.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Path saveImageToDirectory(File imageFile, String userImagesDir) throws IOException {
        // Crear el directorio si no existe
        Path userImagesPath = Paths.get(userImagesDir);
        if (!Files.exists(userImagesPath)) {
            Files.createDirectories(userImagesPath);
        }

        // Crear una nueva ruta para la imagen
        String fileName = imageFile.getName();
        Path outputPath = userImagesPath.resolve(fileName);

        // Copiar el archivo a la nueva ubicación
        try (FileInputStream fis = new FileInputStream(imageFile);
             FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }

        return outputPath;
    }
}