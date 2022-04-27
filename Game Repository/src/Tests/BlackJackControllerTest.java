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
	private IDatabase db;
	
	@Before
	public void setUp() {
<<<<<<< HEAD
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		pp = new Game(IDatabase.Key_Blackjack);
		control = new BlackJackController();
		one = new Player(true, 1);
		two = new Player(true, 2);
		pp.addPlayer(db.createPlayer(one));
		pp.addPlayer(db.createPlayer(two));
		
	}
	
	@Test
	public void testInitialize() throws Exception {
		control.initialize(pp);
	
		// main deck should have 48 cards
		assertEquals(48, pp.getMainPile().getNumCards());
		assertEquals(1000000000, pp.getMainPile().getVisibleIndex());	

		assertEquals(2, pp.getPlayers().get(0).getPile().getNumCards());
		assertEquals(0, pp.getPlayers().get(0).getPile().getVisibleIndex());
		
		assertEquals(2, pp.getPlayers().get(1).getPile().getNumCards());
		assertEquals(0, pp.getPlayers().get(1).getPile().getVisibleIndex());
=======
		/*
		controller = new BlackJackController();
		model = new BlackJackModel();
		turns = new TurnOrder();
		turns.AddPlayer(1);
		turns.AddPlayer(2);
		*/
>>>>>>> refs/remotes/origin/main
	}
	
//	@Test
//	public void testHold() throws Exception {
//		control.hold(pp);
//		assertEquals(2, control.getTurnOrder().CurrentPlayer());
//	}
	
//	@Test
//	public void testHit() throws Exception {
//		control.initialize();
//		
//		assertEquals(48, control.getMainPile().getNumCards());
//		
//		assertEquals(2, control.getPlayers().get(0).getPile().getNumCards());
//		
//		control.hit(one);
//		
//		assertEquals(47, control.getMainPile().getNumCards());
//		assertEquals(3, one.getPile().getNumCards());
//		
//	}
	
}