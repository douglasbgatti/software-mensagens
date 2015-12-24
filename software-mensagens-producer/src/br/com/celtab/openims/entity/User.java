package br.com.celtab.openims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@SequenceGenerator(name="users_seq", sequenceName="users_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,	generator="users_seq")
	private Long id;
	@Column(unique=true)	
	private String username;
	@Column
	private String password;
//	private Role role;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
