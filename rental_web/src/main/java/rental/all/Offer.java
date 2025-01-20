package rental.all;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.BikeDAO;
import rental.entities.Bike;

@Named
@ViewScoped
public class Offer implements Serializable {

	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	BikeDAO bikeDAO;
	
	private LazyDataModel<Bike> lazyModel;
	
	private String model;
	private String idType;  
	private String bikeCondition;  
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getBikeCondition() {  
		return bikeCondition;
	}

	public void setBikeCondition(String bikeCondition) {  
		this.bikeCondition = bikeCondition;
	}
	
	public String newRental(Bike bike) {
		
		if(bike.getBikeCondition() == 1) {
			flash.put("bikeToBook", bike);
			
			return "/pages/user/bookBike?faces-redirect=true";
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rower jest niedostępny", null));
			return null;
		}
	}
	
	@PostConstruct
	public void init() {
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (model != null && model.length() > 0) {
			searchParams.put("model", model);
		}
		
		if (idType != null && idType.length() > 0) {
			searchParams.put("idType", idType);
		}
		
		if (bikeCondition != null && bikeCondition.length() > 0) {  
			searchParams.put("bikeCondition", bikeCondition);
		}
		
		lazyModel = new BikeLazyDataModel(bikeDAO, searchParams);
	}
	
	public LazyDataModel<Bike> getLazyModel() {
		return lazyModel;
	}
}
