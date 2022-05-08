package  Models;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class BlackJackController {
	

	private static IDatabase db;
	
	public static void initialize(int gameId) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		Game model = db.getGameFromGameId(gameId);
		
		
		model.getMainPile().populate();
		model.getMainPile().shuffle();
		model.getMainPile().shuffle();
		
		for(Player players : model.getPlayers()) {
			players.getPile().addCards(model.getMainPile().removeCards(2));
			players.getPile().setVisibleIndex(players.getPile().getIndexOfTopCard());
		}
		db.updateGame(gameId, model);
	}
	
	// for hold we want to basically skip a players turn
	public static void hold(int gameId){
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.incrementAuxInt();
		if (model.getAuxInt() >= 2) {
			endGame(gameId);
			return;
		}
		model.nextTurn();
		db.updateGame(gameId, model);
	}
	
	
	public static void hit(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.resetAuxInt();
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCards(model.getMainPile().removeCards(1));
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		if (!checkBust(gameId)) {
			model.nextTurn();
			db.updateGame(gameId, model);
			db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		}
	}
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	public static void freeze(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.incrementAuxInt();
		if (model.getAuxInt() >= 2) {
			endGame(gameId);
			return;
		}
		model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
		db.updateGame(gameId, model);
	}
	
	public static boolean checkWin(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player Player0 = db.getPlayerFromPlayerId(model.getPlayerIds().get(0));
		Player Player1 = db.getPlayerFromPlayerId(model.getPlayerIds().get(1));
		int Val0 = Player0.getPile().getValueStandard();
		int Val1 = Player1.getPile().getValueStandard();
		if ((Val0 > 21) && (Val1 > 21)) {return true;}
		if (Val0 > 21) {return false;}
		if (Val1 > 21) {return true;}
		if (Val0 > Val1) {return true;}
		if (Val0 < Val1) {return false;}
		Val0 = Player0.getPile().getNumCards();
		Val1 = Player1.getPile().getNumCards();
		if (Val0 > Val1) {return true;}
		if (Val0 < Val1) {return false;}
		return true;
	}
	
	public static boolean checkBust(int gameId) {
        Game model = db.getGameFromGameId(gameId);
        Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());

        if(current.getPile().getValueStandard() >  21) {
            endGame(gameId);
            return true;
        }
        return false;
    }
	
	private static void endGame(int gameId) {
        InitDatabase.init();
        db = DatabaseProvider.getInstance();
        Game model = db.getGameFromGameId(gameId);
        TurnOrder o = new TurnOrder();
        model.setTurnOrder(o);
        db.updateGame(gameId, model);
    }

}