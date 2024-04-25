package org.JavviFdeez.model;

public class Contact {

    // Attributes
    private int contact_id;
    private String name;
    private String last_name;
    private String image;
    private String mobile;
    private String email;
    private String linkedin;
    private String location;
    private String extra;

    // Constructor
    public Contact(int contact_id, String name, String last_name, String image, String mobile, String email, String linkedin, String location, String extra) {
        this.contact_id = contact_id;
        this.name = name;
        this.last_name = last_name;
        this.image = image;
        this.mobile = mobile;
        this.email = email;
        this.linkedin = linkedin;
        this.location = location;
        this.extra = extra;
    }

    // Getters and Setters
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
