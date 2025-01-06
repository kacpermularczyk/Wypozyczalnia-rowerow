package rental.employee;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
public class typeList implements Serializable {

	@EJB
	TypesOfBikeDAO typesOfBikeDAO;
	@EJB
	BikeDAO bikeDAO;
	@Inject
	Flash flash;
	@Inject
	FacesContext context;
	
	private String type;
	
	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
    
    
    
    
	
	public List<TypesOfBike> getFullTypesList(){
		return typesOfBikeDAO.getFullList();
	}
	
	public void turnOffType(TypesOfBike t) {
		if(bikeDAO.howManyBikesHaveType(t.getIdType()) == 0) {
			t.setIsActive(0);
			typesOfBikeDAO.merge(t);
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieją rowery o tym typie", null));
		}
	}
	
	public List<TypesOfBike> getTypesListWithParams(){
		List<TypesOfBike> list = null;
		
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (type != null && type.length() > 0) {
			searchParams.put("type", type);
		}
		
		
		list = typesOfBikeDAO.getTypesListWithParams(searchParams);
		
		return list;
	}
	
	
	
	
	
	public String newTypesOfBike(){
		TypesOfBike typesOfBike = new TypesOfBike();
		
		typesOfBike.setIsActive(1);
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("typesOfBike", typesOfBike);
		
		return "typeEdit?faces-redirect=true";
	}

	public String editTypesOfBike(TypesOfBike typesOfBike){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("typesOfBike", typesOfBike);
		
		return "typeEdit?faces-redirect=true";
	}
}
