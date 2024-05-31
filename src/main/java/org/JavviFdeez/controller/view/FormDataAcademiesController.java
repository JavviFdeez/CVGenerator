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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.JavviFdeez.controller.AcademiesController;
import org.JavviFdeez.controller.ContactController;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FormDataAcademiesController implements Initializable {


    @FXML
    private Button buttonSaveData;

    @FXML
    private ImageView checkContact;


    @FXML
    private Pane paneForm;
    @FXML
    private VBox academiesContainer;

    private AcademiesController academiesController;
    private ContactController contactController;
    private Connection conn;
    private Session session;

    public FormDataAcademiesController() {
        this.academiesController = new AcademiesController();
        this.conn = ConnectionMariaDB.getConnection();
        this.session = Session.getInstance();
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
        //loadAcademiesData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurarse de que ningún campo de texto esté seleccionado al inicio con una pequeña demora
        Platform.runLater(() -> checkContact.getParent().requestFocus());
        //buttonSaveData.setOnAction(event -> handleFormaDataSave());
       checkContact.setOnMouseClicked(event -> handleBackContact());
        handleAddAcademicForm();
    }

    @FXML
    private void handleAddAcademicForm() {
        // Crear y añadir un nuevo formulario al presionar el botón "Añadir Academia"
        createAcademyForm();
    }

    private void createAcademyForm() {
        // Crear campos de Name, Entity, Location y Year
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        VBox.setMargin(nameTextField, new Insets(0, 100, 0, 0));

        TextField entityTextField = new TextField();
        entityTextField.setPromptText("Entity");
        VBox.setMargin(entityTextField, new Insets(0, 100, 0, 0));

        TextField locationTextField = new TextField();
        locationTextField.setPromptText("Location");
        VBox.setMargin(locationTextField, new Insets(0, 100, 0, 0));

        ComboBox<String> yearComboBox = new ComboBox<>();
        yearComboBox.getItems().addAll(
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019",
                "2020", "2021", "2022", "2023", "2024"
        );
        yearComboBox.setPromptText("Year");

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

        // Agregar las etiquetas en la primera fila
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(new Label("Entity:"), 1, 0);
        gridPane.add(new Label("Location:"), 2, 0);
        gridPane.add(new Label("Year:"), 3, 0);

        // Agregar los campos de texto en la segunda fila
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(entityTextField, 1, 1);
        gridPane.add(locationTextField, 2, 1);
        gridPane.add(yearComboBox, 3, 1);

        // Ajustar las restricciones de columna para que se extiendan horizontalmente
        for (ColumnConstraints constraint : gridPane.getColumnConstraints()) {
            constraint.setHgrow(Priority.ALWAYS);
        }

        // Agregar el GridPane al VBox
        academiesContainer.getChildren().add(gridPane);
    }






/*
    private void handleFormaDataSave() {
        // Recoger los datos de los campos iniciales
        String name = nameTextField.getText().trim();
        String entity = entityTextField.getText().trim();
        String location = locationTextField.getText().trim();
        String year = yearComboBox.getValue();
        String name1 = nameTextField1.getText().trim();
        String entity1 = entityTextField1.getText().trim();
        String location1 = locationTextField1.getText().trim();
        String year1 = yearComboBox1.getValue();
        String name2 = nameTextField2.getText().trim();
        String entity2 = entityTextField2.getText().trim();
        String location2 = locationTextField2.getText().trim();
        String year2 = yearComboBox2.getValue();

        try {
            boolean saveDataToDatabase = academiesController.saveDataToDatabase(Session.getInstance().getContactId(), name, entity, location, year, name1, entity1, location1, year1, name2, entity2, location2, year2);

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


    public void loadAcademiesData() {
        // Obtener el contactId del usuario autenticado desde la sesión
        int contactId = Session.getInstance().getContactId();

        // Obtener las academias asociadas al contacto utilizando el contactId
        Academies academies = contactController.getIDAcademies();
        if (academies != null) {
            List<Academies> academiesList = Collections.singletonList(academies);
            // Mostrar los datos de las academias en los campos de texto
            for (int i = 0; i < academiesList.size(); i++) {
                switch (i) {
                    case 0:
                        nameTextField.setText(academies.getName());
                        entityTextField.setText(academies.getEntity());
                        locationTextField.setText(academies.getLocation());
                        yearComboBox.setValue(academies.getYear());
                        break;
                }
            }
        } else {
            // Mostrar un mensaje de advertencia si no se encuentran las academias
            showAlert("Advertencia", "No se encontraron academias asociadas al contacto.", Alert.AlertType.WARNING);
        }
    }


 */

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
            showAlert("Error", "No se pudo cargar la pantalla de Experience.", Alert.AlertType.ERROR);
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
            showAlert("Error", "No se pudo cargar la pantalla de inicio de sesión.", Alert.AlertType.ERROR);
        }
    }
}