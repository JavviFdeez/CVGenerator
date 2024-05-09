package org.JavviFdeez.model.entity;

public class Academies {

    //==============
    // Attributes
    //==============
    private int academies_id;
    private int contact_id;
    private String name;
    private String entity;
    private String location;
    private int year;


    // ==============
    // Constructor
    // ==============
    public Academies(int contact_id, String name, String entity, String location, int year) {
        this.academies_id = 0;
        this.contact_id = contact_id;
        this.name = name;
        this.entity = entity;
        this.location = location;
        this.year = year;
    }

    // =====================
    // Getters and Setters
    // =====================
    public int getAcademies_id() {
        return academies_id;
    }

    public void setAcademies_id(int academies_id) {
        this.academies_id = academies_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // ===========
    // toString
    // ===========
    public String toString() {
        return "Academia: {" +
                "academies_id=" + academies_id +
                "Contact_id=" + contact_id +
                ", Nombre='" + name + '\'' +
                ", Entidad='" + entity + '\'' +
                ", Ubicación='" + location + '\'' +
                ", Año=" + year +
                '}';
    }
}
