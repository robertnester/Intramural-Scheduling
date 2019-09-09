package com.company.DTO;

import java.util.Objects;

public class TeamDTO {

    private int id;
    private String name;
    private boolean senior;

    public TeamDTO() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDTO teamDTO = (TeamDTO) o;
        return id == teamDTO.id &&
                senior == teamDTO.senior &&
                Objects.equals(name, teamDTO.name);
    }
}
