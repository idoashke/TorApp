package com.example.finalproject;

import java.util.ArrayList;

public class Business {
    BusinessProfiler busProfiler;
    ArrayList<Event> events;

    public Business(){
        this.busProfiler = new BusinessProfiler();
        this.events = new ArrayList<Event>();
    }

    public Business(BusinessProfiler busProfiler){
        this.busProfiler = busProfiler;
    }

    public Business(BusinessProfiler busProfiler, ArrayList<Event> events) {
        this.busProfiler = busProfiler;
        this.events = events;
    }

    public BusinessProfiler getBusProfiler() {
        return busProfiler;
    }

    public void setBusProfiler(BusinessProfiler busProfiler) {
        this.busProfiler = busProfiler;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


////    public void addEvent(Event event) {
////        this.events.add(event);
////    }
}
