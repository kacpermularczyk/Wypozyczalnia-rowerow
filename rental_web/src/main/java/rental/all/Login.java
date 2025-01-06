package rental.all;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.UserDAO;
import rental.entities.User;

@Named
@ViewScoped
public class Login implements Serializable {

	@Inject
	FacesContext context;
	
	@EJB
	UserDAO userDAO;
	
	private String login;
	private String password;
	
	private int generate = 0; //usunac
	
	public int getGenerate() {
		return generate;
	}
	
	
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
	
	public void loginToSystem() {
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(login.equals(userAccount.getLogin())) {
				if(password.equals(userAccount.getPassword())) {
					generate = 1; // usunac
				} else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieprawidłowe dane logowania", null));
				}
			}
		}
	}
}
