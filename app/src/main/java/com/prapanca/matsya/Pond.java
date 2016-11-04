package com.prapanca.matsya;

/**
 * Created by jayasowmya on 4/11/16.
 */

public class Pond {
    private String pondnumber, tempvalue, time,phvalue;

    public Pond() {
    }

    public Pond(String pondnumber, String tempvalue, String time,String phvalue) {
        this.pondnumber = pondnumber;
        this.tempvalue = tempvalue;
        this.time = time;
        this.phvalue=phvalue;
    }

    public String getPondnumber() {
        return pondnumber;
    }

    public void setPondnumber(String pondnumber) {
        this.pondnumber = pondnumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTempvalue() {
        return tempvalue;
    }

    public void setTempvalue(String tempvalue) {
        this.tempvalue = tempvalue;
    }

    public String getPhvalue() {
        return phvalue;
    }

    public void setPhvalue(String phvalue) {
        this.phvalue = phvalue;
    }
}



