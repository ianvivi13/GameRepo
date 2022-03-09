package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import Models.User;
import Models.UserList;

public class UserListTest {
	private User userOne;
	private User userTwo;
	private User userThree;
	private User userFour;
	
	private UserList userList;
	
	@Before
	public void setUp() {
		userOne = new User("Admin", "Password");
		userTwo = new User("User", "passWORD");
		userThree = new User("Three", "Pass");
		userFour = new User("AdMIn", "PASS");
		
		userList = new UserList();
	}
	
	@Test
	public void createUserTest() {
		userList.createUser(userOne.getUsername(), "Password");
		userList.createUser(userTwo.getUsername(), "passWORD");
		userList.createUser(userThree.getUsername(), "Pass");
		userList.createUser(userFour.getUsername(), "PASS");
		
		assertEquals(userList.getListSize(), 3);
	}
}