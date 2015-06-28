package com.cinematographer.core.reservation;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;

public class ReservationPoolTest {

	private static final UserProfile[] TEST_USERS = {
			new UserProfile("user1", Role.BASIC),
			new UserProfile("user2", Role.ADMINISTRATOR) };

	private static final Screening[] TEST_SCREENINGS = {
			new Screening("title1", 1, 120), new Screening("title2", 2, 150),
			new Screening("title3", 11, 90), new Screening("title5", 5, 180) };

	private ReservationsPool classUnderTest;

	@Before
	public void setUp() {
		classUnderTest = new ReservationsPool() {
			protected void startTimer(Reservation reservation) {
				// No timers
			}

			public void confirmReservation(Reservation reservation) {
				deleteReservation(reservation);
			}
		};
	}

	@Test
	public void addReservationTest() {
		Reservation reservation = new Reservation(TEST_USERS[0],
				TEST_SCREENINGS[0], "a-1", "a-2");
		classUnderTest.addReservation(reservation);
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				TEST_SCREENINGS[0].getTitle()), is(reservation));
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				"invalid"), nullValue());
		assertThat(
				classUnderTest.getReservation("invalid",
						TEST_SCREENINGS[0].getTitle()), nullValue());

	}

	@Test
	public void getReservationsByUsernameTest() {
		Reservation[] reservations = {
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[0], "a-1", "a-2"),
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[1], "a-1"),
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[2], "a-1",
						"a-2", "a-3") };
		classUnderTest.addReservation(reservations[0]);
		classUnderTest.addReservation(reservations[1]);
		classUnderTest.addReservation(reservations[2]);

		for (int i = 0; i < reservations.length; ++i) {
			assertThat(classUnderTest.getReservationsByTitle( //
					TEST_SCREENINGS[i].getTitle()), contains(reservations[i]));
		}

		assertThat(classUnderTest.getReservationsByUsername(TEST_USERS[0]
				.getName()), containsInAnyOrder(reservations));
		assertThat(classUnderTest.getReservationsByUsername("invalid"), empty());
	}

	@Test
	public void getReservationsByTitleTest() {
		Reservation[] reservations = {
				new Reservation(TEST_USERS[0], TEST_SCREENINGS[0], "a-1"),
				new Reservation(TEST_USERS[1], TEST_SCREENINGS[0], "a-2", "a-3") };
		classUnderTest.addReservation(reservations[0]);
		classUnderTest.addReservation(reservations[1]);

		for (int i = 0; i < reservations.length; ++i) {
			assertThat(classUnderTest.getReservationsByUsername( //
					TEST_USERS[i].getName()), contains(reservations[i]));
		}

		assertThat(classUnderTest.getReservationsByTitle(TEST_SCREENINGS[0]
				.getTitle()), containsInAnyOrder(reservations));
		assertThat(classUnderTest.getReservationsByTitle("invalid"), empty());
	}

	@Test
	public void timerTest() throws InterruptedException {
		classUnderTest = new ReservationsPool(2); // seconds
		Reservation reservation = new Reservation(TEST_USERS[0],
				TEST_SCREENINGS[0], "a-1", "a-2");
		classUnderTest.addReservation(reservation);
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				TEST_SCREENINGS[0].getTitle()), is(reservation));
		Thread.sleep(3000);
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				TEST_SCREENINGS[0].getTitle()), nullValue());
	}

	@Test
	public void confirmReservationTest() throws InterruptedException {
		classUnderTest = new ReservationsPool(2); // seconds
		Reservation reservation = new Reservation(TEST_USERS[0],
				TEST_SCREENINGS[0], "a-1", "a-2");
		classUnderTest.addReservation(reservation);
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				TEST_SCREENINGS[0].getTitle()), is(reservation));
		classUnderTest.confirmReservation(reservation);
		assertThat(classUnderTest.getReservation(TEST_USERS[0].getName(),
				TEST_SCREENINGS[0].getTitle()), nullValue());

		// Ensure there are no problems with the timer
		Thread.sleep(3000);
	}
}
