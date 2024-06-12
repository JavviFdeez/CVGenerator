package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.JavviFdeez.model.entity.ColorModel;
import org.JavviFdeez.model.entity.Session;
import org.JavviFdeez.model.entity.TemplateModel;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CustomizeController implements Initializable {
    @FXML
    private Button ButtonGreen;

    @FXML
    private Button ButtonFocus;

    @FXML
    private Button ButtonPink;

    @FXML
    private Button ButtonBlack;

    @FXML
    private Button ButtonTemplate;

    @FXML
    private ImageView backForm;

    @FXML
    private ImageView checkGreen;

    @FXML
    private ImageView checkBlue;

    @FXML
    private ImageView checkPink;

    @FXML
    private ImageView checkBlack;

    @FXML
    private ImageView checkTemplate;

    @FXML
    private Button buttonSaveData;

    private boolean isVisible = false;

    private final TemplateModel templateModel = new TemplateModel();

    private PreviewController previewController;

    private LogInController logInController;
    private Session session;
    private ColorModel colorModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonGreen.setOnAction(event -> showCheckGreen());
        ButtonFocus.setOnAction(event -> showCheckFocus());
        ButtonPink.setOnAction(event -> showCheckPink());
        ButtonBlack.setOnAction(event -> showCheckBlack());

        ButtonTemplate.setOnAction(event -> showTemplate());
        backForm.setOnMouseClicked(event -> backToFormData());
        buttonSaveData.setOnMouseClicked(event -> changeSceneToPreview());

        this.logInController = new LogInController();
        this.previewController = new PreviewController();
        this.session = Session.getInstance();
        this.colorModel = new ColorModel();
    }

    private void showCheckGreen() {
        setColor("#62FF00");
        checkGreen.setOpacity(1);
        checkBlue.setOpacity(0);
        checkPink.setOpacity(0);
        checkBlack.setOpacity(0);
    }

    private void showCheckFocus() {
        setColor("#008BFF");
        checkGreen.setOpacity(0);
        checkBlue.setOpacity(1);
        checkPink.setOpacity(0);
        checkBlack.setOpacity(0);
    }

    private void showCheckPink() {
        setColor("#FF0049");
        checkGreen.setOpacity(0);
        checkBlue.setOpacity(0);
        checkPink.setOpacity(1);
        checkBlack.setOpacity(0);
    }

    private void showCheckBlack() {
        setColor("#616161");
        checkGreen.setOpacity(0);
        checkBlue.setOpacity(0);
        checkPink.setOpacity(0);
        checkBlack.setOpacity(1);
    }

    private void setColor(String color) {
        System.out.println("Color seleccionado: " + color);
        colorModel.setSelectedColor(color);
    }

    private void showTemplate() {
        if (isVisible) {
            checkTemplate.setOpacity(0);
        } else {
            checkTemplate.setOpacity(1);
            TemplateModel.getInstance().setSelectedTemplate("Nombre de la plantilla aquí");
        }
        isVisible = !isVisible;
    }

    private void backToFormData() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/FormDataSkills.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) ButtonGreen.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de contacto.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

    private void changeSceneToPreview() {
        if (colorModel.getSelectedColor() == null || colorModel.getSelectedColor().isEmpty()) {
            // Mostrar un mensaje de advertencia al usuario si no se ha seleccionado un color
            logInController.showAutoClosingAlert("NOTICE: Please select a color before continuing.", LogInController.AlertType.WARNING, Duration.seconds(3));
        } else if (TemplateModel.getInstance().getSelectedTemplate() == null || TemplateModel.getInstance().getSelectedTemplate().isEmpty()) {
            //Mostrar un mensaje de advertencia al usuario si no se ha seleccionado una plantilla
            logInController.showAutoClosingAlert("NOTICE: Please select a template before continuing.", LogInController.AlertType.WARNING, Duration.seconds(3));
        } else {
            if (backForm.getScene() != null && backForm.getScene().getWindow() instanceof Stage) {
                try {
                    // Cargar la nueva escena desde el archivo FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/fxml/Preview.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la nueva escena
                    PreviewController previewController = loader.getController();
                    previewController.setTemplate(templateModel);
                    previewController.setColorModel(this.colorModel);


                    // Obtener el escenario actual desde el emailTextField
                    Stage stage = (Stage) checkBlue.getScene().getWindow();

                    // Establecer la nueva escena en el escenario
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Manejar cualquier error de carga del archivo FXML
                    logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de Preview.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
                }
            }
        }
    }
}