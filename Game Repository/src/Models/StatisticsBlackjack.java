package Models;

public class StatisticsBlackjack extends StatisticsParent{
	private int Holds;
	private int Hits;
	private int Froze;
	
	public StatisticsBlackjack() {
		this.Holds = 0;
		this.Hits = 0;
		this.Froze = 0;
	}
	
	// Setters
	public void SetBlackjacks(int Holds) {
		this.Holds = Holds;
	}
	
	public void SetHits(int Hits) {
		this.Hits = Hits;
	}
	
	public void SetFroze(int Froze) {
		this.Froze = Froze;
	}
	
	// Getters
	public int GetHolds() {
		return Holds;
	}
	
	public int GetHits() {
		return Hits;
	}
	
	public int GetFroze() {
		return Froze;
	}
	
	// Setters - called when a stat should be updated
	public void Hold() {
		Holds ++;
	}
	
	public void Hit() {
		Hits ++;
	}
	
	public void Froze() {
		Froze ++;
	}
	
}
