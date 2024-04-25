package org.JavviFdeez.model;

public class Academies {

    // Attributes
    private int academy_id;
    private int contact_id;
    private String name;
    private String entity;
    private String location;
    private int year;

    // Constructor
    public Academies(int academy_id, int contact_id, String name, String entity, String location, int year) {
        this.academy_id = academy_id;
        this.contact_id = contact_id;
        this.name = name;
        this.entity = entity;
        this.location = location;
        this.year = year;
    }

    // Getters and Setters

    public int getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(int academy_id) {
        this.academy_id = academy_id;
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
}
