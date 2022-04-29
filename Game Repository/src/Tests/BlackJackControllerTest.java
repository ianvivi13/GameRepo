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
	private static BlackJackController control;
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
		control = new BlackJackController();
		one = new Player(true, db.createUser("dummy", "u"));
		two = new Player(true, db.createUser("baby", "t"));
		try {
		model.addPlayer(db.createPlayer(one));
		model.addPlayer(db.createPlayer(two));
		} catch(PlayerAlreadyExistsException e) {
			
		}
		try {
			modelId = control.initialize(model);
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
		//assertEquals(1, model.getPlayers().get(0).getPile().getVisibleIndex());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(1).getPile().getVisibleIndex());
		
		//Test if hold method works
		control.hold(model);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(two));
		
		//Test if hit method work
		assertEquals(48, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(1).getPile().getNumCards());
		control.hit(model);
		model = db.getGameFromGameId(modelId);
		
		assertEquals(47, model.getMainPile().getNumCards());
		assertEquals(3, model.getPlayers().get(1).getPile().getNumCards());
		
		//Test if freeze method works
		control.freeze(model);
		model = db.getGameFromGameId(modelId);
		assertEquals(1, model.getTurnOrder().getTurnList().size());
		
		control.checkWin(model);
		model = db.getGameFromGameId(modelId);
		assertTrue(control.checkWin(model));
	}

	
}