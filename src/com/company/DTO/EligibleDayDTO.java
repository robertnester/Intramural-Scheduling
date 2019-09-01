package com.company.DTO;

import java.util.Calendar;

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


}
