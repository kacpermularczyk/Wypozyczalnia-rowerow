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
import rental.dao.UserDAO;
import rental.entities.User;

@Named
@ViewScoped
public class Registration implements Serializable {
	
	@Inject
	FacesContext context;
	
	@EJB
	UserDAO userDAO;
	
	private String passwordRep;
	private User user = new User();
	
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
	
	public boolean checkPasswordAndLoginStructure() {
		String regex = "^(?=.*[A-Z])(?=.*\\d).{5,30}$";
		
		if(user.getPassword().matches(regex) && user.getLogin().matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła i login muszą mieć od 5 do 30 znaków, co najmniej jedną cyfrę i wielką literę", null));
			return false;
		}
	}
	
	public boolean checkFirstNameAndSurnameStructure() {
		String regex = "^[A-Z][a-zA-Z]{2,29}$";
		
		if(user.getFirstName().matches(regex) && user.getSurname().matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imie i nazwisko musi być z duzej litery i mieć od 3 do 30 znaków", null));
			return false;
		}
	}
	
	public boolean checkE_mailStructure() {
		String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@])(?=.*[\\.]).{5,30}$";
		
		if(user.getE_mail().matches(regex)) {
			return true;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail musi miec od 5 do 30 znakow i zawierac duza litere, cyfre @ i .", null));
			return false;
		}
	}
	
	public boolean checkIfLoginExistInDb() { // sprawdz loginy AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(user.getLogin().equals(userAccount.getLogin())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki login juz istnieje", null));
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfE_mailExistInDb() { // sprawdz emaile AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(user.getE_mail().equals(userAccount.getE_mail())) {
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
        } catch (ParseException e) {
        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
        }
	}
	
	public String validation() {
		if(checkIfPasswordsAreTheSame() && checkPasswordAndLoginStructure() && checkFirstNameAndSurnameStructure() && checkE_mailStructure() && checkIfLoginExistInDb() && checkIfE_mailExistInDb()) {
			saveToDb();
			return "offer?faces-redirect=true"; //zmienic pozniej na strone logowania
		} else {
			return null;
		}
	}
}