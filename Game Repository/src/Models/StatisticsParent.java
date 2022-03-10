package Models;

public class StatisticsParent {
	private int GamesPlayed;
	private int GamesWon;
	private int GamesLost;
	
	public StatisticsParent() {
		this.GamesPlayed = 0;
		this.GamesWon = 0;
		this.GamesLost = 0;
	}
	
	// Getters
	public int GetGamesWon() {
		return GamesWon;
	}
	
	public int GetGamesLost() {
		return GamesLost;
	}
	
	public int GetGamesPlayed() {
		return GamesPlayed;
	}
	
	// Setters - called when game won, played, or lost
	public void GameWon() {
		GamesWon ++;
	}
	
	public void GameLost() {
		GamesLost ++;
	}
	
	public void GamePlayed() {
		GamesPlayed ++;
	}
}
