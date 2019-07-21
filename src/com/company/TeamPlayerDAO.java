package com.company;

import java.sql.*;
import java.util.List;

public class TeamPlayerDAO {
    private Connection conn;

    public TeamPlayerDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS teamplayer (team_id integer, player_id integer, UNIQUE (team_id, player_id), FOREIGN KEY (team_id) REFERENCES team (id), FOREIGN KEY (player_id) REFERENCES player (id));";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //ADD JAVAC COMMENTS
    public void init(TeamDTO t, List<PlayerDTO> p) {
        // SQL for posting inventory
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
            List<TeamDTO> teams = teamDAO.selectAll();
            for (TeamDTO team : teams){
                if (team.getName().equals(t.getName())) {
                    teamID = team.getId();
                }
            }


            // 2. insert the players and teamplayers
            for (int i = 0; i < p.size(); i++) {
                int playerID = 0;
                PlayerDAO playerDAO = new PlayerDAO(conn);
                PlayerDTO playerDTO = p.get(i);
                playerDAO.insert(playerDTO);
                List<PlayerDTO> players = playerDAO.selectAll();
                for (PlayerDTO player : players){
                    if (player.getName().equals(playerDTO.getName())) {
                        playerID = player.getId();
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
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
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



}




