module org.JavviFdeez {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.JavviFdeez to javafx.fxml;
    exports org.JavviFdeez;
}
