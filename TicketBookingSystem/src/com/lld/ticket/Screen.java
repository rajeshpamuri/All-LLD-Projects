package com.lld.ticket;

import java.util.List;

public class Screen {

	String id;
    String name;
    List<Seat> seats; // A screen contains multiple seats of varying types.
    
    public Screen(String id, String name, List<Seat> seats) {
        this.id = id; this.name = name; this.seats = seats;
    }
}
