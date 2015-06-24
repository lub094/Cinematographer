package com.cinematographer.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;

public class DatabaseTest {

	private static final String ENTITY_MANAGER_NAME = "com.cinematographer.test-db";
	protected EntityManagerFactory emf;

	@Before
	public void setUp() {
		emf = createEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		em.createQuery("DELETE FROM Screening s").executeUpdate();
		em.createQuery("DELETE FROM Seat s").executeUpdate();
                //em.createQuery("DELETE FROM User s").executeUpdate();
		transaction.commit();
	}

	private EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(ENTITY_MANAGER_NAME);
	}

}
