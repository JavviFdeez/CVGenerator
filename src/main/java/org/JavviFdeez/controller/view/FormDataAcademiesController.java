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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Button buttonSaveData;

    @FXML
    private ImageView checkContact;

    @FXML
    private ImageView academicdelete;

    @FXML
    private Label textDelete;

    @FXML
    private ImageView academicadd;

    @FXML
    private Label textAdd;

    @FXML
    private Label nameText;

    @FXML
    private Label entityText;

    @FXML
    private Label locationText;

    @FXML
    private Label yearText;

    @FXML
    private Label asteriskName;

    @FXML
    private Label asteriskEntity;

    @FXML
    private Label asteriskLocation;

    @FXML
    private Label asteriskYear;

    @FXML
    private Pane academiesPane;


    @FXML
    private VBox dynamicAcademicData;

    private AcademiesController academiesController;



    public FormDataAcademiesController() {
        this.academiesController = new AcademiesController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> nameTextField.getParent().requestFocus());
        initializeYearComboBox();
        handleFormData();
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        academicadd.setOnMouseClicked(event -> handleAddAcademic(academiesPane));
        academicdelete.setOnMouseClicked(event -> handleDeleteAcademic());
    }

    private void handleAddAcademic(Pane academiesPane) {

    }


    private void handleDeleteAcademic() {
        // Implement logic to delete academic fields dynamically
        // For demonstration purposes, let's just display a message
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
    }

    private void handleFormData() {
        nameTextField.setOnMouseClicked(event -> handleTextFieldClick(nameTextField));
        entityTextField.setOnMouseClicked(event -> handleTextFieldClick(entityTextField));
        locationTextField.setOnMouseClicked(event -> handleTextFieldClick(locationTextField));
        yearComboBox.setOnMouseClicked(event -> yearComboBox.show());
        checkContact.setOnMouseClicked(event -> handleBackContact());
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

        if (name.isEmpty() || entity.isEmpty() || location.isEmpty() || year == null || year.isEmpty()) {
            showAlert("Campos incompletos", "Por favor, rellene todos los campos obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            boolean saveDataToDatabase = academiesController.saveDataToDatabase(name, entity, location, Integer.parseInt(year));

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

        // Recoger los datos de los campos adicionales
        for (Node node : dynamicAcademicData.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                TextField nameField = (TextField) vbox.lookup("#nameTextField");
                TextField entityField = (TextField) vbox.lookup("#entityTextField");
                TextField locationField = (TextField) vbox.lookup("#locationTextField");
                ComboBox<String> yearField = (ComboBox<String>) vbox.lookup("#yearComboBox");

                String additionalName = nameField.getText().trim();
                String additionalEntity = entityField.getText().trim();
                String additionalLocation = locationField.getText().trim();
                String additionalYear = yearField.getValue();

                if (!additionalName.isEmpty() && !additionalEntity.isEmpty() && !additionalLocation.isEmpty() && additionalYear != null && !additionalYear.isEmpty()) {
                    try {
                        academiesController.saveDataToDatabase(additionalName, additionalEntity, additionalLocation, Integer.parseInt(additionalYear));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }

    private void showAlert(String error, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addAcademicDataFields() {
        VBox newFields = new VBox();

        HBox nameHBox = new HBox(new Label("Nombre"), new TextField());
        HBox entityHBox = new HBox(new Label("Entidad"), new TextField());
        HBox locationHBox = new HBox(new Label("Ubicación"), new TextField());
        HBox yearHBox = new HBox(new Label("Año"), createYearComboBox());
        ImageView academicDelete = new ImageView();

        HBox deleteHBox = new HBox(academicDelete, new Label("Eliminar Datos Académicos"));
        academicDelete.setOnMouseClicked(event -> dynamicAcademicData.getChildren().remove(newFields));

        newFields.getChildren().addAll(nameHBox, entityHBox, locationHBox, yearHBox, deleteHBox);
        dynamicAcademicData.getChildren().add(newFields);
    }

    private ComboBox<String> createYearComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        initializeYearComboBox();
        return comboBox;
    }

    private void handleDeleteAcademicData() {
        // Eliminar los nodos correspondientes de la interfaz gráfica
        dynamicAcademicData.getChildren().removeAll( dynamicAcademicData.getChildren() );
    }

    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataExperience.fxml"));
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