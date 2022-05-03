package Models;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class ExplodingKittensController {
	
	private static IDatabase db;
	
	public static void initialize(int gameId) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		
		game.getMainPile().populateExplodingKittens(game.getPlayers().size());
		game.getMainPile().shuffle();
		game.getMainPile().setVisibleIndex(1000000000);
		for(Player p : game.getPlayers()) {
			p.getPile().addCard(new ExplodingKittensCard(Type.Defuse));
			p.getPile().addCards(game.getMainPile().removeCards(7));
			p.getPile().setVisibleIndex(p.getPile().getIndexOfTopCard());;
		}
		
		game.getMainPile().addExplodingKittens(game.getPlayers().size());
		game.getMainPile().shuffle();
		
		db.updateGame(gameId, game);
	}
	
	public static boolean checkKitten(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		ExplodingKittensCard ek = new ExplodingKittensCard(Type.ExplodingKitten);
		if(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getPile().contains(ek)) {
			return true;
		}
		return false;
	}
	
	public static boolean checkDefuse(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		ExplodingKittensCard d = new ExplodingKittensCard(Type.Defuse);
		if(db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer()).getPile().contains(d)) {
			return true;
		}
		return false;
	}
	
	public static void defuse(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Random rand = new Random();
		Player player = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		ExplodingKittensCard d = new ExplodingKittensCard(Type.Defuse);
		ExplodingKittensCard ek = new ExplodingKittensCard(Type.ExplodingKitten);
		if(checkDefuse(gameId)) {
			game.getAltPile().addCard(player.getPile().removeCard(d));
			game.getMainPile().getPile().add(rand.nextInt(game.getMainPile().getNumCards()), ek);
			player.getPile().removeCard(ek);
			db.updateGame(gameId, game);
			db.updatePlayer(db.getPlayerIdFromPlayer(player), player);
			game.nextTurn();
		}
		
		
		else {
			player.getAltPile().removeCard(ek);
			game.getAltPile().addCards(player.getPile().removeCards(player.getPile().getNumCards()));
			game.removePlayerFromTurn(db.getPlayerIdFromPlayer(player));
			db.updateGame(gameId, game);
		}
	}
	
	public static boolean allowMove(int gameId, ArrayList<Object> selection, Player player) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		ExplodingKittensCard nope = new ExplodingKittensCard(Type.Nope);
		if((twoCardRule(selection) || threeCardRule(selection) || fiveCardRule(selection)) && db.getPlayerIdFromPlayer(player) == game.getTurnOrder().CurrentPlayer()) {
			return true;
		}
		else if(selection.size() == 1 && db.getPlayerIdFromPlayer(player) == game.getTurnOrder().CurrentPlayer()) {
			return true;
		}
		else if(selection.size() == 1 && ((ExplodingKittensCard)selection.get(0)).equals(nope)) {
			// Fix so player can only play nope during a certain window
			return true;
		}
		
		return false;
	}
	
	public static boolean twoCardRule(ArrayList<Object> selection) {
		if((selection.size() == 2 && selection.get(0).equals(selection.get(1))) && isCats(selection)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean threeCardRule(ArrayList<Object> selection) {
		if(selection.size() == 3 && selection.get(0).equals(selection.get(1)) && selection.get(1).equals(selection.get(2)) && isCats(selection)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean fiveCardRule(ArrayList<Object> selection) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(selection.get(i).equals(selection.get(j)) && i != j) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean isCats(ArrayList<Object> selection) {
		ExplodingKittensCard bc = new ExplodingKittensCard(Type.BeardCat);
		ExplodingKittensCard cm = new ExplodingKittensCard(Type.Cattermelon);
		ExplodingKittensCard fc = new ExplodingKittensCard(Type.FeralCat);
		ExplodingKittensCard hp = new ExplodingKittensCard(Type.HairyPotatoCat);
		ExplodingKittensCard rc = new ExplodingKittensCard(Type.RainbowRalphingCat);
		ExplodingKittensCard tc = new ExplodingKittensCard(Type.TacoCat);
		ArrayList<ExplodingKittensCard> catList = new ArrayList<>();
		catList.add(bc);
		catList.add(cm);
		catList.add(fc);
		catList.add(hp);
		catList.add(rc);
		catList.add(tc);
		for(Object card : selection) {
			if(!catList.contains(card)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void favor(int gameId, Player player, int index) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Player current  = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		current.getPile().addCard(player.getPile().removeCard(index));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(player), player);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	
	public static void stealCard(int gameId, Player player) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Random rand = new Random();
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		current.getPile().addCard(player.getPile().removeCard(rand.nextInt(player.getPile().getNumCards())));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(player), player);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void chooseCard(int gameId, int index) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		current.getPile().addCard(game.getAltPile().removeCard(index));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void skip(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		game.nextTurn();
		db.updateGame(gameId, game);
	}
	
	public static boolean nope(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		ExplodingKittensCard nope = new ExplodingKittensCard(Type.Nope);
		int nopeCount = 0;
		for(int i = game.getMainPile().getNumCards() - 1; i >= 0; i--) {
			if(((ExplodingKittensCard)game.getMainPile().getCard(i)).equals(nope)) {
				nopeCount += 1;
			}
			
			else {
				break;
			}
		}
		
		if(nopeCount % 2 == 0) {
			return false;
		}
		
		return true;
		/*
		 * This one is a bit weird...
		 * 
		 * Basically, the player will play a card and the game will wait 3-5 seconds (time may need adjusting based on how annoying waiting gets)
		 * During this window, any player can play a nope card
		 * If a player plays a nope card, the timer resets to 3-5 seconds
		 * Once the window expires, if the nope counter is odd, the function relating to the original card that was played WILL NOT get executed
		 * If the nope counter is even, the function relating to the original card that was played WILL get executed
		 * After all of the nopes have been played and the function executes/doesn't execute, the nope counter is reset to 0
		 */
	}
	
	public static void seeFuture(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		current.getAltPile().addCards(game.getMainPile().removeCards(3));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		/*
		 * Starts See the future and Alter the future
		 * Must call alterFuture or seeFutureExit to finish
		 */
	}
	
	public static void seeFutureExit(int gameId) {
		InitDatabase.init(); 
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		game.getMainPile().addCards(current.getAltPile().removeCards(3));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void alterFuture(int gameId, ArrayList<Integer> order) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		int indexOne = order.get(0);
		int indexTwo = order.get(1);
		int indexThree = order.get(2);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		game.getMainPile().addCard(current.getAltPile().removeCard(indexOne));
		if(indexOne == 0) {
			indexTwo -= 1;
			indexThree -= 1;
		}
		if(indexOne == 1) {
			if(indexTwo == 2) {
				indexTwo -= 1;
			}
			else {
				indexThree -= 1;
			}
		}
		game.getMainPile().addCard(current.getAltPile().removeCard(indexTwo));
		if(indexTwo == 0) {
			indexThree -= 1;
		}
		game.getMainPile().addCard(current.getAltPile().removeCard(indexThree));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void drawFromBottom(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(game.getTurnOrder().CurrentPlayer());
		current.getPile().addCard(game.getMainPile().removeCard(0));
		db.updateGame(gameId, game);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		game.nextTurn();
	}
	
	public static boolean checkWin(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game game = db.getGameFromGameId(gameId);
		if(game.getTurnOrder().getTurnList().size() <= 1) {
			return true;
		}
		
		return false;
		
	}
	
}