package org.JavviFdeez.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.JavviFdeez.model.dao.*;
import org.JavviFdeez.model.entity.Contact;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TemplateController implements Initializable {
    // ===========
    // Atributos
    // ===========
    @FXML
    private BorderPane rootBorderPane;

    private ContactDAO contactDAO;

    @FXML
    private Label name;

    @FXML
    private Label lastName;

    @FXML
    private ImageView image;

    // ==============
    // Constructor
    // ==============
    public TemplateController(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    // ==============================
    // Constructore sin argumentos
    // ==============================
    public TemplateController() {
    }

    // ==============
    // Inicialización
    // ==============
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar los estilos CSS
        rootBorderPane.getStylesheets().add(getClass().getResource("/org/JavviFdeez/css/bootstrap.min.css").toExternalForm());
        rootBorderPane.getStylesheets().add(getClass().getResource("/org/JavviFdeez/css/TemplateBasic.css").toExternalForm());
        initialize(contactDAO);
    }

    public void initialize(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
        dataContact();
    }

    // ==============
    // Métodos
    // ==============

    /**
     * Método para obtener los datos del CONTACTO
     */
    private void dataContact() {
        try {
            List<Contact> contactList = contactDAO.findAll();

            // Verificar si se encontraron contactos
            if (!contactList.isEmpty()) {

                // Obtener los datos del contacto
                Contact contact = contactList.get(0);
                // Obtener el nombre del contacto
                name.setText(contact.getName());

                // Obtener los apellidos del contacto
                lastName.setText(contact.getLastname());

                // Obtener la imagen del contacto
                // Verificar si la imagen no es nula
                byte[] imageData = contact.getImage();
                if (imageData != null) {
                    // Si hay una imagen, cargarla
                    Image profileImage = new Image(new ByteArrayInputStream(imageData));
                    image.setImage(profileImage);
                } else {
                    // Si la imagen es nula, cargar una imagen predeterminada desde los recursos
                    String defaultImagePath = "/org/JavviFdeez/images/image_default.png";
                    InputStream inputStream = getClass().getResourceAsStream(defaultImagePath);
                    Image defaultImage = new Image(inputStream);
                    image.setImage(defaultImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dataAcademies() {

    }
}