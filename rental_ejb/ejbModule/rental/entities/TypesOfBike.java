package rental.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the types_of_bikes database table.
 * 
 */
@Entity
@Table(name="types_of_bikes")
@NamedQuery(name="TypesOfBike.findAll", query="SELECT t FROM TypesOfBike t")
public class TypesOfBike implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_type")
	private Integer idType;

	@Column(name="is_active")
	private int isActive;

	private String type;

	//bi-directional many-to-one association to Bike
	@OneToMany(mappedBy="typesOfBike")
	private List<Bike> bikes;

	public TypesOfBike() {
	}

	public Integer getIdType() {
		return this.idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Bike> getBikes() {
		return this.bikes;
	}

	public void setBikes(List<Bike> bikes) {
		this.bikes = bikes;
	}

	public Bike addBike(Bike bike) {
		getBikes().add(bike);
		bike.setTypesOfBike(this);

		return bike;
	}

	public Bike removeBike(Bike bike) {
		getBikes().remove(bike);
		bike.setTypesOfBike(null);

		return bike;
	}

}