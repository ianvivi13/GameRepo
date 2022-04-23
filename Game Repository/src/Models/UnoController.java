package Models;

public class UnoController extends Game {
	
	Game uno = new Game(gameCode,gameKey);
	
	public UnoController(String gameCode, String gameKey) {
		super(gameCode, gameKey);
	}
	
	public void initialize(UnoModel model) {
		mainPile.populate();
		mainPile.shuffle();
		
		for(Player p : players) {
			p.getPile().addCards(mainPile.removeCards(7));
		}
		db.createGame(uno);
	}
	
	//If no cards in your hand does not equal the color or symbol of the top index card in the waste pile, draw one card
	public void draw(UnoModel model) {
		Player p = players.get(tko.CurrentPlayer());
		p.getPile().drawCard();
		nextTurn();
		db.updateGame(db.getGameIdFromGame(uno), uno);
	}

	public void drawTwo() {
		nextTurn();
		Player p = players.get(tko.CurrentPlayer());
		for(int i = 0; i < 2; i++) {
		p.getPile().drawCard();
		}
	}
	
	public void drawFour(Value value) {
		nextTurn();
		Player p = players.get(tko.CurrentPlayer());
		for(int i = 0; i < 4; i++) {
		p.getPile().drawCard();
		}
	}
	
	public void swap() {
		
	}
	public void wild(Color color) {
		
	}

	public void skip() {
		nextTurn();
		nextTurn();
		db.updateGame(db.getGameIdFromGame(uno), uno);
	}
	
	public void reverse() {
		tko.Reverse();
		db.updateGame(db.getGameIdFromGame(uno), uno);
	}

	public boolean checkWin(){
		for(Player p : players) {
			if(p.getPile().getNumCards() == 0) {
				return true;
			}
		}
		return false;
	}
}
