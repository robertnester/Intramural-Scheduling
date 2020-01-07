package com.company.DAO;

import com.company.DTO.EligibleDayDTO;
import com.company.DTO.GameDTO;
import com.company.DTO.PlayerDTO;

import java.sql.*;
import java.util.ArrayList;
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
                "game2_id integer, " +
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

            // 1. insert a new game
            GameDAO gameDAO = new GameDAO(conn);
            EligibleDayDAO elDAO = new EligibleDayDAO(conn);

            gameDAO.insert(g1);
            if (g2 != null) {
                gameDAO.insert(g2);
            }

            List<GameDTO> games = gameDAO.selectAll();
            for (GameDTO game : games){
                if (game.equals(g1)) {
                    game1ID = game.getId();
                } else if (g2 != null && game.equals(g2)) {
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
            if (g2 != null) {
                pstmt.setInt(2, game2ID);
            } else {
                pstmt.setInt(2, 0);
            }
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

    public void deleteAll() {
        String sql = "DELETE FROM gameday";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Object[]> getAllRowInformation(){
        String sql = "SELECT game1_id, game2_id, elday_id FROM gameday";
        ArrayList<Object[]> rows = new ArrayList<Object[]>();

        EligibleDayDAO eldao = new EligibleDayDAO(conn);
        GameDAO gdao = new GameDAO(conn);
        TeamPlayerDAO tpdao = new TeamPlayerDAO(conn);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                boolean t1IsSenior = gdao.getDTOByID(rs.getInt("game1_id")).getTeam1().isSenior();
                String t1Name = gdao.getDTOByID(rs.getInt("game1_id")).getTeam1().getName();
                ArrayList<PlayerDTO> t1Players = tpdao.getPlayerByTeamID(gdao.getDTOByID(rs.getInt("game1_id")).getTeam1().getId());
                String t1PlayerNames = "";
                for (PlayerDTO pdto : t1Players) {
                    t1PlayerNames += pdto.getName();
                    t1PlayerNames += ", ";
                }
                String t2Name = gdao.getDTOByID(rs.getInt("game1_id")).getTeam2().getName();
                ArrayList<PlayerDTO> t2Players = tpdao.getPlayerByTeamID(gdao.getDTOByID(rs.getInt("game1_id")).getTeam2().getId());
                String t2PlayerNames = "";
                for (PlayerDTO pdto : t2Players) {
                    t2PlayerNames += pdto.getName();
                    t2PlayerNames += ", ";
                }
                Object[] objArray = {eldao.getDTOByID(rs.getInt("elday_id")).getDayString(), t1IsSenior, t1Name, t1PlayerNames};
                Object[] objArray2 = {"", "", t2Name, t2PlayerNames};
                rows.add(objArray);
                rows.add(objArray2);

                boolean t3IsSenior = gdao.getDTOByID(rs.getInt("game2_id")).getTeam1().isSenior();
                String t3Name = gdao.getDTOByID(rs.getInt("game2_id")).getTeam1().getName();
                ArrayList<PlayerDTO> t3Players = tpdao.getPlayerByTeamID(gdao.getDTOByID(rs.getInt("game2_id")).getTeam1().getId());
                String t3PlayerNames = "";
                for (PlayerDTO pdto : t3Players) {
                    t3PlayerNames += pdto.getName();
                    t3PlayerNames += ", ";
                }
                String t4Name = gdao.getDTOByID(rs.getInt("game2_id")).getTeam2().getName();
                ArrayList<PlayerDTO> t4Players = tpdao.getPlayerByTeamID(gdao.getDTOByID(rs.getInt("game2_id")).getTeam2().getId());
                String t4PlayerNames = "";
                for (PlayerDTO pdto : t4Players) {
                    t4PlayerNames += pdto.getName();
                    t4PlayerNames += ", ";
                }
                Object[] objArray3 = {"", t3IsSenior, t3Name, t3PlayerNames};
                Object[] objArray4 = {"", "", t4Name, t4PlayerNames};
                rows.add(objArray3);
                rows.add(objArray4);
            }
            return rows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
