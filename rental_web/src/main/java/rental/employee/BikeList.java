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
	
	public List<TypesOfBike> getFullTypesList(){
		return typesOfBikeDAO.getFullList();
	}
	
	public List<Bike> getListWithParams(){
		List<Bike> list = null;
		
		 
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
		
		 
		list = bikeDAO.getListWithParams(searchParams);
		
		return list;
	}
	
	public String newBike(){
		Bike bike = new Bike();
		
		TypesOfBike typesOfBike = new TypesOfBike();
		bike.setTypesOfBike(typesOfBike);
		
		bike.setIsActive(1);
		bike.setBikeCondition(1);
		
		flash.put("bike", bike);
		
		return "bikeEdit?faces-redirect=true";
	}

	public String editBike(Bike bike){
		
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
