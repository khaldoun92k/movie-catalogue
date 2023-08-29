package com.movie.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users") // user table name reserved by h2, Postgres... needs to be renamed
@Validated
public class User implements UserDetails{

	@Id
	@GeneratedValue
	private Long userId;
	@Column(unique=true)
	@NotBlank
	@NotNull
	private String username;
	@NotNull
	@NotBlank
	private String password;
	@NotNull
	private String role = "ROLE_USER";
	@NotNull
	private Date createdAt= new Date();
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	 private Set<Rate> rate=new HashSet<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
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



	public Set<Rate> getRate() {
		return rate;
	}

	public void setRate(Set<Rate> rate) {
		this.rate = rate;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();

        listRole.add(new SimpleGrantedAuthority(role)); 
        return listRole;
    }

	@Override
	public boolean isAccountNonExpired() {  //all info set to true for simplicity
		return true; 
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", createdAt=" + createdAt + ", rate=" + rate + "]";
	}

}
