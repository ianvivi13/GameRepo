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
		model.getAltPile().addCards(model.getMainPile().removeCards(1));
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
	
	public void drawCardOrRecycleWaste(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(model.getMainPile().isEmpty()){
			int loopLength = model.getAltPile().getNumCards();
			for(int i=0;i<loopLength;i++){
				model.getMainPile().addCard(model.getAltPile().removeCards(model.getAltPile().getTopCard()));
				model.getMainPile().shuffle();
			}
		}
		else{
			current.getPile().addCard(model.getMainPile().drawCard());
		}
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
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
	
	public static void drawFour(int gameId, String colorChoice) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		wildColor(gameId, colorChoice);
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
		
		if(colorChoice.equals(Color.BLUE.getSymbol())) {
			((UnoCard) model.getAltPile().getTopCard()).setWild(Color.BLUE.getSymbol());
		}
		else if(colorChoice.equals(Color.RED.toString())) {
			((UnoCard) model.getAltPile().getTopCard()).setWild(Color.RED.getSymbol());
		}
		else if(colorChoice.equals(Color.GREEN.toString())) {
			((UnoCard) model.getAltPile().getTopCard()).setWild(Color.GREEN.getSymbol());
		}
		else if(colorChoice.equals(Color.YELLOW.toString())) {
			((UnoCard) model.getAltPile().getTopCard()).setWild(Color.YELLOW.getSymbol());
		}
		db.updateGame(gameId, model);
	}
	
	public static boolean checkUno(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		db.updateGame(gameId, model);
		if(current.getPile().getNumCards() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean checkWin(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		db.updateGame(gameId, model);
		if(current.getPile().getNumCards() == 0) {
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
		if(allowMove(gameId, selected) && selected.getValues().equals(Value.DrawTwo)) {
			model.getAltPile().addCards(current.getPile().removeCards(selected));
			drawTwo(gameId);
		}
		else if(allowMove(gameId, selected) && selected.getValues().equals(Value.Skip)) {
			model.getAltPile().addCards(current.getPile().removeCards(selected));
			skip(gameId);
		}
		else if(allowMove(gameId, selected) && selected.getValues().equals(Value.Reverse)) {
			model.getAltPile().addCards(current.getPile().removeCards(selected));
			reverse(gameId);
		}
		model.getAltPile().addCards(current.getPile().removeCards(selected));
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}
	
	public static void playSpecialCard(int gameId, UnoCard selected, String color) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(allowMove(gameId, selected) && selected.getValues().equals(Value.Wild_Four)) {
			model.getAltPile().addCards(current.getPile().removeCards(selected));
			drawFour(gameId, color);
		}
		else if(allowMove(gameId, selected) && selected.getValues().equals(Value.Wild)) {
			model.getAltPile().addCards(current.getPile().removeCards(selected));
			wildColor(gameId, color);
		}
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
	}

}
