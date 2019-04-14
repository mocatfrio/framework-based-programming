package com.mocatfrio.restapp;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.mocatfrio.service.UserService;
import com.mocatfrio.model.User;

@RestController
@RequestMapping("/user")

public class UserController {
	@Autowired
	UserService userService;
	
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
	
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String, Object> createUser(
		@RequestParam(value = "userid") Integer userid,
		@RequestParam(value = "username") String username)
	{
		Map<String, Object> map = new LinkedHashMap<>();
		userService.createUser(userid, username);
		map.put("result", "added");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(
			@RequestParam(value = "userid") Integer userid,
			@RequestParam(value = "username") String username)
	{
		Map<String, Object> map = new LinkedHashMap<>();
		userService.updateUser(userid, username);
		map.put("result", "updated");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteUser(
			@PathVariable("id") Integer userid) 
	{
		Map<String, Object> map = new LinkedHashMap<>();
		userService.deleteUser(userid);
		map.put("result", "deleted");
		return map;
	}
}
