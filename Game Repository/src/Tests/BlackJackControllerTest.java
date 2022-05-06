package Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Database.elves.PlayerAlreadyExistsException;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class BlackJackControllerTest{
	
	private static Game model;
	private static Player one;
	private static Player two;
	private static Pile playerOne;
	private static Pile playerTwo;
	private static Pile main;
	private static IDatabase db;
	private static String[] dumb;
	private static int modelId;
	
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e){
		}
		model = new Game(IDatabase.Key_Blackjack);
		one = new Player(true, db.createUser("dummy", "u"));
		two = new Player(true, db.createUser("baby", "t"));
		try {
		model.addPlayer(db.createPlayer(one));
		model.addPlayer(db.createPlayer(two));
		} catch(PlayerAlreadyExistsException e) {
			
		}
		playerOne = new Pile();
		playerTwo = new Pile();
		main = new Pile();
		
		main.addCard(new StandardCard(Rank.EIGHT, Suit.DIAMONDS));
		main.addCard(new StandardCard(Rank.SIX, Suit.HEARTS));
		main.addCard(new StandardCard(Rank.JACK, Suit.SPADES));
		main.addCard(new StandardCard(Rank.ACE, Suit.DIAMONDS));
		main.addCard(new StandardCard(Rank.THREE, Suit.CLUBS));
		main.addCard(new StandardCard(Rank.TWO, Suit.HEARTS));
		main.addCard(new StandardCard(Rank.KING, Suit.SPADES));
		main.addCard(new StandardCard(Rank.FOUR, Suit.CLUBS));
		main.addCard(new StandardCard(Rank.JACK, Suit.HEARTS));
		main.addCard(new StandardCard(Rank.THREE, Suit.SPADES));
		
		playerOne.addCard(new StandardCard(Rank.TEN, Suit.CLUBS));
		playerOne.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
		
		playerTwo.addCard(new StandardCard(Rank.TWO, Suit.CLUBS));
		playerTwo.addCard(new StandardCard(Rank.FIVE, Suit.SPADES));
		
		model.setMainPile(main);
		model.getPlayers().get(0).setPile(playerOne);
		model.getPlayers().get(1).setPile(playerTwo);
		
		modelId = db.createGame(model);
		db.updateGame(modelId, model);
		
		
	}
	
	@Test
	public void aTestInit() throws Exception {
		
		//Test if initialize method works
		model = db.getGameFromGameId(modelId);
		// main deck should have 48 cards
		assertEquals(10, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(0).getPile().getNumCards());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
	}
	
	@Test
	public void bTestHold() throws Exception {
		//Test if hold method works
		BlackJackController.hold(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(two));
	}
	
	@Test
	public void cTestHit() throws Exception {
		//Test if hit method work
		assertEquals(10, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
		BlackJackController.hit(modelId);
		model = db.getGameFromGameId(modelId);
		
		assertEquals(9, model.getMainPile().getNumCards());
		assertEquals(3, model.getPlayers().get(1).getPile().getNumCards());
		model = db.getGameFromGameId(modelId);
		assertFalse(BlackJackController.checkBust(modelId));
	}
	
	@Test
	public void dTestFreeze() throws Exception {
		//Test if freeze method works
		BlackJackController.freeze(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(1, model.getTurnOrder().getTurnList().size());
	}
	
	@Test
	public void eTestChecWin() throws Exception {
		//Test if checkWin method works
		model = db.getGameFromGameId(modelId);
		assertTrue(BlackJackController.checkWin(modelId));
	}
	
}