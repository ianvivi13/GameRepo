package Tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Database.elves.PlayerAlreadyExistsException;
import Models.Color;
import Models.Game;
import Models.Player;
import Models.UnoCard;
import Models.UnoController;
import Models.Value;

public class UnoControllerTest {

	private static Game model;
	private static Player three;
	private static Player four;
	private static Player five;
	private static Player six;
	private static IDatabase db;
	private static String[] dumb;
	private static int modelId;
	
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e){
		}
		model = new Game(IDatabase.Key_Uno);
		three = new Player(true, db.createUser("booboo", "b"));
		four = new Player(true, db.createUser("doodoo", "d"));
		five = new Player(true, db.createUser("coocoo", "c"));
		six = new Player(true, db.createUser("lulu", "L"));
		try {
		model.addPlayer(db.createPlayer(three));
		model.addPlayer(db.createPlayer(four));
		model.addPlayer(db.createPlayer(five));
		model.addPlayer(db.createPlayer(six));
		} catch(PlayerAlreadyExistsException e) {
			
		}
		modelId = db.createGame(model);
		try {
			UnoController.initialize(modelId);
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void testInit() throws Exception {
		
		//Test if initialize method works
		model = db.getGameFromGameId(modelId);
		// main deck should have 48 cards
		assertEquals(79, model.getMainPile().getNumCards());
		
		assertEquals(1, model.getAltPile().getNumCards());
		System.out.println(model.getAltPile().getTopCard());
		
		assertEquals(7, model.getPlayers().get(0).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(0).getPile().getVisibleIndex());
		assertEquals(7, model.getPlayers().get(1).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(1).getPile().getVisibleIndex());
		assertEquals(7, model.getPlayers().get(2).getPile().getNumCards());
		assertEquals(7, model.getPlayers().get(3).getPile().getNumCards());
	}
	

	@Test
	public void testSkip() throws Exception{
		UnoController.skip(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(five));
		
		UnoController.skip(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(three));
	}
	
	@Test
	public void testReverse() throws Exception{
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(six));
	}
	
	@Test
	public void testDrawTwo() throws Exception{
		UnoController.drawTwo(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(9, model.getPlayers().get(2).getPile().getNumCards());
	}
	
	@Test
	public void testDrawFour() throws Exception{
		String choice = Color.BLUE.toString();
		UnoCard plus = new UnoCard(Color.BLACK, Value.Wild_Four);
		UnoController.playSpecialCard(modelId, plus, choice);
		model = db.getGameFromGameId(modelId);
		assertEquals(2, model.getAltPile().getNumCards());
		System.out.println(model.getAltPile().getTopCard());
		assertEquals(11, model.getPlayers().get(3).getPile().getNumCards());
		assertEquals("B", ((UnoCard) model.getAltPile().getTopCard()).getColor());
	}
}
