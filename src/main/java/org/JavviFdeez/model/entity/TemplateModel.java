package org.JavviFdeez.model.entity;

public class TemplateModel {
    private static TemplateModel instance;
    private String selectedTemplate;

    public TemplateModel() {

    }

    public static TemplateModel getInstance() {
        if (instance == null) {
            instance = new TemplateModel();
        }
        return instance;
    }

    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }
}