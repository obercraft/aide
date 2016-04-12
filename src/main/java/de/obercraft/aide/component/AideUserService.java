package de.obercraft.aide.component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.obercraft.aide.dto.AideUserDetails;
import de.obercraft.aide.dto.Authority;
import de.obercraft.aide.dto.UserSecret;

@Service
public class AideUserService implements UserDetailsService {

	@Resource
	private UserSecretRepository userSecretRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<UserSecret> userSecrets = userSecretRepository.findByEmail(username);
		UserSecret user = userSecrets.get(0);
		List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
		for (Authority a : user.getAuthorities()) {
			ga.add(new SimpleGrantedAuthority(a.getName()));
			
		}
		
		UserDetails userDetails = new AideUserDetails(user.getUser(), user.getEmail(), user.getPassword(), ga);		
		return userDetails;
	}
	
}
