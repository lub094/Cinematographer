package com.cinematographer.core.screening.manager;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

import com.cinematographer.core.screening.Screening;

public class ScreeningService implements IScreeningService {

	private static final String GET_ALL_SCREENINGS_QUERY = "SELECT s FROM Screening s";
	private EntityManagerFactory emf;

	public ScreeningService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Screening findScreening(String title) {
		EntityManager em = emf.createEntityManager();
		return em.find(Screening.class, title);
	}

	public Collection<Screening> getAllScreenings() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery(GET_ALL_SCREENINGS_QUERY, Screening.class)
				.getResultList();
	}

	public void addScreening(Screening screening) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		em.merge(screening);
		transaction.commit();
	}

	public void removeScreening(String title) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		Screening screening = em.find(Screening.class, title,
				LockModeType.PESSIMISTIC_WRITE);
		em.remove(screening);
		transaction.commit();
	}
}
