/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinematographer.core.reservation;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Aleksandar
 */
public class ReservationDTO
{
    private String username;
    private String title;
    private List<String> seats;

    public ReservationDTO()
    {
    }

    public ReservationDTO(String username, String title, List<String> seats)
    {
        this.username = username;
        this.title = title;
        this.seats = seats;
    }

    public List<String> getSeats()
    {
        return seats;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUsername()
    {
        return username;
    }

    public void setSeats(List<String> seats)
    {
        this.seats = seats;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    
    
}
