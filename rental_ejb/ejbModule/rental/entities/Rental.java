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
	private int idRental;

	private byte accepted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_end")
	private Date dateEnd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_start")
	private Date dateStart;

	@Column(name="not_returned_in_term")
	private byte notReturnedInTerm;

	@Column(name="returned_in_term")
	private byte returnedInTerm;

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

	public int getIdRental() {
		return this.idRental;
	}

	public void setIdRental(int idRental) {
		this.idRental = idRental;
	}

	public byte getAccepted() {
		return this.accepted;
	}

	public void setAccepted(byte accepted) {
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

	public byte getNotReturnedInTerm() {
		return this.notReturnedInTerm;
	}

	public void setNotReturnedInTerm(byte notReturnedInTerm) {
		this.notReturnedInTerm = notReturnedInTerm;
	}

	public byte getReturnedInTerm() {
		return this.returnedInTerm;
	}

	public void setReturnedInTerm(byte returnedInTerm) {
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