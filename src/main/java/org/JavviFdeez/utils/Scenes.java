package org.JavviFdeez.utils;

public enum Scenes {;
    // LogIn("resources/org/JavviFdeez/fxml/LogIn.fxml");
    // MAIN("view/main.fxml"),
    // ABOUT("view/about.fxml"),
    // FORMAUTHOR("view/formAuthor.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
