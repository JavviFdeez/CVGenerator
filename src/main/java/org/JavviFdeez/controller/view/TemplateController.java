package org.JavviFdeez.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.*;
import org.JavviFdeez.model.entity.*;

import java.io.ByteArrayInputStream;
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
    private ImageView Hexagonnull11;

    @FXML
    private ImageView Hexagon11;

    @FXML
    private ImageView Hexagonnull12;

    @FXML
    private ImageView Hexagon12;

    @FXML
    private ImageView Hexagonnull13;

    @FXML
    private ImageView Hexagon13;

    @FXML
    private ImageView Hexagon14;

    @FXML
    private ImageView Hexagonnull14;

    @FXML
    private ImageView Hexagonnull15;

    @FXML
    private ImageView Hexagon15;

    @FXML
    private Label Skills1;

    @FXML
    private Label Skills2;

    @FXML
    private Label Skills3;

    @FXML
    private Label Skills4;

    @FXML
    private Label Skills5;

    @FXML
    private Label Skills6;

    @FXML
    private Label Skills7;

    @FXML
    private Label Skills8;

    @FXML
    private Label Skills9;

    @FXML
    private Label Skills10;

    @FXML
    private Label Skills11;

    @FXML
    private Label Skills12;

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
                if (contact.getName() !=null) {
                    // Si el nombre no es nulo, muestralo
                    name.setText(contact.getName());
                } else {
                    // Si el nombre es nulo, mostrar una cadena vacía
                    name.setText("-");
                }


                // Obtener los apellidos del contacto
                // Verificar si los apellidos no son nulos
                if (contact.getLastname() != null) {
                    // Si los apellidos no son nulos, mostrarlos
                    lastName.setText(contact.getLastname());
                } else {
                    // Si los apellidos son nulos, mostrar una cadena vacía
                    lastName.setText("-");
                }


                // Obtener la ocupación del contacto
                // Verificar si la ocupación no es nula
                if (contact.getOccupation() != null) {
                    // Si la ocupación no es nula, mostrarla
                    occupation.setText(contact.getOccupation());
                } else {
                    // Si la ocupación es nula, mostrar una cadena vacía
                    occupation.setText("-");
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
                    mobile.setText("-");
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
                    linkedin.setText("-");
                }

                // Obtener la ubicación del contacto
                // Verificar si la ubicación no es nula
                if (contact.getLocation() != null) {
                    // Si la ubicación no es nula, mostrarla
                    location.setText(contact.getLocation());
                } else {
                    // Si la ubicación es nula, mostrar una cadena vacía
                    location.setText("-");
                }

                // Obtener el extra del contacto
                // Verificar si el extra no es nulo
                if (contact.getExtra() != null) {
                    // Si el extra no es nulo, mostrarlo
                    extra.setText(contact.getExtra());
                } else {
                    // Si el extra es nulo, mostrar una cadena vacía
                    extra.setText("-");
                }
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
                    // Si el valor del idioma Español es nulo, mostrar una cadena vacía
                    updateStarsSpanish(0);
                }

                // Obtener el valor del idioma ingles
                Integer englishValue = languages.getEnglish();
                // Verificar si el valor del idioma ingles es nulo
                if (englishValue != null) {
                    // Actualizar las imágenes de las estrellas según el valor del idioma ingles
                    updateStarsEnglish(englishValue);
                } else {
                    // Si el valor del idioma ingles es nulo, mostrar una cadena vacía
                    updateStarsEnglish(0);
                }

                // Obtener el valor del idioma francés
                Integer frenchValue = languages.getFrench();
                // Verificar si el valor del idioma francés es nulo
                if (frenchValue != null) {
                    // Actualizar las imágenes de las estrellas según el valor del idioma francés
                    updateStarsFrench(frenchValue);
                } else {
                    // Si el valor del idioma francés es nulo, mostrar una cadena vacía
                    updateStarsFrench(0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStarsSpanish(int rating) {
        // Obtener las imagenes de los hexágonos
        String hexagon = getClass().getResource("/org/JavviFdeez/images/hexagon.png").toExternalForm();
        String hexagonNull = getClass().getResource("/org/JavviFdeez/images/hexagonNull.png").toExternalForm();

        // Obtener las imagenes de los hexágonos
        ImageView[] hexagonImages = {Hexagon1, Hexagon2, Hexagon3, Hexagon4, Hexagon5};
        ImageView[] hexagonNullImages = {Hexagonnull1, Hexagonnull2, Hexagonnull3, Hexagonnull4, Hexagonnull5};

        for (int i = 0; i < hexagonImages.length; i++) {
            if (rating > i) {
                hexagonImages[i].setImage(new Image(hexagon));
            } else {
                hexagonImages[i].setImage(new Image(hexagonNull));
            }
        }

        for (int i = 0; i < hexagonNullImages.length; i++) {
            hexagonNullImages[i].setImage(new Image(hexagonNull));
        }
    }

    public void updateStarsEnglish(int rating) {
        // Obtener las imagenes de los hexágonos
        String hexagon = getClass().getResource("/org/JavviFdeez/images/hexagon.png").toExternalForm();
        String hexagonNull = getClass().getResource("/org/JavviFdeez/images/hexagonNull.png").toExternalForm();

        // Obtener las imagenes de los hexágonos
        ImageView[] hexagonImages = {Hexagon6, Hexagon7, Hexagon8, Hexagon9, Hexagon10};
        ImageView[] hexagonNullImages = {Hexagonnull6, Hexagonnull7, Hexagonnull8, Hexagonnull9, Hexagonnull10};

        // Verificar si el valor del idioma está entre 0 y 5
        for (int i = 0; i < hexagonImages.length; i++) {
            if (rating > i) {
                hexagonImages[i].setImage(new Image(hexagon));
            } else {
                hexagonImages[i].setImage(new Image(hexagonNull));
            }
        }

        for (int i = 0; i < hexagonNullImages.length; i++) {
            hexagonNullImages[i].setImage(new Image(hexagonNull));
        }
    }

    public void updateStarsFrench(int rating) {
        // Obtener las imagenes de los hexágonos
        String hexagon = getClass().getResource("/org/JavviFdeez/images/hexagon.png").toExternalForm();
        String hexagonNull = getClass().getResource("/org/JavviFdeez/images/hexagonNull.png").toExternalForm();

        // Obtener las imagenes de los hexágonos
        ImageView[] hexagonImages = {Hexagon11, Hexagon12, Hexagon13, Hexagon14, Hexagon15};
        ImageView[] hexagonNullImages = {Hexagonnull11, Hexagonnull12, Hexagonnull13, Hexagonnull14, Hexagonnull15};

        for (int i = 0; i < hexagonImages.length; i++) {
            if (hexagonImages[i] != null) {
                if (rating > i) {
                    hexagonImages[i].setImage(new Image(hexagon));
                } else {
                    hexagonImages[i].setImage(new Image(hexagonNull));
                }
            }
        }

        for (int i = 0; i < hexagonNullImages.length; i++) {
            hexagonNullImages[i].setImage(new Image(hexagonNull));
        }
    }

    // Metodo datos SKills
    /*
    public void dataSkills() {
        try {
            List<Skills> skillList = skillsDAO.findAll();


            // Verificar si se encontraron skills
            if (!skillList.isEmpty()) {
                for (Skills skill : skillList) {
                    // Obtener el valor de la habilidad
                    Integer skillValue = skill.getName();

                    // Verificar si el valor de la habilidad es nulo
                    if (skillValue != null) {
                        // Si el valor de la habilidad no es nulo, mostrarlo
                        switch (skillValue) {

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

             */


}