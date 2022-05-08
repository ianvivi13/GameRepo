package Models;


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
		Pile wrong = new Pile();
		UnoCard top = null;
		while(top == null || top.getColor() == Color.BLACK || top.getValues() == Value.DrawTwo || top.getValues() == Value.Skip || top.getValues() == Value.Reverse  ) {
			top = (UnoCard) model.getMainPile().removeCards(1).get(0);
			wrong.addCard(top);
		}
		model.getAltPile().addCard(wrong.removeCards(1).get(0));
		model.getMainPile().addCards(wrong.getPile());
		model.getMainPile().shuffle();
		System.out.println(model.getAltPile().getNumCards());
		System.out.println(model.getMainPile().getNumCards());
		db.updateGame(gameId, model);
	}
	
	public static void skip(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		model.nextTurn();
		db.updateGame(gameId, model);
	}
	
	public static void reverse(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.reverseOrder();
		model.nextTurn();
		db.updateGame(gameId, model);
	}
	
	public static void drawCardOrRecycleWaste(int gameId, int numCards) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		while(numCards > 0) {
			if(model.getMainPile().isEmpty()){
				model.getMainPile().addCards(model.getAltPile().removeCards(model.getAltPile().getNumCards()));
				model.getAltPile().addCard(model.getMainPile().removeCard(model.getMainPile().getNumCards() - 1));
				model.getMainPile().shuffle();
			}
			current.getPile().addCard(model.getMainPile().removeCard(model.getMainPile().getNumCards() - 1));
			numCards--;
		}
		model.nextTurn();
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void drawTwo(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		db.updateGame(gameId, model);
		drawCardOrRecycleWaste(gameId, 2);
	}
	
	public static void drawFour(int gameId, String colorChoice) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.setWildColor(colorChoice);
		model.nextTurn();
		db.updateGame(gameId, model);
		drawCardOrRecycleWaste(gameId, 4);
	}
	
	public static boolean checkUno(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(current.getPile().getNumCards() == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean checkWin(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(current.getPile().getNumCards() == 0) {
			model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
			db.updateGame(gameId, model);
			return true;
		}
		return false;
	}
	
	public static boolean allowMove(int gameId, UnoCard card) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Color selectColor = card.getColor();
		Value selectValue = card.getValues();
			if ((selectColor == ((UnoCard) model.getAltPile().getTopCard()).getColor()) || (selectValue == ((UnoCard) model.getAltPile().getTopCard()).getValues())) {
				return true;
			}
			else if((((UnoCard) model.getAltPile().getTopCard()).getColor() == Color.BLACK) && selectColor.toString().equals(model.getWildColor())) {
				return true;
			}
			else if(selectColor == Color.BLACK) {
				return true;
			}
		return false;
	}
	
	public static void playCard(int gameId, UnoCard selected) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(selected.getColor() == Color.BLACK) {
			return;
		}
		if(allowMove(gameId, selected)) {
			switch (selected.getValues()) {
				case DrawTwo:
					model.getAltPile().addCard(current.getPile().removeCard(selected));
					db.updateGame(gameId, model);
					db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
					if(!checkWin(gameId)) {
						drawTwo(gameId);
					}
					break;
				case Reverse:
					model.getAltPile().addCard(current.getPile().removeCard(selected));
					db.updateGame(gameId, model);
					db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
					if(!checkWin(gameId)) {
						reverse(gameId);
					}
					break;
				case Skip:
					model.getAltPile().addCard(current.getPile().removeCard(selected));
					db.updateGame(gameId, model);
					db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
					if(!checkWin(gameId)) {
						skip(gameId);
					}
					break;
				default:
					model.getAltPile().addCard(current.getPile().removeCard(selected));
					db.updateGame(gameId, model);
					db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
					if(!checkWin(gameId)) {
						model = db.getGameFromGameId(gameId);
						model.nextTurn();
						db.updateGame(gameId, model);
					}
					break;
			}
		}
	}
	
	public static void playSpecialCard(int gameId, UnoCard selected, String color) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(selected.getColor() != Color.BLACK) {
			return;
		}
		if(allowMove(gameId, selected)) {
			switch(selected.getValues()) {
			case Wild_Four:
				model.getAltPile().addCard(current.getPile().removeCard(selected));
				db.updateGame(gameId, model);
				db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
				if(!checkWin(gameId)) {
					drawFour(gameId, color);
				}
				break;
			default:
				model.getAltPile().addCard(current.getPile().removeCard(selected));
				db.updateGame(gameId, model);
				db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
				if(!checkWin(gameId)) {
					model = db.getGameFromGameId(gameId);
					model.setWildColor(color);
					db.updateGame(gameId, model);
				}
				break;
			}
		}
	}

}
