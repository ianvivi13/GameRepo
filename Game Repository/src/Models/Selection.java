package Models;

import java.util.ArrayList;

public class Selection{
	
	private Location loc;
	private ArrayList<Object> select;
	
	public Selection(Location ori, ArrayList<Object> selected) {
		this.loc=ori;
		this.select=selected;
	}
	
	public Location getOrigin() {
		return loc;
	}
	
	public ArrayList<Object> getCards(){
		return select;
	}
	
	public int getNumCards() {
		return select.size();
	}
}