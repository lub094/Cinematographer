package com.cinematographer.core.movies;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Seat {
        @Id
	private String number;
	private boolean wasUsed;
        @Enumerated(EnumType.STRING)
	private Status status;

    public Seat()
    {
    }

    public Seat(String number, boolean wasUsed, Status status)
    {
        this.number = number;
        this.wasUsed = wasUsed;
        this.status = status;
    }

    public String getNumber()
    {
        return number;
    }

    public Status getStatus()
    {
        return status;
    }

    public boolean isWasUsed()
    {
        return wasUsed;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public void setWasUsed(boolean wasUsed)
    {
        this.wasUsed = wasUsed;
    }
        
    
    
}
