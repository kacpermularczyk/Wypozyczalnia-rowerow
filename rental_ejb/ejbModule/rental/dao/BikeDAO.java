package rental.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.Bike;

@Stateless
public class BikeDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(Bike bike) { //insert
		em.persist(bike);
	}

	public Bike merge(Bike bike) { //update
		return em.merge(bike);
	}

	public void remove(Bike bike) { //delete
		em.remove(em.merge(bike));
	}

	public Bike find(Object id) { //get
		return em.find(Bike.class, id);
	}
	
	public List<Bike> getFullList() {
		List<Bike> list = null;

		Query query = em.createQuery("select b from Bike b");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Bike> getListWithParams(Map<String, Object> searchParams) {
		List<Bike> list = null;

		// 1. Build query string with parameters
		
		String select = "select b ";
		String from = "from Bike b ";
		String where = "where b.isActive = 1 ";
		String orderby = "order by b.model asc, b.bikeCondition";

		// search for surname
		String model = (String) searchParams.get("model");
		String bikeCondition = (String) searchParams.get("bikeCondition"); //qwe
		
		if (model != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "b.model like :model ";
		}
		
		if (bikeCondition != null) { //qwe
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "b.bikeCondition like :bikeCondition ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (model != null) {
			query.setParameter("model", model+"%");
		}
		
		if (bikeCondition != null) { //qwe
			query.setParameter("bikeCondition", bikeCondition+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
