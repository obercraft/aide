package de.obercraft.aide.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.obercraft.aide.component.AuthorityRepository;
import de.obercraft.aide.component.ForgottenPasswordComponent;
import de.obercraft.aide.component.UserRepository;
import de.obercraft.aide.dto.Authority;
import de.obercraft.aide.dto.ForgottenPassword;
import de.obercraft.aide.dto.Register;
import de.obercraft.aide.dto.User;

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
	private AuthorityRepository authorityRepository;

	@Resource
	private UserRepository UserRepository;
	
	@Resource
	private ForgottenPasswordComponent forgottenPasswordComponent;

	@RequestMapping(method = RequestMethod.GET)
	public String getIndex(Model model,
			@RequestParam(value = "error", required = false) String error) {
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		return "redirect:/aide#/login"
				+ (error != null ? "?error=error" : "");
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String getRegister(@ModelAttribute("register") Register register,
			Model model) {
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String postRegister(@ModelAttribute("register") Register register,
			Model model) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setEmail(register.getEmail());
		System.out.println("XXX=" + register.getPassword());
		user.setPassword(encoder.encode(register.getPassword()));
		user.setCreated(new Date());
		user.setName(register.getName());

		user = userRepository.save(user);

		Authority authority = new Authority();
		authority.setName("USER");
		authority.setUser(user);

		authorityRepository.save(authority);
		return "registerSuccess";
	}

	@RequestMapping(value = "forgotten", method = RequestMethod.GET)
	public String getForgotten(@RequestParam(value="user", required = true) String username,@RequestParam(value="key", required = true) String key) throws Exception {
		if (forgottenPasswordComponent.checkForgottenPassword(username, key)) {
			return "registerSuccess";
		}
		return "register";
	}
	
	@RequestMapping(value = "forgotten", method = RequestMethod.POST)
	public String postForgotten(@RequestParam(value="email", required = true) String email) throws Exception {
		forgottenPasswordComponent.createForgottenPassword(email);
		return "registerSuccess";
	}
	
}
