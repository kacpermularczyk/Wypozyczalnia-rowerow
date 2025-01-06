package rental.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.Role;

@Stateless
public class RoleDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(Role role) { //insert
		em.persist(role);
	}

	public Role merge(Role role) { //update
		return em.merge(role);
	}

	public void remove(Role role) { //delete
		em.remove(em.merge(role));
	}

	public Role find(Object id) { //get
		return em.find(Role.class, id);
	}
	
	public List<Role> getActiveRoles() {
		List<Role> list = null;

		Query query = em.createQuery("select r from Role r WHERE r.isActive = 1");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Role> getRoleListWithParams(Map<String, Object> searchParams) {
		List<Role> list = null;
		
		String select = "select r ";
		String from = "from Role r ";
		String where = "where r.isActive = 1 ";
		String orderby = "order by r.role asc";
		
		String role = (String) searchParams.get("role");
		
		if (role != null) {
			where += "and r.role like :role ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (role != null) {
			query.setParameter("role", role+"%");
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Role> getRolesThatUserDontHave(int idUser) {
		List<Role> list = null;

		Query query = em.createQuery("SELECT r FROM Role r WHERE r.idRole NOT IN (SELECT ur.role.idRole FROM UserRole ur WHERE ur.user.idUser = :idUser AND ur.isActive = 1) AND r.isActive = 1");

		
		query.setParameter("idUser", idUser);
		
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public long getAmountOfUserActualRoles(int idUser) {
		Query query = em.createQuery("SELECT (SELECT COUNT(r) FROM Role r) - (SELECT COUNT(ur) FROM UserRole ur WHERE ur.user.idUser = :idUser AND ur.isActive = 1)");
		
		query.setParameter("idUser", idUser);
		
		Long count = (Long) query.getSingleResult();
		
		return count;
	}
}
