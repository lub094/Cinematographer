package com.cinematographer.core.reservation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.Seat;
import com.cinematographer.core.screening.Status;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.user.UserProfile;
import com.cinematographer.core.user.service.IUserService;

public class ReservationService implements IReservationService {

	private static final String UNKNOWN_SCREENING_ERROR = "Screening with title '%s' does not exist";
	private static final String RESERVATION_ALREADY_MADE_ERROR = "User '%s' already has a reservation for movie '%s'";
	private static final String IVALID_SEAT_NUMBER_ERROR = "There is no seat %s in hall %d";
	private static final String SEAT_ALREADY_TAKEN_ERROR = "Seat %s is already taken";
	private static final String RESERVATON_DOES_NOT_EXIST_ERROR = "User '%s' has not reservation for movie '%s'";

	private EntityManagerFactory emf;
	private IReservationPool reservations;

	public ReservationService(EntityManagerFactory emf, int reservationDuration) {
		this.emf = emf;
		reservations = new ReservationsPool(reservationDuration);
	}

	public ReservationService(EntityManagerFactory emf) {
		this.emf = emf;
		reservations = new ReservationsPool();
	}

	@Override
	public void reserveSeats(String username, String title, List<String> seats) {
		Reservation reservation = reservations.getReservation(username, title);
		if (null != reservation) {
			throw new ReservationException(String.format(
					RESERVATION_ALREADY_MADE_ERROR, username, title));
		}
		Screening screening = getScreening(title);
		validateSeatsExistAndNotSold(screening, seats);
		validateSeatsNotReserved(screening, seats);
		reservation = new Reservation(getUser(username), screening, seats);
		reservations.addReservation(reservation);
	}

	private UserProfile getUser(String username) {
		return ServiceManager.getInstance().findService(IUserService.class)
				.findUser(username);
	}

	private void validateSeatsNotReserved(Screening screening,
			List<String> seats) {
		StringBuffer errorsBuffer = new StringBuffer();
		Set<String> reservedSeats = createReservedSeats(reservations
				.getReservationsByTitle(screening.getTitle()));
		for (String selectedSeat : seats) {
			if (reservedSeats.contains(selectedSeat)) {
				errorsBuffer.append(
						String.format(SEAT_ALREADY_TAKEN_ERROR, selectedSeat))
						.append("\n");

			}
		}
		if (errorsBuffer.length() > 0) {
			throw new ReservationException(errorsBuffer.toString());
		}
	}

	private Set<String> createReservedSeats(Collection<Reservation> reservations) {
		Set<String> seatsSet = new HashSet<String>();

		for (Reservation reservation : reservations) {
			seatsSet.addAll(reservation.getSeats());
		}

		return seatsSet;
	}

	private void validateSeatsExistAndNotSold(Screening screening,
			Collection<String> selectedSeats) {

		Map<String, Seat> allSeats = createSeatsMap(screening);
		StringBuffer invalidSeatsBuffer = new StringBuffer();
		StringBuffer seatsTakenBuffer = new StringBuffer();
		for (String seatNumber : selectedSeats) {
			Seat originalSeat = allSeats.get(seatNumber);
			if (null == originalSeat) {
				invalidSeatsBuffer.append(
						String.format(IVALID_SEAT_NUMBER_ERROR, seatNumber,
								screening.getHall())).append("\n");
				continue;
			}
			if (originalSeat.getStatus() != Status.FREE) {
				seatsTakenBuffer.append(
						String.format(SEAT_ALREADY_TAKEN_ERROR, seatNumber))
						.append("\n");
				continue;
			}
		}
		if (invalidSeatsBuffer.length() > 0 || seatsTakenBuffer.length() > 0) {
			throw new ReservationException(invalidSeatsBuffer.toString()
					+ seatsTakenBuffer.toString());
		}
	}

	private Map<String, Seat> createSeatsMap(Screening screening) {
		Map<String, Seat> allSeats = new HashMap<String, Seat>();

		for (Seat seat : screening.getSeats()) {
			allSeats.put(seat.getNumber(), seat);
		}

		return allSeats;
	}

	private Screening getScreening(String title) {
		IScreeningService screeningService = getScreeningService();
		Screening screening = screeningService.findScreening(title);
		if (null == screening) {
			throw new ReservationException( //
					String.format(UNKNOWN_SCREENING_ERROR, title));
		}
		return screening;
	}

	private IScreeningService getScreeningService() {
		return ServiceManager.getInstance()
				.findService(IScreeningService.class);
	}

	@Override
	public Collection<Reservation> getActiveReservations(String username) {
		return reservations.getReservationsByUsername(username);
	}

	@Override
	public Collection<Reservation> getHistoricReservations(String username) {
		return emf
				.createEntityManager()
				.createNamedQuery(Reservation.SELECT_USER_RESERVATIONS,
						Reservation.class)
				.setParameter(Reservation.USERNAME_PARAMETER, username)
				.getResultList();
	}

	@Override
	public void payReservation(String userName, String title) {
		Reservation reservation = reservations //
				.getReservation(userName, title);
		if (null == reservation) {
			throw new ReservationException(String.format(
					RESERVATON_DOES_NOT_EXIST_ERROR, userName, title));
		}
		reservations.confirmReservation(reservation);
		markSeatsAsSold(title, reservation);
		persistReservation(reservation);
	}

	private void markSeatsAsSold(String title, Reservation reservation) {
		IScreeningService screeningService = getScreeningService();
		screeningService.updateSeatsStatus(title, reservation.getSeats(),
				Status.SOLD);
	}

	private void persistReservation(Reservation reservation) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		em.persist(reservation);
		transaction.commit();

		em.close();
	}
}
