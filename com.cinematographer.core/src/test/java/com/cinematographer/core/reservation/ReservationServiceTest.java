package com.cinematographer.core.reservation;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cinematographer.core.DatabaseTest;
import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.Seat;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.screening.service.ScreeningService;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;
import com.cinematographer.core.user.service.IUserService;
import com.cinematographer.core.user.service.UserService;

public class ReservationServiceTest extends DatabaseTest {

	private static final String[] TEST_USERNAMES = { "user1", "user2" };
	private static final UserProfile[] TEST_USERS = {
			new UserProfile(TEST_USERNAMES[0], Role.BASIC),
			new UserProfile(TEST_USERNAMES[1], Role.ADMINISTRATOR) };

	private static final String[] TEST_TITLES = { "title1", "title2", "title3",
			"title5" };
	private static final Screening[] TEST_SCREENINGS = {
			new Screening(TEST_TITLES[0], 1, 120, Arrays.asList(
					new Seat("a-1"), new Seat("a-2"), new Seat("a-3"))),
			new Screening(TEST_TITLES[1], 2, 150, Arrays.asList(
					new Seat("a-1"), new Seat("a-2"), new Seat("a-3"))),
			new Screening(TEST_TITLES[2], 11, 90, Arrays.asList(
					new Seat("a-1"), new Seat("a-2"), new Seat("a-3"))),
			new Screening(TEST_TITLES[3], 5, 180, Arrays.asList(
					new Seat("a-1"), new Seat("a-2"), new Seat("a-3"))) };

	private ReservationService classUnderTest;
	private ScreeningService screeningService;

	@Before
	public void setUp() {
		super.setUp();
		classUnderTest = new ReservationService(emf);

		screeningService = new ScreeningService(emf);
		for (Screening screening : TEST_SCREENINGS) {
			screeningService.addScreening(screening);
		}

		IUserService userService = new UserService(emf);
		for (UserProfile user : TEST_USERS) {
			userService.addUser(user);
		}

		IServiceManager serviceManager = ServiceManager.getInstance();
		serviceManager.registerService(IUserService.class, userService);
		serviceManager.registerService(IScreeningService.class,
				screeningService);
	}

	@After
	public void cleanUp() {
		ServiceManager.getInstance().removeService(IUserService.class);
		ServiceManager.getInstance().removeService(IScreeningService.class);
	}

	@Test
	public void getUserReservationsTest() {
		Reservation[] reservations = {
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[0], "a-1", "a-2"),
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[1], "a-1"),
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[2], "a-1",
						"a-2", "a-3") };
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1", "a-2"));
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[1],
				Arrays.asList("a-1"));
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[2],
				Arrays.asList("a-1", "a-2", "a-3"));

		assertThat(
				classUnderTest.getActiveReservations(TEST_USERS[0].getName()),
				containsInAnyOrder(reservations));
		assertThat(
				classUnderTest.getActiveReservations(TEST_USERS[1].getName()),
				empty());
	}

	@Test(expected = ReservationException.class)
	public void attemptToReserveAgainTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1"));
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-2"));
	}

	@Test
	public void payReservationTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1"));
		classUnderTest.payReservation(TEST_USERNAMES[0], TEST_TITLES[0]);
		classUnderTest.reserveSeats(TEST_USERNAMES[1], TEST_TITLES[0],
				Arrays.asList("a-2"));
		classUnderTest.payReservation(TEST_USERNAMES[1], TEST_TITLES[0]);

		assertThat(
				classUnderTest.getHistoricReservations(TEST_USERNAMES[0]),
				contains(new Reservation(TEST_USERS[0], screeningService
						.findScreening(TEST_TITLES[0]), Arrays.asList("a-1"))));
		assertThat(
				classUnderTest.getHistoricReservations(TEST_USERNAMES[1]),
				contains(new Reservation(TEST_USERS[1], screeningService
						.findScreening(TEST_TITLES[0]), Arrays.asList("a-2"))));
	}

	@Test(expected = ReservationException.class)
	public void payUnexistingReservationTest() {
		classUnderTest.payReservation(TEST_USERNAMES[0], TEST_TITLES[0]);
	}

	@Test(expected = ReservationException.class)
	public void reserveAlreadySoldSeatsTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1", "a-2"));
		classUnderTest.payReservation(TEST_USERNAMES[0], TEST_TITLES[0]);
		classUnderTest.reserveSeats(TEST_USERNAMES[1], TEST_TITLES[0],
				Arrays.asList("a-2", "a-3"));
	}

	@Test(expected = ReservationException.class)
	public void reserveAlreadyReservedSeatsTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1", "a-2"));
		classUnderTest.reserveSeats(TEST_USERNAMES[1], TEST_TITLES[0],
				Arrays.asList("a-2", "a-3"));
	}

	@Test(expected = ReservationException.class)
	public void reserveInvalidReservationTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1", "a-5"));
	}

	@Test(expected = ReservationException.class)
	public void reserveForUnexistendScreeningTest() {
		classUnderTest.reserveSeats(TEST_USERNAMES[0], "invalid",
				Arrays.asList("a-1", "a-5"));
	}

	@Test(expected = ReservationException.class)
	public void buyDeletedReservationTest() throws InterruptedException {
		classUnderTest = new ReservationService(emf, 2); // in seconds
		classUnderTest.reserveSeats(TEST_USERNAMES[0], TEST_TITLES[0],
				Arrays.asList("a-1", "a-5"));
		Thread.sleep(3000); // wait for the reservation to expire
		classUnderTest.payReservation(TEST_USERNAMES[0], TEST_TITLES[0]);
	}
}