package org.JavviFdeez.model.entity;

public class Languages {

    // =============
    // Attributes
    // =============
    private int lang_id;
    private int contact_id;
    private int spanish;
    private int english;
    private int french;

    // ==============
    // Constructor
    // ==============
    public Languages(int contact_id, int spanish, int english, int french) {
        this.lang_id = 0;
        this.contact_id = contact_id;
        this.spanish = spanish;
        this.english = english;
        this.french = french;
    }

    // ======================
    // Getters and Setters
    // ======================
    public int getLang_id() {
        return lang_id;
    }

    public void setLang_id(int lang_id) {
        this.lang_id = lang_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public int getSpanish() {
        return spanish;
    }

    public void setSpanish(int spanish) {
        this.spanish = spanish;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getFrench() {
        return french;
    }

    public void setFrench(int french) {
        this.french = french;
    }
}
