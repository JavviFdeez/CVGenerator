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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.JavviFdeez.controller.Contact_SkillsController;
import org.JavviFdeez.controller.CoursesController;
import org.JavviFdeez.controller.LanguagesController;
import org.JavviFdeez.controller.SkillsController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private Contact_SkillsController contact_skillsController;
    private LogInController logInController;
    private LanguagesController languagesController;
    private Connection conn;
    private Session session;
    private List<GridPane> skillsForms = new ArrayList<>();
    private List<TextField> nameTextFields = new ArrayList<>();
    private List<ComboBox<String>> valueComboBoxes = new ArrayList<>();


    public FormDataSkillsController() {
        this.skillsController = new SkillsController();
        this.contact_skillsController = new Contact_SkillsController();
        this.logInController = new LogInController();
        this.languagesController = new LanguagesController();
        this.session = Session.getInstance();
        this.conn = ConnectionMariaDB.getConnection();
    }

    public void setSkillsController(LanguagesController languagesController) throws SQLException {
        this.languagesController = languagesController;
        loadSkillsData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkLanguages.getParent().requestFocus());
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
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
    private void handleDeleteSkillsForm() throws SQLException {
        // Eliminar un nuevo formulario
        handleDeleteSkills();
    }

    private void createSkillsForm() {
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
        Label nameLabel = new Label("NAME:");
        nameLabel.setStyle("-fx-font-size: 18px;");

        Label valueLabel = new Label("VALUE:");
        valueLabel.setStyle("-fx-font-size: 18px;");

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

    private void handleDeleteSkills() throws SQLException {
        // Obtener el último formulario de skills agregado
        GridPane lastskillsForm = skillsForms.isEmpty() ? null : skillsForms.get(skillsForms.size() - 1);


        if (lastskillsForm != null) {
            // Eliminar el GridPane del VBox
            skillsContainer.getChildren().remove(lastskillsForm);

            // Eliminar el GridPane de la lista
            skillsForms.remove(lastskillsForm);
        }


        if (contact_skillsController.getSkillsById(session.getContactId()) != null) {
            // Llamar al controlador para eliminar la experiencia
            skillsController.deleteSkills(session.getContactId());
        }
    }

    private void handleFormaDataSave() {
        // Obtener el contact_id de la sesión actual
        int contactId = session.getContactId();

        try {
            // Obtener todas las skills asociadas al contacto
            List<Contact_Skills> existingSkills = contact_skillsController.getSkillsById(contactId);

            for (int i = 0; i < skillsContainer.getChildren().size(); i++) {
                Node node = skillsContainer.getChildren().get(i);
                if (node instanceof GridPane) {
                    GridPane gridPane = (GridPane) node;

                    // Recoger los datos de cada skill utilizando las referencias guardadas
                    TextField nameTextField = nameTextFields.get(i);
                    ComboBox<String> valueComboBox = valueComboBoxes.get(i);

                    // Crear un objeto Skills con los datos recolectados
                    Skills skills = new Skills();
                    skills.setName(nameTextField.getText().trim());

                    if (i < existingSkills.size()) {
                        // Actualizar la skill existente
                        Contact_Skills existingContactSkills = existingSkills.get(i);
                        int skillId = existingContactSkills.getSkill_id();

                        // Actualizar la skill en la base de datos
                        skills.setSkill_id(skillId);
                        skillsController.updateSkills(skills);

                        // Actualizar la relación en cvv_contact_skills
                        existingContactSkills.setValue(Integer.parseInt(valueComboBox.getValue()));
                        contact_skillsController.updateContact_Skill(existingContactSkills);

                        logInController.showAutoClosingAlert("AVISO: Skills se ha actualizado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                    } else {
                        // Insertar nueva skill
                        skillsController.saveSkills(skills);

                        // Obtener el id de la skill recién insertada
                        int skillId = skillsController.getLastInsertedId();

                        // Crear la relación en cvv_contact_skills
                        Contact_Skills contactSkills = new Contact_Skills();
                        contactSkills.setContact_id(contactId);
                        contactSkills.setSkill_id(skillId);
                        contactSkills.setValue(Integer.parseInt(valueComboBox.getValue()));
                        contact_skillsController.saveContact_Skill(contactSkills);

                        logInController.showAutoClosingAlert("AVISO: Skills se ha guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                    }
                    changeSceneToFormData();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: Error al guardar los datos de Skills.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    public void loadSkillsData() throws SQLException {
        if (skillsController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener todas las skills asociadas con el contactId
            List<Contact_Skills> contactSkillsList = contact_skillsController.getSkillsById(contactId);

            // Verificar si se encontraron skills asociadas
            if (contactSkillsList != null && !contactSkillsList.isEmpty()) {
                // Recorrer todas las skills recuperadas
                for (int i = 0; i < contactSkillsList.size(); i++) {
                    Contact_Skills contactSkills = contactSkillsList.get(i);
                    Skills skills = contactSkills.getSkills();

                    // Verificar si skills no es null
                    if (skills != null) {
                        // Verificar si hay suficientes GridPane y campos de texto
                        if (i < skillsForms.size()) {
                            GridPane gridPane = skillsForms.get(i);

                            // Obtener los campos de texto correspondientes del GridPane actual
                            TextField nameTextField = nameTextFields.get(i);
                            ComboBox<String> valueComboBox = valueComboBoxes.get(i);

                            // Cargar los datos de la habilidad en los campos de texto y ComboBox correspondientes
                            nameTextField.setText(skills.getName());
                            valueComboBox.setValue(String.valueOf(contactSkills.getValue()));
                        } else {
                            // Si hay más habilidades que GridPane disponibles, se crea un nuevo GridPane y se cargan los datos
                            createSkillsForm();
                            GridPane gridPane = skillsForms.get(skillsForms.size() - 1);

                            // Obtener los campos de texto correspondientes del nuevo GridPane
                            TextField nameTextField = nameTextFields.get(nameTextFields.size() - 1);
                            ComboBox<String> valueComboBox = valueComboBoxes.get(valueComboBoxes.size() - 1);

                            // Cargar los datos de la habilidad en los campos de texto y ComboBox correspondientes
                            nameTextField.setText(skills.getName());
                            valueComboBox.setValue(String.valueOf(contactSkills.getValue()));
                        }
                    }
                }
            }
        }
    }


    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/Customize.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            CustomizeController customizeController = loader.getController();

            // Obtener el escenario actual desde el emailTextField
            Scene scene = buttonSaveData.getScene();
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
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Customize.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
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
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Languages.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}