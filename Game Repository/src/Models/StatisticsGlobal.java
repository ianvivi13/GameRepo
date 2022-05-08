package Models;

public class StatisticsGlobal extends StatisticsParent{
	private int Rank;
	
	public StatisticsGlobal() {
		this.Rank = 0;
	}
	
	// Setters
	public void SetRank(int Rank) {
		this.Rank = Rank;
	}
	
	// Getters
	
	public int GetRank() {
		return Rank;
	}
	
	// Updaters	
	public void UpdateRank() { // Currently not doing anything
		Rank += 1;
	}
	
}
