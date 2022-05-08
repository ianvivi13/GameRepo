package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnoControllerTest {

	private static Game model;
	private static Game modelTwo;
	private static Player three;
	private static Player four;
	private static Player five;
	private static Player six;
	private static Player seven;
	private static Player eight;
	private static Player nine;
	private static IDatabase db;
	private static String[] dumb;
	private static int modelId;
	private static int modelIdTwo;
	
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e){
		}
		model = new Game(IDatabase.Key_Uno);
		modelTwo = new Game(IDatabase.Key_Uno); 
		three = new Player(true, db.createUser("booboo", "b"));
		four = new Player(true, db.createUser("doodoo", "d"));
		five = new Player(true, db.createUser("coocoo", "c"));
		six = new Player(true, db.createUser("lulu", "L"));
		seven = new Player(true, db.createUser("peepee", "p"));
		eight = new Player(true, db.createUser("deedee", "e"));
		nine = new Player(true, db.createUser("meme", "m"));
		try {
		model.addPlayer(db.createPlayer(three));
		model.addPlayer(db.createPlayer(four));
		model.addPlayer(db.createPlayer(five));
		model.addPlayer(db.createPlayer(six));
		modelTwo.addPlayer(db.createPlayer(seven));
		modelTwo.addPlayer(db.createPlayer(eight));
		modelTwo.addPlayer(db.createPlayer(nine));
		} catch(PlayerAlreadyExistsException e) {
			
		}
		modelId = db.createGame(model);
		modelIdTwo = db.createGame(modelTwo);
		try {
			UnoController.initialize(modelId);
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void aTestInit() throws Exception {
		
		//Test if initialize method works
		model = db.getGameFromGameId(modelId);
		System.out.println(model.getAltPile().getNumCards());
		assertEquals(1, model.getAltPile().getNumCards());
		assertEquals(79, model.getMainPile().getNumCards());
		assertNotEquals(Color.BLACK, ((UnoCard) model.getAltPile().getTopCard()).getColor());
		assertNotEquals(Value.DrawTwo, ((UnoCard) model.getAltPile().getTopCard()).getValues());
		assertNotEquals(Value.Skip, ((UnoCard) model.getAltPile().getTopCard()).getValues());
		assertNotEquals(Value.Reverse, ((UnoCard) model.getAltPile().getTopCard()).getValues());
		
		assertEquals(7, model.getPlayers().get(0).getPile().getNumCards());
		assertEquals(7, model.getPlayers().get(1).getPile().getNumCards());
		//assertEquals(1, model.getPlayers().get(1).getPile().getVisibleIndex());
		assertEquals(7, model.getPlayers().get(2).getPile().getNumCards());
		assertEquals(7, model.getPlayers().get(3).getPile().getNumCards());
	}
	

	@Test
	public void bTestSkip() throws Exception{
		UnoController.skip(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(five));
		
		UnoController.skip(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(three));
	}
	
	@Test
	public void cTestAllowMove() throws Exception{
		UnoCard si = new UnoCard(Color.GREEN, Value.Two);
		UnoCard wild = new UnoCard(Color.BLACK, Value.Wild);
		UnoCard redDos = new UnoCard(Color.RED, Value.Two);
		UnoCard redTres = new UnoCard(Color.RED, Value.Three);
		UnoCard azulUno = new UnoCard(Color.BLUE, Value.One);
		UnoCard greenTres = new UnoCard(Color.GREEN, Value.Three);
		UnoCard azulReverse = new UnoCard(Color.BLUE, Value.Reverse);
		UnoCard amarilloSkip = new UnoCard(Color.YELLOW, Value.Skip);
		UnoCard amarilloPlusDos = new UnoCard(Color.YELLOW, Value.DrawTwo);
		model = db.getGameFromGameId(modelId);
		model.getAltPile().addCard(redDos);
		db.updateGame(modelId, model);
		assertTrue(UnoController.allowMove(modelId, wild));
		assertTrue(UnoController.allowMove(modelId, redDos));
		assertTrue(UnoController.allowMove(modelId, si));
		assertTrue(UnoController.allowMove(modelId, redTres));
		assertFalse(UnoController.allowMove(modelId, azulUno));
		assertFalse(UnoController.allowMove(modelId, amarilloSkip));
		assertFalse(UnoController.allowMove(modelId, azulReverse));
		model.getAltPile().addCard(amarilloPlusDos);
		db.updateGame(modelId, model);
		assertTrue(UnoController.allowMove(modelId, amarilloSkip));
		model.getAltPile().addCard(wild);
		model.setWildColor(Color.RED.toString());
		db.updateGame(modelId, model);
		assertTrue(UnoController.allowMove(modelId, wild));
		assertTrue(UnoController.allowMove(modelId, redDos));
		assertTrue(UnoController.allowMove(modelId, redTres));
		assertFalse(UnoController.allowMove(modelId, azulUno));
		assertFalse(UnoController.allowMove(modelId, si));
	}
	
	@Test
	public void dTestDraw() throws Exception{
		model = db.getGameFromGameId(modelId);
		int num = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards();
		model.getAltPile().addCards(model.getMainPile().removeCards(model.getMainPile().getNumCards()));
		db.updateGame(modelId, model);
		int alt = model.getAltPile().getNumCards();
		UnoCard card = (UnoCard) model.getAltPile().getTopCard();
		UnoController.drawCardOrRecycleWaste(modelId, 1, false);
		model = db.getGameFromGameId(modelId);
		model.reverseOrder();
		model.nextTurn();
		assertEquals(num + 1, db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards());
		assertTrue(card.equals(model.getAltPile().getTopCard()));
		assertEquals(alt - 2, model.getMainPile().getNumCards());
		
	}
	
	@Test
	public void eTestDrawFour() throws Exception{
		String c = Color.YELLOW.toString();
		model = db.getGameFromGameId(modelId);
		model.nextTurn();
		db.updateGame(modelId, model);
		model = db.getGameFromGameId(modelId);
		int num = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards();
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		model.nextTurn();
		db.updateGame(modelId, model);
		UnoController.reverse(modelId);
		UnoController.drawFour(modelId, c);
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(num + 4,  db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards());
		assertEquals(c, model.getWildColor());
	}
	
	@Test
	public void fTestDrawTwo() throws Exception{
		model = db.getGameFromGameId(modelId);
		model.nextTurn();
		db.updateGame(modelId, model);
		model = db.getGameFromGameId(modelId);
		int num = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards();
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		model.nextTurn();
		db.updateGame(modelId, model);
		UnoController.reverse(modelId);
		UnoController.drawTwo(modelId);
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(num + 2,  db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer()).getPile().getNumCards());
	}
	
	@Test
	public void gTestReverse() throws Exception{
		model = db.getGameFromGameId(modelId);
		int p = model.getTurnOrder().CurrentPlayer();
		UnoController.reverse(modelId);
		UnoController.reverse(modelId);
		model = db.getGameFromGameId(modelId);
		assertEquals(model.getTurnOrder().CurrentPlayer(), p);
		UnoController.reverse(modelId);
		model.nextTurn();
		Game model2 = db.getGameFromGameId(modelId);
		assertNotEquals(model.getTurnOrder().CurrentPlayer(), model2.getTurnOrder().CurrentPlayer());
		model.nextTurn();
		assertNotEquals(model.getTurnOrder().CurrentPlayer(), model2.getTurnOrder().CurrentPlayer());
		model.nextTurn();
		assertEquals(model.getTurnOrder().CurrentPlayer(), model2.getTurnOrder().CurrentPlayer());
	}
	
	@Test
	public void hTestPlayCard() throws Exception{
		//cards for testing
		UnoCard wild = new UnoCard(Color.BLACK, Value.Wild);
		UnoCard azulUno = new UnoCard(Color.BLUE, Value.One);
		UnoCard rojoTres = new UnoCard(Color.RED, Value.Three);
		
		/* getting the model from the gameId
		 * grabbing the current player
		 * giving the player a blue 1, wild card, and red 3
		 *giving the waste pile a blue 1
		 */
		model = db.getGameFromGameId(modelId);
		int currentId = model.getTurnOrder().CurrentPlayer();
		Player current = db.getPlayerFromPlayerId(currentId);
		current.getPile().addCard(azulUno);
		current.getPile().addCard(wild);
		current.getPile().addCard(rojoTres);
		model.getAltPile().addCard(azulUno);
		
		/* updating the game and player
		 * getting the number of cards from current player
		 * getting model again after updates
		 */
		db.updateGame(modelId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		int numCards = current.getPile().getNumCards();
		model = db.getGameFromGameId(modelId);
		int numCards2 = model.getAltPile().getNumCards();
		
		//playing a wild card and a blue 1
		UnoController.playCard(modelId, wild);
		UnoController.playCard(modelId, rojoTres);
		
		//creating a new model and establishing that model one is equal to model2
		Game model2 = db.getGameFromGameId(modelId);
		assertTrue(model.equals(model2));
		
		/*play a blue 1 
		 * create a new game model
		 */
		UnoController.playCard(modelId, azulUno);
		Game model3 = db.getGameFromGameId(modelId);
		
		//check to see if the second models top card is a blue 1
		assertTrue(model2.getAltPile().getTopCard().equals(azulUno));
		
		//call next turn
		model.nextTurn();
		
		//check to see if the current player from model 1 is equal to the current player in model 3
		assertEquals(model.getTurnOrder().CurrentPlayer(), model3.getTurnOrder().CurrentPlayer());
		current = db.getPlayerFromPlayerId(currentId);
		//check that the number of cards in the current players hand is equal to -1
		assertEquals(numCards-1, current.getPile().getNumCards());
		assertEquals(numCards2+1, model3.getAltPile().getNumCards());
	}
	
	@Test
	public void iTestPlaySpecialCard() throws Exception{
		//cards for testing
		UnoCard wild = new UnoCard(Color.BLACK, Value.Wild);
		UnoCard wildFour = new UnoCard(Color.BLACK, Value.Wild_Four);
		UnoCard azulUno = new UnoCard(Color.BLUE, Value.One);
		UnoCard rojoTres = new UnoCard(Color.RED, Value.Three);
		
		/* getting the model from the gameId
		 * grabbing the current player
		 * giving the player a wild card
		 *giving the waste pile a blue 1
		 */
		model = db.getGameFromGameId(modelId);
		int currentId = model.getTurnOrder().CurrentPlayer();
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCard(wild);
		current.getPile().addCard(wildFour);
		current.getPile().addCard(rojoTres);
		model.getAltPile().addCard(rojoTres);
		
		/* updating the game and player
		 * getting the number of cards from current player
		 * getting model again after updates
		 */
		db.updateGame(modelId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		int numCards = current.getPile().getNumCards();
		model = db.getGameFromGameId(modelId);
		int numCards2 = model.getAltPile().getNumCards();
		String color = Color.GREEN.toString();
		String color2 = Color.RED.toString();
		UnoController.playSpecialCard(modelId, azulUno, color);
		UnoController.playSpecialCard(modelId, rojoTres, color2);
		
		Game model2 = db.getGameFromGameId(modelId);
		assertTrue(model.equals(model2));
		UnoController.playSpecialCard(modelId, wild, color);
		Game model3 = db.getGameFromGameId(modelId);
		assertTrue(model3.getAltPile().getTopCard().equals(wild));
		current = db.getPlayerFromPlayerId(currentId);
		assertEquals(numCards-1, current.getPile().getNumCards());
		assertEquals(numCards2+1, model3.getAltPile().getNumCards());
		assertEquals(Color.GREEN.toString(), model3.getWildColor());
	}
	
	@Test
	public void jTestGameSim() throws Exception{
		modelTwo = db.getGameFromGameId(modelIdTwo);
		modelTwo.getMainPile().addCard(new UnoCard(Color.BLUE, Value.Eight));
		modelTwo.getMainPile().addCard(new UnoCard(Color.GREEN, Value.Two));
		modelTwo.getMainPile().addCard(new UnoCard(Color.RED, Value.One));
		modelTwo.getMainPile().addCard(new UnoCard(Color.YELLOW, Value.Five));
		modelTwo.getMainPile().addCard(new UnoCard(Color.BLACK, Value.Wild));
		modelTwo.getMainPile().addCard(new UnoCard(Color.BLUE, Value.Seven));
		modelTwo.getMainPile().addCard(new UnoCard(Color.GREEN, Value.Seven));
		modelTwo.getMainPile().addCard(new UnoCard(Color.RED, Value.Seven));
		modelTwo.getMainPile().addCard(new UnoCard(Color.YELLOW, Value.Nine));
		modelTwo.getMainPile().addCard(new UnoCard(Color.BLUE, Value.Zero));
		modelTwo.getMainPile().addCard(new UnoCard(Color.GREEN, Value.Reverse));
		modelTwo.getMainPile().addCard(new UnoCard(Color.RED, Value.DrawTwo));
		modelTwo.getMainPile().addCard(new UnoCard(Color.YELLOW, Value.Skip));
		modelTwo.getAltPile().addCard(new UnoCard(Color.GREEN, Value.Four));
		modelTwo.getPlayers().get(0).getPile().addCard(new UnoCard(Color.BLACK, Value.Wild_Four));
		modelTwo.getPlayers().get(0).getPile().addCard(new UnoCard(Color.BLUE, Value.Two));
		modelTwo.getPlayers().get(1).getPile().addCard(new UnoCard(Color.BLACK, Value.Wild));
		modelTwo.getPlayers().get(1).getPile().addCard(new UnoCard(Color.GREEN, Value.Reverse));
		modelTwo.getPlayers().get(2).getPile().addCard(new UnoCard(Color.RED, Value.Skip));
		modelTwo.getPlayers().get(2).getPile().addCard(new UnoCard(Color.BLUE, Value.DrawTwo));
		db.updateGame(modelIdTwo, modelTwo);
		db.updatePlayer(modelTwo.getPlayerIds().get(0), modelTwo.getPlayers().get(0));
		db.updatePlayer(modelTwo.getPlayerIds().get(1), modelTwo.getPlayers().get(1));
		db.updatePlayer(modelTwo.getPlayerIds().get(2), modelTwo.getPlayers().get(2));
		modelTwo = db.getGameFromGameId(modelIdTwo);
		int playerOneId = modelTwo.getPlayerIds().get(0);
		int playerTwoId = modelTwo.getPlayerIds().get(1);
		int playerThreeId = modelTwo.getPlayerIds().get(2);
		assertEquals(modelTwo.getMainPile().getNumCards(), 13);
		assertEquals(modelTwo.getAltPile().getNumCards(), 1);
		assertEquals(modelTwo.getPlayers().get(0).getPile().getNumCards(), 2);
		assertEquals(modelTwo.getPlayers().get(1).getPile().getNumCards(), 2);
		assertEquals(modelTwo.getPlayers().get(2).getPile().getNumCards(), 2);
		assertEquals(modelTwo.getTurnOrder().CurrentPlayer(), playerOneId);
		
		UnoController.playCard(modelIdTwo, new UnoCard(Color.BLUE, Value.Two));
		modelTwo = db.getGameFromGameId(modelIdTwo);
		assertEquals(modelTwo.getPlayers().get(0).getPile().getNumCards(), 2);
		UnoController.playSpecialCard(modelIdTwo, new UnoCard(Color.BLACK, Value.Wild_Four), Color.BLUE.toString());
		modelTwo = db.getGameFromGameId(modelIdTwo);
		assertEquals(modelTwo.getMainPile().getNumCards(), 9);
		assertEquals(modelTwo.getAltPile().getNumCards(), 2);
		assertEquals(modelTwo.getPlayers().get(0).getPile().getNumCards(), 1);
		assertEquals(modelTwo.getPlayers().get(1).getPile().getNumCards(), 6);
		assertEquals(modelTwo.getTurnOrder().CurrentPlayer(), playerThreeId);
		UnoController.playCard(modelIdTwo, new UnoCard(Color.BLUE, Value.DrawTwo));
		modelTwo = db.getGameFromGameId(modelIdTwo);
		assertEquals(modelTwo.getMainPile().getNumCards(), 7);
		assertEquals(modelTwo.getAltPile().getNumCards(), 3);
		assertEquals(modelTwo.getPlayers().get(2).getPile().getNumCards(), 1);
		assertEquals(modelTwo.getPlayers().get(0).getPile().getNumCards(), 3);
		assertEquals(modelTwo.getTurnOrder().CurrentPlayer(), playerTwoId);
		UnoController.playSpecialCard(modelIdTwo, new UnoCard(Color.BLACK, Value.Wild), Color.RED.toString());
		modelTwo = db.getGameFromGameId(modelIdTwo);
		assertEquals(modelTwo.getMainPile().getNumCards(), 7);
		assertEquals(modelTwo.getAltPile().getNumCards(), 4);
		assertEquals(modelTwo.getPlayers().get(1).getPile().getNumCards(), 5);
		assertEquals(modelTwo.getTurnOrder().CurrentPlayer(), playerThreeId);
		UnoController.playCard(modelIdTwo, new UnoCard(Color.RED, Value.Skip));
		modelTwo = db.getGameFromGameId(modelIdTwo);
		assertEquals(modelTwo.getPlayers().get(2).getPile().getNumCards(), 0);
		assertEquals(modelTwo.getAltPile().getNumCards(), 5);
		assertEquals(modelTwo.getTurnOrder().getTurnList().size(), 2);
	}
	
	
}
