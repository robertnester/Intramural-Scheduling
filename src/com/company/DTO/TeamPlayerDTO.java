package com.company.DTO;

import java.util.List;

public class TeamPlayerDTO {

    private TeamDTO teamDTO;
    private List<PlayerDTO> players;

    public TeamPlayerDTO(TeamDTO teamDTO, List<PlayerDTO> players) {
        this.teamDTO = teamDTO;
        this.players = players;
    }

    public TeamDTO getTeamDTO() {
        return teamDTO;
    }

    public void setTeamDTO(TeamDTO teamDTO) {
        this.teamDTO = teamDTO;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }



}

