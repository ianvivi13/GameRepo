package Models;

import java.util.ArrayList;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class UnoController {
	
private static IDatabase db;
	
	
	public static void initialize(int gameId) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		
		model.getMainPile().populateUno();
		model.getMainPile().shuffle();
		model.getMainPile().shuffle();
		
		for(Player players : model.getPlayers()) {
			players.getPile().addCards(model.getMainPile().removeCards(7));
		}
		db.updateGame(gameId, model);
	}
	
	public static void skip(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		db.updateGame(gameId, model);
	}
	
	public static void reverse(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.reverseOrder();
		db.updateGame(gameId, model);
	}
	
	public static void drawTwo(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCards(model.getMainPile().removeCards(2));
		model.nextTurn();
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void drawFour(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		//insert Wild method
		model.nextTurn();
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCards(model.getMainPile().removeCards(4));
		model.nextTurn();
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void wildColor(int gameId, String colorChoice) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		
		if(colorChoice.equals(Color.BLUE.toString())) {
			((UnoCard) model.getMainPile().getTopCard()).setWildColor(Color.BLUE);
		}
		if(colorChoice.equals(Color.RED.toString())) {
			((UnoCard) model.getMainPile().getTopCard()).setWildColor(Color.RED);
		}
		if(colorChoice.equals(Color.GREEN.toString())) {
			((UnoCard) model.getMainPile().getTopCard()).setWildColor(Color.GREEN);
		}
		if(colorChoice.equals(Color.GREEN.toString())) {
			((UnoCard) model.getMainPile().getTopCard()).setWildColor(Color.YELLOW);
		}
		db.updateGame(gameId, model);
	}
	
	public static boolean checkUno(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		db.updateGame(gameId, model);
		if(current.getPile().getNumCards() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean checkWin(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(current.getPile().getNumCards() == 0) {
			return true;
		}
		return false;
	}
	
	public Selection select(int gameId, Location location) {
		Game model = db.getGameFromGameId(gameId);
		ArrayList<Object> removed;
		if (location.getLocationType() == LocationType.MAIN_DECK) {
			Pile main = model.getMainPile();
			if (!main.isEmpty() && location.getCardIndex() == main.getIndexOfTopCard()) {
				removed = main.removeCards(1);
				return new Selection(location, removed);
			}
		}
		 else if (location.getLocationType() == LocationType.PILE) {
			Pile hand = model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile();
			int cardIndex = location.getCardIndex();
			if (cardIndex <= model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().getIndexOfTopCard() && cardIndex >= model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().getVisibleIndex()) {
				removed = hand.removeCards(hand.getNumCards()-cardIndex);
				return new Selection(location, removed);
			}
		}
		return null;
	}
	
	public void unselect(int gameId, Selection selection) {
		Game model = db.getGameFromGameId(gameId);
		Location originLoc=selection.getOrigin();
		if(originLoc.getLocationType()==LocationType.PILE){
			model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().addCards(selection.getCards());
		}
		if(originLoc.getLocationType()==LocationType.MAIN_DECK){
			model.getMainPile().addCards(selection.getCards());
		}
	}
	
	public boolean allowMove(int gameId, Selection selection, Location dest) {
		Game model = db.getGameFromGameId(gameId);
		if (dest.getLocationType() == LocationType.WASTEPILE) {
			Pile found = model.getAltPile();
			Color selectColor = ((UnoCard) selection.getCards().get(0)).getColor();
			Value selectValue = ((UnoCard) selection.getCards().get(0)).getValues();
			if (selection.getNumCards() == 1) {
				if ((selectColor == ((UnoCard) model.getMainPile().getTopCard()).getColor()) || (selectValue == ((UnoCard) model.getMainPile().getTopCard()).getValues())) {
					return true;
					}
				}
			}
		return false;
	}
	
	public void moveCards(int gameId, Selection selection, Location dest) {
		Game model = db.getGameFromGameId(gameId);
		if (dest.getLocationType() == LocationType.PILE) {
			model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().addCards(selection.getCards());
			if (selection.getOrigin().getLocationType() == LocationType.MAIN_DECK) {
				model.getMainPile().setVisibleIndex(model.getMainPile().getIndexOfTopCard());
			} else if (selection.getOrigin().getLocationType() == LocationType.PILE) {
				Pile tab3 =model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile();
				if (tab3.getVisibleIndex() > tab3.getIndexOfTopCard()) {
					tab3.setVisibleIndex(tab3.getIndexOfTopCard());
					}
				}
			}
		}
	
	public void drawCardOrRecycleWaste(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		if(model.getMainPile().isEmpty()){
			int loopLength = model.getAltPile().getNumCards();
			for(int i=0;i<loopLength;i++){
				model.getMainPile().addCard(model.getAltPile().drawCard());
			}
			model.getMainPile().setVisibleIndex(model.getMainPile().getNumCards()-1);
		}
		else{
			model.getAltPile().addCard(model.getMainPile().drawCard());
			model.getMainPile().setVisibleIndex(model.getMainPile().getVisibleIndex()-1);
		}
	}
	
	/* Methods to Implement
	 * wild
	 * reverse
	 * skip
	 */

}
