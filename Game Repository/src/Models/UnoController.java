package Models;


import java.util.ArrayList;
import java.util.Collections;

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
	
	public static void drawCardOrRecycleWaste(int gameId, int numCards, boolean AutoPlay) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		UnoCard drawn = null;
		int numLeft = numCards;
		while(numLeft > 0) {
			if(model.getMainPile().isEmpty() && model.getAltPile().getNumCards() != 1){
				model.getMainPile().addCards(model.getAltPile().removeCards(model.getAltPile().getNumCards()));
				model.getAltPile().addCard(model.getMainPile().removeCard(model.getMainPile().getNumCards() - 1));
				model.getMainPile().shuffle();
			}
			else if(model.getMainPile().isEmpty() && model.getAltPile().getNumCards() == 1) {
				model.getMainPile().populateUno();
				model.getMainPile().shuffle();
			}
			drawn = (UnoCard) model.getMainPile().removeCard(model.getMainPile().getNumCards() - 1);
			current.getPile().addCard(drawn);
			numLeft--;
		}
		if ((AutoPlay) & (drawn != null)) {
			db.updateGame(gameId, model);
			db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
			if (!playCard(gameId, drawn)) {
				model = db.getGameFromGameId(gameId);
				model.nextTurn();
				db.updateGame(gameId, model);
			}
		} else {
			model.nextTurn();
			db.updateGame(gameId, model);
			db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		}
	}
	
	public static void drawTwo(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		db.updateGame(gameId, model);
		drawCardOrRecycleWaste(gameId, 2, false);
	}
	
	public static void drawFour(int gameId, String colorChoice) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.setWildColor(colorChoice);
		model.nextTurn();
		db.updateGame(gameId, model);
		drawCardOrRecycleWaste(gameId, 4, false);
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
			model.nextTurn();
			db.updateGame(gameId, model);
			return true;
		}
		return false;
	}
	
	private static int getRandomInt(int max) {
		return (int)(max * Math.random());
	}
	
	public static void communism(int gameId) { // 69 rule
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		ArrayList<Player> subjects = model.getPlayers();
		
		Pile landfill = new Pile();
		for (Player p : subjects) {
			landfill.addCards(p.getPile().removeCards(p.getPile().getNumCards()));
		}
		
		landfill.shuffle();
		int NumPlayers = subjects.size();
		int size = landfill.getNumCards();
		int extraCards = size % NumPlayers;
		Pile garbage = new Pile();
		garbage.addCards(landfill.removeCards(extraCards));
		
		// spread out the left overs
		for (Object o : garbage.getPile()) {
			subjects.get(getRandomInt(NumPlayers)).getPile().addCard(o);
		}

		// spread out the main
		int landfillSize = landfill.getNumCards();
		int numCards = landfillSize / NumPlayers;
		
		for (Player p : subjects) {
			ArrayList<Object> cardsToAdd = landfill.removeCards(numCards);
			p.getPile().addCards(cardsToAdd);
		}

		model.setPlayers(subjects);
		db.updateGame(gameId, model);
	}
	
	public static void utilitarianism(int gameId) { // 0 rule
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		ArrayList<Player> subjects = model.getPlayers();
		ArrayList<Pile> landfill = new ArrayList<>();
		
		for (Player p : subjects) {
			landfill.add(p.getPile());
		}
		
		
		if (model.getTurnOrder().getAdder() > 0) {
			Collections.rotate(landfill, 1);
		} else {
			Collections.rotate(landfill, -1);
		}

		for (Player p : subjects) {
			p.setPile(landfill.get(0));
			landfill.remove(0);
		}
		
		model.setPlayers(subjects);
		db.updateGame(gameId, model);
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
	
	public static boolean playCard(int gameId, UnoCard selected) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		if(selected.getColor() == Color.BLACK) {
			return false;
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
					UnoCard currentTop = (UnoCard) model.getAltPile().getTopCard();
					model.getAltPile().addCard(current.getPile().removeCard(selected));
					db.updateGame(gameId, model);
					db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
					if(!checkWin(gameId)) {
						model = db.getGameFromGameId(gameId);
						model.nextTurn();
						db.updateGame(gameId, model);
						// special game rules
						int spec = model.getAuxInt();
						boolean zero = (spec == 3 || spec == 2);
						boolean sixNine = (spec == 3 || spec == 1);
						if ((zero) && (selected.getValues() == Value.Zero)) { // put 0 rule here
							utilitarianism(gameId);
						} else if ((sixNine) && (selected.getValues() == Value.Nine) && (currentTop.getValues() == Value.Six)) { // call 69 rule
							communism(gameId);
						}
					}
					break;
			}
			return true;
		}
		return false;
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
					model.nextTurn();
					db.updateGame(gameId, model);
				}
				break;
			}
		}
	}

}
