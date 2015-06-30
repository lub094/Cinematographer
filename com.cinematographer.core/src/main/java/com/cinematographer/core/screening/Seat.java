package com.cinematographer.core.screening;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
public class Seat {
	private String number;
	private boolean wasUsed;
	@Enumerated(EnumType.STRING)
	private Status status;

	public Seat() {

	}

	public Seat(String number) {
		this(number, false, Status.FREE);
	}

	public Seat(String number, boolean wasUsed, Status status) {
		this.number = number;
		this.wasUsed = wasUsed;
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isWasUsed() {
		return wasUsed;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setWasUsed(boolean wasUsed) {
		this.wasUsed = wasUsed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + (wasUsed ? 1231 : 1237);
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
		Seat other = (Seat) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (status != other.status)
			return false;
		if (wasUsed != other.wasUsed)
			return false;
		return true;
	}

}
