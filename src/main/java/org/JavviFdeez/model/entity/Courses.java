package org.JavviFdeez.model.entity;

public class Courses {

    // =============
    // Attributes
    // =============
    private int course_id;
    private int contact_id;
    private String name;
    private int duration;

    // ==============
    // Constructor
    // ==============
    public Courses(int contact_id, String name, int duration) {
        this.course_id = course_id;
        this.contact_id = contact_id;
        this.name = name;
        this.duration = duration;
    }

    public Courses() { }

    // ======================
    // Getters and Setters
    // ======================
    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    // ================
    // toString
    // =================
    @Override
    public String toString() {
        return "Courses: {" +
                "course_id=" + course_id +
                ", contact_id=" + contact_id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}