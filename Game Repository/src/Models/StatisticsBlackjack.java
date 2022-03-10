package Models;

public class StatisticsBlackjack extends StatisticsParent{
	private int Blackjacks;
	private int Splits;
	private int FiveCardWins;
	
	public StatisticsBlackjack() {
		this.Blackjacks = 0;
		this.Splits = 0;
		this.FiveCardWins = 0;
	}
	
	// Getters
	public int GetBlackjacks() {
		return Blackjacks;
	}
	
	public int GetSplits() {
		return Splits;
	}
	
	public int GetFiveCardWins() {
		return FiveCardWins;
	}
	
	// Setters - called when a stat should be updated
	public void Blackjack() {
		Blackjacks ++;
	}
	
	public void Split() {
		Splits ++;
	}
	
	public void FiveCardWin() {
		FiveCardWins ++;
	}
}
