package com.cinematographer.core.reservation;

public class ReservationException extends RuntimeException {

	private static final long serialVersionUID = -6489728263541536112L;

	public ReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservationException(String message) {
		super(message);
	}

	public ReservationException(Throwable cause) {
		super(cause);
	}

}
