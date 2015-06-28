package com.cinematographer.core.reservation;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.user.UserProfile;

@Entity
@Table
@NamedQuery(name = Reservation.SELECT_USER_RESERVATIONS, query = Reservation.SELECT_USERNAME_RESERVATIONS_QUERY)
public class Reservation {

	public static final String USERNAME_PARAMETER = "username";
	public static final String SELECT_USER_RESERVATIONS = "Reservation.findAllForUsername";
	static final String SELECT_USERNAME_RESERVATIONS_QUERY = "SELECT r FROM Reservation r WHERE r.owner.name = :username";

	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	private UserProfile owner;
	@OneToOne
	private Screening screening;
	private List<String> seats;

	public Reservation() {

	}

	public Reservation(UserProfile owner, Screening screening, String... seats) {
		this(owner, screening, Arrays.asList(seats));
	}

	public Reservation(UserProfile owner, Screening screening,
			List<String> seats) {
		this.owner = owner;
		this.screening = screening;
		this.seats = seats;
	}

	public Screening getScreening() {
		return screening;
	}

	public void setScreening(Screening screening) {
		this.screening = screening;
	}

	public UserProfile getOwner() {
		return owner;
	}

	public void setOwner(UserProfile owner) {
		this.owner = owner;
	}

	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((screening == null) ? 0 : screening.hashCode());
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (screening == null) {
			if (other.screening != null)
				return false;
		} else if (!screening.equals(other.screening))
			return false;
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		return true;
	}

}
