package Database.elves;

import java.util.List;

import Models.ExplodingKittensCard;
import Models.Pile;
import Models.Player;
import Models.StandardCard;
import Models.StatisticsBlackjack;
import Models.StatisticsExplodingKittens;
import Models.StatisticsGlobal;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.UnoCard;
import Models.UnoFlipCard;
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
	public int createPile(Pile pile);
	public int createPlayer(Player player); //Test
	
	// Deleters
	public void deleteStats(int userId); //Test
	public void deleteStats(String username); //Test
	public void deleteUser(int userId); //Test
	public void deleteUser(String username); //Test
	public void deleteBot(int botId); //Test
	public void deletePlayer(int playerId); //Test
	public void deletePile(int pileId); //Test

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
	
	// Card retrieval
	public StandardCard getStandardCardFromCardId(int cardID);
	public ExplodingKittensCard getExplodingKittensCardFromCardId(int cardID);
	public UnoCard getUnoCardFromCardId(int cardID);
	public UnoFlipCard getUnoFlipCardFromCardId(int cardID);
	public int getCardIdFromStandardCard(StandardCard card);
	public int getCardIdFromExplodingKittensCard(ExplodingKittensCard card);
	public int getCardIdFromUnoCard(UnoCard card);
	public int getCardIdFromUnoFlipCard(UnoFlipCard card);
	
	// User and Player data retrieval
	public String getUsernamefromUserID(int UserID);
	public Pile getPileFromPileId(int pileID);
	public User getUser(int UserID);
	public User getUser(String username);
	public int getUserIDfromUsername(String username);
	public boolean isHuman(int PlayerId); //Test
	public Player getPlayerFromPlayerId(int playerId);
	
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
	public void updatePile(int pile_id, Pile pile);
	//public void updatePlayer(int player_id, Player player);
	
	// Quality of Life
	//public int getPileFromCardList(List<Object> cards);
}
