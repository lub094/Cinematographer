package com.cinematographer.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.reservation.manager.IReservationService;
import com.cinematographer.core.reservation.manager.ReservationService;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.screening.service.ScreeningService;
import com.cinematographer.core.user.manager.IUserService;
import com.cinematographer.core.user.manager.UserService;

public class Bootstrap extends Application {

	private static final String PERSISTENCE_UNIT_NAME = "com.cinematographer.jpa";

	@Override
	@SuppressWarnings("unchecked")
	public Set<Class<?>> getClasses() {
		registerServices();
		return new HashSet<Class<?>>(Arrays.asList(ScreeningRestApi.class));
	}

	private void registerServices() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		IServiceManager manager = ServiceManager.getInstance();

		manager.registerService(IScreeningService.class, new ScreeningService(emf));
		manager.registerService(IReservationService.class, new ReservationService(emf));
		manager.registerService(IUserService.class, new UserService(emf));
	}
}
