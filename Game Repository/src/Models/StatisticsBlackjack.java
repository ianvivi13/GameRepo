package Models;

public class StatisticsBlackjack extends StatisticsParent{
	private int Blackjacks;
	private int Hits;
	private int Froze;
	
	public StatisticsBlackjack() {
		this.Blackjacks = 0;
		this.Hits = 0;
		this.Froze = 0;
	}
	
	// Setters
	public void SetBlackjacks(int Blackjacks) {
		this.Blackjacks = Blackjacks;
	}
	
	public void SetHits(int Hits) {
		this.Hits = Hits;
	}
	
	public void SetFroze(int Froze) {
		this.Froze = Froze;
	}
	
	// Getters
	public int GetBlackjacks() {
		return Blackjacks;
	}
	
	public int GetHits() {
		return Hits;
	}
	
	public int GetFroze() {
		return Froze;
	}
	
	// Setters - called when a stat should be updated
	public void Blackjack() {
		Blackjacks ++;
	}
	
	public void Hit() {
		Hits ++;
	}
	
	public void Froze() {
		Froze ++;
	}
	
}
