package rental.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the bikes database table.
 * 
 */
@Entity
@Table(name="bikes")
@NamedQuery(name="Bike.findAll", query="SELECT b FROM Bike b")
public class Bike implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bike")
	private Integer idBike;

	@Column(name="bike_condition")
	private int bikeCondition;

	private String description;

	@Column(name="is_active")
	private int isActive;

	private String model;

	private String picture;

	private double price;

	//bi-directional many-to-one association to TypesOfBike
	@ManyToOne
	@JoinColumn(name="id_type")
	private TypesOfBike typesOfBike;

	//bi-directional many-to-one association to Rental
	@OneToMany(mappedBy="bike")
	private List<Rental> rentals;

	public Bike() {
	}

	public Integer getIdBike() {
		return this.idBike;
	}

	public void setIdBike(Integer idBike) {
		this.idBike = idBike;
	}

	public int getBikeCondition() {
		return this.bikeCondition;
	}

	public void setBikeCondition(int bikeCondition) {
		this.bikeCondition = bikeCondition;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TypesOfBike getTypesOfBike() {
		return this.typesOfBike;
	}

	public void setTypesOfBike(TypesOfBike typesOfBike) {
		this.typesOfBike = typesOfBike;
	}

	public List<Rental> getRentals() {
		return this.rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public Rental addRental(Rental rental) {
		getRentals().add(rental);
		rental.setBike(this);

		return rental;
	}

	public Rental removeRental(Rental rental) {
		getRentals().remove(rental);
		rental.setBike(null);

		return rental;
	}

}