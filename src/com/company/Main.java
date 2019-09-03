package com.company;

import com.company.DAO.*;
import com.company.DTO.EligibleDayDTO;
import com.company.DTO.GameDTO;
import com.company.DTO.PlayerDTO;
import com.company.DTO.TeamDTO;

import java.sql.Connection;

import java.util.*;

public class Main {

    /**
     * Create a new table in the test database
     *
     */
/*    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS player (id integer PRIMARY KEY, name text NOT NULL UNIQUE, grade text);";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // createTable a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLConnection.createNewDatabase("C:/sqlite/db/test.db");
        Connection c = SQLConnection.connect();

        TeamDAO tdao = new TeamDAO(c);
        tdao.createTable();
        PlayerDAO pdao = new PlayerDAO(c);
        pdao.createTable();
        TeamPlayerDAO tpdao = new TeamPlayerDAO(c);
        tpdao.createTable();
        EligibleDayDAO eddao = new EligibleDayDAO(c);
        eddao.createTable();
        GameDAO gdao = new GameDAO(c);
        gdao.createTable();
        GameDayDAO gddao = new GameDayDAO(c);
        gddao.createTable();

        GUI gui = new GUI(c);
        gui.make();


/*        List<PlayerDTO> pList = new ArrayList<PlayerDTO>();
        pList.add(new PlayerDTO("Robert Nester",12));
        pList.add(new PlayerDTO("Micheal Jackson",11));
        pList.add(new PlayerDTO("Biggie Smalls",12));
        pList.add(new PlayerDTO("Luke Skywalker",10));
        TeamDTO t1 = new TeamDTO("Nuggets", true);
        tpdao.insert(t1,pList);
        List<PlayerDTO> pList2 = new ArrayList<PlayerDTO>();
        pList2.add(new PlayerDTO("Bob Bobson",12));
        pList2.add(new PlayerDTO("Mike Tyson",12));
        pList2.add(new PlayerDTO("John Travolta",10));
        pList2.add(new PlayerDTO("Hillary Clinton",10));
        TeamDTO t2 = new TeamDTO("Cavaliers", true);
        tpdao.insert(t2,pList2);
        List<PlayerDTO> pList3 = new ArrayList<PlayerDTO>();
        pList3.add(new PlayerDTO("Bon",12));
        pList3.add(new PlayerDTO("Mikyson",12));
        pList3.add(new PlayerDTO("Johnravolta",10));
        pList3.add(new PlayerDTO("Hiary Clinton",10));
        TeamDTO t3 = new TeamDTO("Suns", true);
        tpdao.insert(t3,pList3);
        List<PlayerDTO> pList4 = new ArrayList<PlayerDTO>();
        pList4.add(new PlayerDTO("Bob",12));
        pList4.add(new PlayerDTO("Miken",12));
        pList4.add(new PlayerDTO("lta",10));
        pList4.add(new PlayerDTO("illton",10));
        TeamDTO t4 = new TeamDTO("Raptors", true);
        tpdao.insert(t4,pList4);
        tpdao.print();

        Calendar cal = Calendar.getInstance();
        cal.set(2019,0,7,0,0);

        System.out.println(t1.getId());

        gddao.insert(new GameDTO(t1, t2), new GameDTO(t3, t4), new EligibleDayDTO(cal));*/

/*




        GameDayDAO gdDAO = new GameDayDAO(c);
        gdDAO.insert()
*/





        //tpd.selectAll();


        /*createNewTable();
        InsertApp app = new InsertApp();
        // insert three new rows
        app.insert("Robert Nester", "12");
        app.insert("Fred Dai", "10");
        //app.insert("Bob Marley", "TREK");
        SelectApp app2 = new SelectApp();
        UpdateApp app3 = new UpdateApp();
        // updateWinner the warehouse with id 3
        app3.updateWinner(3, "Bob Dylan", "TREK");
        //DeleteApp app4 = new DeleteApp();
        // delete the row with id 3
        //app4.delete(2);
        app2.selectAll();*/
    }

}