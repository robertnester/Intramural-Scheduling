package com.company;

import java.sql.*;

public class PlayerDAO extends SQLConnection {

    private Connection conn;

    public PlayerDAO(Connection c) {
        conn = c;
    }

    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS player (id integer PRIMARY KEY, name text NOT NULL, grade integer);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insert(PlayerDTO p) {
        String sql = "INSERT INTO player(name,grade) VALUES(?,?)";

        try (//Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = pstmt.getGeneratedKeys();
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getGrade());
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
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT player_id, name, grade FROM player";

        try (//Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("grade"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
