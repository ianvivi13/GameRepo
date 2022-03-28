package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.Card;
import Models.Rank;
import Models.Suit;

public class CardTest{
	
	private Card jackOfDiamonds;
	private Card twoOfClubs;
	private Card aceOfSpades;
	private Card threeOfHearts;
	private Card queenOfHearts;
	
	@Before
	public void setUp() {
		jackOfDiamonds = new Card(Rank.JACK, Suit.CLUBS);
		twoOfClubs = new Card(Rank.TWO, Suit.DIAMONDS);
		aceOfSpades = new Card(Rank.ACE, Suit.DIAMONDS);
		threeOfHearts = new Card(Rank.THREE, Suit.HEARTS);
		queenOfHearts = new Card(Rank.QUEEN, Suit.SPADES);
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
		assertEquals(Suit.CLUBS, jackOfDiamonds.getSuit());
		assertEquals(Suit.DIAMONDS, twoOfClubs.getSuit());
		assertEquals(Suit.DIAMONDS, aceOfSpades.getSuit());
		assertEquals(Suit.HEARTS, threeOfHearts.getSuit());
		assertEquals(Suit.SPADES, queenOfHearts.getSuit());
	}
	
	@Test
	public void testGetValue() throws Exception {
		assertEquals(Suit.CLUBS, jackOfDiamonds.getSuit());
		assertEquals(Suit.DIAMONDS, twoOfClubs.getSuit());
		assertEquals(Suit.DIAMONDS, aceOfSpades.getSuit());
		assertEquals(Suit.HEARTS, threeOfHearts.getSuit());
		assertEquals(Suit.SPADES, queenOfHearts.getSuit());
	}
	
	@Test
	public void testCompareTo() throws Exception {
		assertTrue(jackOfDiamonds.compareTo(twoOfClubs) < 0);
		assertTrue(new Card(Rank.JACK, Suit.CLUBS).compareTo(jackOfDiamonds) == 0);
		assertTrue(twoOfClubs.compareTo(jackOfDiamonds) > 0);
		assertTrue(queenOfHearts.compareTo(jackOfDiamonds) > 0);
		assertTrue(threeOfHearts.compareTo(aceOfSpades) > 0);
		assertTrue(aceOfSpades.compareTo(threeOfHearts) < 0);
		
		// ACE is the low rank
		assertTrue(aceOfSpades.compareTo(twoOfClubs) < 0);
	}
}