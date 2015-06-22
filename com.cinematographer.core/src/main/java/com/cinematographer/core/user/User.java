package com.cinematographer.core.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.cinematographer.core.screening.Reservation;

@Entity
public class User {
	@Id
	@NotNull
	private String name;
	private String password;
	@OneToMany
	private Collection<Reservation> reservations;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
	}

	public User(String name, String password,
			Collection<Reservation> reservations, Role role) {
		this.name = name;
		this.password = password;
		this.reservations = reservations;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
