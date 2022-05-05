package Models;

public class StatisticsUno extends StatisticsParent{
	private int WildCards;
	private int PlusFours;
	private int Reverses;
	
	public StatisticsUno() {
		this.WildCards = 0;
		this.PlusFours = 0;
		this.Reverses = 0;
	}
	
	// Setters
	public void SetWildCards(int WildCards) {
		this.WildCards = WildCards;
	}
	
	public void SetPlusFours(int PlusFours) {
		this.PlusFours = PlusFours;
	}
	
	public void SetReverses(int Reverses) {
		this.Reverses = Reverses;
	}
	
	// Getters
	public int GetWildCards() {
		return WildCards;
	}
	
	public int GetPlusFours() {
		return PlusFours;
	}
	
	public int GetReverses() {
		return Reverses;
	}
	
	// Setters - called when a stat should be updated
	public void WildCard() {
		WildCards ++;
	}
	
	public void PlusFour() {
		PlusFours ++;
	}
	
	public void Reversed() {
		Reverses ++;
	}
	
	public void print() {
		System.out.println(WildCards);
		System.out.println(PlusFours);
		System.out.println(Reverses);
		System.out.println(GamesPlayed);
		System.out.println(GamesWon);
		System.out.println(GamesLost);
	}
}
