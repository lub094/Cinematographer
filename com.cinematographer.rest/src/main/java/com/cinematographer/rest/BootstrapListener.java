package com.cinematographer.rest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.reservation.IReservationService;
import com.cinematographer.core.reservation.ReservationService;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.screening.service.ScreeningService;
import com.cinematographer.core.user.manager.IUserService;
import com.cinematographer.core.user.manager.UserService;

@WebListener
public class BootstrapListener implements ServletContextListener {

	private static final String PERSISTENCE_UNIT_NAME = "com.cinematographer.jpa";
	private EntityManagerFactory emf;

	public void contextDestroyed(ServletContextEvent ctx) {
		emf.close();
	}

	public void contextInitialized(ServletContextEvent ctx) {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		IServiceManager manager = ServiceManager.getInstance();

		manager.registerService(IScreeningService.class, new ScreeningService(emf));
		manager.registerService(IReservationService.class, new ReservationService(emf));
		manager.registerService(IUserService.class, new UserService(emf));
	}
}
