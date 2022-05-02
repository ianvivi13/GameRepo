package Models;

import java.util.ArrayList;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class UnoController {
	
<<<<<<< HEAD
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
=======
private IDatabase db;
	
	
	public int initialize(Game model) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		model.getMainPile().populateUno();
		model.getMainPile().shuffle();
		
		for(Player players : model.getPlayers()) {
			players.getPile().addCards(model.getMainPile().removeCards(7));
		}
		int gameID = db.createGame(model);
		db.updateGame(gameID, model);
		return gameID;
	}
	
	public boolean checkUno(Game model) {
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(current.getPile().getNumCards() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean checkWin(Game model) {
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(current.getPile().getNumCards() == 0) {
			return true;
		}
		return false;
	}
	
	public Selection select(Game model, Location location) {
		ArrayList<Object> removed;
		if (location.getLocationType() == LocationType.MAIN_DECK) {
			Pile main = model.getMainPile();
			if (!main.isEmpty() && location.getCardIndex() == main.getIndexOfTopCard()) {
				removed = main.removeCards(1);
				return new Selection(location, removed);
			}
		}
		 else if (location.getLocationType() == LocationType.HAND) {
			Pile hand = model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile();
			int cardIndex = location.getCardIndex();
			if (cardIndex <= model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().getIndexOfTopCard() && cardIndex >= model.getPlayers().get(model.getTurnOrder().CurrentPlayer()).getPile().getVisibleIndex()) {
				removed = hand.removeCards(hand.getNumCards()-cardIndex);
				return new Selection(location, removed);
			}
		}
		return null;
	}
	
//	public void unselect(Game model, Selection selection) {
//		Location originLoc=selection.getOrigin();
//		if(originLoc.getLocationType()==LocationType.HAND){
//			model.getTableauPile(originLoc.getPileIndex()).addCards(selection.getCards());
//		}
//		if(originLoc.getLocationType()==LocationType.MAIN_DECK){
//			model.getMainDeck().addCards(selection.getCards());
//		}
//	}
	
//	public boolean allowMove(KlondikeModel model, Selection selection, Location dest) {
//		if (dest.getLocationType() == LocationType.FOUNDATION_PILE) {
//			Pile found = model.getFoundationPile(dest.getPileIndex());
//			Rank selectRank = selection.getCards().get(0).getRank();
//			Suit selectSuit = selection.getCards().get(0).getSuit();
//			if (selection.getNumCards() == 1) {
//				if (found.isEmpty() && selectRank == Rank.ACE) {
//					return true;
//				}
//					else if (!found.isEmpty() && selectSuit == found.getTopCard().getSuit()) {
//						Rank[] rank = Rank.values();
//						for (int x = 0; x < rank.length - 1; x++) {
//							if (selectRank == rank[x + 1] && found.getTopCard().getRank() == rank[x]) {
//								return true;
//							}
//						}
//					}
//				}
//			}
//		 else if (dest.getLocationType() == LocationType.TABLEAU_PILE) {
//			Pile tab2 = model.getTableauPile(dest.getPileIndex());
//			Rank selectRank2 = selection.getCards().get(0).getRank();
//			Suit selectSuit2 = selection.getCards().get(0).getSuit();
//			if (tab2.isEmpty() && selectRank2 == Rank.KING) {
//				return true;
//			}
//				else if (!tab2.isEmpty() && selectSuit2.getColor() != tab2.getTopCard().getSuit().getColor()) {
//					Rank[] rank = Rank.values();
//					for (int x = 0; x < rank.length - 1; x++) {
//						if (selectRank2 == rank[x] && tab2.getTopCard().getRank() == rank[x + 1]) {
//							return true;
//						}
//					}
//				}
//			}
//		return false;
//	}
	
//	public void moveCards(KlondikeModel model, Selection selection, Location dest) {
//		if (dest.getLocationType() == LocationType.TABLEAU_PILE) {
//			model.getTableauPile(dest.getPileIndex()).addCards(selection.getCards());
//			if (selection.getOrigin().getLocationType() == LocationType.MAIN_DECK) {
//				model.getMainDeck().setExposeIndex(model.getMainDeck().getIndexOfTopCard());
//			} else if (selection.getOrigin().getLocationType() == LocationType.TABLEAU_PILE) {
//				Pile tab3 = model.getTableauPile(selection.getOrigin().getPileIndex());
//				if (tab3.getExposeIndex() > tab3.getIndexOfTopCard()) {
//					tab3.setExposeIndex(tab3.getIndexOfTopCard());
//				}
//			}
//		} else if (dest.getLocationType() == LocationType.FOUNDATION_PILE) {
//				model.getFoundationPile(dest.getPileIndex()).addCards(selection.getCards());
//			if (selection.getOrigin().getLocationType() == LocationType.MAIN_DECK) {
//				model.getMainDeck().setExposeIndex(model.getMainDeck().getIndexOfTopCard());
//				}
//			}
//		}
	
//	public void drawCardOrRecycleWaste(Game model) {
//		if(model.getMainDeck().isEmpty()){
//			int loopLength = model.getWastePile().getNumCards();
//			for(int i=0;i<loopLength;i++){
//				model.getMainDeck().addCard(model.getWastePile().drawCard());
//			}
//			model.getMainDeck().setExposeIndex(model.getMainDeck().getNumCards()-1);
//		}
//		else{
//			model.getWastePile().addCard(model.getMainDeck().drawCard());
//			model.getMainDeck().setExposeIndex(model.getMainDeck().getExposeIndex()-1);
//		}
//	}
	
//	public boolean isWin(KlondikeModel model) {
//		for(int i=0;i<4;i++){
//			if(model.getFoundationPile(i).getNumCards() != 13){
//				return false;
//			}
//		}
//		return true;
//	}
	
	/* Methods to Implement
	 * drawtwo
	 * drawfour
>>>>>>> refs/heads/BlackjackMatchmakingExtended
	 * wild
	 * reverse
	 * skip
	 */

}
