package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.JavviFdeez.controller.AcademiesController;
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataAcademiesController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField entityTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private Button academicdelete;

    @FXML
    private ImageView iconAdd;

    @FXML
    private ImageView iconAdd2;

    @FXML
    private Button academicadd;

    @FXML
    private ImageView iconDelete;

    @FXML
    private ImageView iconDelete2;


    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkContact;

    @FXML
    private TextField nameTextField1;

    @FXML
    private TextField entityTextField1;

    @FXML
    private TextField locationTextField1;

    @FXML
    private ComboBox<String> yearComboBox1;

    @FXML
    private Button academicdelete1;

    @FXML
    private Button academicadd1;

    @FXML
    private Label nameText1;

    @FXML
    private Label entityText1;

    @FXML
    private Label locationText1;

    @FXML
    private Label yearText1;

    @FXML
    private TextField nameTextField2;

    @FXML
    private TextField entityTextField2;

    @FXML
    private TextField locationTextField2;

    @FXML
    private ComboBox<String> yearComboBox2;

    @FXML
    private Label nameText2;

    @FXML
    private Label entityText2;

    @FXML
    private Label locationText2;

    @FXML
    private Label yearText2;

    private AcademiesController academiesController;

    private Connection conn;
    private LogInController logInController;


    public FormDataAcademiesController() {
        this.academiesController = new AcademiesController();
        this.conn = ConnectionMariaDB.getConnection();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> nameTextField.getParent().requestFocus());
        loadAcademiesData();
        initializeYearComboBox();
        handleFormData();
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        academicadd.setOnMouseClicked(event -> handleAddAcademic());
        academicdelete.setOnMouseClicked(event -> handleDeleteAcademic());

        academicadd1.setOnMouseClicked(event -> handleAddAcademic1());
        academicdelete1.setOnMouseClicked(event -> handleDeleteAcademic1());
    }

    private void handleAddAcademic() {
        academicdelete.setOpacity(1);
        iconAdd2.setOpacity(1);
        iconDelete.setOpacity(1);
        nameTextField1.setOpacity(1);
        entityTextField1.setOpacity(1);
        locationTextField1.setOpacity(1);
        yearComboBox1.setOpacity(1);
        academicadd1.setOpacity(1);
        nameText1.setOpacity(1);
        entityText1.setOpacity(1);
        locationText1.setOpacity(1);
        yearText1.setOpacity(1);
    }


    private void handleDeleteAcademic() {
        // Restablecer los campos de texto a vacío
        nameTextField1.clear();
        entityTextField1.clear();
        locationTextField1.clear();
        yearComboBox1.getSelectionModel().clearSelection();


        // Restablecer la opacidad a 0
        iconAdd2.setOpacity(0);
        iconDelete.setOpacity(0);
        academicdelete.setOpacity(0);
        nameTextField1.setOpacity(0);
        entityTextField1.setOpacity(0);
        locationTextField1.setOpacity(0);
        yearComboBox1.setOpacity(0);
        academicdelete1.setOpacity(0);
        academicadd1.setOpacity(0);
        nameText1.setOpacity(0);
        entityText1.setOpacity(0);
        locationText1.setOpacity(0);
        yearText1.setOpacity(0);
        showAlert("Academic Deleted", "Academic Deleted", Alert.AlertType.INFORMATION);
    }

    private void handleAddAcademic1() {
        academicdelete1.setOpacity(1);
        iconDelete2.setOpacity(1);
        nameTextField2.setOpacity(1);
        entityTextField2.setOpacity(1);
        locationTextField2.setOpacity(1);
        yearComboBox2.setOpacity(1);
        nameText2.setOpacity(1);
        entityText2.setOpacity(1);
        locationText2.setOpacity(1);
        yearText2.setOpacity(1);
    }


    private void handleDeleteAcademic1() {
        // Restablecer los campos de texto a vacío
        nameTextField2.clear();
        entityTextField2.clear();
        locationTextField2.clear();
        yearComboBox2.getSelectionModel().clearSelection();

        // Restablecer la opacidad a 0
        iconDelete2.setOpacity(0);
        academicdelete1.setOpacity(0);
        nameTextField2.setOpacity(0);
        entityTextField2.setOpacity(0);
        locationTextField2.setOpacity(0);
        yearComboBox2.setOpacity(0);
        nameText2.setOpacity(0);
        entityText2.setOpacity(0);
        locationText2.setOpacity(0);
        yearText2.setOpacity(0);
        showAlert("Academic Deleted", "Academic Deleted", Alert.AlertType.INFORMATION);
    }

    private void initializeYearComboBox() {
        yearComboBox.getItems().add("In progress...");
        yearComboBox.getItems().addAll(
                IntStream.rangeClosed(1980, 2024)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );

        yearComboBox1.getItems().add("In progress...");
        yearComboBox1.getItems().addAll(
                IntStream.rangeClosed(1980, 2024)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );

        yearComboBox2.getItems().add("In progress...");
        yearComboBox2.getItems().addAll(
                IntStream.rangeClosed(1980, 2024)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
    }

    private void handleFormData() {
        nameTextField.setOnMouseClicked(event -> handleTextFieldClick(nameTextField));
        entityTextField.setOnMouseClicked(event -> handleTextFieldClick(entityTextField));
        locationTextField.setOnMouseClicked(event -> handleTextFieldClick(locationTextField));
        yearComboBox.setOnMouseClicked(event -> yearComboBox.show());
        checkContact.setOnMouseClicked(event -> handleBackContact());

        nameTextField1.setOnMouseClicked(event -> handleTextFieldClick(nameTextField1));
        entityTextField1.setOnMouseClicked(event -> handleTextFieldClick(entityTextField1));
        locationTextField1.setOnMouseClicked(event -> handleTextFieldClick(locationTextField1));
        yearComboBox1.setOnMouseClicked(event -> yearComboBox1.show());

        nameTextField2.setOnMouseClicked(event -> handleTextFieldClick(nameTextField2));
        entityTextField2.setOnMouseClicked(event -> handleTextFieldClick(entityTextField2));
        locationTextField2.setOnMouseClicked(event -> handleTextFieldClick(locationTextField2));
        yearComboBox2.setOnMouseClicked(event -> yearComboBox2.show());
    }


    private void handleTextFieldClick(TextField textField) {
        textField.clear();
    }

    private void handleFormaDataSave() {
        // Recoger los datos de los campos iniciales
        String name = nameTextField.getText().trim();
        String entity = entityTextField.getText().trim();
        String location = locationTextField.getText().trim();
        String year = yearComboBox.getValue();
        String name1 = nameTextField1.getText().trim();
        String entity1 = entityTextField1.getText().trim();
        String location1 = locationTextField1.getText().trim();
        String year1 = yearComboBox1.getValue();
        String name2 = nameTextField2.getText().trim();
        String entity2 = entityTextField2.getText().trim();
        String location2 = locationTextField2.getText().trim();
        String year2 = yearComboBox2.getValue();

        // Obtener el último ID de contacto
        int lastContactID = getLastContactID();

        try {
            boolean saveDataToDatabase = academiesController.saveDataToDatabase(name, entity, location, year, name1, entity1, location1, year1, name2, entity2, location2, year2, lastContactID);

            if (saveDataToDatabase) {
                showAlert("Éxito", "Los datos se han guardado exitosamente", Alert.AlertType.INFORMATION);
                // changeSceneToFormData();
            } else {
                showAlert("Error", "No se han podido guardar los datos", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private int getLastContactID() {
        int lastContactID = 0;
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para obtener el último ID de contacto
            String query = "SELECT MAX(contact_id) FROM cvv_contact";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    lastContactID = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastContactID;
    }

    private void loadAcademiesData() {
        int contactId = Session.getInstance().getContactId();

        String query = "SELECT * FROM cvv_academies WHERE contact_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, contactId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nameTextField.setText(rs.getString("name"));
                entityTextField.setText(rs.getString("entity"));
                locationTextField.setText(rs.getString("location"));
                yearComboBox.setValue(rs.getString("year"));
            }
            if (rs.next()) {
                nameTextField1.setText(rs.getString("name"));
                entityTextField1.setText(rs.getString("entity"));
                locationTextField1.setText(rs.getString("location"));
                yearComboBox1.setValue(rs.getString("year"));
            }
            if (rs.next()) {
                nameTextField2.setText(rs.getString("name"));
                entityTextField2.setText(rs.getString("entity"));
                locationTextField2.setText(rs.getString("location"));
                yearComboBox2.setValue(rs.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



private void showAlert(String error, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(error);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void changeSceneToFormData() {
    try {
        // Cargar la nueva escena desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataExperience.fxml"));
        Parent root = loader.load();

        // Obtener el escenario actual desde el emailTextField
        Stage stage = (Stage) nameTextField.getScene().getWindow();

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

private void handleBackContact() {
    try {
        // Cargar la nueva escena desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataContact.fxml"));
        Parent root = loader.load();

        // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
        Stage stage = (Stage) nameTextField.getScene().getWindow();

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
}