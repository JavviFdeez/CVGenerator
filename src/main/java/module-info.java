module org.JavviFdeez {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens org.JavviFdeez.controller.view to javafx.fxml;
    exports org.JavviFdeez;
    opens org.JavviFdeez.model.connection to java.xml.bind;
    exports org.JavviFdeez.test.view;
    exports org.JavviFdeez.controller.view;
}