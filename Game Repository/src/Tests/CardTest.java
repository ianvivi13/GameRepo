package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.StandardCard;
import Models.Rank;
import Models.Suit;

public class CardTest{
	
	private StandardCard jackOfDiamonds;
	private StandardCard twoOfClubs;
	private StandardCard aceOfSpades;
	private StandardCard threeOfHearts;
	private StandardCard queenOfHearts;
	
	@Before
	public void setUp() {
		jackOfDiamonds = new StandardCard(Rank.JACK, Suit.DIAMONDS);
		twoOfClubs = new StandardCard(Rank.TWO, Suit.CLUBS);
		aceOfSpades = new StandardCard(Rank.ACE, Suit.SPADES);
		threeOfHearts = new StandardCard(Rank.THREE, Suit.HEARTS);
		queenOfHearts = new StandardCard(Rank.QUEEN, Suit.HEARTS);
	}
	
	@Test
	public void testGetRank() throws Exception {
		assertEquals(Rank.JACK, jackOfDiamonds.getRank());
		assertEquals(Rank.TWO, twoOfClubs.getRank());
		assertEquals(Rank.ACE, aceOfSpades.getRank());
		assertEquals(Rank.THREE, threeOfHearts.getRank());
		assertEquals(Rank.QUEEN, queenOfHearts.getRank());
	}
	
	@Test
	public void testGetSuit() throws Exception {
		assertEquals(Suit.DIAMONDS, jackOfDiamonds.getSuit());
		assertEquals(Suit.CLUBS, twoOfClubs.getSuit());
		assertEquals(Suit.SPADES, aceOfSpades.getSuit());
		assertEquals(Suit.HEARTS, threeOfHearts.getSuit());
		assertEquals(Suit.HEARTS, queenOfHearts.getSuit());
	}
	
	@Test
	public void testGetValue() throws Exception {
		assertEquals(10, jackOfDiamonds.getValues());
		assertEquals(2, twoOfClubs.getValues());
		assertEquals(3, threeOfHearts.getValues());
		assertEquals(10, queenOfHearts.getValues());
	}
	
	@Test
	public void testCompareTo() throws Exception {
		assertTrue(jackOfDiamonds.compareTo(twoOfClubs) > 0);
		assertTrue(new StandardCard(Rank.JACK, Suit.DIAMONDS).compareTo(jackOfDiamonds) == 0);
		assertTrue(twoOfClubs.compareTo(jackOfDiamonds) < 0);
		assertTrue(queenOfHearts.compareTo(jackOfDiamonds) > 0);
		assertTrue(threeOfHearts.compareTo(aceOfSpades) < 0);
		assertTrue(aceOfSpades.compareTo(threeOfHearts) > 0);
		
		// ACE is the low rank
		assertTrue(aceOfSpades.compareTo(twoOfClubs) > 0);
	}
}