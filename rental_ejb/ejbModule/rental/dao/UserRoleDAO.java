package rental.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import rental.entities.UserRole;

@Stateless
public class UserRoleDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(UserRole userRole) { //insert
		em.persist(userRole);
	}

	public UserRole merge(UserRole userRole) { //update
		return em.merge(userRole);
	}

	public void remove(UserRole userRole) { //delete
		em.remove(em.merge(userRole));
	}

	public UserRole find(Object id) { //get
		return em.find(UserRole.class, id);
	}
	
	public long howManyUsersHaveRole(int idRole) {
		Query query = em.createQuery("SELECT COUNT(ur) FROM UserRole ur WHERE ur.role.idRole = :idRole AND ur.role.isActive = 1");
		
		query.setParameter("idRole", idRole);
		
		Long count = (Long) query.getSingleResult();
		
		return count;
	}
	
	public List<UserRole> getUserRoleListWithParams(Map<String, Object> searchParams) {
		List<UserRole> list = null;
		
		String select = "select ur ";
		String from = "from UserRole ur ";
		String where = "where ur.idUserRole > 0 "; //bez tego trzeba by bylo dodawac za kazdym razem ifa i spradzac czy juz jest where (and where albo where)
		String orderby = "order by ur.user.surname asc";
		
		String surname = (String) searchParams.get("surname");
		
		if (surname != null) {
			if(where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "ur.user.surname like :surname ";
		}
		
		String e_mail = (String) searchParams.get("e_mail");
		
		if (e_mail != null) {
			if(where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "ur.user.e_mail like :e_mail ";
		}
		
		String idRoleStr = (String) searchParams.get("idRole");
		Integer idRole = null;
		
		if (idRoleStr != null) {
		    try {
		    	idRole = Integer.parseInt(idRoleStr);
		    	
		    	if(where.isEmpty()) {
					where = "where ";
				} else {
					where += "and ";
				}
		        where += "ur.role.idRole = :idRole ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid returnedInTerm value: " + idRoleStr);
		    }
		}
		
		String isActiveRoleStr = (String) searchParams.get("isActiveRole");
		Integer isActiveRole = null;
		
		if (isActiveRoleStr != null) {
		    try {
		    	isActiveRole = Integer.parseInt(isActiveRoleStr);

		    	if(where.isEmpty()) {
					where = "where ";
				} else {
					where += "and ";
				}
		        where += "ur.isActive = :isActiveRole ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid returnedInTerm value: " + isActiveRoleStr);
		    }
		}
		
		String isActiveUserStr = (String) searchParams.get("isActiveUser");
		Integer isActiveUser = null;
		
		if (isActiveUserStr != null) {
		    try {
		    	isActiveUser = Integer.parseInt(isActiveUserStr);

		    	if(where.isEmpty()) {
					where = "where ";
				} else {
					where += "and ";
				}
		        where += "ur.user.isActive = :isActiveUser ";
		    } catch (NumberFormatException e) {
		        System.err.println("Invalid returnedInTerm value: " + isActiveUserStr);
		    }
		}
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}
		
		if (e_mail != null) {
			query.setParameter("e_mail", e_mail+"%");
		}
		
		if (idRole != null) {
			query.setParameter("idRole", idRole);
		}
		
		if (isActiveRole != null) {
			query.setParameter("isActiveRole", isActiveRole);
		}
		
		if (isActiveUser != null) {
			query.setParameter("isActiveUser", isActiveUser);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
