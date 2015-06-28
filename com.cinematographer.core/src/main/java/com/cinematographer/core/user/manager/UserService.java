package com.cinematographer.core.user.manager;

import com.cinematographer.core.exceptions.InvalidUserException;
import com.cinematographer.core.exceptions.UnauthorizedException;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class UserService implements IUserService {
	private static final String INVALID_USERNAME_ERROR = "Wrong username %s";
	private static final String INVALID_PASSWORD_ERROR = "Wrong password";
	private static final String UNAUTHORIZED_USER_ERROR = "Unauthorized action for user %s";

	private EntityManagerFactory emf;

	public UserService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public UserProfile findUser(String name) {
		EntityManager em = emf.createEntityManager();
		return em.find(UserProfile.class, name);
	}

	public void addUser(UserProfile user) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		em.merge(user);
		transaction.commit();
	}

	public Collection<UserProfile> getAllUsers() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("SELECT s FROM User s", UserProfile.class).getResultList();
	}

	@Override
	public String getMessageDigest(String password) {
		String userPassword = new String();
		try {
			MessageDigest mda = MessageDigest.getInstance("SHA-512");
			userPassword = new String(mda.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return userPassword;
	}

	public void authenticate(String name, String password) throws InvalidUserException { // here password is hashed
		EntityManager em = emf.createEntityManager();
		UserProfile user = em.find(UserProfile.class, name);

		if (user == null)
			throw new InvalidUserException(String.format(INVALID_USERNAME_ERROR, name));

		String userPassword = new String(getMessageDigest(password));

		if (!password.equals(userPassword))
			throw new InvalidUserException(INVALID_PASSWORD_ERROR);
	}

	@Override
	public void authorize(UserProfile user, Role role) throws UnauthorizedException {
		if (!role.equals(user.getRole()))
			throw new UnauthorizedException(String.format(UNAUTHORIZED_USER_ERROR, user.getName()));
	}
}
