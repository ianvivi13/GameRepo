package Models;

import java.util.ArrayList;
import java.util.Random;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class Game {
	
	private ArrayList<Integer> playerIds;
	private ArrayList<Player> players;
	private Pile mainPile;
	private Pile altPile;
	private String gameCode;
	private String gameKey;
	private TurnOrder tko;
	private boolean cardSideA;
	private String wildColor;
	private IDatabase db;
	private int MaxPlayers;
	private int AuxInt;
	private int Update;
	
	public Game(String gameKey){
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		playerIds = new ArrayList<>();
		players = new ArrayList<>();
		gameCode = generateGameCode();
		this.gameKey = gameKey;
		mainPile = new Pile();
		altPile = new Pile();
		cardSideA = true;
		wildColor = "B";
		tko = new TurnOrder();
		MaxPlayers = 2; 
		AuxInt = 0;
		Update = 0;
		
		while(db.gameCodeValid(gameCode) > 0) {
			gameCode = generateGameCode();
		}
	}
	
	public Game(String gameCode, String gameKey){
		playerIds = new ArrayList<>();
		players = new ArrayList<>();
		this.gameCode = gameCode;
		this.gameKey = gameKey;
		mainPile = new Pile();
		altPile = new Pile();
		cardSideA = true;
		wildColor = "B";
		MaxPlayers = 2;
		tko = new TurnOrder();
		AuxInt = 0;
		Update = 0;
	}
	
	public int getUpdate() {
		return Update;
	}
	
	public void setUpdate(int count) {
		Update = count;
	}
	
	public int getAuxInt() {
		return AuxInt;
	}
	
	public void setAuxInt(int Aux) {
		AuxInt = Aux;
	}
	
	public void incrementAuxInt() {
		AuxInt ++;
	}
	
	public void resetAuxInt() {
		AuxInt = 0;
	}
	
	public void flip() {
		if(cardSideA) {
			cardSideA = false;
		}
		else {
			cardSideA = true;
		}
	}
	
	public void setWildColor(String color) {
		wildColor = color;
	}
	
	public Player getIndexPlayer(int index) {
		return players.get(index);
	}
	
	public void setGameCode(String code) {
		gameCode = code;
	}
	
	public void setMainPile(Pile pile) {
		mainPile = pile;
	}
	
	public void setAltPile(Pile pile) {
		altPile = pile;
	}
	
	public void setMaxPlayers(int val) {
		MaxPlayers = val;
	}
	
	public int getMaxPlayers() {
		return MaxPlayers;
	}
	
	public void setTurnOrder(TurnOrder turn) {
		tko = turn;
	}
	
	public void setPlayerIds(ArrayList<Integer> playerIds) {
		this.playerIds = playerIds;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public Pile getMainPile() {
		return mainPile;
	}
	
	public Pile getAltPile() {
		return altPile;
	}
	
	public String getGameCode() {
		return gameCode;
	}
	
	public String getGameKey() {
		return gameKey;
	}
	
	public ArrayList<Integer> getPlayerIds() {
		return playerIds;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public int getNumOfPlayers() {
		return players.size();
	}
	
	public TurnOrder getTurnOrder() {
		return tko;
	}
	
	public String getWildColor() {
		return wildColor;
	}
	
	public boolean getCardSideA() {
		return cardSideA;
	}
	
	public void nextTurn() {
		tko.NextTurn();
	}
	
	public void addPlayer(int playerId) {
		db = DatabaseProvider.getInstance();
		tko.AddPlayer(playerId);
		playerIds.add(playerId);

		players.add(db.getPlayerFromPlayerId(playerId));	

		
		

	}
	
	public void removePlayerFromTurn(int playerId) {
		tko.RemovePlayer(playerId);
	}
	
	public void removePlayerFromGame(int playerId) {
		db = DatabaseProvider.getInstance();
		tko.RemovePlayer(playerId);
		playerIds.remove(playerId);
		players.remove(db.getPlayerFromPlayerId(playerId));
		playerIds.remove(playerId);
	}
	
	public String generateGameCode() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String randomStringOne = "";
		String randomStringTwo = "";
		int length = 5;
		
		Random rand = new Random();
		
		char[] textOne = new char[length];
		char[] textTwo = new char[length];
		
		for(int i = 0; i < length; i++) {
			textOne[i] = characters.charAt(rand.nextInt(characters.length()));
			textTwo[i] = characters.charAt(rand.nextInt(characters.length()));
		}
		
		for(int i = 0; i < textOne.length; i++) {
			randomStringOne += textOne[i];
			randomStringTwo += textTwo[i];
		}
		
		return randomStringOne + "-" +  randomStringTwo;
	}
	
	public boolean equals(Game game) {
		if(this.cardSideA != game.cardSideA) {
			return false;
		}
		
		else if(!this.gameKey.equals(game.gameKey)) {
			return false;
		}
		
		else if(!this.gameCode.equals(game.gameCode)) {
			return false;
		}
		
		else if(!this.mainPile.equals(game.getMainPile())) {
			return false;
		}
		
		else if(!this.altPile.equals(game.getAltPile())) {
			return false;
		}
		
		else if(!this.tko.equals(game.getTurnOrder())) {
			return false;
		}
		
		else if(!this.wildColor.equals(game.getWildColor())) {
			return false;
		}
		
		for (int i = 0 ; i < this.playerIds.size() ; i ++) {
    		if (!this.playerIds.get(i).equals(game.getPlayerIds().get(i))) {
    			return false;
    		}
		}
    		
		for (int i = 0 ; i < this.players.size() ; i ++) {
    		if (!this.players.get(i).equals(game.getPlayers().get(i))) {
    			return false;
    		}
		}
		
		return true;
	}
	
	public boolean lobbyFull() {
		return (MaxPlayers == playerIds.size());
	}
	
	public void reverseTurnOrder() {
		tko.Reverse();
	}
	
	public void currentPlayer(String player) {
		tko.CurrentPlayer();
	}
	
	public void reverseOrder() {
		tko.Reverse();
	}
	
	public void updateStats() {
		
	}
}