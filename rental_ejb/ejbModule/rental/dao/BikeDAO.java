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
		
		if (model != null) {
			where += "and b.model like :model ";
		}
		
		
		String idTypeStr = (String) searchParams.get("idType");
		Integer idType = null;
		
		if (idTypeStr != null) {
		    try {
		        // Parsowanie String na Integer
		        idType = Integer.parseInt(idTypeStr);

		        // Jeżeli konwersja się powiedzie, dodajemy warunek do zapytania
		        where += "and b.typesOfBike.idType = :idType ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid bikeCondition value: " + idTypeStr);
		    }
		}
		
		String bikeConditionStr = (String) searchParams.get("bikeCondition");
		Integer bikeCondition = null;
		
		if (bikeConditionStr != null) {
		    try {
		        // Parsowanie String na Integer
		        bikeCondition = Integer.parseInt(bikeConditionStr);

		        // Jeżeli konwersja się powiedzie, dodajemy warunek do zapytania
		        where += "and b.bikeCondition = :bikeCondition ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid bikeCondition value: " + bikeConditionStr);
		    }
		}
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (model != null) {
			query.setParameter("model", model+"%");
		}
		
		if (idType != null) {
			query.setParameter("idType", idType);
		}
		
		if (bikeCondition != null) { //qwe
			query.setParameter("bikeCondition", bikeCondition);
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
