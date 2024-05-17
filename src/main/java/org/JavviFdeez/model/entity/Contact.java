package org.JavviFdeez.model.entity;

import org.JavviFdeez.utils.EmailValidator;

public class Contact {
    // ==============
    // Attributes
    // ==============
    private int contact_id;
    private String name;
    private String lastname;
    private String image;
    private String occupation;
    private String mobile;
    private String email;
    private String linkedin;
    private String location;
    private String extra;

    // ==============
    // Constructor
    // ==============
    public Contact(String name, String lastname, String image, String occupation, String mobile, String email, String linkedin, String location, String extra) {
        this.contact_id = 0;
        this.name = name;
        this.lastname = lastname;
        this.image = image;
        this.occupation = occupation;
        this.mobile = mobile;
        if (EmailValidator.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("❌ Correo electrónico no válido");
        }
        this.linkedin = linkedin;
        this.location = location;
        this.extra = extra;
    }

    // ======================
    // Getters and Setters
    // ======================
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String last_name) {
        this.lastname = last_name;
    }

    public String getImage() {
        return image;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String setImage(String image) {
        this.image = image;
        return image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    // =================
    // toString
    // =================
    public String toString() {
       return "Contact: {" +
               "contact_id=" + contact_id +
               ", name='" + name + '\'' +
               ", last_name='" + lastname + '\'' +
               ", image='" + image + '\'' +
               ", occupation='" + occupation + '\'' +
               ", mobile='" + mobile + '\'' +
               ", email='" + email + '\'' +
               ", linkedin='" + linkedin + '\'' +
               ", location='" + location + '\'' +
               ", extra='" + extra + '\'' +
               '}';
    }
}
