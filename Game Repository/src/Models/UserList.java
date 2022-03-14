package Models;

import java.util.*;

public class UserList {
	private TreeMap<String, User> userLogin;
	
	public UserList() {
		userLogin = new TreeMap<String, User>();
		User admin = new User("Admin", "admin");
		userLogin.put("admin", admin);
	}
	
	public void createUser(String username, String password) {
		if(!userLogin.containsKey(username.toLowerCase())) {
			User user = new User(username, password);
			userLogin.put(username.toLowerCase(), user);
		}
	}
	
	public boolean login(String name, String pass) {
		if(userLogin.containsKey(name.toLowerCase())) {
			return userLogin.get(name.toLowerCase()).checkPassword(pass);
		}
		
		return false;
	}
	
	public User getUser(String username) {
		if(userLogin.containsKey(username.toLowerCase())) {
			return userLogin.get(username.toLowerCase());
		}
		
		return null;
	}
	
	public int getListSize() {
		return userLogin.size();
	}
	
	public TreeMap<String, User> getList() {
		return userLogin;
	}
}