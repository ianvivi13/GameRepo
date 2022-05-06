package Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
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

public class BlackJackControllerTest{
	
	private static Game model;
	private static Player one;
	private static Player two;
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
		modelId = db.createGame(model);
		try {
			BlackJackController.initialize(modelId);
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void testGameSim() throws Exception {
		
		//Test if initialize method works
		model = db.getGameFromGameId(modelId);
		// main deck should have 48 cards
		assertEquals(48, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(0).getPile().getNumCards());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
		
		//Test if hold method works
		BlackJackController.hold(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(two));
		
		//Test if hit method work
		assertEquals(48, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
		BlackJackController.hit(modelId);
		model = db.getGameFromGameId(modelId);
		
		assertEquals(47, model.getMainPile().getNumCards());
		assertEquals(3, model.getPlayers().get(1).getPile().getNumCards());
		assertTrue(BlackJackController.checkBust(modelId));
		
		//Test if freeze method works
		BlackJackController.freeze(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(1, model.getTurnOrder().getTurnList().size());
		
		model = db.getGameFromGameId(modelId);
		assertFalse(BlackJackController.checkWin(modelId));
		System.out.println(model.getPlayers().get(0).getPile().getValueStandard());
		System.out.println(model.getPlayers().get(1).getPile().getValueStandard());
	}
}