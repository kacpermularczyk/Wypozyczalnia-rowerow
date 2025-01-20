package rental.employee;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.TypesOfBikeDAO;
import rental.entities.TypesOfBike;

@Named
@ViewScoped
public class typeEdit implements Serializable {
	private TypesOfBike typesOfBike = new TypesOfBike();
	private TypesOfBike loaded = null;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	TypesOfBikeDAO typesOfBikeDAO;
	
	public TypesOfBike getTypesOfBike() {
		return typesOfBike;
	}
	
	public void loadData() throws IOException {
		loaded = (TypesOfBike) flash.get("typesOfBike");
		
		if (loaded != null) {
			typesOfBike = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}
	
	public String saveToDb() {
		if (loaded == null) {
			return null; // zostan na tej samej stronie
		}
		
		try {
			if (typesOfBike.getIdType() == null) { 
				
				typesOfBikeDAO.create(typesOfBike);
			} else {
				
				typesOfBikeDAO.merge(typesOfBike);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null; // zostan na tej samej stronie
		}

		return "workerPanel?faces-redirect=true";
	}
}
