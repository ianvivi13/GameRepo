package Models;

import java.util.ArrayList;

public class Selection{
	
	private Location loc;
	private ArrayList<Card> select;
	
	public Selection(Location ori, ArrayList<Card> selected) {
		this.loc=ori;
		this.select=selected;
	}
	
	public Location getOrigin() {
		return loc;
	}
	
	public ArrayList<Card> getCards(){
		return select;
	}
	
	public int getNumCards() {
		return select.size();
	}
}