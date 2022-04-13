package Models;

import java.util.ArrayList;

public class Game {
	
	private ArrayList<Integer> players;
	private String gameID;
	private TurnOrder tko;
	
	
	public Game(){
		players = new ArrayList<>();
		gameID = " ";
	}
	
	public void nextTurn() {
		tko.NextTurn();
	}
	
	public void addPlayer(String player) {
		tko.AddPlayer(player);
	}
	
	public void removePlayer(String player) {
		tko.RemovePlayer(player);
	}
	
	
	
	public void updateStats() {
		
	}
}