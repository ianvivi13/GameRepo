package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import Models.UnoCard;
import Models.StandardCard;
import Models.ExplodingKittensCard;
import Models.Pile;
import Models.Rank;
import Models.Suit;

public class PileTest{
	
	private Pile pile;
	private Pile fullPile;
	private Pile unoPile;
	private Pile fullUnoPile;
	private Pile fullExpPile;
	private Pile duplicatePile;
	
	@Before
	public void setUp() {
		// pile which starts out empty
		pile = new Pile();
		unoPile = new Pile();
		
		// pile which starts out full
		fullPile = new Pile();
		fullUnoPile = new Pile();
		fullExpPile = new Pile();
		
		fullPile.populate();
		fullUnoPile.populateUno();
		fullExpPile.populateExplodingKittens(2);
		fullExpPile.addExplodingKittens(2);
		
		duplicatePile = new Pile();
		duplicatePile.populate();
	}
	
	@Test
	public void testGetNumCards() throws Exception {
		assertEquals(0, pile.getNumCards());
		assertEquals(52, fullPile.getNumCards());
		
		assertEquals(0, unoPile.getNumCards());
		assertEquals(108, fullUnoPile.getNumCards());
		
	}
	
	@Test
	public void testAllCardsPresentInFullPile() throws Exception {
		// A TreeSet will not allow duplicate Cards to be added
		// (assuming that the Card class properly implements the
		// Comparable interface)
		TreeSet<Object> cards = new TreeSet<Object>();
		
		// Add all Cards in the full pile to the TreeSet
		for (int i = 0; i < fullPile.getNumCards(); i++) {
			cards.add(fullPile.getCard(i));
		}
		
		// If there are 52 cards in the TreeSet, that means that
		// all Cards are accounted for
		assertEquals(52, cards.size());
	}
	
	@Test
	public void testGetInitialVisibleIndex() throws Exception {
		assertEquals(0, pile.getVisibleIndex());
	}
	
	@Test
	public void testSetExposeIndex() throws Exception {
		fullPile.setVisibleIndex(51);
		assertEquals(51, fullPile.getVisibleIndex());
	}
	
	@Test
	public void testIsEmpty() throws Exception {
		assertTrue(pile.isEmpty());
		assertFalse(fullPile.isEmpty());
		
		pile.addCard(new StandardCard(Rank.QUEEN, Suit.HEARTS));
		assertFalse(pile.isEmpty());
	}
		
	@Test
	public void testGetTopCard() throws Exception {
		StandardCard sixOfSpades = new StandardCard(Rank.SIX, Suit.SPADES);
		pile.addCard(sixOfSpades);
		assertEquals(sixOfSpades, pile.getTopCard());
		StandardCard fourOfClubs = new StandardCard(Rank.FOUR, Suit.CLUBS);
		pile.addCard(fourOfClubs);
		assertEquals(fourOfClubs, pile.getTopCard());
	}
	
	@Test
	public void testGetTopCardOnEmptyPile() throws Exception {
		// Calling getTopCard on an empty Pile object should throw NoSuchElementException
		try {
			pile.getTopCard();
			assertTrue("getTopCard on an empty Pile should throw NoSuchElementException", false);
		} catch (NoSuchElementException e) {
			// Good
		}
	}
	
	@Test
	public void testGetCardWithInvalidIndex() throws Exception {
		try {
			fullPile.getCard(-1); // this should throw an exception
			assertTrue("getCard with a negative index should throw NoSuchElementException", false);
		} catch (NoSuchElementException e) {
			// good
		}
		
		fullPile.getCard(51); // this shouldn't throw an exception
		try {
			fullPile.getCard(52); // this should throw an exception
			assertTrue("getCard with a too-high index should throw NoSuchElementException", false);
		} catch (NoSuchElementException e) {
			// good
		}
	}
	
	
	
	@Test
	public void testGetIndexOfTopCard() throws Exception {
		Object sixOfSpades = new StandardCard(Rank.SIX, Suit.SPADES);
		pile.addCard(sixOfSpades);
		assertEquals(0, pile.getIndexOfTopCard());
		Object fourOfClubs = new StandardCard(Rank.FOUR, Suit.CLUBS);
		pile.addCard(fourOfClubs);
		assertEquals(1, pile.getIndexOfTopCard());
	}
	
	@Test
	public void testRemoveCards() throws Exception {
		Util.addAllCards(pile);
		
		// Removing the top 16 cards should give us
		// the jack of hearts through the king of spades.
		// Note that ACE is the low rank.
		ArrayList<Object> removed = pile.removeCards(16);
		
		int index = 0;
		assertEquals(new StandardCard(Rank.JACK, Suit.HEARTS), removed.get(index++));
		assertEquals(new StandardCard(Rank.QUEEN, Suit.HEARTS), removed.get(index++));
		assertEquals(new StandardCard(Rank.KING, Suit.HEARTS), removed.get(index++));
		assertEquals(new StandardCard(Rank.ACE, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.TWO, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.THREE, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.FOUR, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.FIVE, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.SIX, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.SEVEN, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.EIGHT, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.NINE, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.TEN, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.JACK, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.QUEEN, Suit.SPADES), removed.get(index++));
		assertEquals(new StandardCard(Rank.KING, Suit.SPADES), removed.get(index++));
	}
	
