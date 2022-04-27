package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Models.Pile;
import Models.UnoModel;

public class UnoModelTest {

	private UnoModel model;
	
	@Before
	public void setUp() throws Exception {
		model = new UnoModel();
	}
	
	@Test
	public void testDeck() throws Exception {
		Pile deck = model.getDeck();
		assertNotNull(deck);
		assertTrue(deck.isEmpty());
	}
	
	@Test
	public void testHand() throws Exception {
		Pile hand = model.getHand();
		assertNotNull(hand);
		assertTrue(hand.isEmpty());
	}
	
	@Test
	public void testWastePile() throws Exception {
		Pile waste = model.getWastePile();
		assertNotNull(waste);
		assertTrue(waste.isEmpty());
	}
}