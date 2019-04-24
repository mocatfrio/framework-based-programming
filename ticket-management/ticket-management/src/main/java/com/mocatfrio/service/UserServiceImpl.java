package com.mocatfrio.service;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mocatfrio.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	SecurityService securityService;
	
	// Dummy users
	public static List<User> users;

	public UserServiceImpl() {
		users = new LinkedList<>();
		/*
		users.add(new User("sammy", "pass", 3)); // 3 - admin
		users.add(new User("chloe", "pass", 2)); // 2 - CSR
		users.add(new User("peter", "pass", 1)); // 1 - general user
		users.add(new User("kevin", "pass", 1)); // 1 - general user	
		*/  
	}
	
	// READ
	// Get all users
	@Override
	public List<User> getAllUsers() {
		return this.users;
	}
	
	// Get user by user ID
	@Override
	public User getUser(Integer userid) {
		return users.stream()
		.filter(x -> x.getUserId() == userid)
		.findAny()
		.orElse(null);
	}
	
	// Get user by username, password, and usertype
	@Override
	public User getUser(String username, String password, Integer usertype) {
		return users.stream()
		.filter(x -> x.getUsername().equalsIgnoreCase(username) && x.getPassword().equalsIgnoreCase(password)  && x.getUsertype() == usertype )
		.findAny()
		.orElse(null);
	}
	
	// Get user by token
	@Override
	public User getUserByToken(String token){
		// Parse the created token
		Claims claims = Jwts.parser()         
			       		.setSigningKey(DatatypeConverter.parseBase64Binary(SecurityServiceImpl.secretKey))
		       			.parseClaimsJws(token)
	       				.getBody();
		if(claims == null || claims.getSubject() == null){
			return null;
		}
		// Get the subject
		String subject = claims.getSubject();
		if(subject.split("=").length != 2){
			return null;
		}
		String[] subjectParts = subject.split("=");
		Integer userid = Integer.parseInt(subjectParts[0]);
		Integer usertype = Integer.parseInt(subjectParts[1]);		
		System.out.println("{getUserByToken} usertype["+usertype+"], userid["+userid+"]");
		return new User(userid, usertype);
	}
	
	// CREATE
	@Override
	public void createUser(String username, String password, Integer usertype){
		User user = new User(username, password, usertype);
		this.users.add(user);
	}
	
	// UPDATE
	@Override
	public void updateUser(Integer userid, String username) {
		users.stream()
				.filter(x -> x.getUserId()  == userid)
				.findAny()
				.orElseThrow(() -> new RuntimeException("Item not found"))
				.setUsername(username);		
	}
	
	// DELETE
	@Override
	public void deleteUser(Integer userid) {   
		users.removeIf((User u) -> u.getUserId() == userid);
	}
}
