package rental.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rentals database table.
 * 
 */
@Entity
@Table(name="rentals")
@NamedQuery(name="Rental.findAll", query="SELECT r FROM Rental r")
public class Rental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rental")
	private Integer idRental;

	private Integer accepted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_end")
	private Date dateEnd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_start")
	private Date dateStart;

	private double price;

	@Column(name="returned_in_term")
	private Integer returnedInTerm;

	//bi-directional many-to-one association to Bike
	@ManyToOne
	@JoinColumn(name="id_bike")
	private Bike bike;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public Rental() {
	}

	public Integer getIdRental() {
		return this.idRental;
	}

	public void setIdRental(Integer idRental) {
		this.idRental = idRental;
	}

	public Integer getAccepted() {
		return this.accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getReturnedInTerm() {
		return this.returnedInTerm;
	}

	public void setReturnedInTerm(Integer returnedInTerm) {
		this.returnedInTerm = returnedInTerm;
	}

	public Bike getBike() {
		return this.bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}