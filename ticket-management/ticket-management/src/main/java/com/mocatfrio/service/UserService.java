package com.mocatfrio.service;

import java.util.List;
import com.mocatfrio.model.User;

public interface UserService {
	// READ
	List<User> getAllUsers();
	User getUser(Integer userid);
	User getUser(String username, String password, Integer usertype);
	User getUserByToken(String token);
	
	// CREATE
	void createUser(String username, String password, Integer usertype);
	
	// UPDATE
	void updateUser(Integer userid, String username);
	
	// DELETE
	void deleteUser(Integer userid);
}
