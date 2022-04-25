package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.BlackJackController;
import Models.Game;
import Models.Pile;
import Models.Player;
import Models.Rank;
import Models.StandardCard;
import Models.Suit;
import Models.TurnOrder;
import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class BlackJackControllerTest{
	
	private Game pp;
	private BlackJackController control;
	private Player one;
	private Player two;
	private TurnOrder turns;
	private IDatabase db;
	
	@Before
	public void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		control = new BlackJackController(IDatabase.Key_Blackjack);
		one = new Player(true, 1);
		two = new Player(true, 2);
		
		control.addPlayer(db.createPlayer(one));
		control.addPlayer(db.createPlayer(two));
		
	}
	
	@Test
	public void testInitialize() throws Exception {
		control.initialize();
	
		// main deck should have 48 cards
		assertEquals(48, control.getMainPile().getNumCards());
		assertEquals(1000000000, control.getMainPile().getVisibleIndex());	
		
//		one = control.getPlayers().get(turns.CurrentPlayer());
//		two = control.getPlayers().get(turns.CurrentPlayer() + 1);
//		assertEquals(2, one.getPile().getNumCards());
//		assertEquals(0, one.getPile().getVisibleIndex());
//		
//		assertEquals(2, two.getPile().getNumCards());
//		assertEquals(0, two.getPile().getVisibleIndex());
	}
	
//	@Test
//	public void testHold() throws Exception {
//		control.hold();
//		assertEquals(2, turns.CurrentPlayer());
//	}
	
//	@Test
//	public void testHit() throws Exception {
//		control.initialize();
//		
//		assertEquals(48, control.getMainPile().getNumCards());
//		Player one = control.getPlayers().get(turns.CurrentPlayer());
//		Player two = control.getPlayers().get(turns.CurrentPlayer() + 1);
//		assertEquals(2, one.getPile().getNumCards());
//		assertEquals(2, two.getPile().getNumCards());
//		
//		control.hit();
//		
//		assertEquals(47, control.getMainPile().getNumCards());
//		assertEquals(3, one.getPile().getNumCards());
//		
//	}
//	
//	@Test
//	public void testSplit() throws Exception {
//	Pile p1 = new Pile();
//	p1.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
//	p1.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
//	Player yer = new Player(false, 0);
//	yer.setPile(p1);
//	
//	control.split(yer);
//	
//	assertEquals(yer.getPile().getCard(0),yer.getAltPile().getCard(0));
//	}
}