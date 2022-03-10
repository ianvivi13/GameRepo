package Models;

public class StatisticsUnoFlip extends StatisticsParent{
	private int Flips;
	private int SkipAlls;
	private int PlusFives;
	
	public StatisticsUnoFlip() {
		this.Flips = 0;
		this.SkipAlls = 0;
		this.PlusFives = 0;
	}
	
	// Getters
	public int GetFlips() {
		return Flips;
	}
	
	public int GetSkipAlls() {
		return SkipAlls;
	}
	
	public int GetPlusFives() {
		return PlusFives;
	}
	
	// Setters - called when a stat should be updated
	public void Flip() {
		Flips ++;
	}
	
	public void SkipAll() {
		SkipAlls ++;
	}
	
	public void PlusFive() {
		PlusFives ++;
	}
}
