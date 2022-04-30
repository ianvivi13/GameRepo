package Models;

import java.util.ArrayList;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class UnoController {
	
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
	 * wild
	 * reverse
	 * skip
	 */

}
