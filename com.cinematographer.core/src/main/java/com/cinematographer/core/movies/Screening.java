package com.cinematographer.core.movies;

import java.sql.Time;
import java.util.Collection;

public class Screening {
	private String title;
	private Time startTime;
	private Time duration;
	private Collection<Seat> seats;
	private int hall;
}
