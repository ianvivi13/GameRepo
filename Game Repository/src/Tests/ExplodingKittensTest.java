package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Models.ExplodingKittensCard;
import Models.ExplodingKittensController;
import Models.Game;
import Models.Pile;
import Models.Player;
import Models.Type;

public class ExplodingKittensTest{
	
	private static Game game;
	private static Player one;
	private static Player two;
	private static IDatabase db;
	private static String[] dumb;
	private static int gameId;
	private static Pile twoCardPile;
	private static Pile threeCardPile;
	private static Pile fiveCardPile;
	private static Pile notCatsPile;
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e){
			
		}
		game = new Game(IDatabase.Key_ExplodingKittens);
		gameId = db.createGame(game);
		one = new Player(true, 11);
		two = new Player(true, 21);
		game.addPlayer(db.createPlayer(one));
		game.addPlayer(db.createPlayer(two));
		
		twoCardPile = new Pile();
		threeCardPile = new Pile();
		fiveCardPile = new Pile();
		notCatsPile = new Pile();
		
		try {
			ExplodingKittensController.initialize(gameId);
			game.getMainPile().addExplodingKittens(game.getPlayers().size());
			game.getMainPile().shuffle();
			db.updateGame(gameId, game);
		} catch (Exception e) {
		}
	}
	
	/*@Test
	public void testExplodingKittensGame() {
		game = db.getGameFromGameId(gameId);
		ExplodingKittensCard exp = new ExplodingKittensCard(Type.ExplodingKitten);
		assertTrue(game.getMainPile().contains(exp));
		for(Player p : game.getPlayers()) {
			assertTrue(ExplodingKittensController.checkDefuse(gameId));
			assertFalse(ExplodingKittensController.checkKitten(gameId));
			assertEquals(8, p.getPile().getNumCards());
			game.nextTurn();
		}
		assertEquals(98, game.getMainPile().getNumCards());
		assertEquals(0, game.getAltPile().getNumCards());
		assertFalse(ExplodingKittensController.checkWin(gameId));
		
		game.getPlayers().get(0).getPile().addCard(exp);
		db.updatePlayer(db.getPlayerIdFromPlayer(game.getPlayers().get(0)), game.getPlayers().get(0));
		assertEquals(9, game.getPlayers().get(0).getPile().getNumCards());
		assertTrue(ExplodingKittensController.checkKitten(gameId));
		assertFalse(ExplodingKittensController.checkWin(gameId));
		
		ExplodingKittensController.defuse(gameId);
		game = db.getGameFromGameId(gameId);
		assertEquals(99, game.getMainPile().getNumCards());
		assertEquals(1, game.getAltPile().getNumCards());
		assertEquals(7, game.getPlayers().get(0).getPile().getNumCards());
		assertFalse(ExplodingKittensController.checkWin(gameId));
		
		assertEquals(2, game.getTurnOrder().getTurnList().size());
		game.getPlayers().get(0).getPile().addCard(exp);
		ExplodingKittensController.defuse(gameId);
		game = db.getGameFromGameId(gameId);
		assertEquals(99, game.getMainPile().getNumCards());
		assertEquals(8, game.getAltPile().getNumCards());
		assertTrue(ExplodingKittensController.checkWin(gameId));
	}*/
	
	@Test
	public void testCardRules() {
		ExplodingKittensCard cm = new ExplodingKittensCard(Type.Cattermelon);
		ExplodingKittensCard fc = new ExplodingKittensCard(Type.FeralCat);
		ExplodingKittensCard hp = new ExplodingKittensCard(Type.HairyPotatoCat);
		ExplodingKittensCard rc = new ExplodingKittensCard(Type.RainbowRalphingCat);
		ExplodingKittensCard tc = new ExplodingKittensCard(Type.TacoCat);
		ExplodingKittensCard d = new ExplodingKittensCard(Type.Defuse);
		
		twoCardPile.addCard(cm);
		twoCardPile.addCard(cm);
		assertTrue(ExplodingKittensController.twoCardRule(twoCardPile.getPile()));
		assertFalse(ExplodingKittensController.twoCardRule(threeCardPile.getPile()));
		
		threeCardPile.addCards(twoCardPile.getPile());
		threeCardPile.addCard(cm);
		assertTrue(ExplodingKittensController.threeCardRule(threeCardPile.getPile()));
		assertFalse(ExplodingKittensController.threeCardRule(twoCardPile.getPile()));
		
		fiveCardPile.addCard(cm);
		fiveCardPile.addCard(fc);
		fiveCardPile.addCard(hp);
		fiveCardPile.addCard(rc);
		fiveCardPile.addCard(tc);
		assertTrue(ExplodingKittensController.fiveCardRule(fiveCardPile.getPile()));
		assertFalse(ExplodingKittensController.fiveCardRule(threeCardPile.getPile()));
		
		notCatsPile.addCard(d);
		notCatsPile.addCard(d);
		assertFalse(ExplodingKittensController.twoCardRule(notCatsPile.getPile()));
	}
	
	@Test
	public void testAttack() {
		int current = game.getTurnOrder().CurrentPlayer();
		ExplodingKittensController.skip(gameId);
		assertTrue(current != game.getTurnOrder().CurrentPlayer());
	}
}
