package com.mocatfrio.restapp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocatfrio.service.SecurityService;
import com.mocatfrio.service.UserService;
//import com.mocatfrio.service.SecurityService;
import com.mocatfrio.util.Util;
import com.mocatfrio.aop.TokenRequired;
import com.mocatfrio.model.User;

@RestController
@RequestMapping("/user")

public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	SecurityService securityService;
	
	@ResponseBody
	@RequestMapping("")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}")
	public User getUser(@PathVariable("id") Integer id) {
		return userService.getUser(3);
	}
	
	// REGISTER
	// Customer registration
	@ResponseBody
	@RequestMapping(value = "/register/customer", method = RequestMethod.POST)
	public Map<String, Object> registerCustomer(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		userService.createUser(username, password, 1);
		return Util.getSuccessResult();
	}
	
	// CSR registration
	@ResponseBody
	@RequestMapping(value = "/register/csr", method = RequestMethod.POST)
	public Map<String, Object> registerCSR(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		userService.createUser(username, password, 2);
		return Util.getSuccessResult();
	}
	
	// Admin registration
	@ResponseBody
	@RequestMapping(value = "/register/admin", method = RequestMethod.POST)
	public Map<String, Object> registerAdmin(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		userService.createUser(username, password, 3); 
		return Util.getSuccessResult();
	}
	
	// LOGIN
	// Login as customer
	@ResponseBody
	@RequestMapping(value = "/login/customer", method = RequestMethod.POST)
	public Map<String, Object> loginCustomer(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		User user = userService.getUser(username, password, 1);
		if(user == null){
			return Util.getUserNotAvailableError();
		}

		String subject = user.getUserId()+"="+user.getUsertype();
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		return Util.getSuccessResult(token);
	}
	
	// Login as CSR
	@ResponseBody
	@RequestMapping(value = "/login/csr", method = RequestMethod.POST)
	public Map<String, Object> loginCSR(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		User user = userService.getUser(username, password, 2);
		if(user == null){
			return Util.getUserNotAvailableError();
		}
		
		String subject = user.getUserId()+"="+user.getUsertype();
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		return Util.getSuccessResult(token);
	}
	
	// Login as Admin
	@ResponseBody
	@RequestMapping(value = "/login/admin", method = RequestMethod.POST)
	public Map<String, Object> loginAdmin(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		User user = userService.getUser(username, password, 3);
		if(user == null){
			return Util.getUserNotAvailableError();
		}
		
		String subject = user.getUserId()+"="+user.getUsertype();
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		return Util.getSuccessResult(token);
	}
	
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(@RequestParam(value = "userid") Integer userid,
			@RequestParam(value = "username") String username
		) {
		userService.updateUser(userid, username);		
		return Util.getSuccessResult();
	}

	@ResponseBody
	@TokenRequired
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteUser(
			@PathVariable("id") Integer userid
		) {   
	    userService.deleteUser(userid); 
	    return Util.getSuccessResult();
	}
}
