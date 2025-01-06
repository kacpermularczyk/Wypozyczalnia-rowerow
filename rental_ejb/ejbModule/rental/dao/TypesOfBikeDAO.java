package rental.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.TypesOfBike;

@Stateless
public class TypesOfBikeDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(TypesOfBike typesOfBike) { //insert
		em.persist(typesOfBike);
	}

	public TypesOfBike merge(TypesOfBike typesOfBike) { //update
		return em.merge(typesOfBike);
	}

	public void remove(TypesOfBike typesOfBike) { //delete
		em.remove(em.merge(typesOfBike));
	}

	public TypesOfBike find(Object id) { //get
		return em.find(TypesOfBike.class, id);
	}
	
	public List<TypesOfBike> getFullList() {
		List<TypesOfBike> list = null;

		Query query = em.createQuery("select t from TypesOfBike t WHERE t.isActive = 1");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<TypesOfBike> getTypesListWithParams(Map<String, Object> searchParams) {
		List<TypesOfBike> list = null;
		
		String select = "select t ";
		String from = "from TypesOfBike t ";
		String where = "where t.isActive = 1 ";
		String orderby = "order by t.type asc";
		
		String type = (String) searchParams.get("type");
		
		if (type != null) {
			where += "and t.type like :type ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (type != null) {
			query.setParameter("type", type+"%");
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
