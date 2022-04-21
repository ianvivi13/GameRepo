package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

import Models.StandardCard;
import Models.StatisticsBlackjack;
import Models.StatisticsExplodingKittens;
import Models.StatisticsGlobal;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.ExplodingKittensCard;
import Models.Pile;
import Models.Player;
import Models.UnoCard;
import Models.UnoFlipCard;

import Models.Color;
import Models.Value;
import Models.Rank;
import Models.Suit;
import Models.Type;

import Models.TurnOrder;

public class DatabaseTest{
	private static IDatabase db;
	private static String[] dumb;
	
	private static StandardCard cardOne;
	private static StandardCard cardTwo;
	private static StandardCard cardThree;
	
	private static ExplodingKittensCard cardFour;
	private static ExplodingKittensCard cardFive;
	private static ExplodingKittensCard cardSix;
	
	private static UnoCard cardSeven;
	private static UnoCard cardEight;
	private static UnoCard cardNine;
	
	private static Pile pileOne;
	private static Pile pileTwo;
	private static Pile pileThree;
	
	private static Player playerOne;
	private static Player playerTwo;
	
	private static StatisticsBlackjack blackjackStat = new StatisticsBlackjack();
	private static StatisticsUno unoStat = new StatisticsUno();
	private static StatisticsUnoFlip flipStat = new StatisticsUnoFlip();
	private static StatisticsExplodingKittens explodingStat = new StatisticsExplodingKittens();
	private static StatisticsGlobal globalStat = new StatisticsGlobal();
	
