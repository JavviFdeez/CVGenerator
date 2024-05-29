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
    private String year;

    // ==============
    // Constructor
    // ==============
    public Experiences(int contact_id, String name, String duration, String entity, String location, String year) {
        this.experience_id = experience_id;
        this.contact_id = contact_id;
        this.name = name;
        this.duration = duration;
        this.company = entity;
        this.location = location;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    // ================
    // toString
    // =================
    @Override
    public String toString() {
       return "Experiences: {" +
                "experience_id=" + experience_id +
                ", contact_id=" + contact_id +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", year=" + year +
                '}';
    }
}
