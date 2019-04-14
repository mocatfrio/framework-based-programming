package com.mocatfrio.model;

public class User {
	private Integer userid;
	private String username;
	
	public User(Integer userid, String username) {
		this.userid = userid;
		this.username = username;
	}

	public Integer getUserId() {
		return userid;
	}

	public void setUserId(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
