package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Models.UnoController;
import Models.UnoParentController;
import Models.UnoModel;
import Models.TurnOrder;

public class UnoControllerTest {
	private UnoModel model;
	private UnoParentController unoParentController;
	private UnoController unoController;
	private TurnOrder turns;

	@Before
	public void setUp() {
		model = new UnoModel();
		unoParentController = new UnoParentController();
		unoController = new UnoController();
		turns = new TurnOrder();
		
		turns.AddPlayer("1");
		turns.AddPlayer("2");
		turns.AddPlayer("3");
		turns.AddPlayer("4");
		turns.AddPlayer("5");
	}

	@Test
	public void testInitModel() throws Exception {
		UnoModel uModel = new UnoModel();
		unoController.initialize(uModel);
		
		assertEquals(109, uModel.getDeck().getNumCards());
		assertEquals(7, uModel.getHand().getNumCards());
		assertEquals(7, uModel.getHand().getVisibleIndex());
		assertEquals(1, uModel.getWastePile().getNumCards());
		
	}
}
