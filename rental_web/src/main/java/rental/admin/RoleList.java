package rental.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.RoleDAO;
import rental.dao.UserRoleDAO;
import rental.entities.Role;

@Named
@ViewScoped
public class RoleList implements Serializable {

	@EJB
	RoleDAO roleDAO;
	@EJB
	UserRoleDAO userRoleDAO;
	@Inject
	FacesContext context;
	@Inject
	Flash flash;
	
	private String role;
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public List<Role> getRoleListWithParams() {
		List<Role> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (role != null && role.length() > 0) {
			searchParams.put("role", role);
		}
		
		list = roleDAO.getRoleListWithParams(searchParams);
		
		return list;
	}
	
	public void turnOffRole(Role r) {
		if(userRoleDAO.howManyUsersHaveRole(r.getIdRole()) == 0) {
			r.setIsActive(0);
			roleDAO.merge(r);
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieją usery o tej roli", null));
		}
	}
	
	public String newRole(){
		Role r = new Role();
		
		r.setIsActive(1);
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("role", r);
		
		return "roleEdit?faces-redirect=true";
	}
	
	public String editRole(Role r){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("role", r);
		
		return "roleEdit?faces-redirect=true";
	}
}
