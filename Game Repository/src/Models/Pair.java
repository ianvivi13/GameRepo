package Models;
import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class Pair {
	
	private final int userID;
	private int gameID;
	
	public Pair(int userID) {
		this.userID = userID;
		this.gameID = -1;
		
	}
	
	public Pair(String username) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		this.userID = db.getUserIDfromUsername(username);
		
		this.gameID = -1;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setBackground(String id) {
		if(id.equals(IDatabase.Key_Blackjack)) {
			this.gameID = -2;
		}
		else if (id.equals(IDatabase.Key_Uno)) {
			this.gameID = -3;
		}
		else if(id.equals(IDatabase.Key_ExplodingKittens)) {
			this.gameID = -4;
		}
		else if(id.equals(IDatabase.Key_UnoFlip)) {
			this.gameID = -5;
		}
		else {
			this.gameID = -1;
		}
	}
	
	public String getBackground() {
		if(gameID == -2) {
			return IDatabase.Key_Blackjack;
		}
		if(gameID == -3) {
			return IDatabase.Key_Uno;
		}
		if(gameID == -4) {
			return IDatabase.Key_ExplodingKittens;
		}
		if(gameID == -5) {
			return IDatabase.Key_UnoFlip;
		}
		
		return IDatabase.Key_Global;
	}
	
	public String getUsername() {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getUsernamefromUserID(userID);
	}
}