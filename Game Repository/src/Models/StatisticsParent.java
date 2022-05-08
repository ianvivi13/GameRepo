package Models;

public class StatisticsParent {
	protected int GamesPlayed;
	protected int GamesWon;
	protected int GamesLost;
	
	public StatisticsParent() {
		this.GamesPlayed = 0;
		this.GamesWon = 0;
		this.GamesLost = 0;
	}
	
	// Setters
	public void SetGamesWon(int won) {
		this.GamesWon = won;
	}
	
	public void SetGamesLost(int lost) {
		this.GamesLost = lost;
	}
	
	public void SetGamesPlayed(int played) {
		this.GamesPlayed = played;
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
	
	public double GetRatio() {
		if(GetGamesLost() == 0) {
			return 0;
		}
		return GetGamesWon()/GetGamesLost();
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
