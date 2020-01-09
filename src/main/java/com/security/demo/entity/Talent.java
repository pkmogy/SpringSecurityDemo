package com.security.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * 會員
 *
 * @author 李羅
 */
@Entity
@Table(catalog = "securityscl", schema = "public", name = "talent", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"identifier"})
	, @UniqueConstraint(columnNames = {"live_id"})
	, @UniqueConstraint(columnNames = {"google_id"})
	, @UniqueConstraint(columnNames = {"facebook_id"})
	, @UniqueConstraint(columnNames = {"email"})
	, @UniqueConstraint(columnNames = {"line_id"})})
public class Talent implements Serializable {

	private static final long serialVersionUID = 3951200073948219080L;

	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Basic(optional = false)
	@Column(name = "identifier", nullable = false, unique = true)
	@Lob
	@NotNull
	@org.hibernate.annotations.Type(type = "pg-uuid")
	private UUID identifier;

	@Column(name = "facebook_id", unique = true)
	private String facebookId;

	@Column(name = "google_id", unique = true)
	private String googleId;

	@Column(name = "line_id", unique = true)
	private String lineId;

	@Column(name = "live_id", unique = true)
	private String liveId;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "birth")
	@Temporal(TemporalType.DATE)
	private Date birth;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "email", unique = true)
	//@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	private String email;

	@Column(name = "shadow")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private String shadow;

	@Column(name = "brief")
	private String brief;

	@Basic(optional = false)
	@Column(name = "instructor", nullable = false)
	@NotNull
	private boolean instructor;

	@Basic(optional = false)
	@Column(name = "administrator", nullable = false)
	@NotNull
	private boolean administrator;

	@Column(name = "advantage")
	@Temporal(TemporalType.TIMESTAMP)
	private Date advantage;

	@Basic(optional = false)
	@Column(name = "banned", nullable = false)
	@NotNull
	private boolean banned;

	@Basic(optional = false)
	@Column(name = "required_to_change_shadow", nullable = false)
	@NotNull
	private boolean requiredToChangeShadow;

	@Column(name = "role")
	private String role;

	@Column(name = "enabled")
	private boolean enabled;

	/**
	 * 預設建構子
	 */
	public Talent() {
		super();
		this.enabled = false;
		this.identifier = UUID.randomUUID();
	}

	/**
	 * @param instructor 講師
	 * @param administrator 管理者
	 */
	public Talent(boolean instructor, boolean administrator) {
		this.identifier = UUID.randomUUID();
		this.instructor = instructor;
		this.administrator = administrator;
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
	 * @return 通用唯一辨識碼
	 */
	public UUID getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier 通用唯一辨識碼
	 */
	public void setIdentifier(UUID identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return Facebook帳號
	 */
	public String getFacebookId() {
		return facebookId;
	}

	/**
	 * @param facebookId Facebook帳號
	 */
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	/**
	 * @return Google帳號
	 */
	public String getGoogleId() {
		return googleId;
	}

	/**
	 * @param googleId Google帳號
	 */
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	/**
	 * @return Line帳號
	 */
	public String getLineId() {
		return lineId;
	}

	/**
	 * @param lineId Line帳號
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return Live帳號
	 */
	public String getLiveId() {
		return liveId;
	}

	/**
	 * @param liveId live帳號
	 */
	public void setLiveId(String liveId) {
		this.liveId = liveId;
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
	 * @return 生日
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * @param birth 生日
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * @return 性別
	 */
	public Boolean getGender() {
		return gender;
	}

	/**
	 * @param gender 性別
	 */
	public void setGender(Boolean gender) {
		this.gender = gender;
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
	 * @return 簡介
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * @param brief 簡介
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * @return 講師
	 */
	public boolean getInstructor() {
		return instructor;
	}

	/**
	 * @param instructor 講師
	 */
	public void setInstructor(boolean instructor) {
		this.instructor = instructor;
	}

	/**
	 * @return 管理者
	 */
	public boolean getAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator 管理者
	 */
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	/**
	 * @return 會員福利
	 */
	public Date getAdvantage() {
		return advantage;
	}

	/**
	 * @param advantage 會員福利
	 */
	public void setAdvantage(Date advantage) {
		this.advantage = advantage;
	}

	/**
	 * @return 停權
	 */
	public boolean isBanned() {
		return banned;
	}

	/**
	 * @param banned 停權
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	/**
	 * @return 必須修改密碼
	 */
	public boolean isRequiredToChangeShadow() {
		return requiredToChangeShadow;
	}

	/**
	 * @param requiredToChangeShadow 必須修改密碼
	 */
	public void setRequiredToChangeShadow(boolean requiredToChangeShadow) {
		this.requiredToChangeShadow = requiredToChangeShadow;
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
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled 已啟用
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
		if (!(object instanceof Talent)) {
			return false;
		}
		Talent other = (Talent) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "com.springsecurityacl.SpringSecurityAcl.entity.Talent[ id=" + id + " ]";
	}

}
