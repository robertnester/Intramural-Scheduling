package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO extends SQLConnection {

    private Connection conn;

    public PlayerDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS player (id integer PRIMARY KEY NOT NULL, name text UNIQUE NOT NULL, grade integer);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insert(PlayerDTO p) {
        String sqlPlayer = "INSERT INTO player(name,grade) VALUES(?,?)";

        try (
             PreparedStatement pstmt = conn.prepareStatement(sqlPlayer, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = pstmt.getGeneratedKeys();
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getGrade());
            int rowAffected = pstmt.executeUpdate();

            int playerId = -1;
            if (rs.next()) {
                playerId = rs.getInt(1);
            }
            if (rowAffected != 1) {
                conn.rollback();
            }
            return playerId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Delete a player specified by the id
     *
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM player WHERE player_id = ?";

        try (//Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update data of a warehouse specified by the id
     *
     * @param id
     * @param name name of the player
     * @param grade capacity of the warehouse
     */
    public void update(int id, String name, String grade) {
        String sql = "UPDATE player SET name = ? , "
                + "grade = ? "
                + "WHERE player_id = ?";

        try (//Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, grade);
            pstmt.setInt(3, id);
            // update
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

    /**
     * select all rows in the warehouses table
     */
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
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("grade"));
            }
            return players;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
