package Models;

public class StatisticsGlobal extends StatisticsParent{
	private double WinLossRatio;
	private int Rank;
	
	public StatisticsGlobal() {
		this.WinLossRatio = 0;
		this.Rank = 0;
	}
	
	// Getters
	public double GetRatio() {
		return WinLossRatio;
	}
	
	public int GetRank() {
		return Rank;
	}
	
	// Updaters
	public void UpdateRatio() {
		WinLossRatio = GetGamesWon()/GetGamesLost();
	}
	
	public void UpdateRank() { // Currently not doing anything
		Rank += 1;
	}
	
}
