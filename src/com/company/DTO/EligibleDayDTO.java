package com.company.DTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class EligibleDayDTO {

    private int id;
    private Calendar cal;

    public EligibleDayDTO () {

    }

    public EligibleDayDTO(Calendar cal){
        this.cal = cal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return cal;
    }

    public void setCalendar(Calendar cal) {
        this.cal = cal;
    }

    public String getDayString() {
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formatted = dateFormat.format(date);
        return formatted;
    }


}
