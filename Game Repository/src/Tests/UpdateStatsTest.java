package Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Models.StatisticsBlackjack;
import Models.StatisticsExplodingKittens;
import Models.StatisticsGlobal;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;

public class UpdateStatsTest {
	static IDatabase db;
	static String[] dumb = new String[0];
	static StatisticsBlackjack blackjackStat = new StatisticsBlackjack();
	static StatisticsUno unoStat = new StatisticsUno();
	static StatisticsUnoFlip flipStat = new StatisticsUnoFlip();
	static StatisticsExplodingKittens explodingStat = new StatisticsExplodingKittens();
	static StatisticsGlobal globalStat = new StatisticsGlobal();
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
			db.createAllStats(db.createUser("FunnyUser69", "Pass"));
		} catch(IOException e) {
			
		} finally {
			
		}
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
} 
