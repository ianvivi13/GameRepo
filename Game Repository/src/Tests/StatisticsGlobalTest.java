package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.StatisticsGlobal;

public class StatisticsGlobalTest {
	private StatisticsGlobal stats;
	
	@Before
	public void setUp() {
		stats = new StatisticsGlobal();
		for (int x = 0 ; x < 5 ; x++) {
			stats.GameWon();
		}
		for (int x = 0 ; x < 9 ; x++) {
			stats.GameLost();
		}
		for (int x = 0 ; x < 3 ; x++) {
			stats.GamePlayed();
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
	
	// Testing the global statistics
	@Test
	public void testCheckRatio() {
		assertTrue(stats.GetRatio() == 0);
		stats.UpdateRatio();
		assertTrue(stats.GetRatio() == 5/9);
	}
}