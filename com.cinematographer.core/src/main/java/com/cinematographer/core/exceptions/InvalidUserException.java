/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinematographer.core.exceptions;

/**
 *
 * @author Aleksandar
 */
public class InvalidUserException extends RuntimeException {

	private static final long serialVersionUID = 6478427511766868942L;

	public InvalidUserException() {
	}

	public InvalidUserException(String msg) {
		super(msg);
	}
}
