package com.cinematographer.core.movies;

import java.sql.Time;
import java.util.Collection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "SCREENING")
public class Screening {
        @Id
        @NotNull
	private String title;
	private Time startTime;
	private Time duration;
        @OneToMany
	private Collection<Seat> seats;
	private Integer hall;

    public Screening()
    {
    }

    public Screening(String title, Time startTime, Time duration, Collection<Seat> seats, int hall)
    {
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
        this.seats = seats;
        this.hall = hall;
    }

    public Time getDuration()
    {
        return duration;
    }

    public int getHall()
    {
        return hall;
    }

    public String getTitle()
    {
        return title;
    }

    public Collection<Seat> getSeats()
    {
        return seats;
    }

    public Time getStartTime()
    {
        return startTime;
    }

    public void setDuration(Time duration)
    {
        this.duration = duration;
    }

    public void setHall(int hall)
    {
        this.hall = hall;
    }

    public void setSeats(Collection<Seat> seats)
    {
        this.seats = seats;
    }

    public void setStartTime(Time startTime)
    {
        this.startTime = startTime;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
        
        
}
