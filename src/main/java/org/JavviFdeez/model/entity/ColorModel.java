package org.JavviFdeez.model.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ColorModel {
    private final StringProperty selectedColor = new SimpleStringProperty();

    public String getSelectedColor() {
        return selectedColor.get();
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor.set(selectedColor);
    }

    public StringProperty selectedColorProperty() {
        return selectedColor;
    }
}
