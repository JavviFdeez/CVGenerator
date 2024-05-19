package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.JavviFdeez.controller.CoursesController;
import org.JavviFdeez.controller.ExperiencesController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataCoursesController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField entityTextField;

    @FXML
    private Button experiencedelete;

    @FXML
    private ImageView iconAdd;

    @FXML
    private ImageView iconAdd2;

    @FXML
    private Button experienceAdd;

    @FXML
    private ImageView iconDelete1;

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
    private Button experiencedelete1;

    @FXML
    private Button experiencedd1;

    @FXML
    private Label nameText1;

    @FXML
    private Label entityText1;

    @FXML
    private TextField nameTextField2;

    @FXML
    private TextField entityTextField2;

    @FXML
    private Label nameText2;

    @FXML
    private Label entityText2;

    private CoursesController coursesController;

    private Connection conn;


    public FormDataCoursesController() {
        this.coursesController = new CoursesController();
        this.conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> nameTextField.getParent().requestFocus());
        loadExperienceData();
        handleFormData();
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        experienceAdd.setOnMouseClicked(event -> handleAddExperience());
        experiencedelete.setOnMouseClicked(event -> handleDeleteExperience());

        experiencedd1.setOnMouseClicked(event -> handleAddExperience1());
        experiencedelete1.setOnMouseClicked(event -> handleDeleteExperience1());
    }

    private void handleAddExperience() {
        experiencedelete.setOpacity(1);
        experiencedd1.setOpacity(1);
        iconAdd2.setOpacity(1);
        iconDelete1.setOpacity(1);
        nameTextField1.setOpacity(1);
        entityTextField1.setOpacity(1);
        experienceAdd.setOpacity(1);
        nameText1.setOpacity(1);
        entityText1.setOpacity(1);
        iconDelete1.setOpacity(1);
    }

    private void handleDeleteExperience() {
        // Restablecer los campos de texto a vacío
        nameTextField1.clear();
        entityTextField1.clear();


        // Restablecer la opacidad a 0
        iconAdd2.setOpacity(0);
        iconDelete1.setOpacity(0);
        experiencedd1.setOpacity(0);
        nameTextField1.setOpacity(0);
        entityTextField1.setOpacity(0);
        experiencedelete.setOpacity(0);
        nameText1.setOpacity(0);
        entityText1.setOpacity(0);
        showAlert("Experience Deleted", "Experience Deleted", Alert.AlertType.INFORMATION);
    }

    private void handleAddExperience1() {
        experiencedelete.setOpacity(1);
        iconDelete2.setOpacity(1);
        nameTextField2.setOpacity(1);
        entityTextField2.setOpacity(1);
        nameText2.setOpacity(1);
        entityText2.setOpacity(1);
        experiencedelete1.setOpacity(1);
    }

    private void handleDeleteExperience1() {
        // Restablecer los campos de texto a vacío
        nameTextField2.clear();
        entityTextField2.clear();

        // Restablecer la opacidad a 0
        iconDelete2.setOpacity(0);
        experiencedelete1.setOpacity(0);
        nameTextField2.setOpacity(0);
        entityTextField2.setOpacity(0);

        nameText2.setOpacity(0);
        entityText2.setOpacity(0);
        showAlert("Academic Deleted", "Academic Deleted", Alert.AlertType.INFORMATION);
    }



    private void handleFormData() {
        nameTextField.setOnMouseClicked(event -> handleTextFieldClick(nameTextField));
        entityTextField.setOnMouseClicked(event -> handleTextFieldClick(entityTextField));
        checkContact.setOnMouseClicked(event -> handleBackAcademies());

        nameTextField1.setOnMouseClicked(event -> handleTextFieldClick(nameTextField1));
        entityTextField1.setOnMouseClicked(event -> handleTextFieldClick(entityTextField1));

        nameTextField2.setOnMouseClicked(event -> handleTextFieldClick(nameTextField2));
        entityTextField2.setOnMouseClicked(event -> handleTextFieldClick(entityTextField2));
    }

    private void handleTextFieldClick(TextField textField) {
        textField.clear();
    }

    private void handleFormaDataSave() {
        // Recoger los datos de los campos iniciales
        String name = nameTextField.getText().trim();
        String entity = entityTextField.getText().trim();
        String name1 = nameTextField1.getText().trim();
        String entity1 = entityTextField1.getText().trim();
        String name2 = nameTextField2.getText().trim();
        String entity2 = entityTextField2.getText().trim();


        try {
            boolean saveDataToDatabase = coursesController.saveDataToDatabase(name, entity, name1, entity1, name2, entity2);

            if (saveDataToDatabase) {
                showAlert("Éxito", "Los datos se han guardado exitosamente", Alert.AlertType.INFORMATION);
                changeSceneToFormData();
            } else {
                showAlert("Error", "No se han podido guardar los datos", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void setAcademyDataInFields(Academies academy, int count) {
        // Establecer el texto de la etiqueta correspondiente
        if (count == 1) {
            nameTextField.setText(academy.getName());
            entityTextField.setText(academy.getEntity());
        } else if (count == 2) {
            nameTextField1.setText(academy.getName());
            entityTextField1.setText(academy.getEntity());
        } else if (count == 3) {
            nameTextField2.setText(academy.getName());
            entityTextField2.setText(academy.getEntity());
        }
    }

    private void loadExperienceData() {
        int contactId = Session.getInstance().getContactId();

        String query = "SELECT * FROM cvv_academies WHERE contact_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, contactId);
            ResultSet rs = pst.executeQuery();

            int academicCount = 0; // Contador para llevar la cuenta de las academias cargadas

            while (rs.next()) {
                academicCount++;

                // Crear un objeto Academies con los datos recuperados de la base de datos
                Academies academy = new Academies(
                        rs.getString("name"),
                        rs.getString("entity"),
                        rs.getString("location"),
                        rs.getString("year")
                );

                // Llamar al método setAcademyDataInFields para establecer los valores en los campos de texto
                setAcademyDataInFields(academy, academicCount);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataCourses.fxml"));
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
            showAlert("Error", "No se pudo cargar la pantalla de Experience.", Alert.AlertType.ERROR);
        }
    }

    private void handleBackAcademies() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataAcademies.fxml"));
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