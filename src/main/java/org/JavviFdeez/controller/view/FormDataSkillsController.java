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
import org.JavviFdeez.controller.SkillsController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Session;
import org.JavviFdeez.model.entity.Skills;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataSkillsController implements Initializable {

    @FXML
    private TextField spanishComboBox;

    @FXML
    private ComboBox<String> englishComboBox;


    @FXML
    private TextField spanishComboBox1;

    @FXML
    private ComboBox<String> englishComboBox1;


    @FXML
    private TextField spanishComboBox2;

    @FXML
    private ComboBox<String> englishComboBox2;


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
    private ImageView checkLanguages;

    @FXML
    private Button experiencedelete1;

    @FXML
    private Button experiencedd1;

    @FXML
    private Label spanishText;

    @FXML
    private Label englishText;

    @FXML
    private Label spanishText1;

    @FXML
    private Label englishText1;


    @FXML
    private Label spanishText2;

    @FXML
    private Label englishText2;

    private SkillsController skillsController;

    private Connection conn;


    public FormDataSkillsController() {
        this.skillsController = new SkillsController();
        this.conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> spanishComboBox.getParent().requestFocus());
        loadSkillsData();
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
        experienceAdd.setOpacity(1);
        spanishText1.setOpacity(1);
        englishText.setOpacity(1);
    }

    private void handleDeleteLanguage() {
        // Restablecer la opacidad a 0
        englishComboBox1.setOpacity(0);
        iconAdd1.setOpacity(0);
        iconDelete.setOpacity(0);
        iconDelete1.setOpacity(0);
        experiencedd1.setOpacity(0);
        spanishComboBox1.setOpacity(0);
        experiencedelete.setOpacity(0);
        spanishText1.setOpacity(0);
        englishText1.setOpacity(0);
        showAlert("Languages Deleted", "Languages Deleted", Alert.AlertType.INFORMATION);
    }

    private void handleAddLanguage1() {
        experiencedelete.setOpacity(1);
        iconDelete1.setOpacity(1);
        spanishComboBox2.setOpacity(1);
        englishComboBox2.setOpacity(1);
        experienceAdd.setOpacity(1);
        spanishText2.setOpacity(1);
        englishText2.setOpacity(1);
        experiencedelete1.setOpacity(1);
    }

    private void handleDeleteLanguage1() {
        // Restablecer la opacidad a 0
        iconDelete1.setOpacity(0);
        experiencedelete1.setOpacity(0);
        spanishComboBox2.setOpacity(0);
        englishComboBox2.setOpacity(0);
        spanishText2.setOpacity(0);
        englishText2.setOpacity(0);
        showAlert("Languages Deleted", "Languages Deleted", Alert.AlertType.INFORMATION);
    }

    private void initializeComboBox() {
        englishComboBox.getItems().addAll(
                IntStream.rangeClosed(0, 5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        englishComboBox1.getItems().addAll(
                IntStream.rangeClosed(0, 5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
        englishComboBox2.getItems().addAll(
                IntStream.rangeClosed(0, 5)
                        .boxed()
                        .sorted((a, b) -> b - a)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
        );
    }

    private void handleFormData() {
        englishComboBox.setOnMouseClicked(event -> englishComboBox.show());
        englishComboBox1.setOnMouseClicked(event -> englishComboBox1.show());
        englishComboBox2.setOnMouseClicked(event -> englishComboBox2.show());

        checkLanguages.setOnMouseClicked(event -> handleCheckLanguages());
    }

    private void handleFormaDataSave() {
        // Recoger los datos de los campos iniciales y manejar los posibles valores null
        String name = spanishComboBox.getText().trim();
        String name1 = spanishComboBox1.getText().trim();
        String name2 = spanishComboBox2.getText().trim();
        Integer value = Integer.valueOf(englishComboBox.getValue());
        Integer value1 = Integer.valueOf(englishComboBox1.getValue());
        Integer value2 = Integer.valueOf(englishComboBox2.getValue());


        try {
            boolean saveDataToDatabase = skillsController.saveDataToDatabase(name, value, name1, value1, name2, value2);
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



    private void loadSkillsData() {
        int contactId = Session.getInstance().getContactId();

        String query = "SELECT * FROM cvv_contact_skills WHERE contact_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, contactId);
            ResultSet rs = pst.executeQuery();

            int academicCount = 0; // Contador para llevar la cuenta de las academias cargadas

            while (rs.next()) {
                academicCount++;

                // Crear un objeto Academies con los datos recuperados de la base de datos
                Skills skills = new Skills(
                        rs.getString("name")
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/Customize.fxml"));
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

    private void handleCheckLanguages() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataLanguages.fxml"));
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
            showAlert("Error", "No se pudo cargar la pantalla de Languages.", Alert.AlertType.ERROR);
        }
    }
}