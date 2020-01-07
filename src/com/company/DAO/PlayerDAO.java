package com.company.DAO;

import com.company.DTO.PlayerDTO;
import com.company.SQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO extends SQLConnection {

    private Connection conn;

    public PlayerDAO(Connection c) {
        conn = c;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player (id integer PRIMARY KEY NOT NULL, name text UNIQUE NOT NULL, grade integer);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(PlayerDTO p) {
        String sqlPlayer = "INSERT INTO player(name,grade) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlPlayer)) {
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getGrade());
            pstmt.executeUpdate();

    } catch(SQLException e) {
        System.out.println(e.getMessage());
    }
}
    /**
     * Delete a player specified by the id
     *
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM player WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, String name, String grade) {
        String sql = "UPDATE player SET name = ? , "
                + "grade = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, grade);
            pstmt.setInt(3, id);
            // updateWinner
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * select Player by Name
     */
    public PlayerDTO getDTOByName(String name) {
        String sql = "SELECT id, name, grade FROM player WHERE name = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, name);
            // execute the select statement
            pstmt.executeUpdate();
            ResultSet rs = pstmt.executeQuery(sql);
            PlayerDTO playerDTO = new PlayerDTO();
            // loop through the result set
            while (rs.next()) {
                playerDTO.setId(rs.getInt("id"));
                playerDTO.setName(rs.getString("name"));
                playerDTO.setGrade(rs.getInt("grade"));
            }
            return playerDTO;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public PlayerDTO getDTOByID(int id) {
        String sql = "SELECT name, grade FROM player WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the select statement
            ResultSet rs = pstmt.executeQuery();
            PlayerDTO playerDTO = new PlayerDTO();
            // loop through the result set
            while (rs.next()) {
                playerDTO.setId(id);
                playerDTO.setName(rs.getString("name"));
                playerDTO.setGrade(rs.getInt("grade"));
            }
            return playerDTO;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<PlayerDTO> selectAll(){
        String sql = "SELECT id, name, grade FROM player";
        List<PlayerDTO> players = new ArrayList<PlayerDTO>();
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                PlayerDTO pDTO = new PlayerDTO();
                pDTO.setId(rs.getInt("id"));
                pDTO.setName(rs.getString("name"));
                pDTO.setGrade(rs.getInt("grade"));
                players.add(pDTO);
            }
            return players;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
