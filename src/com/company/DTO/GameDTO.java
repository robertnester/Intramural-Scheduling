package com.company.DTO;

import java.util.Objects;

public class GameDTO {

    private int id;
    private TeamDTO team1;
    private TeamDTO team2;

    public GameDTO() {

    }

    public GameDTO(TeamDTO team1, TeamDTO team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public GameDTO(int id, TeamDTO team1, TeamDTO team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TeamDTO getTeam1() {
        return team1;
    }

    public void setTeam1(TeamDTO team1) {
        this.team1 = team1;
    }

    public TeamDTO getTeam2() {
        return team2;
    }

    public void setTeam2(TeamDTO team2) {
        this.team2 = team2;
    }

    public boolean equals(GameDTO g) {
        if (g.getTeam1().getId() == this.team1.getId() && g.getTeam2().getId() == this.team2.getId()) {
            return true;
        } else {
            return false;
        }
    }

}
