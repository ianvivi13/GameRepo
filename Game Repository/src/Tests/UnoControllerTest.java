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
import Models.BlackJackController;
import Models.Game;
import Models.Player;
import Models.UnoController;

public class UnoControllerTest {

	private static Game model;
	private static UnoController control;
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
		control = new UnoController();
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
		try {
			modelId = control.initialize(model);
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void testGameSim() throws Exception {
		
		//Test if initialize method works
		model = db.getGameFromGameId(modelId);
		// main deck should have 48 cards
		assertEquals(80, model.getMainPile().getNumCards());
		assertEquals(7, model.getPlayers().get(0).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(0).getPile().getVisibleIndex());
		assertEquals(7, model.getPlayers().get(1).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(1).getPile().getVisibleIndex());
		assertEquals(7, model.getPlayers().get(2).getPile().getNumCards());
		assertEquals(7, model.getPlayers().get(3).getPile().getNumCards());
	}
}
