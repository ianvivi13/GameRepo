package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Models.BlackJackController;
import Models.BlackJackModel;
import Models.Rank;
import Models.Suit;
import Models.TurnOrder;

public class BlackJackControllerTest{
	
	private BlackJackController controller;
	private BlackJackModel model;
	private TurnOrder turns;
	
	@Before
	public void setUp() {
		controller = new BlackJackController();
		model = new BlackJackModel();
		turns = new TurnOrder();
		turns.AddPlayer("1");
		turns.AddPlayer("2");
	}
	
	@Test
	public void testInitModel() throws Exception {
		BlackJackModel bModel = new BlackJackModel();
		controller.initialize(bModel);
		
		// main deck should have 24 cards,
		
		assertEquals(50, bModel.getDeck().getNumCards());
		assertEquals(1000000000, bModel.getDeck().getVisibleIndex());
		
		assertEquals(2, bModel.getHand().getNumCards());
		assertEquals(0, bModel.getHand().getVisibleIndex());
	}
	
	@Test
	public void testHold() throws Exception {
		
		
		controller.hold(model);
		
		assertEquals("2", turns.CurrentPlayer());
	}
	
	@Test
	public void testHit() throws Exception {
		BlackJackModel cModel = new BlackJackModel();
		controller.initialize(cModel);
		
		assertEquals(50, cModel.getDeck().getNumCards());
		assertEquals(2, cModel.getHand().getNumCards());
		
		controller.hit(cModel);
		
		assertEquals(49, cModel.getDeck().getNumCards());
		assertEquals(3, cModel.getHand().getNumCards());
		
	}
	
	@Test
	public void testSplit() throws Exception {
		
	}
}