package rental.user;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import rental.all.Registration;
import rental.dao.RentalDAO;
import rental.dao.UserDAO;
import rental.entities.Rental;
import rental.entities.User;

@Named
@ViewScoped
public class UserPanel extends Registration implements Serializable {
	
	@Inject
	FacesContext context;
	@EJB
	RentalDAO rentalDAO;
	@EJB
	UserDAO userDAO;
	
	public List<Rental> getUserRentalsById(User user) {
		return rentalDAO.getUserRentalsById(user.getIdUser());
	}
	
	public String printReturnedInTermAsString(Integer x) {
		if(x == null) {
			return "W trakcie rezerwacji";
		} else if(x == 1) {
			return "Zwrócono w terminie :)";
		} else {
			return "Zwrócono po terminie :(";
		}
	}
	
	public boolean checkIfLoginExistInDb(User user) { // sprawdz loginy AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(user.getLogin().equals(userAccount.getLogin())) {
				if(user.getIdUser() != userAccount.getIdUser()) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki login juz istnieje", null));
					return false;
				}
				return true;
			}
		}
		return true;
	}
	
	public boolean checkIfE_mailExistInDb(User user) { // sprawdz emaile AKTYWNYCH userow
		List<User> userAccounts = userDAO.getActiveUsers();
		
		for(User userAccount : userAccounts) {
			if(user.getE_mail().equals(userAccount.getE_mail())) {
				if(user.getIdUser() != userAccount.getIdUser()) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki e-mail juz istnieje", null));
					return false;
				}
				return true;
			}
		}
		return true;
	}
	
	public String saveUserData() {
		try {
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			RemoteClient<User> client = RemoteClient.load(session);
			
			if( checkPasswordAndLoginStructure(client.getDetails().getUser().getPassword(), client.getDetails().getUser().getLogin()) && checkFirstNameAndSurnameStructure(client.getDetails().getUser().getFirstName(), client.getDetails().getUser().getSurname()) &&  checkE_mailStructure(client.getDetails().getUser().getE_mail()) && checkIfLoginExistInDb(client.getDetails().getUser()) && checkIfE_mailExistInDb(client.getDetails().getUser())) {
				Date now = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedDate = formatter.format(now);
				Date resultDate = formatter.parse(formattedDate);
				
				User user = client.getDetails().getUser();
				user.setWhenModified(resultDate);
				
				userDAO.merge(user);
				
				session.invalidate();
				
				return "/public/offer?faces-redirect=true";
			}
			
			return null;
			
		} catch (ParseException e) {
			System.out.println("Błąd parsowania daty: " + e.getMessage());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null;
		}
	}
}
