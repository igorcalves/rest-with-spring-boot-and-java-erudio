package br.com.igor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails,Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name",unique = true)
	private String userName;
	
	@Column (name = "full_name")
	private String fullName;
	
	@Column (name = "password")
	private String password;
	
	@Column (name = "account_non_expired")
	private Boolean accountnonexpired;
	
	
	@Column (name = "account_non_locked")
	private Boolean accountnonlocked;
	
	@Column (name = "credentials_non_expired")
	private Boolean credentialsnonexpired;
	
	
	@Column (name = "enabled")
	private Boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = {@JoinColumn (name = "id_user")},
		inverseJoinColumns = {@JoinColumn (name = "id_permission")}
			)
	private List<Permission> permissions;
	
	
	public User() {
	}


	public List<String> getRoles(){
		List<String> roles = new ArrayList<>();
		for (Permission permision : permissions) {
			roles.add(permision.getDescription());
		}
		return roles ;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permissions;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountnonexpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountnonlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsnonexpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public Boolean getAccountnonexpired() {
		return accountnonexpired;
	}


	public void setAccountnonexpired(Boolean accountnonexpired) {
		this.accountnonexpired = accountnonexpired;
	}


	public Boolean getAccountnonlocked() {
		return accountnonlocked;
	}


	public void setAccountnonlocked(Boolean accountnonlocked) {
		this.accountnonlocked = accountnonlocked;
	}


	public Boolean getCredentialsnonexpired() {
		return credentialsnonexpired;
	}


	public void setCredentialsnonexpired(Boolean credentialsnonexpired) {
		this.credentialsnonexpired = credentialsnonexpired;
	}


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public List<Permission> getPermissions() {
		return permissions;
	}


	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		return Objects.hash(accountnonexpired, accountnonlocked, credentialsnonexpired, enabled, fullName, id, password,
				permissions, userName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(accountnonexpired, other.accountnonexpired)
				&& Objects.equals(accountnonlocked, other.accountnonlocked)
				&& Objects.equals(credentialsnonexpired, other.credentialsnonexpired)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(permissions, other.permissions) && Objects.equals(userName, other.userName);
	}
	
	
	
	
}
