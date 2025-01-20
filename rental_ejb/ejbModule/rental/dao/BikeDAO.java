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
	
	public void create(Bike bike) {  //insert
		em.persist(bike);
	}

	public Bike merge(Bike bike) {  //update
		return em.merge(bike);
	}

	public void remove(Bike bike) {  //delete
		em.remove(em.merge(bike));
	}

	public Bike find(Object id) {  //get
		return em.find(Bike.class, id);
	}
	
	public List<Bike> getFullList() {
		List<Bike> list = null;

		Query query = em.createQuery("select b from Bike b WHERE b.isActive = 1");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Bike> getListWithParams(Map<String, Object> searchParams) {
		List<Bike> list = null;

		 
		
		Query query = bikesWithParamsBody(searchParams, false);  // false bo chce dostac zwyklego selecta a nie count :)
		

		 

		 
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public long howManyBikesHaveType(int idType) {
		Query query = em.createQuery("SELECT COUNT(b) FROM Bike b WHERE b.typesOfBike.idType = :idType AND b.isActive = 1");
		
		query.setParameter("idType", idType);
		
		Long count = (Long) query.getSingleResult();
		
		return count;
	}
	
	
	
	
	//--------------- LAZY LOADING ------------------
	 
	
	
	
	
	public List<Bike> findRange(int first, int pageSize, Map<String, Object> searchParams) {
		List<Bike> list = null;
		
		Query query = bikesWithParamsBody(searchParams, false);  // false bo chce dostac zwyklego selecta a nie count :)
		
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		
		System.out.println("(Z bikeDAO) Pierwszy rekord = " + first + ", rozmiar = " + pageSize);  
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public long count(Map<String, Object> searchParams) {
		 
		
		Query query = bikesWithParamsBody(searchParams, true);  //true bo chce counta :)
		
		return (Long) query.getSingleResult();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Query bikesWithParamsBody(Map<String, Object> searchParams, boolean isCountWhatYouWant) { 
		
		String select;
		
		if(isCountWhatYouWant == true) {
			select = "SELECT COUNT(b) ";
		} else {
			select = "select b ";
		}
		
		String from = "from Bike b ";
		String where = "where b.isActive = 1 ";
		String orderby = "order by b.model asc, b.bikeCondition";

		 
		String model = (String) searchParams.get("model");
		
		if (model != null) {
			where += "and b.model like :model ";
		}
		
		
		String idTypeStr = (String) searchParams.get("idType");
		Integer idType = null;
		
		if (idTypeStr != null) {
		    try {
		         
		        idType = Integer.parseInt(idTypeStr);

		         
		        where += "and b.typesOfBike.idType = :idType ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid bikeCondition value: " + idTypeStr);
		    }
		}
		
		String bikeConditionStr = (String) searchParams.get("bikeCondition");
		Integer bikeCondition = null;
		
		if (bikeConditionStr != null) {
		    try {
		         
		        bikeCondition = Integer.parseInt(bikeConditionStr);

		         
		        where += "and b.bikeCondition = :bikeCondition ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid bikeCondition value: " + bikeConditionStr);
		    }
		}
		 

		 
		Query query = em.createQuery(select + from + where + orderby);

		 
		if (model != null) {
			query.setParameter("model", model+"%");
		}
		
		if (idType != null) {
			query.setParameter("idType", idType);
		}
		
		if (bikeCondition != null) {  
			query.setParameter("bikeCondition", bikeCondition);
		}
		
		return query;
	}
}
