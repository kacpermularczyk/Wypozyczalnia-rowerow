package rental.admin;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.RoleDAO;
import rental.dao.UserDAO;
import rental.dao.UserRoleDAO;
import rental.entities.Role;
import rental.entities.User;
import rental.entities.UserRole;

@Named
@ViewScoped
public class UserRoleList implements Serializable {
	
	@EJB
	UserRoleDAO userRoleDAO;
	@EJB
	RoleDAO roleDAO;
	@EJB
	UserDAO userDAO;
	@Inject
	Flash flash;
	
	private String surname;
	private String e_mail;
	private String idRole;
	private String isActiveRole;
	private String isActiveUser;
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	
	public String getE_mail() {
		return e_mail;
	}
	
	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}
	
	public String getIdRole() {
		return idRole;
	}
	
	public void setIsActiveRole(String isActiveRole) {
		this.isActiveRole = isActiveRole;
	}
	
	public String getIsActiveRole() {
		return isActiveRole;
	}
	
	public void setIsActiveUser(String isActiveUser) {
		this.isActiveUser = isActiveUser;
	}
	
	public String getIsActiveUser() {
		return isActiveUser;
	}
	
	
	
	
	
	

	public List<UserRole> getUserRoleListWithParams() {
		List<UserRole> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}
		
		if (e_mail != null && e_mail.length() > 0) {
			searchParams.put("e_mail", e_mail);
		}
		
		if (idRole != null && idRole.length() > 0) {
			searchParams.put("idRole", idRole);
		}
		
		if (isActiveRole != null && isActiveRole.length() > 0) {
			searchParams.put("isActiveRole", isActiveRole);
		}
		
		if (isActiveUser != null && isActiveUser.length() > 0) {
			searchParams.put("isActiveUser", isActiveUser);
		}
		
		list = userRoleDAO.getUserRoleListWithParams(searchParams);
		
		return list;
	}
	
	public String printIsActiveAsString(int isActive) {
		if(isActive == 1) {
			return "TAK";
		} else {
			return "NIE";
		}
	}
	
	public String printActiveSinceAsStringIfNull(Date activeUntil) {
		if(activeUntil == null) {
			return "TERAZ";
		} else {
			return activeUntil + "";
		}
	}
	
	public List<Role> getActiveRoles() {
		return roleDAO.getActiveRoles();
	}
	
	public void turnOffUser(UserRole ur) {
		try {
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = formatter.format(now);
			Date resultDate = formatter.parse(formattedDate);
			
			userRoleDAO.removeAllUserRoles(ur, resultDate);
			
			ur.getUser().setIsActive(0);
			userDAO.merge(ur.getUser());
			
		} catch (ParseException e) {
			System.out.println("Błąd parsowania daty: " + e.getMessage());
		}
	}
	
	public void turnOffUserRole(UserRole ur) {
		
		try {
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = formatter.format(now);
			Date resultDate = formatter.parse(formattedDate);
            
            ur.setActiveUntil(resultDate);
            ur.setIsActive(0);
    		userRoleDAO.merge(ur);
        } catch (ParseException e) {
            System.out.println("Błąd parsowania daty: " + e.getMessage());
        }
	}
	
	public String newUserRole(UserRole ur){
		UserRole userRole = new UserRole();
		
		userRole.setRole(ur.getRole());
		
		userRole.setUser(ur.getUser());
		
		userRole.setIsActive(1);
		
		try {
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = formatter.format(now);
			Date resultDate = formatter.parse(formattedDate);
            
            userRole.setActiveSince(resultDate);
        } catch (ParseException e) {
            System.out.println("Błąd parsowania daty: " + e.getMessage());
        }
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("userRole", userRole);
		
		return "userRoleAdd?faces-redirect=true";
	}
	
	public long getAmountOfRolesUserDoesNotHave(UserRole ur) {
		return roleDAO.getAmountOfRolesUserDoesNotHave(ur.getUser().getIdUser());
	}
}
