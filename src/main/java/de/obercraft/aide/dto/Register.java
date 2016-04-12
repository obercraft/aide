package de.obercraft.aide.dto;

import java.io.Serializable;

public class Register implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String email;
	private String password;
	
	public Register() {		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
