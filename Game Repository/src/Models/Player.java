package Models;

public class Player {
	private boolean isHuman;
	private Pile pile;
	private Pile altPile;
	private int userBotID;
	
	public Player(boolean isHuman, int userBotID) {
		this.isHuman = isHuman;
		this.userBotID = userBotID;
		pile = new Pile();
		altPile = new Pile();
	}
	
	public void setUserBotId(int Id) {
		this.userBotID = Id;
	}
	
	public boolean getIsHuman() {
		return isHuman;
	}
	
	public int getUserBotID() {
		return userBotID;
	}
	
	public Pile getPile() {
		return pile;
	}
	
	public Pile getAltPile() {
		return altPile;
	}
	
	public void setPile(Pile pile) {
		this.pile = pile;
	}
	
	public void setAltPile(Pile altPile) {
		this.altPile = pile;
	}
	
	public boolean equals(Player player) {
		if (this.isHuman != player.isHuman) {
			return false;
		}
		
		if (this.userBotID != player.userBotID) {
			return false;
		}
		
		if (!this.pile.equals(player.pile)) {
			return false;
		}
		
		if (!this.altPile.equals(player.altPile)) {
			return false;
		}
		
		return true;
	}
	
}