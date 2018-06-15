package ru.yakovlevdmv.parser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Process {

    private int ID;
    private String Name;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private long time;

    public Process() {
    }

    public Process(int ID, String name, LocalDateTime timeIn, LocalDateTime timeOut) {
        this.ID = ID;
        Name = name;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
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
        this.time = ChronoUnit.MILLIS.between(timeIn, timeOut);
    }

    @Override
    public String toString() {
        return "Process{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", time=" + time +
                '}';
    }
}
