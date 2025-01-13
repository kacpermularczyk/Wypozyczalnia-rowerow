package rental.admin;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.RoleDAO;
import rental.entities.Role;

@Named
@ViewScoped
public class RoleEdit implements Serializable {
	private Role role = new Role();
	private Role loaded = null;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	RoleDAO roleDAO;
	
	public Role getRole() {
		return role;
	}
	
	public void loadData() throws IOException {
		loaded = (Role) flash.get("role");
		
		if (loaded != null) {
			role = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}
	
	public String saveToDb() {
		if (loaded == null) {
			return null; // zostan na tej samej stronie
		}
		
		try {
			if (role.getIdRole() == null) { 
				// new record
				roleDAO.create(role);
			} else {
				// existing record
				roleDAO.merge(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null; // zostan na tej samej stronie
		}

		return "adminPanel?faces-redirect=true";
	}
}
