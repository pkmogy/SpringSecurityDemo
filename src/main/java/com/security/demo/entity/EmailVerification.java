package com.security.demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(catalog = "securityscl", schema = "public", name = "email_verification", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"someone"})
})
public class EmailVerification implements Serializable {

	private static final long serialVersionUID = -1899028127932162552L;
	private static final int EXPIRATION = 60 * 24;

	@Id
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic(optional = false)
	@Column(name = "verification_code", nullable = false)
	@NotNull
	private String verificationCode;

	@JoinColumn(name = "someone", nullable = false, referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Someone someone;

	@Column(name = "expiry")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public EmailVerification() {
		this.expiry = calculateExpiryDate(EXPIRATION);
	}

	public EmailVerification(Long id) {
		this.id = id;
		this.expiry = calculateExpiryDate(EXPIRATION);
	}

	public EmailVerification(String token, Someone talent) {
		this.verificationCode = token;
		this.someone = talent;
		this.expiry = calculateExpiryDate(EXPIRATION);
	}

	public EmailVerification(Long id, String token) {
		this.id = id;
		this.verificationCode = token;
	}

	public Someone getSomeone() {
		return someone;
	}

	public void setSomeone(Someone someone) {
		this.someone = someone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiryDate() {
		this.expiry = calculateExpiryDate(EXPIRATION);
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
		if (!(object instanceof EmailVerification)) {
			return false;
		}
		EmailVerification other = (EmailVerification) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.security.demo.entity.EmailVerification[ id=" + id + " ]";
	}

}
