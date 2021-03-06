package de.obercraft.aide.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.obercraft.aide.dto.AideUserDetails;
import de.obercraft.aide.dto.Authority;
import de.obercraft.aide.dto.User;


@Service
public class AideUserService implements UserDetailsService {

	@Resource
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findByEmail(username);

		User user = users.get(0);
		
		List<SimpleGrantedAuthority> ga = new ArrayList<SimpleGrantedAuthority>();
		for (Authority a : user.getAuthorities()) {
			ga.add(new SimpleGrantedAuthority(a.getName()));
			
		}
		
		UserDetails userDetails = new AideUserDetails(user, user.getEmail(), user.getPassword(), ga);		
		
		user.setLastLogin(new Date());
		userRepository.save(user);
		return userDetails;
	}
	
}
