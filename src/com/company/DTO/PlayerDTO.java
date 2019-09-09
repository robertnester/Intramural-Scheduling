package com.company.DTO;

public class PlayerDTO {

    private int id;
    private String name;
    private int grade;

    public PlayerDTO()
        {

    }

    public PlayerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getId () { return id;}

    public void setId (int id) {this.id = id; }


}
