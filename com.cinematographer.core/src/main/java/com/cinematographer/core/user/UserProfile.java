package com.cinematographer.core.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cinematographer.core.screening.Reservation;

@Entity
public class UserProfile {
	@Id
	private String name;
	@OneToMany
	private Collection<Reservation> reservations;
	@Enumerated(EnumType.STRING)
	private Role role;

	public UserProfile() {
	}

	public UserProfile(String name, String password, Collection<Reservation> reservations, Role role) {
		this.name = name;
		this.reservations = reservations;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public Role getRole() {
		return role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reservations == null) ? 0 : reservations.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		UserProfile other = (UserProfile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reservations == null) {
			if (other.reservations != null)
				return false;
		} else if (!reservations.equals(other.reservations))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
}
