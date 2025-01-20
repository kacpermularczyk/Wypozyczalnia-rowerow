package rental.dao;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.User;

@Stateless
public class UserDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(User user) { //insert
		em.persist(user);
	}

	public User merge(User user) { //update
		return em.merge(user);
	}

	public void remove(User user) { //delete
		em.remove(em.merge(user));
	}

	public User find(Object id) { //get
		return em.find(User.class, id);
	}
	
	public List<User> getActiveUsers() {
		List<User> list = null;

		Query query = em.createQuery("SELECT u FROM User u WHERE u.isActive = 1");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public User getUserByHisLogin(String login) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.isActive = 1 AND u.login = :login");
		
		query.setParameter("login", login);
		
		User user = (User) query.getSingleResult();
		
		return user;
	}
}
