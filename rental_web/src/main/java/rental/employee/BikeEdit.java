package rental.employee;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import rental.dao.BikeDAO;
import rental.entities.Bike;

@Named
@ViewScoped
public class BikeEdit implements Serializable { //?
	private static final long serialVersionUID = 1L; //?
	
	private Bike bike = new Bike();
	private Bike loaded = null;

	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	BikeDAO bikeDAO;
	
	public Bike getBike() {
		return bike;
	}
	
	public void loadData() throws IOException {
		loaded = (Bike) flash.get("bike");
		
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
			if (bike.getIdBike() == null) { 
				// new record
				bikeDAO.create(bike);
			} else {
				// existing record
				bikeDAO.merge(bike);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null; // zostan na tej samej stronie
		}

		return "bikeList?faces-redirect=true";
	}
}
