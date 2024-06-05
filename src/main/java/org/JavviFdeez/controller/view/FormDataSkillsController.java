package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataSkillsController implements Initializable {
    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkLanguages;

    @FXML
    private ImageView backLanguages;

    @FXML
    private Pane paneForm;

    @FXML
    private VBox skillsContainer;


    private SkillsController skillsController;
    private LogInController logInController;
    private LanguagesController languagesController;
    private Connection conn;
    private Session session;
    private List<GridPane> skillsForms = new ArrayList<>();
    private List<TextField> nameTextFields = new ArrayList<>();
    private List<ComboBox<String>> valueComboBoxes = new ArrayList<>();


    public FormDataSkillsController() {
        this.skillsController = new SkillsController();
        this.logInController = new LogInController();
        this.languagesController = new LanguagesController();
        this.session = Session.getInstance();
        this.conn = ConnectionMariaDB.getConnection();
    }

    public void setLanguagesController(LanguagesController languagesController) throws SQLException {
        this.languagesController = languagesController;
        loadSkillsData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkLanguages.getParent().requestFocus());
        buttonSaveData.setOnAction(event -> changeSceneToFormData());
        checkLanguages.setOnMouseClicked(event -> handleBackLanguages());
        backLanguages.setOnMouseClicked(event -> handleBackLanguages());
        handleAddSkillsForm();
    }

    @FXML
    private void handleAddSkillsForm() {
        // Crear y añadir un nuevo formulario
        createSkillsForm();
    }

    @FXML
    private void handleDeleteSkillsForm(){
        // Eliminar un nuevo formulario
        handleDeleteSkills();
    }

    private void createExperienceForm() {
        // Crear campos Name y Value
        TextField nameTextField = new TextField();
        VBox.setMargin(nameTextField, new Insets(0, 100, 0, 0));
        nameTextField.setStyle(
                "-fx-font-weight: bold; " +
                        "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        nameTextField.setPromptText("Name");

        ComboBox<String> valueComboBox = new ComboBox<>();
        valueComboBox.getItems().addAll(
                "0", "5", "10", "15", "20", "25", "30", "35", "40",
                "45", "50", "55", "60", "65", "70", "75", "80", "85", "90",
                "95", "100"
        );

        VBox.setMargin(valueComboBox, new Insets(0, 100, 0, 0));
        valueComboBox.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        valueComboBox.setPromptText("Value");
        valueComboBox.setPrefWidth(200);

        // Crear un GridPane para organizar los elementos
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.getColumnConstraints().addAll(
                new ColumnConstraints(),
                new ColumnConstraints()
        );


        // Crear etiquetas
        Label nameLabel = new Label("NAME:");nameLabel.setStyle("-fx-font-size: 18px;");

        Label valueLabel = new Label("VALUE:");valueLabel.setStyle("-fx-font-size: 18px;");

        // Agregar las etiquetas en la primera fila
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(valueLabel, 1, 0);

        // Añadir los campos de texto en la segunda fila
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(valueComboBox, 1, 1);

        // Agregar los campos a las listas
        nameTextFields.add(nameTextField);
        valueComboBoxes.add(valueComboBox);

        skillsForms.add(gridPane);
        // Agregar el GridPane al VBox
        skillsContainer.getChildren().add(gridPane);
    }

    private void handleDeleteExperience() throws SQLException {
        // Obtener el último formulario de skills agregado
        GridPane lastskillsForm = skillsForms.isEmpty() ? null : skillsForms.get(skillsForms.size() - 1);


        if (lastskillsForm != null) {
            // Eliminar el GridPane del VBox
            skillsContainer.getChildren().remove(lastskillsForm);

            // Eliminar el GridPane de la lista
            skillsForms.remove(lastskillsForm);
        }


        if (skillsController.getSkillsById(session.getContactId()) != null) {
            // Llamar al controlador para eliminar la experiencia
            experiencesController.deleteExperiences(session.getContactId());
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
            Stage stage = (Stage) checkLanguages.getScene().getWindow();

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

    private void handleBackLanguages() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataLanguages.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Stage stage = (Stage) checkLanguages.getScene().getWindow();

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