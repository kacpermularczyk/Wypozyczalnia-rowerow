package rental.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.faces.application.FacesMessage;
import rental.dao.BikeDAO;
import rental.dao.RentalDAO;
import rental.entities.Bike;
import rental.entities.Rental;
import rental.entities.User;

@Named
@ViewScoped
public class BookBike implements Serializable {
	
	private Bike bike = new Bike();
	private Bike loaded = null;
	
	private Rental rental = new Rental();

	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	RentalDAO rentalDAO;
	
	@EJB
	BikeDAO bikeDAO;
	
	public Bike getBike() {
		return bike;
	}
	
	public Rental getRental() {
		return rental;
	}
	
	public void loadData() throws IOException {
		loaded = (Bike) flash.get("bikeToBook");
		
		if (loaded != null) {
			bike = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}
	
	public String saveToDb() {
		if (loaded == null) {
			return null; // zostan na tej samej stronie
		}
		
		try {
			Date now = new Date();
			if(rental.getDateStart().before(now) || rental.getDateEnd().before(now) || rental.getDateEnd().before(rental.getDateStart())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podano błędną datę", null));
				return null;
			}
			
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			RemoteClient<User> client = RemoteClient.load(session);
			
			rental.setUser(client.getDetails().getUser());
			
			bike.setBikeCondition(0);
			
			rental.setBike(bike);
			
			rental.setPrice(roundToDecimal(calculateRentalPrice(rental.getDateStart(), rental.getDateEnd()) * bike.getPrice()));
			
			rental.setAccepted(null);
			rental.setReturnedInTerm(null);
			
			rentalDAO.create(rental);
			
			bikeDAO.merge(bike);
			
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null; // zostan na tej samej stronie
		}

		return "/public/offer?faces-redirect=true";
	}
	
	public double calculateRentalPrice(Date start, Date end) {
		long result = end.getTime() - start.getTime();
		
		return (result / (60.0 * 1000) / 60); // milisekundy na minuty
	}
	
	public double roundToDecimal(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
