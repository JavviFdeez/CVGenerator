package org.JavviFdeez.controller.view;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.JavviFdeez.model.entity.ColorModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificar si el modelo de color no es nulo

        if (colorModel != null) {
            // Observar la propiedad de color seleccionado para actualizar la opacidad de las imágenes
            colorModel.selectedColorProperty().addListener((observable, oldValue, newValue) -> applyOpacity(newValue));
            System.out.println(colorModel);
            // Establecer la opacidad inicial
            applyOpacity(colorModel.getSelectedColor());



        }
        buttonSaveData.setOnAction(event -> generatePDF());
    }

    public void setColorModel(ColorModel colorModel) {
        this.colorModel = colorModel;
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



    private void generatePDF() {
        try (PDDocument document = new PDDocument()) {
            // Crear una página en el documento PDF
            PDPage page = new PDPage();
            document.addPage(page);

            // Crear un flujo de contenido para escribir en la página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                // Aquí puedes agregar el contenido que desees en la página
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Contenido de TemplateBasic.fxml");
                contentStream.endText();
            }

            // Especificar la ruta donde se guardará el documento PDF
            String filePath = "C:\\Users\\usuario\\Downloads\\TemplateBasic.pdf";

            // Guardar el documento PDF en la ruta especificada
            document.save(filePath);
            showAlert("Exito","PDF generado correctamente.", Alert.AlertType.INFORMATION);
            System.out.println("PDF generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error al generar el PDF.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}