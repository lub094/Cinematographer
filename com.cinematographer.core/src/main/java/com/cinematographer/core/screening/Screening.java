package com.cinematographer.core.screening;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class Screening {

	@Id
	private String title;
	private String startTime;
	private int duration;
	private int hall;
	private double price;
	@ElementCollection(fetch = FetchType.LAZY)
	private List<Seat> seats;

	public Screening() {

	}

	public Screening(String title, int duration, int hall, List<Seat> seats) {
		this(title, ""/*new Time(System.currentTimeMillis())*/, duration, hall, 0.0,
				seats);
	}

	public Screening(String title, int duration, int hall) {
		this(title, "", duration, hall, 0.0,
				new ArrayList<Seat>());
	}

	public Screening(String title, String startTime, int duration, int hall,
			double price, List<Seat> seats) {
		this.title = title;
		this.startTime = startTime;
		this.duration = duration;
		this.price = price;
		this.hall = hall;
		this.seats = seats;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getHall() {
		return hall;
	}

	public void setHall(int hall) {
		this.hall = hall;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + hall;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Screening other = (Screening) obj;
		if (duration != other.duration)
			return false;
		if (hall != other.hall)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public void setStatus(List<String> seats, Status status) {
		Set<String> selectedSeats = new HashSet<String>(seats);
		for (Seat seat : getSeats()) {
			if (selectedSeats.contains(seat.getNumber())) {
				seat.setStatus(status);
			}
		}
	}
}
