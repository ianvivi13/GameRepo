package Models;

public class StatisticsExplodingKittens extends StatisticsParent{
	private int Defuses;
	private int Favors;
	private int Futures;
	
	public StatisticsExplodingKittens() {
		this.Defuses = 0;
		this.Favors = 0;
		this.Futures = 0;
	}
	
	// Getters
	public int GetDefuses() {
		return Defuses;
	}
	
	public int GetFavors() {
		return Favors;
	}
	
	public int GetFutures() {
		return Futures;
	}
	
	// Setters - called when a stat should be updated
	public void Defuse() {
		Defuses ++;
	}
	
	public void Favor() {
		Favors ++;
	}
	
	public void Future() {
		Futures ++;
	}
}
