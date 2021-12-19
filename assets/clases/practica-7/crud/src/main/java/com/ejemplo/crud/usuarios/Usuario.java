package com.ejemplo.crud.usuarios;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(unique = true)
	private String email;
	private String pass;
	private boolean admin;
	
	// Constructores
	public Usuario() {}
	
	public Usuario(int id, String name, String email, String pass, boolean admin) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.admin = admin;
	}
	
	public Usuario(String email) {
		Random r = new Random();
		this.id = Math.abs(r.nextInt());
		this.name = email;
		this.email = email;
		this.pass = String.valueOf(Integer.toHexString(this.hashCode()));
		this.admin = false;
	}
	
	// Getters y Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "Usuario {"
				+ "id=" + id + 
				", name=" + name + 
				", email=" + email + 
				", pass=" + pass + 
				", admin=" + admin
				+ "}";
	}
	
	
	
	
	
	
	

}
