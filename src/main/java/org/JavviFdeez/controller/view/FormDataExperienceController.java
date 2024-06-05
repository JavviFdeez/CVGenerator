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
import org.JavviFdeez.controller.AcademiesController;
import org.JavviFdeez.controller.ExperiencesController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Experiences;
import org.JavviFdeez.model.entity.Session;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormDataExperienceController implements Initializable {

    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkAcademies;

    @FXML
    private ImageView backAcademies;

    @FXML
    private Pane paneForm;

    @FXML
    private VBox experienceContainer;


    private ExperiencesController experiencesController;
    private LogInController logInController;
    private AcademiesController academiesController;
    private Connection conn;
    private Session session;
    private List<GridPane> experienceForms = new ArrayList<>();
    private List<TextField> nameTextFields = new ArrayList<>();
    private List<TextField> durationTextFields = new ArrayList<>();
    private List<TextField> companyTextFields = new ArrayList<>();
    private List<TextField> locationTextFields = new ArrayList<>();
    private List<ComboBox<String>> yearComboBoxes = new ArrayList<>();

    public FormDataExperienceController() {
        this.experiencesController = new ExperiencesController();
        this.session = Session.getInstance();
        this.logInController = new LogInController();
        this.conn = ConnectionMariaDB.getConnection();
        this.academiesController = new AcademiesController();
    }

    public void setAcademiesController(AcademiesController academiesController) throws SQLException {
        this.academiesController = academiesController;
        loadExperienceData();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkAcademies.getParent().requestFocus());
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        checkAcademies.setOnMouseClicked(event -> handleBackAcademies());
        backAcademies.setOnMouseClicked(event -> handleBackAcademies());
        handleAddExperienceForm();
    }

    @FXML
    private void handleAddExperienceForm() {
        // Crear y añadir un nuevo formulario
        createExperienceForm();
    }

    @FXML
    private void handleDeleteExperienceForm() throws SQLException {
        // Eliminar un nuevo formulario
        handleDeleteExperience();
    }

    private void createExperienceForm() {
        // Crear campos de Name, Entity, Location y Year
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

        TextField durationTextField = new TextField();
        durationTextField.setPromptText("Duration(months)");
        VBox.setMargin(durationTextField, new Insets(0, 100, 0, 0));
        durationTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );


        TextField companyTextField = new TextField();
        companyTextField.setPromptText("Company");
        VBox.setMargin(companyTextField, new Insets(0, 100, 0, 0));
        companyTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );

        TextField locationTextField = new TextField();
        locationTextField.setPromptText("Location");
        VBox.setMargin(locationTextField, new Insets(0, 100, 0, 0));
        locationTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );

        ComboBox<String> yearComboBox = new ComboBox<>();
        yearComboBox.getItems().addAll(
                "2024", "2023", "2022", "2021", "2020",
                "2019", "2018", "2017", "2016", "2015",
                "2014", "2013", "2012", "2011", "2010",
                "2009", "2008", "2007", "2006", "2005",
                "2004", "2003", "2002", "2001", "2000",
                "1999", "1998", "1997", "1996", "1995",
                "1994", "1993", "1992", "1991", "1990",
                "1989", "1988", "1987", "1986", "1985",
                "1984", "1983", "1982", "1981", "1980"
        );

        VBox.setMargin(yearComboBox, new Insets(0, 100, 0, 0));yearComboBox.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        yearComboBox.setPromptText("Year");
        yearComboBox.setPrefWidth(200);

        // Crear un GridPane para organizar los elementos
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.getColumnConstraints().addAll(
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints()
        );


        // Crear etiquetas
        Label nameLabel = new Label("NAME:");nameLabel.setStyle("-fx-font-size: 18px;");

        Label durationLabel = new Label("DURATION:");durationLabel.setStyle("-fx-font-size: 18px;");

        Label companyLabel = new Label("COMPANY:"); companyLabel.setStyle("-fx-font-size: 18px;");

        Label locationLabel = new Label("LOCATION:"); locationLabel.setStyle("-fx-font-size: 18px;");

        Label yearLabel = new Label("YEAR:");yearLabel.setStyle("-fx-font-size: 18px;");

        // Agregar las etiquetas en la primera fila
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(durationLabel, 1, 0);
        gridPane.add(companyLabel, 2, 0);
        gridPane.add(locationLabel, 3, 0);
        gridPane.add(yearLabel, 4, 0);

        // Añadir los campos de texto en la segunda fila
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(durationTextField, 1, 1);
        gridPane.add(companyTextField, 2, 1);
        gridPane.add(locationTextField, 3, 1);
        gridPane.add(yearComboBox, 4, 1);

        // Ajustar las restricciones de columna para que se extiendan horizontalmente
        for (ColumnConstraints constraint : gridPane.getColumnConstraints()) {
            constraint.setHgrow(Priority.ALWAYS);
        }

        // Agregar los campos a las listas
        nameTextFields.add(nameTextField);
        durationTextFields.add(durationTextField);
        companyTextFields.add(companyTextField);
        locationTextFields.add(locationTextField);
        yearComboBoxes.add(yearComboBox);

        experienceForms.add(gridPane);
        // Agregar el GridPane al VBox
        experienceContainer.getChildren().add(gridPane);
    }

    private void handleDeleteExperience() throws SQLException {
        // Obtener el último formulario de experiencia agregado
        GridPane lastExperienceForm = experienceForms.isEmpty() ? null : experienceForms.get(experienceForms.size() - 1);


        if (lastExperienceForm != null) {
            // Eliminar el GridPane del VBox
            experienceContainer.getChildren().remove(lastExperienceForm);

            // Eliminar el GridPane de la lista
            experienceForms.remove(lastExperienceForm);
        }


        if (experiencesController.getExperienceById(session.getContactId()) != null) {
            // Llamar al controlador para eliminar la experiencia
            experiencesController.deleteExperiences(session.getContactId());
        }
    }


    private void handleFormaDataSave() {
        // Obtener el contact_id de la sesión actual
        int contactId = session.getContactId();

        try {
            // Obtener todas las experiencias asociadas al contacto
            List<Experiences> existingExperiences = experiencesController.getExperienceById(contactId);

            for (int i = 0; i < experienceContainer.getChildren().size(); i++) {
                Node node = experienceContainer.getChildren().get(i);
                if (node instanceof GridPane) {
                    GridPane gridPane = (GridPane) node;

                    // Recoger los datos de cada experiencia utilizando las referencias guardadas
                    TextField nameTextField = nameTextFields.get(i);
                    TextField companyTextField = companyTextFields.get(i);
                    TextField durationTextField = durationTextFields.get(i);
                    TextField locationTextField = locationTextFields.get(i);
                    ComboBox<String> yearComboBox = yearComboBoxes.get(i);

                    // Crear un objeto Experiences con los datos recolectados
                    Experiences experiences = new Experiences();
                    experiences.setContact_id(contactId);
                    experiences.setName(nameTextField.getText().trim());
                    experiences.setCompany(companyTextField.getText());
                    experiences.setDuration(durationTextField.getText().trim());
                    experiences.setLocation(locationTextField.getText().trim());
                    experiences.setYear(yearComboBox.getValue());

                    if (i < existingExperiences.size()) {
                        // Actualizar experiencia existente
                        experiences.setExperience_id(existingExperiences.get(i).getExperience_id());
                        experiencesController.updateExperiences(experiences);
                        logInController.showAutoClosingAlert("AVISO: Experiencia se ha actualizado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    } else {
                        // Insertar nueva experiencia
                        experiencesController.saveExperiences(experiences);
                        logInController.showAutoClosingAlert("AVISO: Experiencia se ha guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: Error al guardar los datos de Experiencias.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    public void loadExperienceData() throws SQLException {
        if (academiesController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener todas las experiencias asociadas con el contactId
            List<Experiences> experiencesList = experiencesController.getExperienceById(contactId);

            // Verificar si se encontraron experiencias asociadas
            if (experiencesList != null && !experiencesList.isEmpty()) {
                // Recorrer todas las experiencias recuperadas
                for (int i = 0; i < experiencesList.size(); i++) {
                    Experiences experiences = experiencesList.get(i);

                    // Verificar si hay suficientes GridPane y campos de texto
                    if (i < experienceForms.size()) {
                        GridPane gridPane = experienceForms.get(i);

                        // Obtener los campos de texto correspondientes del GridPane actual
                        TextField nameTextField = nameTextFields.get(i);
                        TextField durationTextField = durationTextFields.get(i);
                        TextField companyTextField = companyTextFields.get(i);
                        TextField locationTextField = locationTextFields.get(i);
                        ComboBox<String> yearComboBox = yearComboBoxes.get(i);

                        // Cargar los datos de la experiencia en los campos de texto correspondientes
                        nameTextField.setText(experiences.getName());
                        durationTextField.setText(experiences.getDuration());
                        companyTextField.setText(experiences.getCompany());
                        locationTextField.setText(experiences.getLocation());
                        yearComboBox.setValue(experiences.getYear());
                    } else {
                        // Si hay más experiencias que GridPane disponibles, se crea un nuevo GridPane y se cargan los datos
                        createExperienceForm();
                        GridPane gridPane = experienceForms.get(experienceForms.size() - 1);

                        // Obtener los campos de texto correspondientes del nuevo GridPane
                        TextField nameTextField = nameTextFields.get(nameTextFields.size() - 1);
                        TextField durationTextField = durationTextFields.get(durationTextFields.size() - 1);
                        TextField companyTextField = companyTextFields.get(companyTextFields.size() - 1);
                        TextField locationTextField = locationTextFields.get(locationTextFields.size() - 1);
                        ComboBox<String> yearComboBox = yearComboBoxes.get(yearComboBoxes.size() - 1);

                        // Cargar los datos de la academia en los campos de texto correspondientes
                        nameTextField.setText(experiences.getName());
                        durationTextField.setText(experiences.getDuration());
                        companyTextField.setText(experiences.getCompany());
                        locationTextField.setText(experiences.getLocation());
                        yearComboBox.setValue(experiences.getYear());
                    }
                }
            }
        }
    }

    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataCourses.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            FormDataCoursesController formDataCoursesController = loader.getController();
            formDataCoursesController.setCoursesController(experiencesController);

            // Obtener el escenario actual
            Stage stage = (Stage) checkAcademies.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Courses.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBackAcademies() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataAcademies.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual
            Stage stage = (Stage) checkAcademies.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la Academia.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}