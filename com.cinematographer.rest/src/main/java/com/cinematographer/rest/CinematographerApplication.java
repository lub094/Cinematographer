package com.cinematographer.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.cinematographer.rest.services.ReservationRestAPI;
import com.cinematographer.rest.services.ScreeningRestApi;
import com.cinematographer.rest.services.UserRestAPI;

public class CinematographerApplication extends Application {

	@Override
	@SuppressWarnings("unchecked")
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(ScreeningRestApi.class, UserRestAPI.class, ReservationRestAPI.class));
	}

}
