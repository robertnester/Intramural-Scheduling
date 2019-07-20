package com.company;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS player (id integer PRIMARY KEY, name text NOT NULL UNIQUE, grade text);";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLConnection.createNewDatabase("C:/sqlite/db/test.db");
        Connection c = SQLConnection.connect();
        TeamDAO tdao = new TeamDAO(c);
        tdao.create();
        PlayerDAO pdao = new PlayerDAO(c);
        pdao.create();
        TeamPlayerDAO tpdao = new TeamPlayerDAO(c);
        tpdao.create();
        List<PlayerDTO> pList = new ArrayList<PlayerDTO>();
        pList.add(new PlayerDTO("Robert Nester",12));
        pList.add(new PlayerDTO("Micheal Jackson",11));
        pList.add(new PlayerDTO("Biggie Smalls",12));
        pList.add(new PlayerDTO("Luke Skywalker",10));
        TeamDTO t1 = new TeamDTO("Nuggets", true);
        TeamPlayerDAO tpd = new TeamPlayerDAO(c);
        tpd.init(t1,pList);


        /*createNewTable();
        InsertApp app = new InsertApp();
        // insert three new rows
        app.insert("Robert Nester", "12");
        app.insert("Fred Dai", "10");
        //app.insert("Bob Marley", "TREK");
        SelectApp app2 = new SelectApp();
        UpdateApp app3 = new UpdateApp();
        // update the warehouse with id 3
        app3.update(3, "Bob Dylan", "TREK");
        //DeleteApp app4 = new DeleteApp();
        // delete the row with id 3
        //app4.delete(2);
        app2.selectAll();*/
    }

}