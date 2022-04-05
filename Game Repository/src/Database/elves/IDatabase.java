package Database.elves;

import java.util.List;

import Models.Pile;
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
	
	// Creators
	public int createUser(String username, String password);
	public void createAllStats(int UserId);
	public int createBot(String gameKey, int difficulty);
	public int createPile(String gameKey, int exposeIndex);
	public int createPile(List<Object> cards, int exposeIndex);
	public int createPlayer(int id, boolean user, Pile hand, Pile altHand);
	
	// Card Initializers
	public void initializeBlackJackCards();
	public void initializeExplodingKittensCards();
	public void initializeUnoCards();
	public int getUserIDfromUsername(String username);

	// Login
	public boolean login(String username, String password);
	
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
	
	// User data retrieval
	public String getUsernamefromUserID(int UserID);
	public User getUser(int UserID);
	public User getUser(String username);
	public boolean isHuman(int PlayerId);
	
	// Stats updating - overloaded to allow use of username or user_id
	public void updateGlobalStats(StatisticsGlobal stat, int user_id);
	public void updateGlobalStats(StatisticsGlobal stat, String username);
	public void updateUnoStats(StatisticsUno stat, int user_id);
	public void updateUnoStats(StatisticsUno stat, String username);
	public void updateUnoFlipStats(StatisticsUnoFlip stat, int user_id);
	public void updateUnoFlipStats(StatisticsUnoFlip stat, String username);
	public void updateBlackjackStats(StatisticsBlackjack stat, int user_id);
	public void updateBlackjackStats(StatisticsBlackjack stat, String username);
	public void updateExplodingKittensStats(StatisticsExplodingKittens stat, int user_id);
	public void updateExplodingKittensStats(StatisticsExplodingKittens stat, String username);
	
	// Other updaters
	public void updatePile(int pile_id, int exposeIndex, List<Object> cards);
	public void updatePlayer(int player_id, int newPileId, int newAltPileId);
	
	// Quality of Life
	public String encodeCardIds(List<Object> cards);
	public int getPileFromCardList(List<Object> cards);
	public String encodeCardIds(Pile pile);
}
