package rental.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user_role")
	private int idUserRole;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="active_since")
	private Date activeSince;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="active_until")
	private Date activeUntil;

	@Column(name="is_activated")
	private byte isActivated;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;

	public UserRole() {
	}

	public int getIdUserRole() {
		return this.idUserRole;
	}

	public void setIdUserRole(int idUserRole) {
		this.idUserRole = idUserRole;
	}

	public Date getActiveSince() {
		return this.activeSince;
	}

	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}

	public Date getActiveUntil() {
		return this.activeUntil;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	public byte getIsActivated() {
		return this.isActivated;
	}

	public void setIsActivated(byte isActivated) {
		this.isActivated = isActivated;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}