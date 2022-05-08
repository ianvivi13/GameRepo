package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.User;
import Models.UsernameGenerator;

public class UserTest {
	private User user;
	
	@Before
	public void setUp() {
		user = new User("Admin", "Password");
	}
	
	@Test
	public void testGetUsername() {
		assertEquals(user.getUsername(), "Admin");
	}
	
	@Test
	public void testCheckPassword() {
		assertEquals(user.checkPassword("Password"), true);
	}
	

}