	@Test
	public void testRemoveCardsAllCards() throws Exception {
		// test removing all cards
		ArrayList<Object> all = fullPile.removeCards(52);
		assertEquals(52, all.size());
		assertEquals(0, fullPile.getNumCards());
	}
	
	
	@Test
	public void testRemoveCardsTooMany() throws Exception {
		// attempting to use removeCards to remove more cards than
		// the pile has should result in IllegalArgumentException
		try {
			fullPile.removeCards(53);
			assertTrue("removeCards to remove more cards than Pile has should throw IllegalArgumentException", false);
		} catch (IllegalArgumentException e) {
			// good
		}
	}
	
	@Test
	public void testAddCards() throws Exception {
		ArrayList<Object> cardsToAdd = new ArrayList<Object>();
		cardsToAdd.add(new StandardCard(Rank.THREE, Suit.HEARTS));
		cardsToAdd.add(new StandardCard(Rank.NINE, Suit.DIAMONDS));
		cardsToAdd.add(new StandardCard(Rank.ACE, Suit.CLUBS));
		cardsToAdd.add(new StandardCard(Rank.SEVEN, Suit.SPADES));
		
		assertEquals(0, pile.getNumCards());
		pile.addCards(cardsToAdd);
		assertEquals(4, pile.getNumCards());
		int index = 0;
		assertEquals(new StandardCard(Rank.THREE, Suit.HEARTS), pile.getCard(index++));
		assertEquals(new StandardCard(Rank.NINE, Suit.DIAMONDS), pile.getCard(index++));
		assertEquals(new StandardCard(Rank.ACE, Suit.CLUBS), pile.getCard(index++));
		assertEquals(new StandardCard(Rank.SEVEN, Suit.SPADES), pile.getCard(index++));
	}
	
	@Test
	public void testShuffle() throws Exception {
		// Test that when a Pile is shuffled, at least one card changes position
		Pile unshuffled = new Pile();
		Util.addAllCards(pile);
		Util.addAllCards(unshuffled);
		pile.shuffle();
		int numDifferent = 0;
		for (int i = 0; i < 52; i++) {
			Object c1 = pile.getCard(i);
			Object c2 = unshuffled.getCard(i);
			if (((StandardCard) c1).getRank() != ((StandardCard) c2).getRank() || ((StandardCard) c1).getSuit() != ((StandardCard) c2).getSuit()) {
				numDifferent++;
			}
		}
		assertTrue(numDifferent > 0);
	}
	
	
	@Test
	public void testRemoveCardsTwice() throws Exception {
		// Add all cards (in expected order)
		assertEquals(0, pile.getNumCards());
		Util.addAllCards(pile);
		
		ArrayList<Object> r1 = pile.removeCards(3);
		ArrayList<Object> r2 = pile.removeCards(5);
		
		assertEquals(3, r1.size());
		assertEquals(5, r2.size());
		
		assertEquals(new StandardCard(Rank.JACK, Suit.SPADES), r1.get(0));
		assertEquals(new StandardCard(Rank.QUEEN, Suit.SPADES), r1.get(1));
		assertEquals(new StandardCard(Rank.KING, Suit.SPADES), r1.get(2));
		
		assertEquals(new StandardCard(Rank.SIX, Suit.SPADES), r2.get(0));
		assertEquals(new StandardCard(Rank.SEVEN, Suit.SPADES), r2.get(1));
		assertEquals(new StandardCard(Rank.EIGHT, Suit.SPADES), r2.get(2));
		assertEquals(new StandardCard(Rank.NINE, Suit.SPADES), r2.get(3));
		assertEquals(new StandardCard(Rank.TEN, Suit.SPADES), r2.get(4));
	}
	
	@Test
	public void testSetValues() throws Exception {
		pile.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
		pile.addCard(new StandardCard(Rank.ACE, Suit.HEARTS));
		assertEquals(12, pile.getValueStandard());
		
		Pile pile2 = new Pile();
		
		pile2.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
		pile2.addCard(new StandardCard(Rank.TWO, Suit.SPADES));
		assertEquals(13, pile2.getValueStandard());
		
		Pile pile3 = new Pile();
		pile3.addCard(new StandardCard(Rank.ACE, Suit.SPADES));
		pile3.addCard(new StandardCard(Rank.TEN, Suit.SPADES));
		pile3.addCard(new StandardCard(Rank.KING, Suit.HEARTS));
		assertEquals(21, pile3.getValueStandard());
		
		Pile pile4 = new Pile();
		pile4.addCard(new StandardCard(Rank.SIX, Suit.SPADES));
		pile4.addCard(new StandardCard(Rank.QUEEN, Suit.SPADES));
		pile4.addCard(new StandardCard(Rank.SEVEN, Suit.HEARTS));
		assertEquals(23, pile4.getValueStandard());
	}
	
	@Test
	public void testEquals() {
		assertTrue(fullPile.equals(duplicatePile));
	}
	
	@Test
	public void Temporary() {
		ArrayList<Object> arr = fullExpPile.getPile();
		for(Object c : arr) {
			ExplodingKittensCard t = ((ExplodingKittensCard) c);
			System.out.println(t.getImagePath());
		}
	}
}