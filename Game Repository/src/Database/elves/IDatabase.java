package Database.elves;

public interface IDatabase {
	public static final String Key_Global = "GLB";
	public static final String Key_ExplodingKittens = "EXP";
	public static final String Key_Uno = "UNO";
	public static final String Key_UnoFlip = "UNF";
	public static final String Key_Blackjack = "BLJ";
	
	public int createUser(String username, String password);
	public void createAllStats(int UserId);
	public int createBot(String gameKey, int difficulty);
	public int getUserIDfromUsername(String username);
	public String getUsernamefromUserID(int UserID);
}
