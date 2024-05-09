package org.JavviFdeez.model.entity;

public class Skills {

    // =============
    // Attributes
    // =============
    private int skill_id;
    private String name;

    // ==============
    // Constructor
    // ==============
    public Skills(String name) {
        this.skill_id = 0;
        this.name = name;
    }

    // ======================
    // Getters and Setters
    // ======================
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

    @Override
    public String toString() {
        return "Skills: {" +
                "skill_id=" + skill_id +
                ", name='" + name + '\'' +
                '}';
    }
}
