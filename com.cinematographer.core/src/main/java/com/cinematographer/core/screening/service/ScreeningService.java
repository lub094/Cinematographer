package com.cinematographer.core.screening.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.Status;

public class ScreeningService implements IScreeningService {

	private static final String GET_ALL_SCREENINGS_QUERY = "SELECT s FROM Screening s";
	private EntityManagerFactory emf;

	public ScreeningService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Screening findScreening(String title) {
		EntityManager em = emf.createEntityManager();
		Screening sreening = em.find(Screening.class, title);
		em.close();
		return sreening;
	}

	public Collection<Screening> getAllScreenings() {
		EntityManager em = emf.createEntityManager();
		List<Screening> screenings = em.createQuery(GET_ALL_SCREENINGS_QUERY,
				Screening.class).getResultList();
		em.close();
		return screenings;
	}

	public void addScreening(Screening screening) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		em.persist(screening);
		transaction.commit();
		em.close();
	}

	public void removeScreening(String title) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		Screening screening = em.find(Screening.class, title,
				LockModeType.PESSIMISTIC_WRITE);
		em.remove(screening);
		transaction.commit();
		em.close();
	}

	@Override
	public void updateSeatsStatus(String title, List<String> selectedSeats,
			Status status) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		Screening screening = em.find(Screening.class, title);
		screening.setStatus(selectedSeats, status);
		transaction.commit();

		em.close();
	}
}
