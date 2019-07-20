package com.company;

import java.sql.*;
import java.util.List;

public class TeamDAO extends SQLConnection {

    private Connection conn;

    public TeamDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS team (id integer PRIMARY KEY, name text NOT NULL, senior boolean);";
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
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = pstmt.getGeneratedKeys();
            pstmt.setString(1, t.getName());
            pstmt.setBoolean(2, t.isSenior());
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected != 1) {
                conn.rollback();
            }
            int teamId = -1;
            if (rs.next()) {
                teamId = rs.getInt(1);
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
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM team WHERE id = ?";

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
     * select all rows in the warehouses table
     */
    public void selectAll(){
            String sql = "SELECT id, name, senior FROM team";

            try (//Connection conn = this.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") +  "\t" +
                            rs.getString("name") + "\t" +
                            rs.getBoolean("senior"));
                }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
