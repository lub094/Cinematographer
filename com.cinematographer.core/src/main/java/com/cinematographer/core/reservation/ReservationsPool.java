package com.cinematographer.core.reservation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationsPool implements IReservationPool {

	private static final long DEFAULT_RESERVATION_DURATION = 10 * 60 * 1000;

	private long reservationDuration;
	private Map<Reservation, Timer> timers;
	private Map<String, Map<String, Reservation>> reservationsByUsername;
	private Map<String, Map<String, Reservation>> reservationsByScreening;

	public ReservationsPool() {
		this(DEFAULT_RESERVATION_DURATION);
	}

	public ReservationsPool(long reservationDuration) {
		reservationsByUsername = new HashMap<String, Map<String, Reservation>>();
		reservationsByScreening = new HashMap<String, Map<String, Reservation>>();
		timers = new HashMap<Reservation, Timer>();
		this.reservationDuration = reservationDuration * 1000;
	}

	@Override
	public Reservation getReservation(String username, String title) {
		Map<String, Reservation> userReservations = reservationsByUsername
				.get(username);
		if (null == userReservations) {
			return null;
		}
		return userReservations.get(title);
	}

	@Override
	public Collection<Reservation> getReservationsByUsername(String username) {
		Map<String, Reservation> userReservations = reservationsByUsername
				.get(username);
		if (null == userReservations) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableCollection(userReservations.values());
	}

	@Override
	public Collection<Reservation> getReservationsByTitle(String title) {
		Map<String, Reservation> screeningReservations = reservationsByScreening
				.get(title);
		if (null == screeningReservations) {
			return Collections.emptyList();
		}
		return screeningReservations.values();
	}

	@Override
	public void addReservation(Reservation reservation) {
		Map<String, Reservation> userReservations = reservationsByUsername
				.get(reservation.getOwner().getName());
		if (null == userReservations) {
			userReservations = new HashMap<String, Reservation>();
		}
		userReservations
				.put(reservation.getScreening().getTitle(), reservation);
		reservationsByUsername.put(reservation.getOwner().getName(),
				userReservations);

		Map<String, Reservation> screeningReservations = reservationsByScreening
				.get(reservation.getScreening().getTitle());
		if (null == screeningReservations) {
			screeningReservations = new HashMap<String, Reservation>();
		}
		screeningReservations
				.put(reservation.getOwner().getName(), reservation);
		reservationsByScreening.put(reservation.getScreening().getTitle(),
				screeningReservations);
		startTimer(reservation);
	}

	protected void startTimer(final Reservation reservation) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				deleteReservation(reservation);
			}
		}, reservationDuration);
		timers.put(reservation, timer);
	}

	@Override
	public void confirmReservation(Reservation reservation) {
		Timer timer = timers.remove(reservation);
		timer.cancel();
		deleteReservation(reservation);
	}

	protected void deleteReservation(Reservation reservation) {
		reservationsByUsername.get(reservation.getOwner().getName()).remove(
				reservation.getScreening().getTitle());
		reservationsByScreening.get(reservation.getScreening().getTitle())
				.remove(reservation.getOwner().getName());
	}
}
