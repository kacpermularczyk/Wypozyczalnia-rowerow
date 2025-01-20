package rental.all;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.RentalDAO;
import rental.dao.RoleDAO;
import rental.dao.UserDAO;
import rental.dao.UserRoleDAO;
import rental.entities.User;
import rental.entities.UserRole;

@Named
@ViewScoped
public class Registration implements Serializable {
	
	@Inject
	FacesContext context;
	
	@EJB
	UserDAO userDAO;
	@EJB
	RentalDAO rentalDAO;
	@EJB
	UserRoleDAO userRoleDAO;
	@EJB
	RoleDAO roleDAO;
	
	private String passwordRep;
	private User user = new User();
	
	private UserRole userRole = new UserRole(); // po to zeby dalo sie przypisac role nowemu userowi
	
	public User getUser() {
		return user;
	}
	
	public String getPasswordRep() {
		return passwordRep;
	}
	public void setPasswordRep(String passwordRep) {
		this.passwordRep = passwordRep;
	}
	
	
	
	//------------------------------------------
	
	
	
	public boolean checkIfPasswordsAreTheSame() {
		if(user.getPassword().equals(getPasswordRep())) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła się nie zgadzają", null));
			return false;
		}
	}
	
	public boolean checkPasswordAndLoginStructure(String password, String login) {
		String regex = "^(?=.*[A-Z])(?=.*\\d).{5,30}$";
		
		if(password.matches(regex) && login.matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła i login muszą mieć od 5 do 30 znaków, co najmniej jedną cyfrę i wielką literę", null));
			return false;
		}
	}
	
	public boolean checkFirstNameAndSurnameStructure(String firstName, String surname) {
		String regex = "^[A-Z][a-zA-Z]{2,29}$";
		
		if(firstName.matches(regex) && surname.matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imie i nazwisko musi być z duzej litery i mieć od 3 do 30 znaków", null));
			return false;
		}
	}
	
	public boolean checkE_mailStructure(String e_mail) {
		String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@])(?=.*[\\.]).{5,30}$";
		
		if(e_mail.matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail musi miec od 5 do 30 znakow i zawierac duza litere, cyfre @ i .", null));
			return false;
		}
	}
	
	public boolean checkIfLoginExistInDb(String login) { // sprawdz loginy AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(login.equals(userAccount.getLogin())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki login juz istnieje", null));
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfE_mailExistInDb(String e_mail) { // sprawdz emaile AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(e_mail.equals(userAccount.getE_mail())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki e-mail juz istnieje", null));
				return false;
			}
		}
		return true;
	}
	
	//---------------------------------------------------
	
	public void saveToDb() {
		
		try {
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = formatter.format(now);
			Date resultDate = formatter.parse(formattedDate);
            
            user.setIsActive(1);
            user.setWhenModified(resultDate);
    		user.setUser(user);
    		userDAO.create(user);
    		
    		userRole.setUser(userDAO.getUserByHisLogin(user.getLogin())); //od tad dodawanie roli user dla nowego usera
			userRole.setRole(roleDAO.getRoleIdByRoleName("user")); // wyszykaj po user
			userRole.setIsActive(1);
			userRole.setActiveSince(resultDate);
			userRoleDAO.create(userRole);
    		
        } catch (ParseException e) {
        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
        }
	}
	
	public String validation() {
		if(checkIfPasswordsAreTheSame() && checkPasswordAndLoginStructure(user.getPassword(), user.getLogin()) && checkFirstNameAndSurnameStructure(user.getFirstName(), user.getSurname()) && checkE_mailStructure(user.getE_mail()) && checkIfLoginExistInDb(user.getLogin()) && checkIfE_mailExistInDb(user.getE_mail())) {
			saveToDb();
			return "offer?faces-redirect=true";
		} else {
			return null;
		}
	}
}