package org.JavviFdeez.model;

public class Skills {

    // Attributes
    private int skill_id;
    private String name;

    // Constructor
    public Skills(int skill_id, String name) {
        this.skill_id = skill_id;
        this.name = name;
    }

    // Getters and Setters
    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
