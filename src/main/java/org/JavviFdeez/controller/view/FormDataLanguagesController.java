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
import org.JavviFdeez.controller.LanguagesController;
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

public class FormDataLanguagesController implements Initializable {

    @FXML
    private ComboBox<String>  spanishComboBox;

    @FXML
    private ComboBox<String>  englishComboBox;

    @FXML
    private ComboBox<String>  frenchComboBox;

    @FXML
    private ComboBox<String>  spanishComboBox1;

    @FXML
    private ComboBox<String>  englishComboBox1;

    @FXML
    private ComboBox<String>  frenchComboBox1;

    @FXML
    private ComboBox<String>  spanishComboBox2;

    @FXML
    private ComboBox<String>  englishComboBox2;

    @FXML
    private ComboBox<String>  frenchComboBox2;

    @FXML
    private Button experiencedelete;

    @FXML
    private ImageView iconAdd1;

    @FXML
    private ImageView iconAdd;

    @FXML
    private Button experienceAdd;

    @FXML
    private ImageView iconDelete1;

    @FXML
    private ImageView iconDelete;

    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkCourses;

    @FXML
    private Button experiencedelete1;

    @FXML
    private Button experiencedd1;

    @FXML
    private Label spanishText;

    @FXML
    private Label englishText;

    @FXML
    private Label frenchText;

    @FXML
    private Label spanishText1;

    @FXML
    private Label englishText1;

    @FXML
    private Label frenchText1;

    @FXML
    private Label spanishText2;

    @FXML
    private Label englishText2;

    @FXML
    private Label frenchText2;

    private LanguagesController languagesController;

    private Connection conn;


    public FormDataLanguagesController() {
        this.languagesController = new LanguagesController();
        this.conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> spanishComboBox.getParent().requestFocus());
        loadExperienceData();
        initializeComboBox();
        handleFormData();
        buttonSaveData.setOnAction(event -> changeSceneToFormData());
        experienceAdd.setOnMouseClicked(event -> handleAddLanguage());
        experiencedelete.setOnMouseClicked(event -> handleDeleteLanguage());

        experiencedd1.setOnMouseClicked(event -> handleAddLanguage1());
        experiencedelete1.setOnMouseClicked(event -> handleDeleteLanguage1());
    }

    private void handleAddLanguage() {
        experiencedelete.setOpacity(1);
        experiencedd1.setOpacity(1);
        iconAdd1.setOpacity(1);
        iconDelete.setOpacity(1);
        spanishComboBox1.setOpacity(1);
        englishComboBox1.setOpacity(1);
        frenchComboBox1.setOpacity(1);
        experienceAdd.setOpacity(1);
        spanishText1.setOpacity(1);
        englishText.setOpacity(1);
        frenchText.setOpacity(1);
    }

    private void handleDeleteLanguage() {
        // Restablecer la opacidad a 0
        englishComboBox1.setOpacity(0);
        frenchComboBox1.setOpacity(0);
        iconAdd1.setOpacity(0);
        iconDelete.setOpacity(0);
        iconDelete1.setOpacity(0);
        experiencedd1.setOpacity(0);
        spanishComboBox1.setOpacity(0);
        experiencedelete.setOpacity(0);
        spanishText1.setOpacity(0);
        englishText1.setOpacity(0);
        frenchText1.setOpacity(0);
        showAlert("Languages Deleted", "Languages Deleted", Alert.AlertType.INFORMATION);
    }

    private void handleAddLanguage1() {
        experiencedelete.setOpacity(1);
        iconDelete1.setOpacity(1);
        spanishComboBox2.setOpacity(1);
        englishComboBox2.setOpacity(1);
        frenchComboBox2.setOpacity(1);
        experienceAdd.setOpacity(1);
        spanishText2.setOpacity(1);
        englishText2.setOpacity(1);
        frenchText2.setOpacity(1);
        experiencedelete1.setOpacity(1);
    }

    private void handleDeleteLanguage1() {
        // Restablecer la opacidad a 0
        iconDelete1.setOpacity(0);
        experiencedelete1.setOpacity(0);
        spanishComboBox2.setOpacity(0);
        englishComboBox2.setOpacity(0);
        frenchComboBox2.setOpacity(0);
        spanishText2.setOpacity(0);
        englishText2.setOpacity(0);
        frenchText2.setOpacity(0);
        showAlert("Languages Deleted", "Languages Deleted", Alert.AlertType.INFORMATION);
    }

    private void initializeComboBox() {
        spanishComboBox.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        englishComboBox.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        frenchComboBox.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );

        spanishComboBox1.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        englishComboBox1.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        frenchComboBox1.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );

        spanishComboBox2.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        englishComboBox2.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        frenchComboBox2.getItems().addAll(
                IntStream.rangeClosed(0,5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
    }

    private void handleFormData() {
        spanishComboBox.setOnMouseClicked(event -> spanishComboBox.show());
        englishComboBox.setOnMouseClicked(event -> englishComboBox.show());
        frenchComboBox.setOnMouseClicked(event -> frenchComboBox.show());

        spanishComboBox1.setOnMouseClicked(event -> spanishComboBox1.show());
        englishComboBox1.setOnMouseClicked(event -> englishComboBox1.show());
        frenchComboBox1.setOnMouseClicked(event -> frenchComboBox1.show());

        spanishComboBox2.setOnMouseClicked(event -> spanishComboBox2.show());
        englishComboBox2.setOnMouseClicked(event -> englishComboBox2.show());
        frenchComboBox2.setOnMouseClicked(event -> frenchComboBox2.show());
        checkCourses.setOnMouseClicked(event -> handleBackCourses());
    }

    private void handleFormaDataSave() {
        // Recoger los datos de los campos iniciales
        String spanish = spanishComboBox.getValue();
        String english = englishComboBox.getValue();
        String french = frenchComboBox.getValue();

        String spanish1 = spanishComboBox1.getValue();
        String english1 = englishComboBox1.getValue();
        String french1 = frenchComboBox1.getValue();

        String spanish2 = spanishComboBox2.getValue();
        String english2 = englishComboBox2.getValue();
        String french2 = frenchComboBox2.getValue();



        try {
            boolean saveDataToDatabase = languagesController.saveDataToDatabase(spanish, english, french, spanish1, english1, french1, spanish2, english2, french2);

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
                        rs.getInt("contact_id"),
                        rs.getString("name"),
                        rs.getString("entity"),
                        rs.getString("location"),
                        rs.getString("year")
                );

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataSkills.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) spanishComboBox.getScene().getWindow();

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

    private void handleBackCourses() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataCourses.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Stage stage = (Stage) spanishComboBox.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert("Error", "No se pudo cargar la pantalla de courses.", Alert.AlertType.ERROR);
        }
    }
}