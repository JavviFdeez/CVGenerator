package org.JavviFdeez.controller.view;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.JavviFdeez.controller.*;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.*;
import org.JavviFdeez.model.entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TemplateController implements Initializable {
    // ===========
    // Atributos
    // ===========
    private ContactDAO contactDAO;
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
    private Label acdemiesText;

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

    @FXML
    private AnchorPane experiencesAnchorPane;

    @FXML
    private AnchorPane coursesAnchorPane;

    private LogInController logInController;

    @FXML
    private Pane bgColor1;

    @FXML
    private Pane bgColor;

    private ColorModel colorModel;
    private Session session;
    private Connection conn;
    private ContactController contactController;
    private LanguagesController languagesController;
    private Contact_SkillsController contact_skillsController;
    private AcademiesController academiesController;
    private ExperiencesController experiencesController;
    private CoursesController coursesController;
    private PreviewController previewController;

    // ==============================
    // Constructore sin argumentos
    // ==============================
    public TemplateController() {
    }

    public void setPreviewController(PreviewController previewController) {
        this.previewController = previewController;
    }

    // ==============
    // Inicialización
    // ==============
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> generatePDF());
        this.contactController = new ContactController();
        this.academiesController = new AcademiesController();
        this.experiencesController = new ExperiencesController();
        this.contact_skillsController = new Contact_SkillsController();
        this.languagesController = new LanguagesController();
        this.coursesController = new CoursesController();
        this.languagesController = new LanguagesController();
        this.skillsDAO = new SkillsDAO(ConnectionMariaDB.getConnection());
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        this.colorModel = new ColorModel();
        this.session = Session.getInstance();
        this.conn = ConnectionMariaDB.getConnection();
        this.logInController = new LogInController();
        this.previewController = new PreviewController();
        dataContact();
        dataAcademies(academiesAnchorPane);
        dataExperience(experiencesAnchorPane);
        dataCourses(coursesAnchorPane);
        dataSkills();
        dataLanguages();
        generatePDF();
    }

    // ==============
    // Métodos
    // ==============

    /**
     * Método para obtener los datos del CONTACTO
     */
    public void dataContact() {
        try {
            int contactId = session.getContactId();

            // Obtener el contacto por ID
            Contact contact = contactController.getContactById(contactId);

            // Verificar si se encontró el contacto
            if (contact != null) {
                // Obtener el nombre del contacto
                String contactName = contact.getName();
                name.setText(contactName != null ? contactName : "-");

                // Obtener los apellidos del contacto
                String contactLastName = contact.getLastname();
                lastName.setText(contactLastName != null ? contactLastName : "-");

                // Obtener la ocupación del contacto
                String contactOccupation = contact.getOccupation();
                occupation.setText(contactOccupation != null ? "· " + contactOccupation + " ·" : "-");

                // Obtener la ruta de la imagen del contacto
                String imagePath = contact.getImage();

                // Verificar si hay una ruta de imagen válida
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image profileImage = new Image(new File(imagePath).toURI().toString());
                    image.setImage(profileImage);
                } else {
                    // Si no hay una ruta de imagen válida, cargar una imagen predeterminada
                    String defaultImagePath = "/org/JavviFdeez/images/image_default.png";
                    InputStream inputStream = getClass().getResourceAsStream(defaultImagePath);
                    Image defaultImage = new Image(inputStream);
                    image.setImage(defaultImage);
                }

                // Obtener el móvil del contacto
                String contactMobile = contact.getMobile();
                mobile.setText(contactMobile != null ? contactMobile : "-");

                // Obtener el email del contacto
                String contactEmail = contact.getEmail();
                email.setText(contactEmail != null ? contactEmail : "");

                // Obtener el LinkedIn del contacto
                String contactLinkedin = contact.getLinkedin();
                linkedin.setText(contactLinkedin != null ? contactLinkedin : "-");

                // Obtener la ubicación del contacto
                String contactLocation = contact.getLocation();
                location.setText(contactLocation != null ? contactLocation : "-");

                // Obtener el extra del contacto
                String contactExtra = contact.getExtra();
                extra.setText(contactExtra != null ? contactExtra : "-");
            } else {
                // Si no se encontró el contacto, establecer valores predeterminados
                name.setText("-");
                lastName.setText("-");
                occupation.setText("-");
                mobile.setText("-");
                email.setText("");
                linkedin.setText("-");
                location.setText("-");
                extra.setText("-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener los datos de los idiomas
     */
    public void dataLanguages() {
        try {
            int contactId = session.getContactId();
            // Recuperar los idiomas asociados al contact_id de la sesión
            List<Languages> languageList = languagesController.getLanguagesById(contactId);
            // Verificar si se encontraron idiomas
            if (!languageList.isEmpty()) {
                // Iterar sobre la lista de idiomas
                for (Languages languages : languageList) {
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
                    // Obtener el valor del idioma Inglés
                    Integer englishValue = languages.getEnglish();
                    // Verificar si el valor del idioma Inglés es nulo
                    if (englishValue != null) {
                        // Actualizar las imágenes de las estrellas según el valor del idioma Inglés
                        updateStarsEnglish(englishValue);
                    } else {
                        // Si el valor del idioma Inglés es nulo, mostrar una cadena vacía
                        updateStarsEnglish(0);
                    }
                    // Obtener el valor del idioma Francés
                    Integer frenchValue = languages.getFrench();
                    // Verificar si el valor del idioma Francés es nulo
                    if (frenchValue != null) {
                        // Actualizar las imágenes de las estrellas según el valor del idioma Francés
                        updateStarsFrench(frenchValue);
                    } else {
                        // Si el valor del idioma Francés es nulo, mostrar una cadena vacía
                        updateStarsFrench(0);
                    }

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
            int contactId = session.getContactId();
            // Obtener todas las skills asociadas con el contactId
            List<Contact_Skills> contactSkillsList = contact_skillsController.getSkillsById(contactId);

            // Verificar si hay habilidades disponibles
            if (!contactSkillsList.isEmpty()) {
                // Iterar sobre cada habilidad en la lista
                for (int i = 0; i < contactSkillsList.size(); i++) {
                    Label skillLabel = getSkillLabel(i);
                    ProgressBar progressBar = getProgressBar(i);

                    // Obtener el skillId de la habilidad actual
                    int skillId = contactSkillsList.get(i).getSkill_id();

                    // Obtener el nombre de la habilidad utilizando el skillId
                    String skillName = getSkillNameById(skillId);

                    // Verificar si el nombre de la habilidad y su valor son ambos no nulos
                    if (skillLabel != null && progressBar != null && skillName != null) {
                        Integer skillValue = contactSkillsList.get(i).getValue();

                        // Verificar si el valor de la habilidad es válido
                        if (skillValue != null && skillValue >= 0 && skillValue <= 100) {
                            // Configurar la etiqueta y la barra de progreso correspondientes
                            skillLabel.setText(skillName);
                            progressBar.setProgress(skillValue / 100.0);
                            skillLabel.setVisible(true);
                            progressBar.setVisible(true);
                        } else {
                            // Si el valor de la habilidad no es válido, ocultar la etiqueta y la barra de progreso
                            skillLabel.setVisible(false);
                            progressBar.setVisible(false);
                        }
                    } else {
                        // Si no se puede obtener el nombre de la habilidad, ocultar la etiqueta y la barra de progreso
                        skillLabel.setVisible(false);
                        progressBar.setVisible(false);
                    }
                }
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL mostrando un mensaje de error
            throw new RuntimeException("❌ Error al obtener las habilidades: " + e.getMessage(), e);
        }
    }

    // Método para obtener el nombre de la habilidad por su ID
    private String getSkillNameById(int skillId) throws SQLException {
        Skills skill = skillsDAO.findById(skillId);
        if (skill != null) {
            return skill.getName();
        }
        return null;
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
     */
    public void dataAcademies(AnchorPane anchorPane) {
        try {
            int contactId = session.getContactId();
            // Obtener la lista de academias desde la base de datos
            List<Academies> academiesList = academiesController.getAcademiesById(contactId);


            // Coordenadas iniciales para posicionar los elementos
            double yearX = 264.0;
            double nameAndYearY = 135.0;
            double nameAndEntityAndLocationX = 331.0;
            double entityY = 148;
            double locationY = 164;
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
                nameLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 16));
                nameLabel.setTextFill(textColorBlack);
                nameLabel.setLayoutX(nameAndEntityAndLocationX - 17);
                nameLabel.setLayoutY(nameAndYearY - 4);
                nameLabel.setWrapText(true);
                nameLabel.setMaxHeight(20);

                // Crear y configurar el Label para la entidad (entity)
                Label entityLabel = new Label(academy.getEntity());
                entityLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 14));
                entityLabel.setTextFill(textColorBlack);
                entityLabel.setLayoutX(nameAndEntityAndLocationX - 15.5);
                entityLabel.setLayoutY(entityY);

                // Crear y configurar el Label para la ubicación (location)
                Label locationLabel = new Label(academy.getLocation());
                locationLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
                locationLabel.setFont(regularFont);
                locationLabel.setTextFill(textColorGray);
                locationLabel.setLayoutX(nameAndEntityAndLocationX - 16);
                locationLabel.setLayoutY(locationY);

                // Crear y configurar el ImageView para el icono
                ImageView iconImageView = new ImageView(new Image(getClass().getResource("/org/JavviFdeez/images/Point.png").toExternalForm()));
                iconImageView.setFitHeight(iconSize);
                iconImageView.setFitWidth(iconSize);
                iconImageView.setLayoutX(iconX - 25);
                iconImageView.setLayoutY(nameAndYearY + 5);

                // Crear y configurar la línea vertical
                if (i > 0) {
                    Line verticalLine = new Line();
                    verticalLine.setStartX(iconX - 15); // Alineación horizontal de la línea
                    verticalLine.setStartY(nameAndYearY - spacing - 7); // Inicio vertical de la línea
                    verticalLine.setEndX(iconX - 15); // Alineación horizontal de la línea
                    verticalLine.setEndY(nameAndYearY + 7); // Fin vertical de la línea
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

    /**
     * Metodo para actualizar las barras de progreso y etiquetas de experiencia
     */
    public void dataExperience(AnchorPane anchorPane) {
        try {
            int contactId = session.getContactId();
            // Obtener la lista de academias desde la base de datos
            List<Experiences> experienceList = experiencesController.getExperienceById(contactId);


            // Ordenar la lista de experiencias por año (en orden descendente)
            //Collections.sort(experienceList, Comparator.comparing(Experiences::getYear, Comparator.reverseOrder()));


            // Cordenadas iniciales
            double nameAndCompanyAndLocationX = 309;
            double nameAndYearAndDurationY = 319;
            double durationX = nameAndCompanyAndLocationX + 200;
            double companyY = 334;
            double locationY = 353;
            double yearX = 264;
            double iconSize = 20.0;
            double spacing = 35.0;
            double iconX = yearX + 50;

            // Variable para almacenar la posición de la experiencia anterior
            int previousPosition = -1;

            // Recorrer la lista de experiencias y mostrar los datos dinámicamente
            for (int i = 0; i < experienceList.size(); i++) {
                Experiences experiences = experienceList.get(i);

                // Crear un contenedor Pane para organizar los elementos
                Pane experiencePane = new Pane();

                // Crear la fuente
                Font regularFont = new Font("Myanmar Text", 12);

                // Color del texto en negro
                Color textColorBlack = Color.BLACK;
                Color textColorGray = Color.GRAY;

                // Crear y configurar el Label para el nombre (name)
                Label nameLabel = new Label(experiences.getName());
                nameLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 19));
                nameLabel.setTextFill(textColorBlack);
                nameLabel.setLayoutX(nameAndCompanyAndLocationX);
                nameLabel.setLayoutY(nameAndYearAndDurationY - 6.5);

                // Crear y configurar el Label para el nombre (duration)
                Label durationLabel = new Label("(" + experiences.getDuration() + " meses)");
                durationLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
                durationLabel.setTextFill(textColorGray);
                durationLabel.setLayoutX(durationX);
                durationLabel.setLayoutY(nameAndYearAndDurationY);

                // Crear y configurar el Label para el nombre (company)
                Label companyLabel = new Label(experiences.getCompany());
                companyLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
                companyLabel.setTextFill(textColorBlack);
                companyLabel.setLayoutX(nameAndCompanyAndLocationX);
                companyLabel.setLayoutY(companyY);

                // Crear y configurar el Label para el nombre (location)
                Label locationLabel = new Label(experiences.getLocation());
                locationLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 12));
                locationLabel.setTextFill(textColorGray);
                locationLabel.setLayoutX(nameAndCompanyAndLocationX + 0.5);
                locationLabel.setLayoutY(locationY);

                // Crear y configurar el Label para el nombre (year)
                Label yearLabel = new Label(String.valueOf(experiences.getYear()));
                yearLabel.setFont(regularFont);
                yearLabel.setTextFill(textColorBlack);
                yearLabel.setLayoutX(yearX);
                yearLabel.setLayoutY(nameAndYearAndDurationY + 3);

                // Crear y configurar el ImageView para el icono
                ImageView iconImageView = new ImageView(new Image(getClass().getResource("/org/JavviFdeez/images/Point.png").toExternalForm()));
                iconImageView.setFitHeight(iconSize);
                iconImageView.setFitWidth(iconSize);
                iconImageView.setLayoutX(iconX - 25);
                iconImageView.setLayoutY(nameAndYearAndDurationY + 7);

                // Crear y configurar la línea vertical
                if (i > 0) {
                    Line verticalLine = new Line();
                    verticalLine.setStartX(iconX + iconSize / 2 - 25);
                    verticalLine.setStartY(nameAndCompanyAndLocationX - (iconSize / 2) + iconSize + 14);
                    verticalLine.setEndX(iconX + iconSize / 2 - 25);
                    verticalLine.setEndY(nameAndCompanyAndLocationX - (iconSize / 2) + iconSize + spacing + 33);
                    verticalLine.setStrokeWidth(2.0);
                    verticalLine.setStroke(Color.BLACK);
                    experiencePane.getChildren().add(verticalLine);
                }

                // Añadir los elementos al contenedor Pane
                experiencePane.getChildren().addAll(nameLabel, durationLabel, companyLabel, locationLabel, yearLabel, iconImageView);

                // Verificar si el AnchorPane ya contiene el Pane
                if (!anchorPane.getChildren().contains(experiencePane)) {
                    // Agregar el Pane solo si no está presente
                    anchorPane.getChildren().add(experiencePane);
                }

                // Incrementar la posición en y para la próxima academia
                nameAndYearAndDurationY += iconSize + spacing;
                companyY += spacing * 1.6;
                locationY += spacing * 1.6;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL mostrando un mensaje de error
            throw new RuntimeException("❌ Error al obtener las academias: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para mostrar los datos de los cursos
     */
    public void dataCourses(AnchorPane anchorPane) {
        try {
            int contactId = session.getContactId();
            // Obtener la lista de cursos desde la base de datos
            List<Courses> coursesList = coursesController.getCoursesById(contactId);

            // Coordenadas iniciales
            double nameX = 300;
            double nameY = 650;
            double durationY = nameY + 1;
            double durationX = nameX + 240;
            double iconX = 272;
            double iconY = 658;
            double iconSize = 22.0;
            double spacing = 5.0;

            // Recorrer la lista de cursos y mostrar los datos dinámicamente
            for (Courses course : coursesList) {
                // Crear y configurar los elementos del curso

                // Crear y configurar el Label para el nombre (name)
                Label nameLabel = new Label(course.getName());
                nameLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
                nameLabel.setTextFill(Color.BLACK);
                nameLabel.setLayoutX(nameX);
                nameLabel.setLayoutY(nameY);

                // Crear y configurar el Label para la duración (duration)
                Label durationLabel = new Label("( " + course.getDuration() + " h)");
                durationLabel.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
                durationLabel.setTextFill(Color.GRAY);
                durationLabel.setLayoutX(durationX);
                durationLabel.setLayoutY(durationY);

                // Crear y configurar el ImageView para el icono
                ImageView iconImageView = new ImageView(new Image(getClass().getResource("/org/JavviFdeez/images/FourPoints.png").toExternalForm()));
                iconImageView.setFitHeight(iconSize);
                iconImageView.setFitWidth(iconSize);
                iconImageView.setLayoutX(iconX);
                iconImageView.setLayoutY(iconY);

                // Añadir los elementos al AnchorPane
                anchorPane.getChildren().addAll(iconImageView, nameLabel, durationLabel);

                // Incrementar la posición en y para el próximo curso
                nameY += iconSize + spacing;
                durationY += iconSize + spacing;
                iconY += iconSize + spacing;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL mostrando un mensaje de error
            throw new RuntimeException("❌ Error al obtener los cursos: " + e.getMessage(), e);
        }
    }

    public void setColorModel(ColorModel colorModel) {
        this.colorModel = colorModel;
        applyColor();
    }

    private void applyColor() {
        if (colorModel != null) {
            String selectedColor = colorModel.getSelectedColor();
            System.out.println("Color aplicado: " + selectedColor);

            if ("#62FF00".equals(selectedColor)) {
                bgColor1.setStyle("-fx-background-color: #62FF00;");
                bgColor.setStyle("-fx-background-color: #2f2f2f;");
            } else if ("#008BFF".equals(selectedColor)) {
                bgColor.setStyle("-fx-background-color: #008BFF;");
            } else if ("#FF0049".equals(selectedColor)) {
                bgColor.setStyle("-fx-background-color: #FF0049;");
            } else if ("#616161".equals(selectedColor)) {
                bgColor.setStyle("-fx-background-color: #616161;");
            } else {
                bgColor.setStyle("-fx-background-color: gray;");
            }
        } else {
            bgColor.setStyle("-fx-background-color: #2f2f2f;");
        }
    }

    public void generatePDF() {
        try {
            // Obtener la escena actual desde el nodo raíz de la vista
            Scene scene = academiesAnchorPane.getScene();

            if (scene == null) {
                System.out.println("");
                return;
            }
            // Crear una instantánea de la escena
            WritableImage snapshot = scene.snapshot(null);

            // Guardar la imagen temporalmente
            File tempFile = new File("temp.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", tempFile);

            // Crear un documento PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Convertir la imagen a PDImageXObject
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(tempFile, document);

            // Obtener las dimensiones de la página A4
            float pageWidth = PDRectangle.A4.getWidth();
            float pageHeight = PDRectangle.A4.getHeight();

            // Obtener las dimensiones de la imagen
            float imageWidth = pdImage.getWidth();
            float imageHeight = pdImage.getHeight();

            // Calcular el escalado para ajustar la imagen a la página A4
            float scaleX = pageWidth / imageWidth;
            float scaleY = pageHeight / imageHeight;
            float scale = Math.min(scaleX, scaleY);

            // Calcular las nuevas dimensiones de la imagen escalada
            float scaledWidth = imageWidth * scale;
            float scaledHeight = imageHeight * scale;

            // Calcular las posiciones de inicio para centrar la imagen
            float startX = (pageWidth - scaledWidth) / 2;
            float startY = (pageHeight - scaledHeight) / 2;

            // Escribir la imagen en la página del PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.drawImage(pdImage, startX, startY, scaledWidth, scaledHeight);
                System.out.println("Imagen escrita en el documento PDF correctamente.");
            }

            // Guardar el documento PDF
            String filePath = "C:\\Users\\usuario\\Downloads\\CV.pdf";
            document.save(filePath);


            logInController.showAutoClosingAlert("SUCCESS: PDF generated correctly.", LogInController.AlertType.SUCCESS, Duration.seconds(1.5));
            System.out.println("PDF generado correctamente.");

            // Eliminar el archivo temporal
            tempFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
            logInController.showAutoClosingAlert("ERROR: Error al generar el PDF.", LogInController.AlertType.ERROR, Duration.seconds(1.5));
        }
    }

}