package org.JavviFdeez.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.*;
import org.JavviFdeez.model.entity.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TemplateController implements Initializable {
    // ===========
    // Atributos
    // ===========
    private ContactDAO contactDAO;
    private AcademiesDAO academiesDAO;
    private ExperiencesDAO experiencesDAO;
    private Contact_SkillsDAO contact_SkillsDAO;
    private CoursesDAO coursesDAO;
    private LanguagesDAO languagesDAO;
    private SkillsDAO skillsDAO;

    @FXML
    private Label name;

    @FXML
    private Label lastName;

    @FXML
    private Label occupation;

    @FXML
    private ImageView image;

    @FXML
    private Label mobile;

    @FXML
    private Label email;

    @FXML
    private Label linkedin;

    @FXML
    private Label location;

    @FXML
    private Label extra;

    @FXML
    private ImageView Hexagonnull1;

    @FXML
    private ImageView Hexagon1;

    @FXML
    private ImageView Hexagonnull2;

    @FXML
    private ImageView Hexagon2;

    @FXML
    private ImageView Hexagonnull3;

    @FXML
    private ImageView Hexagon3;

    @FXML
    private ImageView Hexagonnull4;

    @FXML
    private ImageView Hexagon4;

    @FXML
    private ImageView Hexagonnull5;

    @FXML
    private ImageView Hexagon5;

    @FXML
    private ImageView Hexagonnull6;

    @FXML
    private ImageView Hexagon6;

    @FXML
    private ImageView Hexagonnull7;

    @FXML
    private ImageView Hexagon7;

    @FXML
    private ImageView Hexagonnull8;

    @FXML
    private ImageView Hexagon8;

    @FXML
    private ImageView Hexagonnull9;

    @FXML
    private ImageView Hexagon9;

    @FXML
    private ImageView Hexagonnull10;

    @FXML
    private ImageView Hexagon10;

    @FXML
    private GridPane leftPane;


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
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
        this.experiencesDAO = new ExperiencesDAO(ConnectionMariaDB.getConnection());
        this.contact_SkillsDAO = new Contact_SkillsDAO(ConnectionMariaDB.getConnection());
        this.coursesDAO = new CoursesDAO(ConnectionMariaDB.getConnection());
        this.languagesDAO = new LanguagesDAO(ConnectionMariaDB.getConnection());
        this.skillsDAO = new SkillsDAO(ConnectionMariaDB.getConnection());
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        dataContact();
        dataLanguages();
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
                // Verificar si el nombre no es nulo
                /*if (contact.getName() !=null) {
                    // Si el nombre no es nulo, muestralo
                    name.setText(contact.getName());
                } else {
                    // Si el nombre es nulo, mostrar una cadena vacía
                    name.setText("");
                }


                // Obtener los apellidos del contacto
                // Verificar si los apellidos no son nulos
                if (contact.getLastname() != null) {
                    // Si los apellidos no son nulos, mostrarlos
                    lastName.setText(contact.getLastname());
                } else {
                    // Si los apellidos son nulos, mostrar una cadena vacía
                    lastName.setText("");
                }
*/

                // Obtener la ocupación del contacto
                // Verificar si la ocupación no es nula
                if (contact.getOccupation() != null) {
                    // Si la ocupación no es nula, mostrarla
                    occupation.setText(contact.getOccupation());
                } else {
                    // Si la ocupación es nula, mostrar una cadena vacía
                    occupation.setText("");
                }

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

                // Obtener el mobil del contacto
                // Verificar si el mobil no es nulo
                if (contact.getMobile() != null) {
                    // Si el mobil no es nulo, mostrarlo
                    mobile.setText(contact.getMobile());
                } else {
                    // Si el mobil es nulo, mostrar una cadena vacía
                    mobile.setText("");
                }

                // Obtener el email del contacto
                // Verificar si el email no es nulo
                if (contact.getEmail() != null) {
                    // Si el email no es nulo, mostrarlo
                    email.setText(contact.getEmail());
                } else {
                    // Si el email es nulo, mostrar una cadena vacía
                    email.setText("");
                }

                // Obtener el linkedin del contacto
                // Verificar si el linkedin no es nulo
                if (contact.getLinkedin() != null) {
                    // Si el linkedin no es nulo, mostrarlo
                    linkedin.setText(contact.getLinkedin());
                } else {
                    // Si el linkedin es nulo, mostrar una cadena vacía
                    linkedin.setText("");
                }

                // Obtener la ubicación del contacto
                // Verificar si la ubicación no es nula
                if (contact.getLocation() != null) {
                    // Si la ubicación no es nula, mostrarla
                    location.setText(contact.getLocation());
                } else {
                    // Si la ubicación es nula, mostrar una cadena vacía
                    location.setText("");
                }

                /*
                // Obtener el extra del contacto
                // Verificar si el extra no es nulo
                if (contact.getExtra() != null) {
                    // Si el extra no es nulo, mostrarlo
                    extra.setText(contact.getExtra());
                } else {
                    // Si el extra es nulo, mostrar una cadena vacía
                    extra.setText("");
                }

                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private void dataAcademies() {

    public void dataLanguages() {
        try {
            List<Languages> languageList = languagesDAO.findAll();

            // Verificar si se encontraron idiomas
            if (!languageList.isEmpty()) {
                // Obtener los datos del primer idioma de la lista
                Languages languages = languageList.get(0);
                // Obtener el valor del idioma español
                Integer spanishValue = languages.getSpanish();
                // Verificar si el valor del idioma español es nulo
                if (spanishValue != null) {
                    // Actualizar las imágenes de las estrellas según el valor del idioma español
                    updateStarsSpanish(spanishValue);
                } else {
                    // Si el valor del idioma español es nulo, asignar cero estrellas
                    updateStarsSpanish(0);
                }
                // Obtener el valor del idioma ingles
                Integer englishValue = languages.getEnglish();
                // Verificar si el valor del idioma ingles es nulo
                if (englishValue != null) {
                    // Actualizar las imágenes de las estrellas según el valor del idioma español
                    updateStarsEnglish(englishValue);
                } else {
                    // Si el valor del idioma ingles es nulo, asignar cero estrellas
                    updateStarsEnglish(0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStarsSpanish(int rating) {
        // Verificar si el valor del idioma está entre 0 y 5
        if (rating >= 0) {
            Hexagonnull1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        } if (rating >= 1) {
            Hexagon1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 2) {
            Hexagon1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 3) {
            Hexagon1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 4) {
            Hexagon1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 5) {
            Hexagon1.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon2.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon3.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon4.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon5.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
        }
    }

    public void updateStarsEnglish(int rating) {
        // Verificar si el valor del idioma está entre 0 y 5
        if (rating >= 0) {
            Hexagonnull6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        } if (rating >= 1) {
            Hexagon6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 2) {
            Hexagon6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 3) {
            Hexagon6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
            Hexagonnull10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 4) {
            Hexagon6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagonnull10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagonnull.png"));
        }
        if (rating >= 5) {
            Hexagon6.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon7.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon8.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon9.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
            Hexagon10.setImage(new Image("/resources/org/JavviFdeez/images/Hexagon.png"));
        }
    }
}