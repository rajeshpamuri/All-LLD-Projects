package com.lld.ticket;

import java.time.LocalDateTime;

public class Show {
  
	String id;
    Movie movie;
    Cinema cinema;
    Screen screen;
    LocalDateTime startTime;
    
    public Show(String id, Movie movie, Cinema cinema, Screen screen, LocalDateTime startTime) {
        this.id = id; this.movie = movie; this.cinema = cinema; this.screen = screen; this.startTime = startTime;
    }
}
