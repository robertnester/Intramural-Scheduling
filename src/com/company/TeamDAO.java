package com.company;

import java.sql.*;
import java.util.*;

public class TeamDAO extends SQLConnection {

    private Connection conn;

    public TeamDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS team (id integer PRIMARY KEY NOT NULL, name text UNIQUE NOT NULL, senior boolean);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //ADD JAVAC COMMENTS
    public int insert(TeamDTO t) {
        String sql = "INSERT INTO team(name,senior) VALUES(?,?)";

        try (//Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.getGeneratedKeys();
            pstmt.setString(1, t.getName());
            pstmt.setBoolean(2, t.isSenior());
            int rowAffected = pstmt.executeUpdate();

            int teamId = -1;
            if (rs.next()) {
                teamId = rs.getInt(1);
            }
            if (rowAffected != 1) {
                conn.rollback();
            }
            return teamId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Delete a player specified by the id
     *
     * @param name
     */
    public void deleteByName(String name) {
        String sql = "DELETE FROM team WHERE name = ?";

        try (
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update data of a Team specified by the id
     *
     * @param id
     * @param name name of the player
     * @param senior capacity of the warehouse
     */
    public void update(int id, String name, boolean senior) {
        String sql = "UPDATE team SET name = ? , "
                + "senior = ? "
                + "WHERE id = ?";

        try (//Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setBoolean(2, senior);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * select Team by Name
     */
    public TeamDTO getDTOByName(String name) {
        String sql = "SELECT id, name, senior FROM team WHERE name = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, name);
            // execute the select statement

            ResultSet rs = pstmt.executeQuery(sql);
            TeamDTO teamDTO = new TeamDTO();
            // loop through the result set
            while (rs.next()) {
                teamDTO.setId(rs.getInt("id"));
                teamDTO.setName(rs.getString("name"));
                teamDTO.setSenior(rs.getBoolean("senior"));
            }
            return teamDTO;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * select all rows in the warehouses table
     */
    public List<TeamDTO> selectAll(){
            String sql = "SELECT id, name, senior FROM team";
            List<TeamDTO> teams = new ArrayList<TeamDTO>();
            try (
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set

                while (rs.next()) {

                    TeamDTO teamDTO = new TeamDTO();
                    teamDTO.setId(rs.getInt("id"));
                    teamDTO.setName(rs.getString("name"));
                    teamDTO.setSenior(rs.getBoolean("senior"));
                    teams.add(teamDTO);
                    System.out.println(rs.getInt("id") +  "\t" +
                            rs.getString("name") + "\t" +
                            rs.getBoolean("senior"));
                }
                return teams;
            } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
