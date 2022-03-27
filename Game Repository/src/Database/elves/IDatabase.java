package Database.elves;

import Models.StatisticsBlackjack;
import Models.StatisticsExplodingKittens;
import Models.StatisticsGlobal;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.User;

public interface IDatabase {
	public static final String Key_Global = "GLB";
	public static final String Key_ExplodingKittens = "EXP";
	public static final String Key_Uno = "UNO";
	public static final String Key_UnoFlip = "UNF";
	public static final String Key_Blackjack = "BLJ";
	
	public int createUser(String username, String password);
	public void createAllStats(int UserId);
	public int createBot(String gameKey, int difficulty);
	public void createBlackJackCards();
	public int getUserIDfromUsername(String username);
	public String getUsernamefromUserID(int UserID);
	public User getUser(int UserID);
	public User getUser(String username);
	
	// Stats retrieving - overloaded to allow use of username or user_id
	public StatisticsGlobal getGlobalStats(int UserID);
	public StatisticsGlobal getGlobalStats(String username);
	public StatisticsUno getUnoStats(int UserID);
	public StatisticsUno getUnoStats(String username);
	public StatisticsUnoFlip getUnoFlipStats(int UserID);
	public StatisticsUnoFlip getUnoFlipStats(String username);
	public StatisticsBlackjack getBlackjackStats(int UserID);
	public StatisticsBlackjack getBlackjackStats(String username);
	public StatisticsExplodingKittens getExplodingKittenStats(int UserID);
	public StatisticsExplodingKittens getExplodingKittenStats(String username);
}
