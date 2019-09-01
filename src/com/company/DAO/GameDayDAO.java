package com.company.DAO;

import com.company.DTO.EligibleDayDTO;
import com.company.DTO.GameDTO;
import com.company.DTO.PlayerDTO;
import com.company.DTO.TeamDTO;

import java.sql.*;
import java.util.List;

public class GameDayDAO {

    private Connection conn;

    public GameDayDAO(Connection c) {
        conn = c;
    }

    public void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS gameday " +
                "(id integer PRIMARY KEY NOT NULL, " +
                "game1_id integer NOT NULL, " +
                "game2_id integer NOT NULL, " +
                "elday_id integer NOT NULL, " +
                "UNIQUE (game1_id, game2_id, elday_id), " +
                "UNIQUE (game2_id, game1_id, elday_id), " +
                "FOREIGN KEY (game1_id) REFERENCES game (id), " +
                "FOREIGN KEY (game2_id) REFERENCES game (id), " +
                "FOREIGN KEY (elday_id) REFERENCES eligibleday (id));";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(GameDTO g1, GameDTO g2, EligibleDayDTO e) {

        String sqlGameDay = "INSERT INTO gameday(game1_id,game2_id,elday_id)"
                + "VALUES(?,?,?)";

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        int game1ID = 0;
        int game2ID = 0;
        int eldayID = 0;

        try {
            // set auto-commit mode to false
            conn.setAutoCommit(false);

            // 1. insert a new team
            GameDAO gameDAO = new GameDAO(conn);
            EligibleDayDAO elDAO = new EligibleDayDAO(conn);

            gameDAO.insert(g1);
            gameDAO.insert(g2);
            elDAO.insert(e);

            List<GameDTO> games = gameDAO.selectAll();
            for (GameDTO game : games){
                if (game.equals(g1)) {
                    game1ID = game.getId();
                } else if (game.equals(g2)) {
                    game2ID = game.getId();
                }
            }

            //TODO: replace the following with getGameIDsByTeamDTOs method
            List<EligibleDayDTO> eldays = elDAO.selectAll();
            for (EligibleDayDTO elday: eldays) {
                if (elday.getCalendar().equals(e.getCalendar())) {
                    eldayID = e.getId();
                }
            }

                //playerDTO = playerDAO.getDTOByName(playerDTO.getName());
            pstmt = conn.prepareStatement(sqlGameDay);

            pstmt.setInt(1, game1ID);
            pstmt.setInt(2, game2ID);
            pstmt.setInt(3, eldayID);
            pstmt.executeUpdate();

            // commit work
            conn.commit();

        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            //TODO: abstract all rs.close and Statement closes as well as connection close and statement execute thing
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                // if (conn != null) {
                //   conn.close();
                // }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

    /**
     * Delete a player specified by the id
     *
     * @param id
     */
/*    public void delete(int id) {
        String sql = "DELETE FROM game WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/



}
