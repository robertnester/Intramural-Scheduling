package com.company;

import java.sql.Array;
import java.sql.Connection;

import com.company.DAO.EligibleDayDAO;
import com.company.DAO.TeamPlayerDAO;
import com.company.DTO.EligibleDayDTO;
import com.company.DTO.PlayerDTO;
import com.company.DTO.TeamDTO;
import com.github.lgooddatepicker.components.DatePicker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;
import java.util.List;

public class GUI {
    DatePicker datePicker1;
    Connection c;
    EligibleDayDAO eldao;
    TeamPlayerDAO tpdao;
    JTextArea text;
    JList list;
    JPanel panel3;
    JFrame frame;
    JLabel label2;
    JButton delete;
    JScrollPane scroller;
    JTextField teamField;
    List<JTextField> playerFields;
    boolean senior = true;
    JPanel schedPanel;

    public GUI(Connection c) {
        this.c = c;
        eldao = new EligibleDayDAO(c);
        tpdao = new TeamPlayerDAO(c);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void TABSmake() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        JButton tab1 = new JButton("Add Eligible Dates");
        tab1.addActionListener(new TAB1Listener());
        JButton tab2 = new JButton("Add New Team");
        tab2.addActionListener(new TAB2Listener());
        JButton tab3 = new JButton("Schedule");
        tab3.addActionListener(new TAB3Listener());
        JButton tab4 = new JButton("TAB4");

        panel.add(tab1);
        panel.add(tab2);
        panel.add(tab3);
        panel.add(tab4);

        frame.getContentPane().add(BorderLayout.NORTH, panel);
    }

    public void TAB1make() {
        frame.getContentPane().removeAll();
        frame.repaint();
        TABSmake();
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("ADD NEW DAY");

        datePicker1 = new DatePicker();

        JButton add = new JButton("ADD");
        add.addActionListener(new AddButtonListener());

        panel2.add(label);
        panel2.add(datePicker1);
        panel2.add(add);

        frame.getContentPane().add(BorderLayout.EAST, panel2);

        panel3 = new JPanel();

        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        label2 = new JLabel("CURRENT DAYS");

        String [] entries = new String[eldao.selectAll().size()];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new SimpleDateFormat("dd/MM/yyyy").format(eldao.selectAll().get(i).getCalendar().getTime());
        }

        list = new JList(entries);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        delete = new JButton("DELETE");
        delete.addActionListener(new DeleteButtonListener());

        panel3.add(label2);
        panel3.add(scroller);
        panel3.add(delete);

        frame.getContentPane().add(BorderLayout.WEST, panel3);

/*


        labelButton = new JButton("Change Label");
        labelButton.addActionListener(new LabelListener());

        JButton coolButton = new JButton("Change Cool");
        labelButton.addActionListener(new CoolListener());

        label = new JLabel("I'm a label");

        frame.getContentPane().add(BorderLayout.NORTH, coolButton);
        frame.getContentPane().add(BorderLayout.CENTER, label);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);*/

        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public void TAB1remake() {
        //text = new JTextArea(10,20);
        //text.setLineWrap(true);


        panel3.remove(scroller);
        panel3.remove(delete);

        String [] entries = new String[eldao.selectAll().size()];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new SimpleDateFormat("dd/MM/yyyy").format(eldao.selectAll().get(i).getCalendar().getTime());
        }

        list = new JList(entries);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        delete = new JButton("DELETE");
        delete.addActionListener(new DeleteButtonListener());

        panel3.add(scroller);
        panel3.add(delete);

/*


        labelButton = new JButton("Change Label");
        labelButton.addActionListener(new LabelListener());

        JButton coolButton = new JButton("Change Cool");
        labelButton.addActionListener(new CoolListener());

        label = new JLabel("I'm a label");

        frame.getContentPane().add(BorderLayout.NORTH, coolButton);
        frame.getContentPane().add(BorderLayout.CENTER, label);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);*/

