package com.security.demo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 會員
 *
 * @author 李羅
 */
@Entity
@Table(catalog = "securityscl", schema = "public", name = "someone", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"email"})})
public class Someone implements Serializable {

	private static final long serialVersionUID = 3951200073948219080L;

	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "email", unique = true)
	//@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	private String email;

	@Column(name = "shadow")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private String shadow;

	@Column(name = "role")
	private String role;

	@Column(name = "verified")
	private boolean verified;

	/**
	 * 預設建構子
	 */
	public Someone() {
		super();
		this.verified = false;
	}

	/**
	 * @return 主鍵
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id 主鍵
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return 暱稱
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname 暱稱
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return 電子郵件信箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 電子郵件信箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 密碼
	 */
	public String getShadow() {
		return shadow;
	}

	/**
	 * @param shadow 密碼
	 */
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	/**
	 * @return 權限
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role 權限
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return 已啟用
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified 已啟用
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
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
		if (!(object instanceof Someone)) {
			return false;
		}
		Someone other = (Someone) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "com.springsecurityacl.SpringSecurityAcl.entity.Someone[ id=" + id + " ]";
	}

}
