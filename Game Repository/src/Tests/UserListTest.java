package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import Models.User;
import Models.UserList;

public class UserListTest {
	private UserList userList;
	
	@Before
	public void setUp() {
		userList = new UserList();
		
		userList.createUser("Admin", "Password");
		userList.createUser("User", "passWORD");
		userList.createUser("Three", "Pass");
		userList.createUser("AdMIn", "PASS");
		
		System.out.println(userList.getUser("Admin"));
	}
	
	@Test
	public void testCreateUser() {
		assertEquals(userList.getListSize(), 3);
	}
	
	@Test
	public void testLogin() {
		assertTrue(userList.login("Admin", "Password"));
		assertTrue(userList.login("User", "passWORD"));
		assertFalse(userList.login("Three", "Password"));
		assertFalse(userList.login("AdMIn", "PASS"));
	}
}