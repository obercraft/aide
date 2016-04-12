package de.obercraft.aide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.obercraft.aide.component.AuthorityRepository;
import de.obercraft.aide.component.UserRepository;
import de.obercraft.aide.component.UserSecretRepository;
import de.obercraft.aide.dto.Authority;
import de.obercraft.aide.dto.Register;
import de.obercraft.aide.dto.User;
import de.obercraft.aide.dto.UserSecret;

@Controller
@RequestMapping("/aide/login")
public class LoginController {

	@Value("${application.name}")
	private String name;

	@Value("${application.version}")
	private String version;	
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserSecretRepository userSecretRepository;
	
	@Resource
	private AuthorityRepository authorityRepository;
	
	@RequestMapping(method= RequestMethod.GET)
	public String getIndex(Model model) {		
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		return "login";
	}

	@RequestMapping(value = "register", method= RequestMethod.GET)
	public String getRegister(@ModelAttribute ("register") Register register, Model model) {
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		return "register";
	}
	
	
	@RequestMapping(value = "register", method= RequestMethod.POST)
	public String postRegister(@ModelAttribute ("register") Register register,Model model) {		
		PasswordEncoder encoder = new BCryptPasswordEncoder();	
		UserSecret userSecret = new UserSecret();
		userSecret.setEmail(register.getEmail());
		userSecret.setPassword(encoder.encode(register.getPassword()));
		
		User user = new User();
		user.setName(register.getName());
		
		User result = userRepository.save(user);
		
		userSecret.setUser(result);		
		UserSecret secretResult = userSecretRepository.save(userSecret);
		Authority authority = new Authority();
		authority.setName("USER");
		authority.setUserSecret(secretResult);
		
		authorityRepository.save(authority);
		
		
		return "registerSuccess";
	}

		
}
