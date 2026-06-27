package com.lld.lot;

import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final String licensePlate;
    private final long entryTime;
    private TicketStatus status;

    public Ticket(String licensePlate) {
        this.ticketId = UUID.randomUUID().toString();
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis();
        this.status = TicketStatus.ACTIVE;
    }

    public String getTicketId() { return ticketId; }
    public String getLicensePlate() { return licensePlate; }
    public long getEntryTime() { return entryTime; }
    public TicketStatus getStatus() { return status; }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
