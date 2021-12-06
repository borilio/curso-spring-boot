package com.ejemplo.practica5.users;

import java.util.Random;

public class User {
    private int id;
    private String email;
    private String password;

    public User(){}

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    
    public User(String email, String password) {
        Random r = new Random();
    	this.id = Math.abs(r.nextInt()); //valor aleatorio positivo en el rango de los enteros
        this.email = email;
        this.password = password;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
    
    

}