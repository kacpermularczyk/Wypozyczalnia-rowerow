package rental.employee;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import rental.dao.BikeDAO;
import rental.dao.RentalDAO;
import rental.entities.Rental;

@Named
@ViewScoped
public class RentalList implements Serializable {

	@Inject
	Flash flash;
	
	@EJB
	RentalDAO rentalDAO;
	
	@EJB
	BikeDAO bikeDAO;
	
	private String surname;
	private String e_mail;
	private String model;
	private String returnedInTerm;
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getE_mail() {
		return e_mail;
	}
	
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getReturnedInTerm() {
		return returnedInTerm;
	}
	
	public void setReturnedInTerm(String returnedInTerm) {
		this.returnedInTerm = returnedInTerm;
	}
	
	public List<Rental> getFullList() {
		return rentalDAO.getFullList();
	}
	
	public List<Rental> getRentalsToAcceptListWithParams() {
		List<Rental> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}
		
		if (e_mail != null && e_mail.length() > 0) {
			searchParams.put("e_mail", e_mail);
		}
		
		if (model != null && model.length() > 0) {
			searchParams.put("model", model);
		}
		
		list = rentalDAO.getRentalsToAcceptListWithParams(searchParams);
		
		return list;
	}
	
	public List<Rental> getAcceptedRentalsListWithParams() {
		List<Rental> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}
		
		if (e_mail != null && e_mail.length() > 0) {
			searchParams.put("e_mail", e_mail);
		}
		
		if (model != null && model.length() > 0) {
			searchParams.put("model", model);
		}
		
		if (returnedInTerm != null && returnedInTerm.length() > 0) {
			searchParams.put("returnedInTerm", returnedInTerm);
		}
		
		list = rentalDAO.getAcceptedRentalsListWithParams(searchParams);
		
		return list;
	}
	
	public void dontAcceptRentalSoRemoveRecordFromDb(Rental r) {
		r.getBike().setBikeCondition(1); //ustawiam bike na dostepny
		bikeDAO.merge(r.getBike()); //ustawiam bike na dostepny
		
		rentalDAO.remove(r); // remove od razu usuwa fk wiec no need for usuwanie ich.
	}
	
	public void acceptRental(Rental r) {
		r.setAccepted(1);
		rentalDAO.merge(r);
	}
	
	public void setReturnedInTerm(Rental r, int x) {
		r.getBike().setBikeCondition(1); //ustawiam bike na dostepny
		bikeDAO.merge(r.getBike());//ustawiam bike na dostepny
		
		r.setReturnedInTerm(x);
		rentalDAO.merge(r);
	}
	
	public String printReturnedInTermInString(Integer returnedInTerm) {
		if(returnedInTerm == 1) {
			return "Tak";
		} else {
			return "Nie";
		}
	}
}
