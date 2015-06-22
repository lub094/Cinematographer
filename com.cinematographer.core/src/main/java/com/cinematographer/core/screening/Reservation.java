package com.cinematographer.core.screening;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
public class Reservation {
    
        @Id
        @NotNull
	private Screening screening;
        @OneToMany
	private Collection<Seat> seats;

    public Reservation()
    {
    }

    public Reservation(Screening screening, Collection<Seat> seats)
    {
        this.screening = screening;
        this.seats = seats;
    }

    public Screening getScreening()
    {
        return screening;
    }

    public Collection<Seat> getSeats()
    {
        return seats;
    }

    public void setScreening(Screening screening)
    {
        this.screening = screening;
    }

    public void setSeats(Collection<Seat> seats)
    {
        this.seats = seats;
    }
        
        
}
