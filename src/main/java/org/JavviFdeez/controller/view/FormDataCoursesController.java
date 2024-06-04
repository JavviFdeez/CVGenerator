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
import org.JavviFdeez.controller.CoursesController;
import org.JavviFdeez.controller.ExperiencesController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Courses;
import org.JavviFdeez.model.entity.Experiences;
import org.JavviFdeez.model.entity.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataCoursesController implements Initializable {

    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkExperience;

    @FXML
    private ImageView backExperiences;

    @FXML
    private Pane paneForm;
    @FXML
    private VBox coursesContainer;

    private CoursesController coursesController;
    private ExperiencesController experiencesController;
    private LogInController logInController;
    private Connection conn;
    private Session session;
    private List<GridPane> coursesForms = new ArrayList<>();
    private List<TextField> nameTextFields = new ArrayList<>();
    private List<TextField> durationTextFields = new ArrayList<>();



    public FormDataCoursesController() {
        this.coursesController = new CoursesController();
        this.experiencesController = new ExperiencesController();
        this.logInController = new LogInController();
        this.conn = ConnectionMariaDB.getConnection();
        this.session = Session.getInstance();
    }

    public void setCoursesController(ExperiencesController experiencesController) throws SQLException {
        this.experiencesController = experiencesController;
        loadCoursesData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkExperience.getParent().requestFocus());;
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        checkExperience.setOnMouseClicked(event -> handleBackExperience());
        backExperiences.setOnMouseClicked(event -> handleBackExperience());
        handleAddCoursesForm();
    }

    @FXML
    private void handleAddCoursesForm() {
        // Crear y añadir un nuevo formulario
        createCoursesForm();
    }

    @FXML
    private void handleDeleteCoursesForm() throws SQLException {
        // Eliminar un nuevo formulario
        handleDeleteCourses();
    }

    private void createCoursesForm() {
        // Crear campos
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
        durationTextField.setPromptText("Duration(hours)");
        VBox.setMargin(durationTextField, new Insets(0, 100, 0, 0));
        durationTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );

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

        Label durationLabel = new Label("DURATION:");
        durationLabel.setStyle("-fx-font-size: 18px;");

        // Agregar las etiquetas en la primera fila
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(durationLabel, 1, 0);

        // Añadir los campos de texto en la segunda fila
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(durationTextField, 1, 1);

        // Ajustar las restricciones de columna para que se extiendan horizontalmente
        for (ColumnConstraints constraint : gridPane.getColumnConstraints()) {
            constraint.setHgrow(Priority.ALWAYS);
        }

        // Agregar los campos a las listas
        nameTextFields.add(nameTextField);
        durationTextFields.add(durationTextField);

        coursesForms.add(gridPane);
        // Agregar el GridPane al VBox
        coursesContainer.getChildren().add(gridPane);
    }

    private void handleDeleteCourses() throws SQLException {
        // Obtener el último formulario de experiencia agregado
        GridPane lastCoursesForm = coursesForms.isEmpty() ? null : coursesForms.get(coursesForms.size() - 1);


        if (lastCoursesForm != null) {
            // Eliminar el GridPane del VBox
            coursesContainer.getChildren().remove(lastCoursesForm);

            // Eliminar el GridPane de la lista
            coursesForms.remove(lastCoursesForm);
        }

        if (coursesController.getCoursesById(session.getContactId()) != null) {
            // Llamar al controlador para eliminar el cursos
            coursesController.deleteCourses(session.getContactId());
        }
    }

    private void handleFormaDataSave() {
        // Obtener el contact_id de la sesión actual
        int contactId = session.getContactId();

        try {
            // Obtener todas las Curses asociadas al contacto
            List<Courses> existingCourses = coursesController.getCoursesById(contactId);

            for (int i = 0; i < coursesContainer.getChildren().size(); i++) {
                Node node = coursesContainer.getChildren().get(i);
                if (node instanceof GridPane) {
                    GridPane gridPane = (GridPane) node;

                    // Recoger los datos de cada courses utilizando las referencias guardadas
                    TextField nameTextField = nameTextFields.get(i);
                    TextField durationTextField = durationTextFields.get(i);

                    // Crear un objeto Courses con los datos recolectados
                    Courses courses = new Courses();
                    courses.setContact_id(contactId);
                    courses.setName(nameTextField.getText().trim());
                    Pattern pattern = Pattern.compile("^[0-9]*$");
                    Matcher matcher = pattern.matcher(durationTextField.getText());

                    if (matcher.matches()) {
                        // El texto solo contiene números, continuar con la conversión a entero
                        courses.setDuration(Integer.parseInt(durationTextField.getText().trim()));
                    } else {
                        logInController.showAutoClosingAlert("ERROR: Duración de Cursos debe ser un número.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
                    }
                    courses.setDuration(Integer.parseInt(durationTextField.getText().trim()));

                    if (i < existingCourses.size()) {
                        // Actualizar courses existente
                        courses.setCourse_id(existingCourses.get(i).getCourse_id());
                        coursesController.updateCourses(courses);
                        logInController.showAutoClosingAlert("AVISO: Courses se ha actualizado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    } else {
                        // Insertar nueva experiencia
                        coursesController.saveCourses(courses);
                        logInController.showAutoClosingAlert("AVISO: Courses se ha guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: Error al guardar los datos de Courses.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }


    public void loadCoursesData() throws SQLException {
        if (experiencesController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener todas las courses asociadas con el contactId
            List<Courses> coursesList = coursesController.getCoursesById(contactId);

            // Limpiar los formularios existentes
            coursesContainer.getChildren().clear();
            coursesForms.clear();
            nameTextFields.clear();
            durationTextFields.clear();

            // Verificar si se encontraron courses asociadas
            if (coursesList != null && !coursesList.isEmpty()) {
                // Recorrer todas las courses recuperadas
                for (int i = 0; i < coursesList.size(); i++) {
                    Courses courses = coursesList.get(i);

                    // Verificar si hay suficientes GridPane y campos de texto
                    if (i < coursesForms.size()) {
                        GridPane gridPane = coursesForms.get(i);

                        // Obtener los campos de texto correspondientes del GridPane actual
                        TextField nameTextField = nameTextFields.get(i);
                        TextField durationTextField = durationTextFields.get(i);

                        // Cargar los datos de la academia en los campos de texto correspondientes
                        nameTextField.setText(courses.getName());
                        durationTextField.setText(String.valueOf(courses.getDuration()));

                    } else {
                        // Si hay más courses que GridPane disponibles, se crea un nuevo GridPane y se cargan los datos
                        createCoursesForm();
                        GridPane gridPane = coursesForms.get(coursesForms.size() - 1);

                        // Obtener los campos de texto correspondientes del nuevo GridPane
                        TextField nameTextField = nameTextFields.get(nameTextFields.size() - 1);
                        TextField durationTextField = durationTextFields.get(durationTextFields.size() - 1);

                        // Cargar los datos de la courses en los campos de texto correspondientes
                        nameTextField.setText(courses.getName());
                        durationTextField.setText(String.valueOf(courses.getDuration()));
                    }
                }
            }
        }
    }


    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataLanguages.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            FormDataLanguagesController formDataLanguagesController = loader.getController();
            formDataLanguagesController.setCoursesController(coursesController);

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) checkExperience.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Languages.", LogInController.AlertType.ERROR, Duration.seconds(1.5));        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBackExperience() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataExperience.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Stage stage = (Stage) checkExperience.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la Experiencia.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}