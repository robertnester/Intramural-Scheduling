package com.company.DAO;

import com.company.DAO.PlayerDAO;
import com.company.DAO.TeamDAO;
import com.company.DTO.PlayerDTO;
import com.company.DTO.TeamDTO;

import java.sql.*;
import java.util.List;

public class TeamPlayerDAO {

    private Connection conn;

    public TeamPlayerDAO(Connection c) {
        conn = c;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS teamplayer " +
                "(id integer PRIMARY KEY NOT NULL, " +
                "team_id integer NOT NULL, " +
                "player_id integer NOT NULL, " +
                "UNIQUE (team_id, player_id), " +
                "FOREIGN KEY (team_id) REFERENCES team (id), " +
                "FOREIGN KEY (player_id) REFERENCES player (id));";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //ADD JAVAC COMMENTS
    public void insert(TeamDTO t, List<PlayerDTO> pList) {

        String sqlTeamPlayer = "INSERT INTO teamplayer(team_id,player_id)"
                + "VALUES(?,?)";

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        int teamID = 0;

        try {
            // set auto-commit mode to false
            conn.setAutoCommit(false);

            // 1. insert a new team
            TeamDAO teamDAO = new TeamDAO(conn);
            teamDAO.insert(t);
            //TeamDTO teamDTO = teamDAO.getDTOByName(t.getName());
            //teamID = teamDTO.getId();
            //TODO: replace the following with getTeamByName method
            List<TeamDTO> teams = teamDAO.selectAll();
            for (TeamDTO team : teams){
                if (team.getName().equals(t.getName())) {
                    teamID = team.getId();
                    t.setId(teamID);
                }
            }

            // 2. insert the players and teamplayers
            for (int i = 0; i < pList.size(); i++) {
                int playerID = 0;
                PlayerDAO playerDAO = new PlayerDAO(conn);
                PlayerDTO playerDTO = pList.get(i);
                playerDAO.insert(playerDTO);
                //TODO: replace the following with getPlayerByName method
                List<PlayerDTO> players = playerDAO.selectAll();
                for (PlayerDTO player : players){
                    if (player.getName().equals(playerDTO.getName())) {
                        playerID = player.getId();
                        pList.get(i).setId(playerID);
                    }
                }
                //playerDTO = playerDAO.getDTOByName(playerDTO.getName());
                pstmt = conn.prepareStatement(sqlTeamPlayer);

                pstmt.setInt(1, teamID);
                pstmt.setInt(2, playerID);
                pstmt.executeUpdate();

            }

            // commit work
            conn.commit();

        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    System.out.println("rolling back...");
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
               //     conn.close();
               // }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

    public void selectAll(){
        String sql = "SELECT player_id, team_id FROM teamplayer";

        try (//Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("player_id") +  "\t" +
                        rs.getInt("team_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void print(){
        String sql = "SELECT id, name, grade FROM player";

        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("grade"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        sql = "SELECT id, name, senior FROM team";
        try (
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getBoolean("senior"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}




