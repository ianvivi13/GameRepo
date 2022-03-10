package Models;

public class User {
	private String username;
	private String password;
	
	public User(String name, String pass) {
		this.username = name;
		this.password = pass;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public void setPassword(String name) {
		this.password = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean checkPassword(String pass) {
		return pass == password;
	}
	
}