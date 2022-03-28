package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Models.BlackJackModel;
import Models.Pile;

public class BlackJackModelTest{
	
	private BlackJackModel model;
	
	@Before
	public void setUp() {
		model = new BlackJackModel();
	}
	
	@Test
	public void testDeck() throws Exception {
		// Main deck should exist and be empty
		Pile deck = model.getDeck();
		assertNotNull(deck);
		assertTrue(deck.isEmpty());
	}
	
	@Test
	public void testHand() throws Exception {
		// Waste pile should exist and be empty
		Pile hand = model.getHand();
		assertNotNull(hand);
		assertTrue(hand.isEmpty());
	}
	
	@Test
	public void testAltHand() throws Exception {
		// Waste pile should exist and be empty
		Pile altHand = model.getAltHand();
		assertNotNull(altHand);
		assertTrue(altHand.isEmpty());
	}
}