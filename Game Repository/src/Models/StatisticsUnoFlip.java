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
	
	// Setters
	public void SetFlips(int Flips) {
		this.Flips = Flips;
	}
	
	public void SetSkipAlls(int SkipAlls) {
		this.SkipAlls = SkipAlls;
	}
	
	public void SetPlusFives(int PlusFives) {
		this.PlusFives = PlusFives;
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
