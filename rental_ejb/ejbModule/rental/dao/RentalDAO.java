package rental.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.Rental;

@Stateless
public class RentalDAO {
	@PersistenceContext
	EntityManager em;
	
	public void create(Rental rental) { //insert
		em.persist(rental);
	}

	public Rental merge(Rental rental) { //update
		return em.merge(rental);
	}

	public void remove(Rental rental) { //delete
		em.remove(em.merge(rental));
	}

	public Rental find(Object id) { //get
		return em.find(Rental.class, id);
	}
	
	public List<Rental> getFullList() {
		List<Rental> list = null;

		Query query = em.createQuery("select r from Rental r");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Rental> getRentalsToAcceptListWithParams(Map<String, Object> searchParams) {
		List<Rental> list = null;
		
		String select = "select r ";
		String from = "from Rental r ";
		String where = "where r.accepted IS null ";
		String orderby = "order by r.user.surname asc";

		String surname = (String) searchParams.get("surname");
		
		if (surname != null) {
			where += "and r.user.surname like :surname ";
		}
		
		String e_mail = (String) searchParams.get("e_mail");
		
		if (e_mail != null) {
			where += "and r.user.e_mail like :e_mail ";
		}
		
		String model = (String) searchParams.get("model");
		
		if (model != null) {
			where += "and r.bike.model like :model ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}
		
		if (e_mail != null) {
			query.setParameter("e_mail", e_mail+"%");
		}
		
		if (model != null) {
			query.setParameter("model", model+"%");
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<Rental> getAcceptedRentalsListWithParams(Map<String, Object> searchParams) {
		List<Rental> list = null;
		
		String select = "select r ";
		String from = "from Rental r ";
		String where = "WHERE r.accepted = 1 ";
		String orderby = "order by r.user.surname asc";

		String surname = (String) searchParams.get("surname");
		
		if (surname != null) {
			where += "and r.user.surname like :surname ";
		}
		
		String e_mail = (String) searchParams.get("e_mail");
		
		if (e_mail != null) {
			where += "and r.user.e_mail like :e_mail ";
		}
		
		String model = (String) searchParams.get("model");
		
		if (model != null) {
			where += "and r.bike.model like :model ";
		}
		
		String returnedInTermStr = (String) searchParams.get("returnedInTerm");
		Integer returnedInTerm = null;
		
		if (returnedInTermStr != null) {
		    try {
		    	returnedInTerm = Integer.parseInt(returnedInTermStr);

		        where += "and r.returnedInTerm = :returnedInTerm ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid returnedInTerm value: " + returnedInTermStr);
		    }
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}
		
		if (e_mail != null) {
			query.setParameter("e_mail", e_mail+"%");
		}
		
		if (model != null) {
			query.setParameter("model", model+"%");
		}
		
		if (returnedInTerm != null) {
			query.setParameter("returnedInTerm", returnedInTerm);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
