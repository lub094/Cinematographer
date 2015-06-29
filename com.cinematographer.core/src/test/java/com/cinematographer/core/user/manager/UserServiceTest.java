package com.cinematographer.core.user.manager;

import com.cinematographer.core.DatabaseTest;
import com.cinematographer.core.reservation.Reservation;
import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.Seat;

import static com.cinematographer.core.screening.Status.FREE;
import static com.cinematographer.core.screening.Status.RESERVED;

import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;
import com.cinematographer.core.user.service.UserService;

import java.sql.Time;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UserServiceTest extends DatabaseTest {
	private static final String TEST_NAME = "Moon Moon";
	private static final String TEST_PASSWORD = "0000";
	private static final Role TEST_ROLE = Role.BASIC;

	private static final Seat TEST_SEATS_SCREEINING_ONE[] = { new Seat("0", false, FREE), new Seat("1", false, FREE),
			new Seat("2", true, RESERVED), new Seat("3", true, RESERVED) };
	private static final Seat TEST_SEATS_SCREEINING_TWO[] = { new Seat("0", false, FREE), new Seat("1", false, FREE),
			new Seat("2", true, RESERVED), new Seat("3", true, RESERVED) };
	private static final Seat TEST_SEATS_RESERVATION_ONE[] = { new Seat("3", true, RESERVED) };
	private static final Seat TEST_SEATS_RESERVATION_TWO[] = { new Seat("3", true, RESERVED) };
/*
	private static final Reservation TEST_RESERVATIONS[] = {
			new Reservation(new Screening("Batman", new Time(12341204473L), 147, Arrays.asList(TEST_SEATS_SCREEINING_ONE), 4,
					11.0, Arrays.asList(TEST_SEATS_RESERVATION_ONE))),
			new Reservation(
					new Screening("Fatman", new Time(13334504473L), 105, Arrays.asList(TEST_SEATS_SCREEINING_TWO), 3, 9.0),
					Arrays.asList(TEST_SEATS_RESERVATION_TWO)) };

	private static final UserProfile TEST_USER = new UserProfile(TEST_NAME, TEST_ROLE, Arrays.asList(TEST_RESERVATIONS));
 */

	private UserService classUnderTest;
	/* 
	 @Before
	 public void setUp() {
	super.setUp();
	createDatabaseElements();
	classUnderTest = new UserService(emf);
	 }

	 private void createDatabaseElements() {
	     addUser(TEST_USER);
	 }

	private void addUser(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(user);
		transaction.commit();
	}

	@Test
	public void findUserTest() {
		assertThat(classUnderTest.findUser(TEST_NAME) , is (TEST_USER));
		assertThat(classUnderTest.findUser("Not user"), nullValue());
	}

	@Test
	public void createUserTest() {
		User user = new User();
		user.setName("Moon Maybe Moon");
		classUnderTest.addUser(user);

		assertThat(classUnderTest.getAllUsers(),
				Matchers.containsInAnyOrder(TEST_USER, user));
	}
	     

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	 
	 */
}
