package org.JavviFdeez.model.entity;

public class Experiences {

    // =============
    // Attributes
    // =============
    private int experience_id;
    private int contact_id;
    private String name;
    private String duration;
    private String company;
    private String location;
    private int year;
    private String position;

    // ==============
    // Constructor
    // ==============
    public Experiences(int experience_id, int contact_id, String name, String duration, String entity, String location, int year, String position) {
        this.experience_id = experience_id;
        this.contact_id = contact_id;
        this.name = name;
        this.duration = duration;
        this.company = entity;
        this.location = location;
        this.year = year;
        this.position = position;
    }

    // ======================
    // Getters and Setters
    // ======================
    public int getExperience_id() {
        return experience_id;
    }

    public void setExperience_id(int experience_id) {
        this.experience_id = experience_id;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String entity) {
        this.company = entity;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
