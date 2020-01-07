package com.company;

import com.company.DAO.*;

import java.sql.Connection;

public class Main {

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
        gui.TAB1make();
    }
}