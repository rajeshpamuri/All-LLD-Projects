package com.lld.ticket;

public class Seat {

	String id;
    int row;
    int col;
    SeatType seatType;
    
    public Seat(String id, int row, int col, SeatType seatType) {
        this.id = id; this.row = row; this.col = col; this.seatType = seatType;
    }
}
