package org.JavviFdeez.controller.view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.JavviFdeez.model.entity.ColorModel;
import org.JavviFdeez.model.entity.Session;
import org.JavviFdeez.model.entity.TemplateModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PreviewController implements Initializable {
    @FXML
    private ImageView templateGreen;

    @FXML
    private ImageView templateFocus;

    @FXML
    private ImageView templatePink;

    @FXML
    private ImageView templateBlack;

    @FXML
    private Button buttonSaveData;

    private ColorModel colorModel;

    private LogInController logInController;

    private Session session;
    private TemplateController templateController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonSaveData.setOnAction(event -> changeScene());
        logInController = new LogInController();
        this.session = Session.getInstance();
        this.templateController = new TemplateController();
        this.session = Session.getInstance();
    }

    public void setColorModel(ColorModel colorModel) {
        this.colorModel = colorModel;
        applyColor();
    }


    private void applyColor() {
        if (colorModel != null) {
            String selectedColor = colorModel.getSelectedColor();
            System.out.println("Color aplicado: " + selectedColor);
            applyOpacity(selectedColor);
        }
    }


    private void applyOpacity(String color) {
        double opacityGreen  = color.equals("#62FF00") ? 1.0 : 0.0;
        templateGreen.setOpacity(opacityGreen);

        double opacityFocus = color.equals("#008BFF") ? 1.0 : 0.0;
        templateFocus.setOpacity(opacityFocus);

        double opacityPink = color.equals("#FF0049") ? 1.0 : 0.0;
        templatePink.setOpacity(opacityPink);

        double opacityBlack = color.equals("#616161") ? 1.0 : 0.0;
        templateBlack.setOpacity(opacityBlack);
    }


    public void setTemplate(TemplateModel templateModel) {
        if (templateModel != null) {
            String selectedTemplate = templateModel.getSelectedTemplate();
        }
    }

    private void changeScene() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/JavviFdeez/templatesCVV/TemplateBasic.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva escena
            TemplateController templateController = loader.getController();
            templateController.setPreviewController(this);
            templateController.setColorModel(this.colorModel);

            // Obtener el escenario actual desde el emailTextField (o cualquier otro nodo)
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
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            logInController.showAutoClosingAlert("ERROR: No se pudo cargar la pantalla de CV.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }
}