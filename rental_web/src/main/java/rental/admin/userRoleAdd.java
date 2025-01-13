package rental.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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
import rental.entities.UserRole;

@Named
@ViewScoped
public class userRoleAdd implements Serializable {
	private UserRole userRole = new UserRole();
	private UserRole loaded = null;
	
	@Inject
	FacesContext context;
	@Inject
	Flash flash;
	@EJB
	UserRoleDAO userRoleDAO;
	@EJB
	RoleDAO roleDAO;
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void loadData() throws IOException {
		loaded = (UserRole) flash.get("userRole");
		
		if (loaded != null) {
			userRole = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}
	
	public String saveToDb() {
		if (loaded == null) {
			return null; // zostan na tej samej stronie
		}
		
		try {
			if (userRole.getIdUserRole() == null) { 
				// new record
				userRoleDAO.create(userRole);
			} else {
				// existing record
				userRoleDAO.merge(userRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null; // zostan na tej samej stronie
		}

		return "adminPanel?faces-redirect=true";
	}
	
	public List<Role> getRolesThatUserDontHave() {
		return roleDAO.getRolesThatUserDontHave(userRole.getUser().getIdUser());
	}
}
