package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.BlackJackController;
import Models.BlackJackModel;
import Models.Pile;
import Models.Player;
import Models.Rank;
import Models.StandardCard;
import Models.Suit;
import Models.TurnOrder;

public class BlackJackControllerTest{
	
	private BlackJackController controller;
	private TurnOrder turns;
	
	@Before
	public void setUp() {
		controller = new BlackJackController("66666-66666", "blj");
		turns = new TurnOrder();
		turns.AddPlayer(1);
		turns.AddPlayer(2);
	}
	
	@Test
	public void testInitialize() throws Exception {
		controller.initialize();
		// main deck should have 48 cards
		assertEquals(48, controller.getMainPile().getNumCards());
		assertEquals(1000000000, controller.getMainPile().getVisibleIndex());	
		
		Player one = controller.getPlayers().get(turns.CurrentPlayer());
		Player two = controller.getPlayers().get(turns.CurrentPlayer() + 1);
		assertEquals(2, one.getPile().getNumCards());
		assertEquals(0, one.getPile().getVisibleIndex());
		
		assertEquals(2, two.getPile().getNumCards());
		assertEquals(0, two.getPile().getVisibleIndex());
	}
	
	@Test
	public void testHold() throws Exception {
		controller.hold();
		assertEquals(2, turns.CurrentPlayer());
	}
	
	@Test
	public void testHit() throws Exception {
		controller.initialize();
		
		assertEquals(48, controller.getMainPile().getNumCards());
		Player one = controller.getPlayers().get(turns.CurrentPlayer());
		Player two = controller.getPlayers().get(turns.CurrentPlayer() + 1);
		assertEquals(2, one.getPile().getNumCards());
		assertEquals(2, two.getPile().getNumCards());
		
		controller.hit();
		
		assertEquals(47, controller.getMainPile().getNumCards());
		assertEquals(3, one.getPile().getNumCards());
		
	}
	
	@Test
	public void testSplit() throws Exception {
	Pile p1 = new Pile();
	p1.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
	p1.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
	Player yer = new Player(false, 0);
	yer.setPile(p1);
	
	controller.split(yer);
	
	assertEquals(yer.getPile().getCard(0),yer.getAltPile().getCard(0));
	}
}