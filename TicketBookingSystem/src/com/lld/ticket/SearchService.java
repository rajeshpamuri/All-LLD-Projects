package com.lld.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
	
    private List<Show> shows = new ArrayList<>();

    public void addShow(Show show) {
        shows.add(show);
    }

    // Filters the master list of shows down to the specific movie and city requested by the user.
    public List<Show> getShowsByMovieAndCity(Movie movie, City city) {
        return shows.stream()
            .filter(s -> s.movie.id.equals(movie.id) && s.cinema.city.id.equals(city.id))
            .collect(Collectors.toList());
    }
    
}