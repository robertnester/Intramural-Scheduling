package com.company;

import com.company.DAO.EligibleDayDAO;
import com.company.DAO.GameDAO;
import com.company.DAO.GameDayDAO;
import com.company.DAO.TeamDAO;
import com.company.DTO.EligibleDayDTO;
import com.company.DTO.GameDTO;
import com.company.DTO.TeamDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schedule {
    public Schedule(Connection c) {
        TeamDAO tdao = new TeamDAO(c);
        GameDayDAO gddao = new GameDayDAO(c);
        GameDAO gdao = new GameDAO(c);
        EligibleDayDAO eldao = new EligibleDayDAO(c);
        gddao.deleteAll();
        gdao.deleteAll();
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

        for (int i = 0; i < seniorGameDTOs.size()/2; i+=2) {
            Collections.swap(seniorGameDTOs, i, (seniorGameDTOs.size()-i-1));
        }

        for (int i = 0; i < juniorTeamDTOs.size(); i++) {
            for (int j = i + 1; j < juniorTeamDTOs.size(); j++) {
                juniorGameDTOs.add(new GameDTO(juniorTeamDTOs.get(i), juniorTeamDTOs.get(j)));
            }
        }

        for (int i = 0; i < juniorGameDTOs.size()/2; i+=2) {
            Collections.swap(juniorGameDTOs, i, (juniorGameDTOs.size()-i-1));
        }

        int smallerSize;
        boolean seniorSmaller = false;

        if (seniorGameDTOs.size() <= juniorGameDTOs.size()) {
            smallerSize = seniorGameDTOs.size();
            seniorSmaller = true;
        } else {
            smallerSize = juniorGameDTOs.size();
        }

        List<EligibleDayDTO> eldays = eldao.selectAll();

        int d = 0;

        for (int i = 0; i < smallerSize; i++) {
            gddao.insert(seniorGameDTOs.get(i), juniorGameDTOs.get(i), eldays.get(i));
            d = i+1;
        }

        int i = smallerSize;

        if (!seniorSmaller) {
            while (!(seniorGameDTOs.get(i).equals(seniorGameDTOs.get(seniorGameDTOs.size()-1)))) {
                GameDTO game1 = seniorGameDTOs.get(i);
                GameDTO game2 = seniorGameDTOs.get(i+1);
                if (!(game1.getTeam1().equals(game2.getTeam1()) || game1.getTeam1().equals(game2.getTeam2()) || game1.getTeam2().equals(game2.getTeam1()) || game1.getTeam2().equals(game2.getTeam2()))) {
                    gddao.insert(game1, game2, eldays.get(d));
                    i++;
                    d++;
                } else {
                    gddao.insert(game1, null, eldays.get(d));
                    gddao.insert(game2, null, eldays.get(d+1));
                    i++;
                    d+=2;
                }
            } gddao.insert(seniorGameDTOs.get(seniorGameDTOs.size()-1), null, eldays.get(d));
        } else {
            while (!(juniorGameDTOs.get(i).equals(juniorGameDTOs.get(juniorGameDTOs.size()-1)))) {
                GameDTO game1 = juniorGameDTOs.get(i);
                GameDTO game2 = juniorGameDTOs.get(i+1);
                if (!(game1.getTeam1().equals(game2.getTeam1()) || game1.getTeam1().equals(game2.getTeam2()) || game1.getTeam2().equals(game2.getTeam1()) || game1.getTeam2().equals(game2.getTeam2()))) {
                    gddao.insert(game1, game2, eldays.get(d));
                    i++;
                    d++;
                } else {
                    gddao.insert(game1, null, eldays.get(d));
                    gddao.insert(game2, null, eldays.get(d+1));
                    i++;
                    d+=2;
                }
            } gddao.insert(juniorGameDTOs.get(juniorGameDTOs.size()-1), null, eldays.get(d));
        }
    }
}


