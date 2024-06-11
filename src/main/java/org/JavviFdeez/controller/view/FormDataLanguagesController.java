package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.JavviFdeez.controller.CoursesController;
import org.JavviFdeez.controller.LanguagesController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormDataLanguagesController implements Initializable {

    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkCourses;

    @FXML
    private ImageView backCourses;

    @FXML
    private Pane paneForm;
    @FXML
    private VBox languagesContainer;

    private LanguagesController languagesController;
    private CoursesController coursesController;
    private LogInController logInController;
    private Connection conn;
    private Session session;
    private List<GridPane> languagesForms = new ArrayList<>();
    private List<ComboBox<String>> SpanishTextFields = new ArrayList<>();
    private List<ComboBox<String>> EnglishTextFields = new ArrayList<>();
    private List<ComboBox<String>> FrenchTextFields = new ArrayList<>();


    public FormDataLanguagesController() {
        this.languagesController = new LanguagesController();
        this.coursesController = new CoursesController();
        this.logInController = new LogInController();
        this.session = Session.getInstance();
        this.conn = ConnectionMariaDB.getConnection();
    }


    public void setCoursesController(CoursesController coursesController) throws SQLException {
        this.coursesController = coursesController;
        loadLanguagesData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> languagesContainer.getParent().requestFocus());
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        checkCourses.setOnMouseClicked(event -> handleBackCourses());
        backCourses.setOnMouseClicked(event -> handleBackCourses());
        createLanguagesForm();
    }

    private void createLanguagesForm() {
        // Crear campos
        ComboBox<String> spanishComboBox = new ComboBox<>();
        spanishComboBox.getItems().addAll(
                "0", "1", "2", "3", "4", "5"
        );

        VBox.setMargin(spanishComboBox, new Insets(0, 100, 0, 0));
        spanishComboBox.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        spanishComboBox.setPromptText("Spanish");
        spanishComboBox.setPrefWidth(200);

        ComboBox<String> englishComboBox = new ComboBox<>();
        englishComboBox.getItems().addAll(
                "0", "1", "2", "3", "4", "5"
        );

        VBox.setMargin(englishComboBox, new Insets(0, 100, 0, 0));
        englishComboBox.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        englishComboBox.setPromptText("English");
        englishComboBox.setPrefWidth(200);

        ComboBox<String> frenchComboBox = new ComboBox<>();
        frenchComboBox.getItems().addAll(
                "0", "1", "2", "3", "4", "5"
        );

        VBox.setMargin(frenchComboBox, new Insets(0, 100, 0, 0));
        frenchComboBox.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-prompt-text-fill: gray;"
        );
        frenchComboBox.setPromptText("French");
        frenchComboBox.setPrefWidth(200);

        // Crear un GridPane para organizar los elementos
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.getColumnConstraints().addAll(
                new ColumnConstraints()
        );

        // Crear etiquetas
        Label spanishLabel = new Label("SPANISH:");
        spanishLabel.setStyle("-fx-font-size: 18px;");

        Label englishLabel = new Label("ENGLISH:");
        englishLabel.setStyle("-fx-font-size: 18px;");

        Label frenchLabel = new Label("FRENCH:");
        frenchLabel.setStyle("-fx-font-size: 18px;");

        // Agregar las etiquetas en la primera fila
        gridPane.add(spanishLabel, 0, 0);
        gridPane.add(englishLabel, 1, 0);
        gridPane.add(frenchLabel, 2, 0);

        // Añadir los campos de texto en la segunda fila
        gridPane.add(spanishComboBox, 0, 1);
        gridPane.add(englishComboBox, 1, 1);
        gridPane.add(frenchComboBox, 2, 1);

        // Agregar los campos a las listas
        SpanishTextFields.add(spanishComboBox);
        EnglishTextFields.add(englishComboBox);
        FrenchTextFields.add(frenchComboBox);

        languagesForms.add(gridPane);
        // Agregar el GridPane al VBox
        languagesContainer.getChildren().add(gridPane);
    }


    private void handleFormaDataSave() {
        // Obtener el contact_id de la sesión actual
        int contactId = session.getContactId();

        try {
            // Obtener todas las Languages asociadas al contacto
            List<Languages> existingLanguages = languagesController.getLanguagesById(contactId);

            for (int i = 0; i < languagesContainer.getChildren().size(); i++) {
                Node node = languagesContainer.getChildren().get(i);
                if (node instanceof GridPane) {
                    GridPane gridPane = (GridPane) node;

                    // Recoger los datos de cada language utilizando las referencias guardadas
                    ComboBox<String> spanishComboBox = SpanishTextFields.get(i);
                    ComboBox<String> englishComboBox = EnglishTextFields.get(i);
                    ComboBox<String> frenchComboBox = FrenchTextFields.get(i);

                    // Crear un objeto Languages con los datos recolectados
                    Languages languages = new Languages();
                    languages.setContact_id(contactId);
                    languages.setSpanish(Integer.parseInt(spanishComboBox.getValue()));
                    languages.setEnglish(Integer.parseInt(englishComboBox.getValue()));
                    languages.setFrench(Integer.parseInt(frenchComboBox.getValue()));

                    if (i < existingLanguages.size()) {
                        // Actualizar language existente
                        languages.setLang_id(existingLanguages.get(i).getLang_id());
                        languagesController.updateLanguages(languages);
                        logInController.showAutoClosingAlert("AVISO: Languages se ha actualizado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    } else {
                        // Insertar nueva language
                        languagesController.saveLanguages(languages);
                        logInController.showAutoClosingAlert("AVISO: Languages se ha guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
                        changeSceneToFormData();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: Error al guardar los datos de Courses.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    public void loadLanguagesData() throws SQLException {
        if (coursesController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener todas las languages asociadas con el contactId
            List<Languages> languagesList = languagesController.getLanguagesById(contactId);

            // Verificar si se encontraron languages asociadas
            if (languagesList != null && !languagesList.isEmpty()) {
                // Recorrer todas las languages recuperadas
                for (int i = 0; i < languagesList.size(); i++) {
                    Languages languages = languagesList.get(i);

                    // Verificar si hay suficientes GridPane y campos de texto
                    if (i < languagesForms.size()) {
                        GridPane gridPane = languagesForms.get(i);

                        // Obtener los campos de texto correspondientes del GridPane actual
                        ComboBox<String> spanishComBox = SpanishTextFields.get(i);
                        ComboBox<String> englishComBox = EnglishTextFields.get(i);
                        ComboBox<String> frenchComBox = FrenchTextFields.get(i);

                        // Cargar los datos de la language en los campos de texto correspondientes
                        spanishComBox.setValue(String.valueOf(languages.getSpanish()));
                        englishComBox.setValue(String.valueOf(languages.getEnglish()));
                        frenchComBox.setValue(String.valueOf(languages.getFrench()));
                    } else {
                        // Si hay más languages que GridPane disponibles, se crea un nuevo GridPane y se cargan los datos
                        createLanguagesForm();
                        GridPane gridPane = languagesForms.get(languagesForms.size() - 1);

                        // Obtener los campos de texto correspondientes del nuevo GridPane
                        ComboBox<String> spanishTextField = SpanishTextFields.get(languagesForms.size() - 1);
                        ComboBox<String> englishTextField = EnglishTextFields.get(languagesForms.size() - 1);
                        ComboBox<String> frenchTextField = FrenchTextFields.get(languagesForms.size() - 1);

                        // Cargar los datos de la language en los campos de texto correspondientes
                        spanishTextField.setValue(String.valueOf(languages.getSpanish()));
                        englishTextField.setValue(String.valueOf(languages.getEnglish()));
                        frenchTextField.setValue(String.valueOf(languages.getFrench()));
                    }
                }
            }
        }
    }

    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataSkills.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            FormDataSkillsController formDataSkillsController = loader.getController();
            formDataSkillsController.setSkillsController(languagesController);

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) checkCourses.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Skills.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBackCourses() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataCourses.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
            Stage stage = (Stage) checkCourses.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la Languages.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}