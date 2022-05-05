package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.StatisticsBlackjack;

public class StatisticsBlackjackTest {
	private StatisticsBlackjack stats;
	
	@Before
	public void setUp() {
		stats = new StatisticsBlackjack();
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
			stats.Blackjack();
		}
		for (int x = 0 ; x < 12 ; x++) {
			stats.Hit();
		}
		for (int x = 0 ; x < 7 ; x++) {
			stats.Froze();
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
		assertEquals(stats.GetBlackjacks(),4);
	}
	
	@Test
	public void testCheckSplits() {
		assertEquals(stats.GetHits(),12);
	}
	
	@Test
	public void testCheckFiveCardWins() {
		assertEquals(stats.GetFroze(),7);
	}
}