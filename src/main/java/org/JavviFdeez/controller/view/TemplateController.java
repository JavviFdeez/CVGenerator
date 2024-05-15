package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.*;
import org.JavviFdeez.model.entity.*;
import javafx.scene.paint.Color;
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
    private Label skills1;

    @FXML
    private Label skills2;

    @FXML
    private Label skills3;

    @FXML
    private Label skills4;

    @FXML
    private Label skills5;

    @FXML
    private Label skills6;

    @FXML
    private ProgressBar value1;

    @FXML
    private ProgressBar value2;

    @FXML
    private ProgressBar value3;

    @FXML
    private ProgressBar value4;

    @FXML
    private ProgressBar value5;

    @FXML
    private ProgressBar value6;

    @FXML
    private AnchorPane academiesAnchorPane;

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
        dataSkills();
        dataAcademies(academiesAnchorPane);
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
                if (contact.getName() != null) {
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
                    occupation.setText("· " + contact.getOccupation() + " ·");
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

    /**
     *  Método para obtener los datos de los idiomas
     */
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

        // Verificar si el valor del idioma está entre 0 y 5
        for (int i = 0; i < hexagonImages.length; i++) {
            if (hexagonImages[i] != null) {
                if (rating > i) {
                    hexagonImages[i].setImage(new Image(hexagon));
                } else {
                    hexagonImages[i].setImage(new Image(hexagonNull));
                }
            }
        }

        // Verificar si el valor del idioma está entre 0 y 5
        for (int i = 0; i < hexagonNullImages.length; i++) {
            hexagonNullImages[i].setImage(new Image(hexagonNull));
        }
    }

    /**
     * Metodo para actualizar las barras de progreso y etiquetas de habilidad
     */
    public void dataSkills() {
        try {
            List<Skills> skillList = skillsDAO.findAll();
            List<Contact_Skills> contactSkillsList = contact_SkillsDAO.findAll();

            // Verificar si hay habilidades disponibles
            if (!skillList.isEmpty() && !contactSkillsList.isEmpty()) {
                // Iterar sobre cada habilidad en la lista
                // Iterar sobre todas las barras de progreso y etiquetas de habilidad
                for (int i = 0; i < 6; i++) {
                    Label skillLabel = getSkillLabel(i);
                    ProgressBar progressBar = getProgressBar(i);

                    // Verificar si el nombre de la habilidad y su valor son ambos no nulos
                    if (skillLabel != null && progressBar != null && skillList.size() > i && contactSkillsList.size() > i) {
                        String skillName = skillList.get(i).getName();
                        Integer skillValue = contactSkillsList.get(i).getValue();

                        // Verificar si el nombre de la habilidad y su valor son ambos no nulos
                        if (skillName != null && skillValue != null && skillValue >= 0 && skillValue <= 100) {
                            // Si ambos son no nulos, mostrar la etiqueta y la barra de progreso correspondientes
                            skillLabel.setText(skillName);
                            progressBar.setProgress(skillValue / 100.0);
                            skillLabel.setVisible(true);
                            progressBar.setVisible(true);
                        } else {
                            // Si cualquiera de ellos es nulo, ocultar la etiqueta y la barra de progreso correspondientes
                            skillLabel.setVisible(false);
                            progressBar.setVisible(false);
                        }
                    } else {
                        // Si no hay habilidad disponible, ocultar la etiqueta y la barra de progreso correspondientes
                        if (skillLabel != null) {
                            skillLabel.setVisible(false);
                        }
                        if (progressBar != null) {
                            progressBar.setVisible(false);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL mostrando un mensaje de error
            throw new RuntimeException("❌ Error al obtener las habilidades: " + e.getMessage(), e);
        }
    }

    // Método para obtener la etiqueta de habilidad en la posición especificada
    private Label getSkillLabel(int index) {
        switch (index) {
            case 0:
                return skills1;
            case 1:
                return skills2;
            case 2:
                return skills3;
            case 3:
                return skills4;
            case 4:
                return skills5;
            case 5:
                return skills6;
            default:
                return null;
        }
    }

    // Método para obtener la barra de progreso en la posición especificada
    private ProgressBar getProgressBar(int index) {
        switch (index) {
            case 0:
                return value1;
            case 1:
                return value2;
            case 2:
                return value3;
            case 3:
                return value4;
            case 4:
                return value5;
            case 5:
                return value6;
            default:
                return null;
        }
    }

    /**
     * Metodo para mostrar los datos de las academias
     * @param anchorPane
     */
    public void dataAcademies(AnchorPane anchorPane) {
        try {
            // Obtener la lista de academias desde la base de datos
            List<Academies> academiesList = academiesDAO.findAll();

            // Coordenadas iniciales para posicionar los elementos
            double yearX = 264.0;
            double nameAndYearY = 135.0;
            double nameAndEntityAndLocationX = 329.0;
            double entityY = 153;
            double locationY = 166.0;
            double iconSize = 20.0;
            double spacing = 30.0;
            double iconX = yearX + 50;


            // Recorrer la lista de academias y mostrar los datos dinámicamente
            for (int i = 0; i < academiesList.size(); i++) {
                Academies academy = academiesList.get(i);

                // Crear un contenedor Pane para organizar los elementos
                Pane academyPane = new Pane();

                // Crear la fuente
                Font regularFont = new Font("Myanmar Text", 12);

                // Color del texto en negro
                Color textColorBlack = Color.BLACK;
                Color textColorGray = Color.GRAY;

                // Crear y configurar el Label para el año (year)
                Label yearLabel = new Label(String.valueOf(academy.getYear()));
                yearLabel.setFont(regularFont);
                yearLabel.setTextFill(textColorBlack);
                yearLabel.setLayoutX(yearX);
                yearLabel.setLayoutY(nameAndYearY);

                // Crear y configurar el Label para el nombre (name)
                Label nameLabel = new Label(academy.getName());
                nameLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 14));
                nameLabel.setTextFill(textColorBlack);
                nameLabel.setLayoutX(nameAndEntityAndLocationX - 17);
                nameLabel.setLayoutY(nameAndYearY);
                nameLabel.setWrapText(true);
                nameLabel.setMaxHeight(20);

                // Crear y configurar el Label para la entidad (entity)
                Label entityLabel = new Label(academy.getEntity());
                entityLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 14));
                entityLabel.setFont(regularFont);
                entityLabel.setTextFill(textColorBlack);
                entityLabel.setLayoutX(nameAndEntityAndLocationX - 15.5);
                entityLabel.setLayoutY(entityY);

                // Crear y configurar el Label para la ubicación (location)
                Label locationLabel = new Label(academy.getLocation());
                locationLabel.setFont(regularFont);
                locationLabel.setTextFill(textColorGray);
                locationLabel.setLayoutX(nameAndEntityAndLocationX - 16);
                locationLabel.setLayoutY(locationY);

                // Crear y configurar el ImageView para el icono
                ImageView iconImageView = new ImageView(new Image(getClass().getResource("/org/JavviFdeez/images/Point.png").toExternalForm()));
                iconImageView.setFitHeight(iconSize);
                iconImageView.setFitWidth(iconSize);
                iconImageView.setLayoutX(iconX - 20);
                iconImageView.setLayoutY(nameAndYearY + 10);

                // Crear y configurar la línea vertical
                if (i > 0) {
                    Line verticalLine = new Line();
                    verticalLine.setStartX(iconX + iconSize / 2 - 20);
                    verticalLine.setStartY(nameAndYearY - spacing - (iconSize / 2) + iconSize - 10);
                    verticalLine.setEndX(iconX + iconSize / 2 - 20);
                    verticalLine.setEndY(nameAndYearY - (iconSize / 2) + iconSize + 5);
                    verticalLine.setStrokeWidth(2.0);
                    verticalLine.setStroke(Color.BLACK);
                    academyPane.getChildren().add(verticalLine);
                }

                // Añadir los elementos al contenedor Pane
                academyPane.getChildren().addAll(yearLabel, iconImageView, nameLabel, entityLabel, locationLabel);

                // Verificar si el AnchorPane ya contiene el Pane
                if (!anchorPane.getChildren().contains(academyPane)) {
                    // Agregar el Pane solo si no está presente
                    anchorPane.getChildren().add(academyPane);
                }

                // Incrementar la posición en y para la próxima academia
                nameAndYearY += iconSize + spacing;
                entityY += spacing * 1.7;
                locationY += spacing * 1.7;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL mostrando un mensaje de error
            throw new RuntimeException("❌ Error al obtener las academias: " + e.getMessage(), e);
        }
    }
}