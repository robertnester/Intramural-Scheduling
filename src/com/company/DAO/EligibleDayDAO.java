package com.company.DAO;

import com.company.DTO.EligibleDayDTO;
import com.company.DTO.TeamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EligibleDayDAO {

    private Connection conn;

    public EligibleDayDAO(Connection c) {
        conn = c;
    }

    public void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS eligibleday " +
                "(id integer PRIMARY KEY NOT NULL, " +
                "day DATE UNIQUE NOT NULL);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(EligibleDayDTO el) {
        String sql = "INSERT INTO eligibleday(day) VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.getGeneratedKeys();
            pstmt.setDate(1, new Date(el.getCalendar().getTimeInMillis()));
            //int rowAffected =
            pstmt.executeUpdate();

            /*int eldayId = -1;
            if (rs.next()) {
                eldayId = rs.getInt(1);
            }
            if (rowAffected != 1) {
                conn.rollback();
            }
            return eldayId;*/
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //return -1;
        }
    }

    /**
     * Delete a player specified by the id
     *
     * @param id
     */
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

    public void print() {
        String sql = "SELECT id, day FROM eligibleday";

        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getDate("day"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<EligibleDayDTO> selectAll(){
        String sql = "SELECT id, day FROM eligibleday";
        List<EligibleDayDTO> eldays = new ArrayList<EligibleDayDTO>();
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set

            while (rs.next()) {

                EligibleDayDTO eldayDTO = new EligibleDayDTO();
                eldayDTO.setId(rs.getInt("id"));
                Date day = rs.getDate("day");
                System.out.println(day);
                Calendar cal = Calendar.getInstance();
                cal.setTime(day);
                eldayDTO.setCalendar(cal);
                eldays.add(eldayDTO);
            }
            return eldays;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
