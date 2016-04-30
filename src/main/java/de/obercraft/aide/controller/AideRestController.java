package de.obercraft.aide.controller;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.obercraft.aide.component.CommentRepository;
import de.obercraft.aide.component.DisplayChart;
import de.obercraft.aide.component.UserRepository;
import de.obercraft.aide.dto.AideUserDetails;
import de.obercraft.aide.dto.Comment;
import de.obercraft.aide.dto.User;

@RestController
@RequestMapping("/rest")
public class AideRestController {
	
	@Resource
	private DisplayChart displayChart;
	
	@Resource
	private CommentRepository commentRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@RequestMapping(value="/data", method = RequestMethod.GET)
	@ResponseBody
	public Object getDisplay() {		
		return displayChart.getData();
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	@ResponseBody
	public Object getTest() {		
		return userRepository.findAll();
	}
	
	@RequestMapping(value="/comments/{subject}", method = RequestMethod.GET)
	@ResponseBody
	public Object getMessagesByCategory(@PathVariable(value ="subject") String subject) {		
		return commentRepository.findBySubjectOrderByCreatedAsc(subject);
	}
	
	@RequestMapping(value="/username", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUsername(@RequestBody String username) {		
		List<User> users = userRepository.findByName(username);
		if (users == null || users.size() == 0) {
			return false;
		}
		return true;
		
	}
	
	@RequestMapping(value="/email", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkEmail(@RequestBody String email) {
		System.out.println(email);
		List<User> users = userRepository.findByEmail(email);
		if (users == null || users.size() == 0) {
			return false;
		}
		return true;
		
	}

	
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value="/comments", method = RequestMethod.POST)
	@ResponseBody
	public Object postComment(@RequestBody Comment comment, Authentication authentication) {

		AideUserDetails userDetails = (AideUserDetails) authentication.getPrincipal();
		comment.setUser(userDetails.getUser());
		comment.setCreated(new Date());
		commentRepository.save(comment);
		return comment;
	}
	
	
	
	
}
