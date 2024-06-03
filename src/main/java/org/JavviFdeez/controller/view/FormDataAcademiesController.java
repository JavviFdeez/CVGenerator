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
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Session;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class FormDataAcademiesController implements Initializable {
    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkContact;

    @FXML
    private ImageView backContact;

    @FXML
    private Pane paneForm;
    @FXML
    private VBox academiesContainer;

    private AcademiesController academiesController;
    private LogInController logInController;
    private ContactController contactController;
    private Connection conn;
    private Session session;
    private List<GridPane> academyForms = new ArrayList<>();
    private List<TextField> nameTextFields = new ArrayList<>();
    private List<TextField> entityTextFields = new ArrayList<>();
    private List<TextField> locationTextFields = new ArrayList<>();
    private List<ComboBox<String>> yearComboBoxes = new ArrayList<>();



    public FormDataAcademiesController() {
        this.academiesController = new AcademiesController();
        this.conn = ConnectionMariaDB.getConnection();
        this.session = Session.getInstance();
        this.logInController = new LogInController();
    }

    public void setContactController(ContactController contactController) throws SQLException {
        this.contactController = contactController;
        loadAcademiesData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkContact.getParent().requestFocus());
        buttonSaveData.setOnAction(event -> handleFormaDataSave());
        checkContact.setOnMouseClicked(event -> handleBackContact());
        backContact.setOnMouseClicked(event -> handleBackContact());
        handleAddAcademicForm();
    }

    @FXML
    private void handleAddAcademicForm() {
        // Crear y añadir un nuevo formulario
        createAcademyForm();
    }

    @FXML
    private void handleDeleteAcademicForm() throws SQLException {
        // Eliminar un nuevo formulario
        handleDeleteAcademy();
    }

    private void createAcademyForm() {
        // Crear campos de Name, Entity, Location y Year
        TextField nameTextField = new TextField();
        VBox.setMargin(nameTextField, new Insets(0, 100, 0, 0));
        nameTextField.setStyle(
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-prompt-text-fill: gray;"
        );
        nameTextField.setPromptText("Name");

        TextField entityTextField = new TextField();
        entityTextField.setPromptText("Entity");
        VBox.setMargin(entityTextField, new Insets(0, 100, 0, 0));entityTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-prompt-text-fill: gray;"
        );


        TextField locationTextField = new TextField();
        locationTextField.setPromptText("Location");
        VBox.setMargin(locationTextField, new Insets(0, 100, 0, 0));
        locationTextField.setStyle(
                "-fx-background-color: #B4B4B4; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 15; " +
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
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 15; " +
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
                new ColumnConstraints()
        );


        // Crear etiquetas
        Label nameLabel = new Label("NAME:");nameLabel.setStyle("-fx-font-size: 18px;");

        Label entityLabel = new Label("ENTITY:");entityLabel.setStyle("-fx-font-size: 18px;");

        Label locationLabel = new Label("LOCATION:");
        locationLabel.setStyle("-fx-font-size: 18px;");
        Label yearLabel = new Label("YEAR:");yearLabel.setStyle("-fx-font-size: 18px;");

        // Agregar las etiquetas en la primera fila
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(entityLabel, 1, 0);
        gridPane.add(locationLabel, 2, 0);
        gridPane.add(yearLabel, 3, 0);

        // Añadir los campos de texto en la segunda fila
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(entityTextField, 1, 1);
        gridPane.add(locationTextField, 2, 1);
        gridPane.add(yearComboBox, 3, 1);

        // Ajustar las restricciones de columna para que se extiendan horizontalmente
        for (ColumnConstraints constraint : gridPane.getColumnConstraints()) {
            constraint.setHgrow(Priority.ALWAYS);
        }

        // Agregar los campos a las listas
        nameTextFields.add(nameTextField);
        entityTextFields.add(entityTextField);
        locationTextFields.add(locationTextField);
        yearComboBoxes.add(yearComboBox);

        academyForms.add(gridPane);
        // Agregar el GridPane al VBox
        academiesContainer.getChildren().add(gridPane);
    }


    private void handleDeleteAcademy() throws SQLException {
        // Obtener el último formulario de academia agregado
        GridPane lastAcademyForm = academyForms.isEmpty() ? null : academyForms.get(academyForms.size() - 1);


        if (lastAcademyForm != null) {
            // Eliminar el GridPane del VBox
            academiesContainer.getChildren().remove(lastAcademyForm);

            // Eliminar el GridPane de la lista
            academyForms.remove(lastAcademyForm);
        }


        if (academiesController.getAcademiesById(session.getContactId()) != null) {
            // Llamar al controlador para eliminar la academia
            academiesController.deleteAcademies(session.getContactId());
        }

    }


    private void handleFormaDataSave() {
        // Obtener el contact_id de la sesión actual
        int contactId = session.getContactId();

        // Recorrer cada hijo del VBox academiesContainer
        for (int i = 0; i < academiesContainer.getChildren().size(); i++) {
            Node node = academiesContainer.getChildren().get(i);
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;

                // Recoger los datos de cada academia utilizando las referencias guardadas
                TextField nameTextField = nameTextFields.get(i);
                TextField entityTextField = entityTextFields.get(i);
                TextField locationTextField = locationTextFields.get(i);
                ComboBox<String> yearComboBox = yearComboBoxes.get(i);


                // Verificar si la academia ya existe en la base de datos
                try {
                    List<Academies> existingAcademies = academiesController.getAcademiesById(contactId);
                    if (existingAcademies != null) {
                        // La academia ya existe en la base de datos, muestra un mensaje de confirmación
                        logInController.showAutoClosingAlert("AVISO: La academia ya existe en la base de datos.", LogInController.AlertType.INFORMATION, Duration.seconds(1.5));
                        changeSceneToFormData();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    logInController.showAutoClosingAlert("ERROR: Error al verificar la existencia de la academia en la base de datos.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
                    return;
                }

                // Crear un objeto Academies con los datos recolectados
                Academies academies = new Academies();
                academies.setContact_id(contactId);
                academies.setName(nameTextField.getText().trim());
                academies.setEntity(entityTextField.getText().trim());
                academies.setLocation(locationTextField.getText().trim());
                academies.setYear(yearComboBox.getValue());

                // Guardar la academia en la base de datos utilizando el controlador AcademiesController
                academiesController.saveAcademies(academies);
                logInController.showAutoClosingAlert("AVISO: Los datos academicos se ha guardado exitosamente.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
            }
        }
    }

    public void loadAcademiesData() throws SQLException {
        if (contactController != null) {
            // Obtener el contactId del usuario autenticado desde la sesión
            int contactId = Session.getInstance().getContactId();

            // Obtener todas las academias asociadas con el contactId
            List<Academies> academiesList = academiesController.getAcademiesById(contactId);

            // Verificar si se encontraron academias asociadas
            if (academiesList != null && !academiesList.isEmpty()) {
                // Recorrer todas las academias recuperadas
                for (int i = 0; i < academiesList.size(); i++) {
                    Academies academies = academiesList.get(i);

                    // Verificar si hay suficientes GridPane y campos de texto
                    if (i < academyForms.size()) {
                        GridPane gridPane = academyForms.get(i);

                        // Obtener los campos de texto correspondientes del GridPane actual
                        TextField nameTextField = nameTextFields.get(i);
                        TextField entityTextField = entityTextFields.get(i);
                        TextField locationTextField = locationTextFields.get(i);
                        ComboBox<String> yearComboBox = yearComboBoxes.get(i);

                        // Cargar los datos de la academia en los campos de texto correspondientes
                        nameTextField.setText(academies.getName());
                        entityTextField.setText(academies.getEntity());
                        locationTextField.setText(academies.getLocation());
                        yearComboBox.setValue(academies.getYear());
                    } else {
                        // Si hay más academias que GridPane disponibles, se crea un nuevo GridPane y se cargan los datos
                        createAcademyForm();
                        GridPane gridPane = academyForms.get(academyForms.size() - 1);

                        // Obtener los campos de texto correspondientes del nuevo GridPane
                        TextField nameTextField = nameTextFields.get(nameTextFields.size() - 1);
                        TextField entityTextField = entityTextFields.get(entityTextFields.size() - 1);
                        TextField locationTextField = locationTextFields.get(locationTextFields.size() - 1);
                        ComboBox<String> yearComboBox = yearComboBoxes.get(yearComboBoxes.size() - 1);

                        // Cargar los datos de la academia en los campos de texto correspondientes
                        nameTextField.setText(academies.getName());
                        entityTextField.setText(academies.getEntity());
                        locationTextField.setText(academies.getLocation());
                        yearComboBox.setValue(academies.getYear());
                    }
                }
            }
        }
    }



    private void changeSceneToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataExperience.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual
            Stage stage = (Stage) checkContact.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Experience.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    private void handleBackContact() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataContact.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual
            Stage stage = (Stage) checkContact.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la Inicio de Sesión.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}