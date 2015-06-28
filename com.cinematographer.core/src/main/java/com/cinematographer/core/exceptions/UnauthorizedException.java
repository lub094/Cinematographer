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
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -5014229468088620784L;

	public UnauthorizedException() {
	}

	public UnauthorizedException(String msg) {
		super(msg);
	}
}
