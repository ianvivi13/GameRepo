package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import Models.User;
import Models.TurnOrder;

public class TurnOrderTest {
	private TurnOrder turns;
	
	@Before
	public void setUp() {
		turns = new TurnOrder();
		turns.AddPlayer("1");
		turns.AddPlayer("2");
		turns.AddPlayer("3");
		turns.AddPlayer("4");
	}
	
	@Test
	public void FakeGamePlay() {
		assertEquals(turns.CurrentPlayer(),"1");	// current player 1
		turns.NextTurn();							// next turn
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.Reverse();							// reverse order
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"2");	// current player 2
		turns.AddTurn("4",2);						// add 2 turns to player 4
		turns.NextTurn();							// next turn
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"4");	// current player 4
		turns.NextTurn();							// next turn
		turns.NextTurn();							// next turn
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.Reverse();							// reverse order
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"4");	// current player 4
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"1");	// current player 1
		
		turns.SetTurn("3");							// set current player to 3
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.AddTurn("2", 2);						// add 2 turns to player 2
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"4");	// current player 4
		turns.Reverse();							// reverse order
		turns.NextTurn();							// next turn
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"2");	// current player 2
		assertEquals(turns.RemoveAllTurns("2"),2);	// remove all turns from player 2, should be 2
		assertEquals(turns.CurrentPlayer(),"2");	// current player 2
		turns.Reverse();							// reverse order
		turns.NextTurn();							// next turn
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.RemovePlayer("2");					// remove player 2
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.AddTurn("3", 1);						// add 1 turn to player 3
		assertEquals(turns.CurrentPlayer(),"3");	// current player 3
		turns.RemovePlayer("3");					// remove player 3
		assertEquals(turns.CurrentPlayer(),"1");	// current player 3
		
		
		
		
	}
}