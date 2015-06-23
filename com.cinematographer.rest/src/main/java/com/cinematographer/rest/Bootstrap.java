package com.cinematographer.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.screening.service.ScreeningService;

public class Bootstrap extends Application {

	private static final String PERSISTENCE_UNIT_NAME = "com.cinematographer.jpa";

	@Override
	@SuppressWarnings("unchecked")
	public Set<Class<?>> getClasses() {
		registerServices();
		return new HashSet<Class<?>>(Arrays.asList(ScreeningRestApi.class));
	}

	private void registerServices() {
		IServiceManager manager = ServiceManager.getInstance();
		manager.registerService(IScreeningService.class, new ScreeningService(
				Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)));
	}
}
