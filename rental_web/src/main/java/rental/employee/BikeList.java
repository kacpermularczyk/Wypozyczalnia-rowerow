package rental.employee;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.BikeDAO;
import rental.dao.TypesOfBikeDAO;
import rental.entities.Bike;
import rental.entities.TypesOfBike;

@Named
@ViewScoped
public class BikeList implements Serializable {
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	BikeDAO bikeDAO;
	@EJB
	TypesOfBikeDAO typesOfBikeDAO;
	
	private String model;
	private String idType; // qwe
	private String bikeCondition; // qwe
	
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
	
	public String getBikeCondition() { // qwe
		return bikeCondition;
	}

	public void setBikeCondition(String bikeCondition) { // qwe
		this.bikeCondition = bikeCondition;
	}
	
	public List<TypesOfBike> getFullTypesList(){
		return typesOfBikeDAO.getFullList();
	}
	
	public List<Bike> getListWithParams(){
		List<Bike> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (model != null && model.length() > 0) {
			searchParams.put("model", model);
		}
		
		if (idType != null && idType.length() > 0) {
			searchParams.put("idType", idType);
		}
		
		if (bikeCondition != null && bikeCondition.length() > 0) { //qwe
			searchParams.put("bikeCondition", bikeCondition);
		}
		
		//2. Get list
		list = bikeDAO.getListWithParams(searchParams);
		
		return list;
	}
	
	public String newBike(){
		Bike bike = new Bike();
		
		bike.setIsActive(1);
		bike.setBikeCondition(1);
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("bike", bike);
		
		return "bikeEdit?faces-redirect=true";
	}

	public String editBike(Bike bike){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("bike", bike);
		
		return "bikeEdit?faces-redirect=true";
	}
	
	public void turnOffBike(Bike bike) {
		bike.setIsActive(0);
		bikeDAO.merge(bike);
	}
	
	public String printBikeCondition(int isActive) {
		if(isActive == 1) {
			return "Dostępny";
		} else {
			return "Niedostępny";
		}
	}
}
