package rental.all;

import java.io.Serializable;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.entities.Bike;

@Named
@ViewScoped
public class Offer implements Serializable {

	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	public String newRental(Bike bike) {
		
		if(bike.getBikeCondition() == 1) {
			flash.put("bikeToBook", bike);
			
			return "/pages/user/bookBike?faces-redirect=true";
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rower jest niedostępny", null));
			return null;
		}
		
		
	}
}
