package com.lld.ticket;

import java.util.List;

public class Cinema {

	String id;
    String name;
    City city;
    List<Screen> screens; // A cinema houses multiple screens.
    
    public Cinema(String id, String name, City city, List<Screen> screens) {
        this.id = id; this.name = name; this.city = city; this.screens = screens;
    }
}
