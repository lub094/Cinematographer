package com.cinematographer.core.reservation;

import java.util.Collection;

public interface IReservationPool {

	public Reservation getReservation(String username, String title);

	public Collection<Reservation> getReservationsByUsername(String username);

	public Collection<Reservation> getReservationsByTitle(String title);

	public void addReservation(Reservation reservation);

	public void confirmReservation(Reservation reservation);

}