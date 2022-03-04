package com.example.finalproject;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Event {

    String name;
    String date;
    String time;
    String data;
    String isAvailable;


    public Event() {
    }

    public Event(String name, String date, String time, String data, String isAvailable) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.data = data;
        this.isAvailable = isAvailable;
    }

    public Event(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.data = "";
        this.isAvailable = "1";
    }

    public Event(String name, String date, String time, String data) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.data = data;
        this.isAvailable = "1";
    }

    public Event(String date, String time) {
        this.name = "";
        this.time = time;
        this.date = date;
        this.data = "";
        this.isAvailable = "1";
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", date=" + date + '\'' +
                ", time='" + time + '\'' +
                ", data='" + data + '\'' +
                ", is available=" + isAvailable +
                '}';
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDate() {
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
//        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy);
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getIsAvailable() { return isAvailable; }

    public void setIsAvailable(String isAvailable) { this.isAvailable = isAvailable; }
}
