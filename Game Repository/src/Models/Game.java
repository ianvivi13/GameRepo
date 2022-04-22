package Models;

import java.util.ArrayList;

public class Game {
	
	protected ArrayList<Integer> players;
	protected String gameID;
	protected char currentColor;
	protected boolean cardSideA;
	protected Pile pile;
	protected Pile altPile;
	protected TurnOrder tko;
	
	
	public Game(){
		players = new ArrayList<>();
		gameID = " ";
	}
	
	
	public void nextTurn() {
		tko.NextTurn();
	}
	
	public void addPlayer(int player) {
		tko.AddPlayer(player);
	}
	
	public void removePlayer(int player) {
		tko.RemovePlayer(player);
	}
	
	
	
	public void updateStats() {
		
	}
}