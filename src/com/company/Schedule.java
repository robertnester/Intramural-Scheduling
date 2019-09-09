package com.company;

import com.company.DAO.EligibleDayDAO;
import com.company.DAO.GameDayDAO;
import com.company.DAO.TeamDAO;
import com.company.DTO.EligibleDayDTO;
import com.company.DTO.GameDTO;
import com.company.DTO.TeamDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public Schedule(Connection c) {
        TeamDAO tdao = new TeamDAO(c);
        GameDayDAO gddao = new GameDayDAO(c);
        EligibleDayDAO eldao = new EligibleDayDAO(c);
        List<TeamDTO> ogTeamDTOs = tdao.selectAll();
        List<TeamDTO> seniorTeamDTOs = new ArrayList<TeamDTO>();
        List<TeamDTO> juniorTeamDTOs = new ArrayList<TeamDTO>();
        List<GameDTO> seniorGameDTOs = new ArrayList<GameDTO>();
        List<GameDTO> juniorGameDTOs = new ArrayList<GameDTO>();
        for (int i = 0; i < ogTeamDTOs.size(); i++) {
            if (ogTeamDTOs.get(i).isSenior()) {
                seniorTeamDTOs.add(ogTeamDTOs.get(i));
            } else {
                juniorTeamDTOs.add(ogTeamDTOs.get(i));
            }
        }

        for (int i = 0; i < seniorTeamDTOs.size(); i++) {
            for (int j = i + 1; j < seniorTeamDTOs.size(); j++) {
                seniorGameDTOs.add(new GameDTO(seniorTeamDTOs.get(i), seniorTeamDTOs.get(j)));
            }
        }

        for (int i = 0; i < juniorTeamDTOs.size(); i++) {
            for (int j = i + 1; j < juniorTeamDTOs.size(); j++) {
                juniorGameDTOs.add(new GameDTO(juniorTeamDTOs.get(i), juniorTeamDTOs.get(j)));
            }
        }

        int smallerSize;

        if (seniorGameDTOs.size() <= juniorGameDTOs.size()) {
            smallerSize = seniorGameDTOs.size();
        } else {
            smallerSize = juniorGameDTOs.size();
        }

        List<EligibleDayDTO> eldays = eldao.selectAll();

        for (int i = 0; i < smallerSize; i++) {
            gddao.insert(seniorGameDTOs.get(i), juniorGameDTOs.get(i), eldays.get(i));
        }

        for (int i = smallerSize; i < (smallerSize + Math.abs(seniorGameDTOs.size() - juniorGameDTOs.size())); i++) {
            try {
                gddao.insert(seniorGameDTOs.get(i), seniorGameDTOs.get(i), eldays.get(i));
            } catch (NullPointerException e) {
                gddao.insert(juniorGameDTOs.get(i), juniorGameDTOs.get(i), eldays.get(i));
            }
        }

    }
}
