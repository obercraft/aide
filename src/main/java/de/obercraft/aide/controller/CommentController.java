package de.obercraft.aide.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/comment")
public class CommentController {

	
	@RequestMapping(method= RequestMethod.GET)
	public String getIndex(Model model, Authentication authentication) {		
		return "comment";
	}
	

	
	
}
