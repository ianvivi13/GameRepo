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
	private static Game gameTwo;
	private static Player one;
	private static Player two;
	private static Player three;
	private static Player four;
	private static IDatabase db;
	private static String[] dumb;
	private static int gameId;
	private static int gameTwoId;
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
		gameTwo = new Game(IDatabase.Key_ExplodingKittens);
		one = new Player(true, 11);
		two = new Player(true, 21);
		three = new Player(true, 10);
		four = new Player(true, 20);
		game.addPlayer(db.createPlayer(one));
		game.addPlayer(db.createPlayer(two));
		gameTwo.addPlayer(db.createPlayer(three));
		gameTwo.addPlayer(db.createPlayer(four));
		
		twoCardPile = new Pile();
		threeCardPile = new Pile();
		fiveCardPile = new Pile();
		notCatsPile = new Pile();
		
		gameId = db.createGame(game);
		gameTwoId = db.createGame(gameTwo);
		try {
			ExplodingKittensController.initialize(gameId);
			ExplodingKittensController.initialize(gameTwoId);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testExplodingKittensGame() {
		gameTwo = db.getGameFromGameId(gameTwoId);
		ExplodingKittensCard exp = new ExplodingKittensCard(Type.ExplodingKitten);
		ExplodingKittensCard d = new ExplodingKittensCard(Type.Defuse);
		ExplodingKittensCard cm = new ExplodingKittensCard(Type.Cattermelon);
		assertTrue(gameTwo.getMainPile().contains(exp));
		for(Player p : gameTwo.getPlayers()) {
			assertTrue(ExplodingKittensController.checkDefuse(gameTwoId));
			assertFalse(ExplodingKittensController.checkKitten(gameTwoId));
			assertEquals(8, p.getPile().getNumCards());
			gameTwo.nextTurn();
		}
		assertEquals(98, gameTwo.getMainPile().getNumCards());
		assertEquals(0, gameTwo.getAltPile().getNumCards());
		assertFalse(ExplodingKittensController.checkWin(gameTwoId));
		
		gameTwo.getPlayers().get(0).getPile().addCard(exp);
		db.updatePlayer(db.getPlayerIdFromPlayer(gameTwo.getPlayers().get(0)), gameTwo.getPlayers().get(0));
		assertEquals(9, gameTwo.getPlayers().get(0).getPile().getNumCards());
		assertTrue(ExplodingKittensController.checkKitten(gameTwoId));
		assertFalse(ExplodingKittensController.checkWin(gameTwoId));
		
		ExplodingKittensController.defuse(gameTwoId);
		gameTwo = db.getGameFromGameId(gameTwoId);
		assertEquals(99, gameTwo.getMainPile().getNumCards());
		assertEquals(1, gameTwo.getAltPile().getNumCards());
		assertEquals(7, gameTwo.getPlayers().get(0).getPile().getNumCards());
		assertFalse(ExplodingKittensController.checkWin(gameId));
		
		assertEquals(2, gameTwo.getTurnOrder().getTurnList().size());
		gameTwo.getPlayers().get(0).getPile().addCard(exp);
		//Gets rid of any extra defuses
		while(gameTwo.getPlayers().get(0).getPile().contains(d)) {
			gameTwo.getPlayers().get(0).getPile().removeCard(d);
		}
		db.updateGame(gameTwoId, gameTwo);
		gameTwo = db.getGameFromGameId(gameTwoId);
		ExplodingKittensController.defuse(gameTwoId);
		gameTwo = db.getGameFromGameId(gameTwoId);
		assertEquals(99, gameTwo.getMainPile().getNumCards());
		assertEquals(1 + gameTwo.getPlayers().get(0).getPile().getNumCards(), gameTwo.getAltPile().getNumCards());
		assertTrue(ExplodingKittensController.checkWin(gameTwoId));
	}
	
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
	public void testSkip() {
		int current = game.getTurnOrder().CurrentPlayer();
		ExplodingKittensController.skip(gameId);
		game = db.getGameFromGameId(gameId);
		assertTrue(current != game.getTurnOrder().CurrentPlayer());
		
	}
	
	@Test
	public void testSeeFuture() {
		game = db.getGameFromGameId(gameId);
		Pile beforePile = game.getMainPile();
		ExplodingKittensController.seeFuture(gameId);
		assertTrue(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getNumCards() == 3);
		ExplodingKittensController.seeFutureExit(gameId);
		game = db.getGameFromGameId(gameId);
		assertTrue(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getNumCards() == 0);
		Pile afterPile = game.getMainPile();
		assertTrue(beforePile.equals(afterPile));
	}
	
	@Test
	public void testalterFuture() {
		ArrayList<Integer> order = new ArrayList<>();
		order.add(0);
		order.add(1);
		order.add(2);
		game = db.getGameFromGameId(gameId);
		ExplodingKittensController.seeFuture(gameId);
		assertTrue(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getNumCards() == 3);
		Pile handOrder = new Pile();
		handOrder.addCard(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getCard(order.get(0)));
		handOrder.addCard(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getCard(order.get(1)));
		handOrder.addCard(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getAltPile().getCard(order.get(2)));
		ExplodingKittensController.alterFuture(gameId, order);
		game = db.getGameFromGameId(gameId);
		Pile pileOrder = new Pile();
		pileOrder.addCard(game.getMainPile().getCard(game.getMainPile().getNumCards() - 3));
		pileOrder.addCard(game.getMainPile().getCard(game.getMainPile().getNumCards() - 2));
		pileOrder.addCard(game.getMainPile().getCard(game.getMainPile().getNumCards() - 1));
		assertTrue(handOrder.equals(pileOrder));
	}
	
	@Test
	public void testDrawFromBottom() {
		game = db.getGameFromGameId(gameId);
		Object topCard = game.getMainPile().getTopCard();
		int mainNumCards = game.getMainPile().getNumCards();
		int playerNumCards = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getPile().getNumCards();
		ExplodingKittensController.drawFromBottom(gameId);
		game = db.getGameFromGameId(gameId);
		assertTrue(game.getMainPile().getTopCard().equals(topCard));
		assertTrue(mainNumCards == game.getMainPile().getNumCards() + 1);
		assertTrue(playerNumCards == db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getPile().getNumCards() - 1);
	}
	
	@Test
	public void testFavor() {
		game = db.getGameFromGameId(gameId);
		int index = 3;
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		int currentNumCards = current.getPile().getNumCards();
		game.nextTurn();
		db.updateGame(gameId, game);
		game = db.getGameFromGameId(gameId);
		Player target = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		int targetNumCards = target.getPile().getNumCards();
		game.nextTurn();
		db.updateGame(gameId, game);
		game = db.getGameFromGameId(gameId);
		Object targetCard = target.getPile().getCard(index);
		assertTrue(!current.equals(target));
		ExplodingKittensController.favor(gameId, target, index);
		game = db.getGameFromGameId(gameId);
		current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		assertTrue(target.getPile().getNumCards() + 1 == targetNumCards);
		assertTrue(current.getPile().getNumCards() - 1 == currentNumCards);
		assertTrue(current.getPile().getPile().get(current.getPile().getNumCards() - 1).equals(targetCard));
	}
	
	@Test
	public void testStealCard() {
		game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		int currentNumCards = current.getPile().getNumCards();
		game.nextTurn();
		db.updateGame(gameId, game);
		game = db.getGameFromGameId(gameId);
		Player target = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		int targetNumCards = target.getPile().getNumCards();
		game.nextTurn();
		db.updateGame(gameId, game);
		game = db.getGameFromGameId(gameId);
		ExplodingKittensController.stealCard(gameId, target);
		game = db.getGameFromGameId(gameId);
		current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		assertTrue(target.getPile().getNumCards() + 1 == targetNumCards);
		assertTrue(current.getPile().getNumCards() - 1 == currentNumCards);
	}
	
	@Test
	public void testChooseCard() {
		game = db.getGameFromGameId(gameId);
		ExplodingKittensCard d = new ExplodingKittensCard(Type.Defuse);
		int index = 0;
		game.getAltPile().addCard(d);
		db.updateGame(gameId, game);
		game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		int currentNumCards = current.getPile().getNumCards();
		int discardNumCards = game.getAltPile().getNumCards();
		ExplodingKittensController.chooseCard(gameId, index);
		game = db.getGameFromGameId(gameId);
		current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		assertTrue(game.getAltPile().getNumCards() + 1 == discardNumCards);
		assertTrue(current.getPile().getNumCards() - 1 == currentNumCards);
		assertTrue(current.getPile().contains(d));
	}
}
