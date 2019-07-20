package com.company;

public class TeamDTO {
    private String name;
    private boolean senior;

    public TeamDTO(String name, boolean senior) {
        this.name = name;
        this.senior = senior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSenior() {
        return senior;
    }

    public void setSenior(boolean senior) {
        this.senior = senior;
    }
}
