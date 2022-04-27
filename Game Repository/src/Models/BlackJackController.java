package  Models;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController {
	

	private IDatabase db;
	private int gameID;
	
//	public BlackJackController(String gameCode, String gameKey) {
//		super(gameCode, gameKey);
//		// TODO Auto-generated constructor stub
//	}
	
	public void initialize(Game model) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		model.getMainPile().populate();
		model.getMainPile().shuffle();
		model.getMainPile().setVisibleIndex(1000000000);
		
		for(Player p : model.getPlayers()) {
			p.getPile().addCards(model.getMainPile().removeCards(2));
		}
		gameID = db.createGame(model);
	}
	
//	// for hold we want to basically skip a players turn
//	public void hold(Game model){
//		model.nextTurn();
//		db.updateGame(i, model);
//	}
	
//	
//	public void hit() {
//		Player p = getPlayers().get(getTurnOrder().CurrentPlayer());
//		System.out.println(p.getPile().getNumCards());
//		p.getPile().addCards(getMainPile().removeCards(1));
//		nextTurn();
//		db.updateGame(i, this);
//	}
//	
//	// for stay/freeze we want to skip a players turn until the other player calls stay
//	public void freeze(Player p) {
//		removePlayerFromTurn(db.getPlayerIdFromPlayer(p));
//		db.updateGame(i, this);
//	}
//	
//	
//	public boolean checkWin() {
//		for(Player p : getPlayers()) {
//			if(p.getPile().getValueStandard() == 21) {
//				return true;
//			}
//		}
//		return false;
////		db.getBlackjackStats(UserID)
////		db.updateBlackjackStats(, user_id);
////		db.updateGlobalStats(stat, user_id);
//	}

}