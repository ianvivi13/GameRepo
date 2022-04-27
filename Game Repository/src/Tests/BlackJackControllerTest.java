package Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Models.BlackJackController;
import Models.Game;
import Models.Pile;
import Models.Player;
import Models.Rank;
import Models.StandardCard;
import Models.Suit;
import Models.TurnOrder;
import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class BlackJackControllerTest{
	
	private static Game model;
	private static BlackJackController control;
	private static Player one;
	private static Player two;
	private static IDatabase db;
	private static String[] dumb;
	
	
	@BeforeClass
	public static void setUp() {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		try {
			DerbyDatabase.main(dumb);
		} catch (IOException e){
			
		}
		model = new Game(IDatabase.Key_Blackjack);
		control = new BlackJackController();
		one = new Player(true, 1);
		two = new Player(true, 2);
		model.addPlayer(db.createPlayer(one));
		model.addPlayer(db.createPlayer(two));
		try {
			control.initialize(model);
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void testGameSim() throws Exception {
		
	
		// main deck should have 48 cards
		assertEquals(48, model.getMainPile().getNumCards());
		assertEquals(1000000000, model.getMainPile().getVisibleIndex());	

		assertEquals(2, one.getPile().getNumCards());
		assertEquals(1, one.getPile().getVisibleIndex());
		
		assertEquals(2, two.getPile().getNumCards());
		assertEquals(1, two.getPile().getVisibleIndex());
		
		control.hold(model);
		System.out.println(one.getPile().getNumCards());
		assertEquals(model.getTurnOrder().CurrentPlayer(), db.getPlayerIdFromPlayer(two));
		
		assertEquals(48, model.getMainPile().getNumCards());
		assertEquals(2, model.getPlayers().get(0).getPile().getNumCards());
		
		control.hit(model);
		System.out.println(one.getPile().getNumCards());
		
		assertEquals(47, model.getMainPile().getNumCards());
		assertEquals(3, one.getPile().getNumCards());
	}
	
}