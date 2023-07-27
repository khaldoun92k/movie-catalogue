package com.movie.models;

import java.util.Collection;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") //user table name reserved by h2, Postgres... needs to be renamed 
public class User {
	
	@Id @GeneratedValue
	private  Long userId;
	private String userName;
	private String password;
	private Date createdAt;
	
	@OneToMany(mappedBy = "user")
    private Collection<Rate> rate;

	public User(String userName, String password, Date createdAt) {
		this.userName = userName;
		this.password = password;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", createdAt="
				+ createdAt + "]";
	}

	
}
