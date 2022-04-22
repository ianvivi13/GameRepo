package Database.elves;

import java.util.List;

import Models.Bot;
import Models.ExplodingKittensCard;
import Models.Pile;
import Models.Player;
import Models.StandardCard;
import Models.StatisticsBlackjack;
import Models.StatisticsExplodingKittens;
import Models.StatisticsGlobal;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.TurnOrder;
import Models.UnoCard;
import Models.UnoFlipCard;
import Models.User;

public interface IDatabase {
	public static final String Key_Global = "GLB";
	public static final String Key_ExplodingKittens = "EXP";
	public static final String Key_Uno = "UNO";
	public static final String Key_UnoFlip = "UNF";
	public static final String Key_Blackjack = "BLJ";

	
	// Still Unsorted
	public User getUserFromPlayerId(int PlayerId);
	public Bot getBotFromPlayerId(int PlayerId);
	public String getBotNameFromBotId(int BotId);
	public String getNameFromPlayerId(int PlayerId);
	public void deleteTurnOrder(int turnOrderId);
	
	
	// Player
	public int createPlayer(Player player);
	public Player getPlayerFromPlayerId(int playerId);
	public int getUserBotIdFromPlayerId(int PlayerId);
	public int getPlayerIdFromPlayer(Player player);
	public void updatePlayer(int player_id, Player player);
	public void deletePlayer(int playerId);
	public void deletePlayer(Player player);
	public boolean isHuman(int PlayerId);
	
	// User
	public int createUser(String username, String password);
	public User getUser(int UserID);
	public User getUser(String username);
	public int getUserIDfromUsername(String username);
	public String getUsernamefromUserID(int UserID);
	public void deleteUser(int userId);
	public void deleteUser(String username);
	public boolean login(String username, String password);
	
	// Bot
	public int createBot(Bot bot);
	public Bot getBot(int BotId);
	public void deleteBot(int botId);
	
	// Stat
	public void createAllStats(int UserId);
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
	public void deleteStats(int userId);
	public void deleteStats(String username);
	
	// TurnOrder
	public int createTurnOrder(TurnOrder turn);
	public TurnOrder getTurnOrderFromTurnOrderId(int turnId);
	public void updateTurnOrder(int turn_id, TurnOrder turn);
	
	// Game
	
	// Standard Card
	public int getCardIdFromStandardCard(StandardCard card);
	public StandardCard getStandardCardFromCardId(int cardID);
	
	// Uno Card
	public int getCardIdFromUnoCard(UnoCard card);
	public UnoCard getUnoCardFromCardId(int cardID);
	
	// Exploding Kitten Card
	public int getCardIdFromExplodingKittensCard(ExplodingKittensCard card);
	public ExplodingKittensCard getExplodingKittensCardFromCardId(int cardID);
	
	// Uno Flip Card
	public int getCardIdFromUnoFlipCard(UnoFlipCard card);
	public UnoFlipCard getUnoFlipCardFromCardId(int cardID);
	
	// Pile
	public int createPile(Pile pile);
	public Pile getPileFromPileId(int pileID);
	public void updatePile(int pile_id, Pile pile);
	public void deletePile(int pileId);

}
