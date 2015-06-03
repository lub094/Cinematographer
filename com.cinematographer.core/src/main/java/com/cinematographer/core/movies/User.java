package com.cinematographer.core.movies;

import java.util.Collection;
import java.util.List;

public class User {
	private String name;
	private String password;
	private Collection<Reservation> reservations;
	private Collection<Role> roles;
}
