package com.movie.models;

import java.util.Collection;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // user table name reserved by h2, Postgres... needs to be renamed
public class User {

	@Id
	@GeneratedValue
	private Long userId;
	@Column(unique=true)
	private String userName;
	private String password;
	private String role;

	private Date createdAt;

	@OneToMany(mappedBy = "user")
	private Collection<Rate> rate;

	public User(String userName, String password, String role, Date createdAt) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;

	}

	public User() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", role=" + role
				+ ", createdAt=" + createdAt + ", rate=" + rate + "]";
	}

	
	

}
