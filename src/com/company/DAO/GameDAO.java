package com.company.DAO;

import com.company.DTO.GameDTO;
import com.company.DTO.TeamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private Connection conn;

    public GameDAO(Connection c) {
        conn = c;
    }

    public void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS game " +
                "(id integer PRIMARY KEY NOT NULL, " +
                "team1_id integer NOT NULL, " +
                "team2_id integer NOT NULL, " +
                "winner_team_id integer, " +
                "UNIQUE (team1_id, team2_id), " +
                "UNIQUE (team2_id, team1_id), " +
                "FOREIGN KEY (team1_id) REFERENCES team (id), " +
                "FOREIGN KEY (team2_id) REFERENCES team (id));";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(GameDTO g) {
        String sqlPlayer = "INSERT INTO game(team1_id, team2_id) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlPlayer)) {
            pstmt.setInt(1, g.getTeam1().getId());
            pstmt.setInt(2, g.getTeam2().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM game WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM game";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
    }

    public void updateWinner(int id, int team_id) {
        String sql = "UPDATE game SET winner_team_id = ? , "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, team_id);
            pstmt.setInt(2, id);
            // updateWinner
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public GameDTO getDTOByID(int id) {
        String sql = "SELECT team1_id, team2_id FROM game WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // updateWinner
            ResultSet rs = pstmt.executeQuery();
            TeamDTO teamDTO1 = new TeamDTO();
            TeamDTO teamDTO2 = new TeamDTO();
            TeamDAO tdao = new TeamDAO(conn);
            while (rs.next()) {
                teamDTO1 = tdao.getDTOById(rs.getInt("team1_id"));
                teamDTO2 = tdao.getDTOById(rs.getInt("team2_id"));
            }
            return new GameDTO(id, teamDTO1, teamDTO2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public List<GameDTO> selectAll(){
        String sql = "SELECT id, team1_id, team2_id FROM game";
        List<GameDTO> games = new ArrayList<GameDTO>();
        TeamDAO teamDAO = new TeamDAO(conn);
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set

            while (rs.next()) {

                GameDTO gameDTO = new GameDTO();
                gameDTO.setId(rs.getInt("id"));
                gameDTO.setTeam1(teamDAO.getDTOById(rs.getInt("team1_id")));
                gameDTO.setTeam2(teamDAO.getDTOById(rs.getInt("team2_id")));
                games.add(gameDTO);
            }
            return games;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}