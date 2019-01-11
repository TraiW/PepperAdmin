package com.example.valentinwiart.pepperadmin;

public class Movement {
    public int index;
    public String name;
    public String description;
    public String icon;
    public boolean isActive;

    public Movement(){

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Movement(int index, String name, String description, String icon, boolean isActive) {

        this.index = index;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
