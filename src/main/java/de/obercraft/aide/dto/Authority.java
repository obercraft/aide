package de.obercraft.aide.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Authority implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    private UserSecret userSecret;

    public Authority(){
    	super();
    }
    
	public Authority(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserSecret getUserSecret() {
		return userSecret;
	}

	public void setUserSecret(UserSecret userSecret) {
		this.userSecret = userSecret;
	}

	
    
}
