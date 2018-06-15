package ru.yakovlevdmv.parser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Method {

    private int ID;
    private String Name;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private int time;

    public Method() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
        this.time = (int)ChronoUnit.MILLIS.between(timeIn, timeOut);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Method{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", time=" + time +
                '}';
    }
}
