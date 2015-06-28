package com.cinematographer.core.reservation;

import java.util.Collection;
import java.util.List;

public interface IReservationService {

	public void reserveSeats(String username, String title, List<String> seats);

	public void payReservation(String username, String title);

	public Collection<Reservation> getActiveReservations(String username);

	public Collection<Reservation> getHistoricReservations(String username);

}
