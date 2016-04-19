package de.obercraft.aide.controller;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.obercraft.aide.component.CommentRepository;

@Controller
@RequestMapping("/aide")
public class MainController {

	@Value("${application.name}")
	private String name;

	@Value("${application.version}")
	private String version;
	
	@Resource
	private CommentRepository commentRepository;
	
	@Resource
	private Properties properties;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method= RequestMethod.GET)
	public String getIndex(Model model, Authentication authentication) {		
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		model.addAttribute("auth", false);
		if (authentication != null) {
			List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
			if (authorities != null) {
				for (SimpleGrantedAuthority a : authorities) {
					if ("USER".equals(a.getAuthority())) {
						model.addAttribute("auth", true);
					}
				}
			}
		}
		return "index";
	}
	
}
