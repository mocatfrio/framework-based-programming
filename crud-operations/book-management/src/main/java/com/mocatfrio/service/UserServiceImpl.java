package com.mocatfrio.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.mocatfrio.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public List<User> getAllUsers() {
		return this.users;
	}
	
	@Override
	public User getUser(Integer userid) {
		return users.stream().filter(x -> x.getUserId() == userid).findAny().orElse(new User(0, "Not available"));
	}
	
	@Override
	public void createUser(Integer userId, String username) {
		User user = new User(userId, username);
		this.users.add(user);
	}
	
	@Override
	public void updateUser(Integer userid, String username) {
		users.stream().filter(x -> x.getUserId() == userid).findAny().orElseThrow(() -> new RuntimeException("Item not found")).setUsername(username);
	}
	
	@Override
	public void deleteUser(Integer userid) {
		users.removeIf((User u) -> u.getUserId() == userid);
	}
	
	// dummy users
	public static List<User> users;
	public UserServiceImpl() {
		users = new LinkedList<>();
		users.add(new User(1, "Hafara"));
		users.add(new User(2, "Akbar"));
		users.add(new User(3, "Alam"));
	}
}
