package rental.all;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import rental.dao.UserDAO;
import rental.dao.UserRoleDAO;
import rental.entities.User;
import rental.entities.UserRole;

@Named
@ViewScoped
public class Login implements Serializable {

	@Inject
	FacesContext context;
	
	@EJB
	UserDAO userDAO;
	@EJB
	UserRoleDAO userRoleDAO;
	
	private String login;
	private String password;
	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String loginToSystem() {
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(login.equals(userAccount.getLogin())) {
				if(password.equals(userAccount.getPassword())) {
					
					RemoteClient<User> client = new RemoteClient<User>(); //create new RemoteClient
					client.setDetails(userAccount);
					
					List<String> roles = userRoleDAO.getUserActiveRolesById(userAccount.getIdUser());
					
					if(roles != null) {
						for(String role : roles) {
							client.getRoles().add(role);
						}
					}
					
					HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
					client.store(request);
					
					return "offer?faces-redirect=true";
					
				} else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieprawidłowe dane logowania", null));
					return null;
				}
			}
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieprawidłowe dane logowania", null));
		return null;
	}
	
	public String logOut() {
		HttpSession session = (HttpSession) context.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return "offer?faces-redirect=true";
	}
}
