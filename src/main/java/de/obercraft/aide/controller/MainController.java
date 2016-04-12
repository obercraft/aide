package de.obercraft.aide.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.obercraft.aide.component.CommentRepository;
import de.obercraft.aide.dto.AideUserDetails;
import de.obercraft.aide.dto.Comment;
import de.obercraft.aide.dto.User;

@Controller
@RequestMapping("/aide")
public class MainController {

	@Value("${application.name}")
	private String name;

	@Value("${application.version}")
	private String version;	
	
	@Resource
	private CommentRepository commentRepository;
	
	
	@RequestMapping(method= RequestMethod.GET)
	public String getIndex(Model model, Authentication authentication) {		
		model.addAttribute("applicationName", name);
		model.addAttribute("applicationVersion", version);
		model.addAttribute("auth", false);
		if (authentication != null) {
			List<SimpleGrantedAuthority> auths = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
			if (auths != null) {
				for (SimpleGrantedAuthority a : auths) {
					if ("USER".equals(a.getAuthority())) {
						model.addAttribute("auth", true);
					}
				}
			}
		}
		return "index";
	}
	
}
