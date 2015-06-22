package com.cinematographer.rest.utils;

public class ErrorMessage {

	private String message;

	public ErrorMessage(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
