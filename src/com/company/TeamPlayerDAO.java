package com.company;

import java.sql.*;
import java.util.List;

public class TeamPlayerDAO {
    private Connection conn;

    public TeamPlayerDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS teamplayer (team_id integer, player_id integer, PRIMARY KEY (team_id, player_id), FOREIGN KEY (team_id) REFERENCES team (id), FOREIGN KEY (player_id) REFERENCES player (id));";
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
        PreparedStatement pstmt1 = null, pstmt2 = null;

        try {
            // set auto-commit mode to false
            conn.setAutoCommit(false);

            // 1. insert a new team
            TeamDAO td = new TeamDAO(conn);
            int teamID = td.insert(t);

            // 2. insert the teamplayer
            PlayerDAO pd = new PlayerDAO(conn);
            for (int i = 0; i < p.size(); i++) {
                int playerID = pd.insert(p.get(i));
                pstmt2 = conn.prepareStatement(sqlTeamPlayer);
                pstmt2.setInt(1, teamID);
                pstmt2.setInt(2, playerID);
                pstmt2.executeUpdate();
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
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }



}




