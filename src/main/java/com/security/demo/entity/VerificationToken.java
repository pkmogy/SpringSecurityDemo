package com.security.demo.entity;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 李羅
 */
@Entity
@Table(catalog = "securityscl", schema = "public", name = "verification_token", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"token"})
})
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = -1899028127932162552L;
	private static final int EXPIRATION = 60 * 24;

	@Id
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false)
	@Lob
	@NotNull
	@org.hibernate.annotations.Type(type = "pg-uuid")
	private UUID token;

	@JoinColumn(name = "talent", nullable = false, referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Talent talent;

	@Column(name = "expiry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public VerificationToken() {
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public VerificationToken(Long id) {
		this.id = id;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public VerificationToken(UUID token, Talent talent) {
		this.token = token;
		this.talent = talent;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public VerificationToken(Long id, UUID token) {
		this.id = id;
		this.token = token;
	}

	public Talent getTalent() {
		return talent;
	}

	public void setTalent(Talent talent) {
		this.talent = talent;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate() {
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof VerificationToken)) {
			return false;
		}
		VerificationToken other = (VerificationToken) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.security.demo.entity.VerificationToken[ id=" + id + " ]";
	}

}
