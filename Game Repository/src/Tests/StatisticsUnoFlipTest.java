package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.StatisticsUnoFlip;

public class StatisticsUnoFlipTest {
	private StatisticsUnoFlip stats;
	
	@Before
	public void setUp() {
		stats = new StatisticsUnoFlip();
		for (int x = 0 ; x < 5 ; x++) {
			stats.GameWon();
		}
		for (int x = 0 ; x < 9 ; x++) {
			stats.GameLost();
		}
		for (int x = 0 ; x < 3 ; x++) {
			stats.GamePlayed();
		}
		for (int x = 0 ; x < 4 ; x++) {
			stats.Flip();
		}
		for (int x = 0 ; x < 12 ; x++) {
			stats.SkipAll();
		}
		for (int x = 0 ; x < 7 ; x++) {
			stats.PlusFive();
		}
	}
	
	// Testing the parent statistics
	@Test
	public void testCheckWins() {
		assertEquals(stats.GetGamesWon(),5);
	}
	
	@Test
	public void testCheckLoses() {
		assertEquals(stats.GetGamesLost(),9);
	}
	
	@Test
	public void testCheckPlays() {
		assertEquals(stats.GetGamesPlayed(),3);
	}
	
	// Testing the blackjack statistics
	@Test
	public void testCheckBlackjacks() {
		assertEquals(stats.GetFlips(),4);
	}
	
	@Test
	public void testCheckSplits() {
		assertEquals(stats.GetSkipAlls(),12);
	}
	
	@Test
	public void testCheckFiveCardWins() {
		assertEquals(stats.GetPlusFives(),7);
	}
}