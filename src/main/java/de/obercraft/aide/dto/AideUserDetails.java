package de.obercraft.aide.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AideUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private User user;
	private String username;
	private String password;
	private List<SimpleGrantedAuthority> authorities;
	
	public AideUserDetails() {
		
	}
	
	public AideUserDetails(User user, String username, String password, List<SimpleGrantedAuthority> authorities) {
		this.user = user;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
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


	public User getUser() {
		return user;
	}

}