	private static TurnOrder turnOrder = new TurnOrder();
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e) {
		}
		
		db.createAllStats(db.createUser("FunnyUser69", "Pass"));
		
		// turn order creation
		turnOrder.AddPlayer(1);
		turnOrder.AddPlayer(3);
		turnOrder.AddPlayer(7);
		turnOrder.AddTurn(3, 2);
		turnOrder.NextTurn();
		turnOrder.Reverse();
		
		// Card Creation

		cardOne = new StandardCard(Rank.ACE, Suit.SPADES);
		cardTwo = new StandardCard(Rank.TEN, Suit.DIAMONDS);
		cardThree = new StandardCard(Rank.SIX, Suit.CLUBS);
		
		cardFour = new ExplodingKittensCard(Type.AlterTheFuture);
		cardFive = new ExplodingKittensCard(Type.DrawFromBottom);
		cardSix = new ExplodingKittensCard(Type.Shuffle);
		
		cardSeven = new UnoCard(Color.BLUE, Value.Reverse);
		cardEight = new UnoCard(Color.RED, Value.Eight);
		cardNine = new UnoCard(Color.YELLOW, Value.Six);
		
		// Pile Creation
		
		pileOne = new Pile();
		pileOne.populate();
		pileOne.shuffle();
		
		pileTwo = new Pile();
		pileTwo.addCard(cardFour);
		pileTwo.addCard(cardFive);
		pileTwo.addCard(cardSix);
		pileTwo.shuffle();
		
		pileThree = new Pile();
		pileThree.populateUno();
		pileThree.shuffle();
		
		// Player Creation
		
		playerOne = new Player(true, 20);
		playerOne.setPile(new Pile());
		playerOne.setAltPile(new Pile());
		
		playerTwo = new Player(false, 20);
		playerTwo.setPile(pileOne);
		playerTwo.setPile(new Pile());
		
		// Setting Statistics
		
		blackjackStat.SetGamesPlayed(5);
		blackjackStat.SetGamesWon(2);
		blackjackStat.SetGamesLost(3);
		blackjackStat.SetBlackjacks(1);
		blackjackStat.SetSplits(1);
		blackjackStat.SetFiveCardWins(1);
		
		unoStat.SetGamesPlayed(7);
		unoStat.SetGamesWon(3);
		unoStat.SetGamesLost(4);
		unoStat.SetPlusFours(6);
		unoStat.SetSwaps(1);
		unoStat.SetWildCards(10);
		
		flipStat.SetGamesPlayed(20);
		flipStat.SetGamesWon(6);
		flipStat.SetGamesLost(14);
		flipStat.SetFlips(24);
		flipStat.SetSkipAlls(17);
		flipStat.SetPlusFives(11);
		
		explodingStat.SetGamesPlayed(6);
		explodingStat.SetGamesWon(2);
		explodingStat.SetGamesLost(4);
		explodingStat.SetDefuses(7);
		explodingStat.SetFavors(9);
		explodingStat.SetFutures(5);
		
		globalStat.SetGamesPlayed(38);
		globalStat.SetGamesWon(13);
		globalStat.SetGamesLost(25);
		globalStat.SetRank(2183);
		
	}
	
	@Test
	public void testGetStandardCard() {
		assertEquals(db.getStandardCardFromCardId(db.getCardIdFromStandardCard(cardOne)), cardOne);
		assertEquals(db.getStandardCardFromCardId(db.getCardIdFromStandardCard(cardTwo)), cardTwo);
		assertEquals(db.getStandardCardFromCardId(db.getCardIdFromStandardCard(cardThree)), cardThree);
	}
	
	@Test
	public void testGetExplodingKittensCard() {
		assertEquals(db.getExplodingKittensCardFromCardId(db.getCardIdFromExplodingKittensCard(cardFour)), cardFour);
		assertEquals(db.getExplodingKittensCardFromCardId(db.getCardIdFromExplodingKittensCard(cardFive)), cardFive);
		assertEquals(db.getExplodingKittensCardFromCardId(db.getCardIdFromExplodingKittensCard(cardSix)), cardSix);
	}
	
	@Test
	public void testGetUnoCard() {
		assertEquals(db.getUnoCardFromCardId(db.getCardIdFromUnoCard(cardSeven)), cardSeven);
		assertEquals(db.getUnoCardFromCardId(db.getCardIdFromUnoCard(cardEight)), cardEight);
		assertEquals(db.getUnoCardFromCardId(db.getCardIdFromUnoCard(cardNine)), cardNine);
	}
	
	
	@Test
	public void testUpdateBlackJackStats() {
		db.updateBlackjackStats(blackjackStat, "FunnyUser69");
		assertTrue(blackjackStat.GetGamesPlayed() == db.getBlackjackStats("FunnyUser69").GetGamesPlayed());
		assertTrue(blackjackStat.GetGamesWon() == db.getBlackjackStats("FunnyUser69").GetGamesWon());
		assertTrue(blackjackStat.GetGamesLost() == db.getBlackjackStats("FunnyUser69").GetGamesLost());
		assertTrue(blackjackStat.GetBlackjacks() == db.getBlackjackStats("FunnyUser69").GetBlackjacks());
		assertTrue(blackjackStat.GetSplits() == db.getBlackjackStats("FunnyUser69").GetSplits());
		assertTrue(blackjackStat.GetFiveCardWins() == db.getBlackjackStats("FunnyUser69").GetFiveCardWins());
	}
	
	@Test
	public void testUpdateUnoStats() {
		db.updateUnoStats(unoStat, "FunnyUser69");
		assertTrue(unoStat.GetGamesPlayed() == db.getUnoStats("FunnyUser69").GetGamesPlayed());
		assertTrue(unoStat.GetGamesWon() == db.getUnoStats("FunnyUser69").GetGamesWon());
		assertTrue(unoStat.GetGamesLost() == db.getUnoStats("FunnyUser69").GetGamesLost());
		assertTrue(unoStat.GetPlusFours() == db.getUnoStats("FunnyUser69").GetPlusFours());
		assertTrue(unoStat.GetSwaps() == db.getUnoStats("FunnyUser69").GetSwaps());
		assertTrue(unoStat.GetWildCards() == db.getUnoStats("FunnyUser69").GetWildCards());
	}
	
	@Test
	public void testUpdateUnoFlipStats() {
		db.updateUnoFlipStats(flipStat, "FunnyUser69");
		assertTrue(flipStat.GetGamesPlayed() == db.getUnoFlipStats("FunnyUser69").GetGamesPlayed());
		assertTrue(flipStat.GetGamesWon() == db.getUnoFlipStats("FunnyUser69").GetGamesWon());
		assertTrue(flipStat.GetGamesLost() == db.getUnoFlipStats("FunnyUser69").GetGamesLost());
		assertTrue(flipStat.GetFlips() == db.getUnoFlipStats("FunnyUser69").GetFlips());
		assertTrue(flipStat.GetSkipAlls() == db.getUnoFlipStats("FunnyUser69").GetSkipAlls());
		assertTrue(flipStat.GetPlusFives() == db.getUnoFlipStats("FunnyUser69").GetPlusFives());
	}
	
	@Test
	public void testUpdateExplodingKittensStats() {
		db.updateExplodingKittensStats(explodingStat, "FunnyUser69");
		assertTrue(explodingStat.GetGamesPlayed() == db.getExplodingKittenStats("FunnyUser69").GetGamesPlayed());
		assertTrue(explodingStat.GetGamesWon() == db.getExplodingKittenStats("FunnyUser69").GetGamesWon());
		assertTrue(explodingStat.GetGamesLost() == db.getExplodingKittenStats("FunnyUser69").GetGamesLost());
		assertTrue(explodingStat.GetDefuses() == db.getExplodingKittenStats("FunnyUser69").GetDefuses());
		assertTrue(explodingStat.GetFavors() == db.getExplodingKittenStats("FunnyUser69").GetFavors());
		assertTrue(explodingStat.GetFutures() == db.getExplodingKittenStats("FunnyUser69").GetFutures());
	}
	
	@Test
	public void testUpdateGlobalStats() {
		db.updateGlobalStats(globalStat, "FunnyUser69");
		assertTrue(globalStat.GetGamesPlayed() == db.getGlobalStats("FunnyUser69").GetGamesPlayed());
		assertTrue(globalStat.GetGamesWon() == db.getGlobalStats("FunnyUser69").GetGamesWon());
		assertTrue(globalStat.GetGamesLost() == db.getGlobalStats("FunnyUser69").GetGamesLost());
		assertTrue(globalStat.GetRank() == db.getGlobalStats("FunnyUser69").GetRank());
	}
	
	@Test
	public void testStandardPile() {
		int pileId = db.createPile(pileOne);
		assertTrue(db.getPileFromPileId(pileId).equals(pileOne));
		pileOne.shuffle();
		db.updatePile(pileId, pileOne);
		assertTrue(db.getPileFromPileId(pileId).equals(pileOne));
	}
	
	@Test
	public void testExplodingKittensPile() {
		int pileId = db.createPile(pileTwo);
		assertTrue(db.getPileFromPileId(pileId).equals(pileTwo));
		pileTwo.shuffle();
		db.updatePile(pileId, pileTwo);
		assertTrue(db.getPileFromPileId(pileId).equals(pileTwo));
	}
	
	@Test
	public void testUnoPile() {
		int pileId = db.createPile(pileThree);
		assertTrue(db.getPileFromPileId(pileId).equals(pileThree));
		pileTwo.shuffle();
		db.updatePile(pileId, pileThree);
		assertTrue(db.getPileFromPileId(pileId).equals(pileThree));
	}
	
	@Test
	public void testPlayerOne() {
		int playerId = db.createPlayer(playerOne);
		assertTrue(db.getPlayerFromPlayerId(playerId).equals(playerOne));
		Pile p = new Pile();
		p.populate();
		playerOne.setPile(p);
		db.updatePlayer(playerId, playerOne);
		assertTrue(db.getPlayerFromPlayerId(playerId).equals(playerOne));
	}
	
	@Test
	public void testPlayerTwo() {
		Pile p = new Pile();
		Pile aP = new Pile();
		p.populateUno();
		aP.populateUno();
		
		playerTwo.setPile(p);
		playerTwo.setAltPile(aP);
		int playerId = db.createPlayer(playerTwo);
		
		assertTrue(db.getPlayerFromPlayerId(playerId).equals(playerTwo));
		
		p.shuffle();
		aP.shuffle();
		
		playerTwo.setPile(p);
		playerTwo.setAltPile(aP);
		
		db.updatePlayer(playerId, playerTwo);
		assertTrue(db.getPlayerFromPlayerId(playerId).equals(playerTwo));
	}
	
	@Test
	public void testTurnOrder() {
		int turnId = db.createTurnOrder(turnOrder);
		assertTrue(db.getTurnOrderFromTurnOrderId(turnId).equals(turnOrder));
		turnOrder = db.getTurnOrderFromTurnOrderId(turnId);
		turnOrder.AddPlayer(8);
		turnOrder.Reverse();
		turnOrder.NextTurn();
		turnOrder.NextTurn();
		turnOrder.NextTurn();
		turnOrder.NextTurn();
		db.updateTurnOrder(turnId, turnOrder);
		assertTrue(db.getTurnOrderFromTurnOrderId(turnId).equals(turnOrder));
	}
	
	@Test
	public void testDeleters() {
		int playerId = db.getPlayerIdFromPlayer(playerOne);
		db.deletePlayer(playerOne);
		assertFalse(db.getPlayerFromPlayerId(playerId).equals(playerOne));
		
		playerId = db.getPlayerIdFromPlayer(playerTwo);
		db.deletePlayer(playerTwo);
	}
}