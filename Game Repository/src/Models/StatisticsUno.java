package Models;

public class StatisticsUno extends StatisticsParent{
	private int WildCards;
	private int PlusFours;
	private int Swaps;
	
	public StatisticsUno() {
		this.WildCards = 0;
		this.PlusFours = 0;
		this.Swaps = 0;
	}
	
	// Getters
	public int GetWildCards() {
		return WildCards;
	}
	
	public int GetPlusFours() {
		return PlusFours;
	}
	
	public int GetSwaps() {
		return Swaps;
	}
	
	// Setters - called when a stat should be updated
	public void WildCard() {
		WildCards ++;
	}
	
	public void PlusFour() {
		PlusFours ++;
	}
	
	public void Swaped() {
		Swaps ++;
	}
}