        frame.setSize(800,600);
        frame.setVisible(true);


    }

    class TAB1Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            TAB1make();
        }
    }

    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                insertDate(datePicker1.getDate());
            } catch (Exception e) {

            }
        }
    }

    class DeleteButtonListener implements ActionListener {
         public void actionPerformed(ActionEvent event) {
             try { Calendar calendar = Calendar.getInstance();
             String valString = (String) list.getSelectedValue();
             Integer[] ints = new Integer[3];
             for (int i = 0; i < valString.split("/").length; i++) {
                 ints[i] = Integer.parseInt(valString.split("/")[i]);
             }
             calendar.clear();
             calendar.set(ints[2], ints[1]-1, ints[0]);
             Date date = new Date(calendar.getTimeInMillis());
             String s = date.toString();
             System.out.println(s);
             eldao.deleteByDate(date);
             //panel3.revalidate();
             //panel3.repaint();
             TAB1remake(); } catch (Exception e) {

             }
             //eldao.deleteByDate(new Date((String) list.getSelectedValue()))
        }
    }

    public void insertDate(LocalDate localDate){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        //assuming start of day
        calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
        EligibleDayDTO eldto = new EligibleDayDTO(calendar);
        eldao.insert(eldto);
        TAB1remake();
    }

    public void TAB2make() {
        frame.getContentPane().removeAll();
        frame.repaint();
        TABSmake();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel teamNameLabel = new JLabel("Team Name:");
        teamField = new JTextField();
        teamField.setMaximumSize(new Dimension(500,3));
        panel.add(teamNameLabel);
        panel.add(teamField);
        playerFields = new ArrayList<JTextField>();
        for (int i = 0; i < 4; i++) {
            JLabel playerLabel = new JLabel("Player Name:");
            playerFields.add(new JTextField(10));
            //TODO: put labels to left of text boxes and make text boxes smaller
            panel.add(playerLabel);
            panel.add(playerFields.get(i));
        }

        JRadioButton senior = new JRadioButton("Senior");
        senior.setActionCommand("senior");
        senior.setSelected(true);
        JRadioButton junior = new JRadioButton("Junior");
        junior.setActionCommand("junior");

        ButtonGroup group = new ButtonGroup();
        group.add(senior);
        group.add(junior);

        senior.addActionListener(new RadioButtonListener());
        junior.addActionListener(new RadioButtonListener());

        panel.add(senior);
        panel.add(junior);

        JButton createTeam = new JButton("Create Team");
        createTeam.addActionListener(new CreateTeamListener());

        panel.add(createTeam);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    class TAB2Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            TAB2make();
        }
    }

    class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equals("senior")) {
                senior = true;
            } else {
                senior = false;
            }
        }
    }

    class CreateTeamListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            TeamDTO team = new TeamDTO(teamField.getText(), senior);
            List<PlayerDTO> players = new ArrayList<PlayerDTO>();
            for (int i = 0; i < playerFields.size(); i++) {
                players.add(new PlayerDTO(playerFields.get(i).getText()));
                System.out.println(playerFields.get(i).getText());
            }
            tpdao.insert(team, players);
        }
    }

    public void TAB3make() {
        frame.getContentPane().removeAll();
        frame.repaint();
        TABSmake();
        schedPanel = new JPanel();
        JButton schedInitButton = new JButton("Finalize and Create Schedule");
        schedInitButton.addActionListener(new InitSchedListener());
        schedPanel.add(schedInitButton);
        frame.getContentPane().add(BorderLayout.CENTER, schedPanel);
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public void initSched() {
        Schedule s = new Schedule(c);
        //Math.random()

        //schedPanel.add(table);
        //frame.setSize(800,600);
        //frame.setVisible(true);
    }

    class TAB3Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            TAB3make();
        }
    }

    class InitSchedListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            initSched();
        }
    }

/*    class LabelListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            label.setText("Ouch!!!");
        }
    }*/

    /*class CoolListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            label.setText("Cool!!!");
            frame.repaint();
        }
    }*/
}

