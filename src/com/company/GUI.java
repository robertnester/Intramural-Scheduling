package com.company;

import java.sql.Connection;

import com.company.DAO.EligibleDayDAO;
import com.company.DTO.EligibleDayDTO;
import com.github.lgooddatepicker.components.DatePicker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class GUI {
    DatePicker datePicker1;
    Connection c;
    EligibleDayDAO eldao;
    JTextArea text;
    JList list;

    public GUI(Connection c) {
        this.c = c;
        eldao = new EligibleDayDAO(c);
    }

    public void make() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        JButton tab1 = new JButton("Add Eligible Dates");
        JButton tab2 = new JButton("TAB2");
        JButton tab3 = new JButton("TAB3");
        JButton tab4 = new JButton("TAB4");

        panel.add(tab1);
        panel.add(tab2);
        panel.add(tab3);
        panel.add(tab4);

        frame.getContentPane().add(BorderLayout.NORTH, panel);

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

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        JLabel label2 = new JLabel("CURRENT DAYS");

        //text = new JTextArea(10,20);
        //text.setLineWrap(true);

        String [] entries = new String[eldao.selectAll().size()];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new SimpleDateFormat("dd/MM/yyyy").format(eldao.selectAll().get(i).getCalendar().getTime());
        }

        list = new JList(entries);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton delete = new JButton("DELETE");
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

    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            insertDate(datePicker1.getDate());
        }
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println((String) list.getSelectedValue());
            //TODO: delete by Date by converting the string to a calendar then the calendar into a date
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

