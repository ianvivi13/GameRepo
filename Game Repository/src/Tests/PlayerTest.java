package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.Player;
import Models.Pile;

public class PlayerTest {
	private Player player;
	private Player realPlayer;
	private Pile pile;
	private Pile altPile;
	private Player duplicatePlayer;
	
	@Before
	public void setUp() {
		player = new Player(false, 20);
		duplicatePlayer = new Player(false, 20);
		realPlayer = new Player(true, 2);
		pile = new Pile();
		altPile = new Pile();
		pile.populate();
		altPile.populateUno();
		pile.shuffle();
		altPile.shuffle();
		player.setPile(pile);
		player.setAltPile(altPile);
		duplicatePlayer.setPile(pile);
		duplicatePlayer.setAltPile(altPile);
	}
	
	@Test
	public void testGetIsHuman() {
		assertEquals(player.getIsHuman(), false);
		assertEquals(realPlayer.getIsHuman(), true);
	}
	
	@Test
	public void testGetID() {
		assertEquals(player.getUserBotID(), 20);
		assertEquals(realPlayer.getUserBotID(), 2);
	}
	
	@Test
	public void testPile() {
		assertTrue(player.getPile().equals(pile));
	}
	
	@Test
	public void testAltPile() {
		assertTrue(player.getAltPile().equals(altPile));
	}
	
	@Test
	public void testEquals() {
		assertTrue(player.equals(duplicatePlayer));
		assertFalse(player.equals(realPlayer));
	}
}