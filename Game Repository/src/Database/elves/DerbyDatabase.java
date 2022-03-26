package Database.elves;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import Models.BotNameGenerator;
import Models.User;
import Models.StatisticsBlackjack;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.StatisticsGlobal;
import Models.StatisticsExplodingKittens;

public class DerbyDatabase implements IDatabase {
	// Max Attempts of transactions before fail
	private static final int MAX_ATTEMPTS = 10;
	
	// Attempt to load derby driver
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	// Transaction interface
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	// Execute Transaction
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	// do Execute Transaction
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	// establish connection with database
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:Dobby.db;create=true");
		
		// Set auto-commit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	// Recursively delete directory
	private static void deleteDirectory(File file) {
	    File[] list = file.listFiles();
	    if (list != null) {
	        for (File temp : list) {
	            //recursive delete
	            deleteDirectory(temp);
	        }
	    }
	    file.delete();
	}
		
	// method to delete Dobby.db if exists and user accepts before re-creation
	private static Boolean DeleteDobby() {
		// check if Dobby.db exists
		File DobbyPath = new File("Dobby.db");
		Boolean DobbyExists = DobbyPath.exists();
		if (DobbyExists) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Dobby the data elf already exists");
			System.out.print("Would you like to re-create him (y/n): ");
			String ans = keyboard.nextLine();
			keyboard.close();
			if (!(ans.equals("y") || ans.equals("Y"))) {
				return false;
			}
			deleteDirectory(DobbyPath);
			System.out.println("Re-creating Dobby...");
		}
		return true;
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		if (DeleteDobby()) {
			System.out.println("Creating tables...");
			DerbyDatabase db = new DerbyDatabase();
			db.createTables();
			System.out.println("Loading initial data...");
			db.loadInitialData();
			System.out.println("Success!");
		}
	}	
	
	// create initial tables
	private void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				String gameKey = "gameKey VARCHAR(3) CHECK (gameKey = 'GLB' OR gameKey = 'EXP' OR gameKey = 'UNO' OR gameKey = 'UNF' OR gameKey = 'BLJ') NOT NUll,";
				String gameKeyNotGlobal = "gameKey VARCHAR(3) CHECK (gameKey = 'EXP' OR gameKey = 'UNO' OR gameKey = 'UNF' OR gameKey = 'BLJ') NOT NUll,";
				
				try {
					// Create Users Table
					stmt = conn.prepareStatement(
							"CREATE TABLE Users (" +
							"	user_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +									
							"	username VARCHAR(20) NOT NULL UNIQUE ," +
							"	password VARCHAR(32) NOT NULL )" 
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Stats Table
					stmt = conn.prepareStatement(
							"CREATE TABLE Stats (" +
							"	user_id INTEGER, FOREIGN KEY (user_id) REFERENCES Users(user_id) ," +
							gameKey +
							"   plays INTEGER DEFAULT 0 ," +
							"   wins INTEGER DEFAULT 0 ," +
							"   loses INTEGER DEFAULT 0 ," +
							"   sOne INTEGER DEFAULT 0 ," +
							"   sTwo INTEGER DEFAULT 0 ," +
							"   sThree INTEGER DEFAULT 0 )" 
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Bots Table
					stmt = conn.prepareStatement(
							"CREATE TABLE Bots (" +
							"	bot_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +	
							gameKeyNotGlobal +
							"	name VARCHAR(36) NOT NULL ," +
							"	difficulty integer NOT NULL CHECK (difficulty >= 1 AND difficulty <= 3) DEFAULT 1)" 
					);
					stmt.executeUpdate();
					stmt.close();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Create and store initial data
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				InitDatabase.init();
				IDatabase db = DatabaseProvider.getInstance();
				try {
					db.createAllStats(db.createUser("NewUser","password still"));
					db.createAllStats(db.createUser("User","password still"));
					db.createBot(IDatabase.Key_Blackjack,2);
					db.createBot(IDatabase.Key_ExplodingKittens,2);
					db.createBot(IDatabase.Key_UnoFlip,2);
					return true;
				} catch (UserExistsException e) {
					System.out.println(e);
					return false;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Define some methods to use to access things within the database
	/*
	import Database.elves.DatabaseProvider;
	import Database.elves.IDatabase;
	InitDatabase.init();
	IDatabase db = DatabaseProvider.getInstance();
	db.createUser(username, password);
	*/
	
	// create new user in database - checks if already exists
	public int createUser(String username, String password) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"INSERT INTO Users (username, password)" +
							"	VALUES (?, ?)"
						);
					stmt.setString(1, username);
					stmt.setString(2, password);
					stmt.executeUpdate();
					stmt.close();
						
					stmt = conn.prepareStatement(
							"SELECT Users.User_id" +
							"	FROM Users" +
							"		WHERE Users.username = ?"
					);
					stmt.setString(1, username);
					resultSet = stmt.executeQuery();
					resultSet.next();
					int res = resultSet.getInt("user_id");
					return res;
				} catch (Exception SQLIntegrityConstraintViolationException) {
					throw new UserExistsException("User already exists in database");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// create new bot in database
	public int createBot(String gameKey, int difficulty) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				BotNameGenerator botName = new BotNameGenerator();
				
				try {
					stmt = conn.prepareStatement(
						"INSERT INTO Bots (name, gameKey, difficulty)" +
						"	VALUES (?, ?, ?)"
					);
					stmt.setString(1, botName.GenerateName());
					stmt.setString(2, gameKey);
					stmt.setInt(3, difficulty);
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
						"SELECT MAX(bot_id) AS bot_id" +
						"	From Bots"
					);
					resultSet = stmt.executeQuery();
					resultSet.next();
					int ret = resultSet.getInt("bot_id");
					stmt.close();
					
					return ret;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// create stats in database
	public void createAllStats(int UserId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				stmt = conn.prepareStatement(
						"SELECT stats.user_id" +
						" 	FROM stats" +
						"		WHERE stats.user_id = ?"
				);
				
				stmt.setInt(1, UserId);
				resultSet = stmt.executeQuery();
				
				
				if (resultSet.next()) {
					stmt.close();
					return null;
				}
				stmt.close();
				try {
					List<String> gameKeyList = new ArrayList<String>();
					gameKeyList.add(IDatabase.Key_Global);
					gameKeyList.add(IDatabase.Key_ExplodingKittens);
					gameKeyList.add(IDatabase.Key_Uno);
					gameKeyList.add(IDatabase.Key_UnoFlip);
					gameKeyList.add(IDatabase.Key_Blackjack);
					
					for (String gameKey : gameKeyList) {
						stmt = conn.prepareStatement(
								"INSERT INTO Stats(user_id, gameKey)" +
								" 	VALUES(?, ?)"
						);
						stmt.setInt(1, UserId);
						stmt.setString(2, gameKey);
						stmt.executeUpdate();
						stmt.close();
					}
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get user_id from username
	public int getUserIDfromUsername(String username) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Users.User_id" +
							"	FROM Users" +
							"		WHERE Users.username = ?"
					);
					stmt.setString(1, username);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int res = resultSet.getInt("user_id");
						return res;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get username from user_id
	public String getUsernamefromUserID(int UserID) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Users.username" +
							"	FROM Users" +
							"		WHERE Users.user_id = ?"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						String res = resultSet.getString("username");
						return res;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object User from user_id
	public User getUser(int UserID) {
		return executeTransaction(new Transaction<User>() {
			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Users.username, Users.password" +
							"	FROM Users" +
							"		WHERE Users.user_id = ?"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						String username = resultSet.getString("username");
						String password = resultSet.getString("password");
						return new User(username, password);
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object User from username
	public User getUser(String username) {
		return getUser(getUserIDfromUsername(username));
	}
	
	// get object UnoStats from user_id
	public StatisticsUno getUnoStats(int UserID) {
		return executeTransaction(new Transaction<StatisticsUno>() {
			@Override
			public StatisticsUno execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"SELECT Stats.plays, Stats.wins, Stats.loses, Stats.sOne, Stats.sTwo, Stats.sThree" +
						"	FROM Stats" +
						"		WHERE Stats.user_id = ?" +
						"		AND Stats.gameKey = 'UNO'"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int plays = resultSet.getInt("plays");
						int wins = resultSet.getInt("wins");
						int loses = resultSet.getInt("loses");
						int sOne = resultSet.getInt("sOne");
						int sTwo = resultSet.getInt("sTwo");
						int sThree = resultSet.getInt("sThree");
						StatisticsUno stat = new StatisticsUno();
						stat.SetGamesPlayed(plays);
						stat.SetGamesWon(wins);
						stat.SetGamesLost(loses);
						stat.SetWildCards(sOne);
						stat.SetPlusFours(sTwo);
						stat.SetSwaps(sThree);
						return stat;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object UnoStats from username
	public StatisticsUno getUnoStats(String username) {
		return getUnoStats(getUserIDfromUsername(username));
	}
	
	// get object UnoFlipStats from user_id
	public StatisticsUnoFlip getUnoFlipStats(int UserID) {
		return executeTransaction(new Transaction<StatisticsUnoFlip>() {
			@Override
			public StatisticsUnoFlip execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"SELECT Stats.plays, Stats.wins, Stats.loses, Stats.sOne, Stats.sTwo, Stats.sThree" +
						"	FROM Stats" +
						"		WHERE Stats.user_id = ?" +
						"		AND Stats.gameKey = 'UNF'"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int plays = resultSet.getInt("plays");
						int wins = resultSet.getInt("wins");
						int loses = resultSet.getInt("loses");
						int sOne = resultSet.getInt("sOne");
						int sTwo = resultSet.getInt("sTwo");
						int sThree = resultSet.getInt("sThree");
						StatisticsUnoFlip stat = new StatisticsUnoFlip();
						stat.SetGamesPlayed(plays);
						stat.SetGamesWon(wins);
						stat.SetGamesLost(loses);
						stat.SetFlips(sOne);
						stat.SetSkipAlls(sTwo);
						stat.SetPlusFives(sThree);
						return stat;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object UnoFlipStats from username
	public StatisticsUnoFlip getUnoFlipStats(String username) {
		return getUnoFlipStats(getUserIDfromUsername(username));
	}
	
	// get object BlackjackStats from user_id
	public StatisticsBlackjack getBlackjackStats(int UserID) {
		return executeTransaction(new Transaction<StatisticsBlackjack>() {
			@Override
			public StatisticsBlackjack execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"SELECT Stats.plays, Stats.wins, Stats.loses, Stats.sOne, Stats.sTwo, Stats.sThree" +
						"	FROM Stats" +
						"		WHERE Stats.user_id = ?" +
						"		AND Stats.gameKey = 'BLJ'"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int plays = resultSet.getInt("plays");
						int wins = resultSet.getInt("wins");
						int loses = resultSet.getInt("loses");
						int sOne = resultSet.getInt("sOne");
						int sTwo = resultSet.getInt("sTwo");
						int sThree = resultSet.getInt("sThree");
						StatisticsBlackjack stat = new StatisticsBlackjack();
						stat.SetGamesPlayed(plays);
						stat.SetGamesWon(wins);
						stat.SetGamesLost(loses);
						stat.SetBlackjacks(sOne);
						stat.SetSplits(sTwo);
						stat.SetFiveCardWins(sThree);
						return stat;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object BlackjackStats from username
	public StatisticsBlackjack getBlackjackStats(String username) {
		return getBlackjackStats(getUserIDfromUsername(username));
	}
	
	// get object ExplodingKittenStats from user_id
	public StatisticsExplodingKittens getExplodingKittenStats(int UserID) {
		return executeTransaction(new Transaction<StatisticsExplodingKittens>() {
			@Override
			public StatisticsExplodingKittens execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"SELECT Stats.plays, Stats.wins, Stats.loses, Stats.sOne, Stats.sTwo, Stats.sThree" +
						"	FROM Stats" +
						"		WHERE Stats.user_id = ?" +
						"		AND Stats.gameKey = 'EXP'"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int plays = resultSet.getInt("plays");
						int wins = resultSet.getInt("wins");
						int loses = resultSet.getInt("loses");
						int sOne = resultSet.getInt("sOne");
						int sTwo = resultSet.getInt("sTwo");
						int sThree = resultSet.getInt("sThree");
						StatisticsExplodingKittens stat = new StatisticsExplodingKittens();
						stat.SetGamesPlayed(plays);
						stat.SetGamesWon(wins);
						stat.SetGamesLost(loses);
						stat.SetDefuses(sOne);
						stat.SetFavors(sTwo);
						stat.SetFutures(sThree);
						return stat;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}	
	
	// get object ExplodingKittenStats from username
	public StatisticsExplodingKittens getExplodingKittenStats(String username) {
		return getExplodingKittenStats(getUserIDfromUsername(username));
	}
	
	// get object GlobalStats from user_id
	public StatisticsGlobal getGlobalStats(int UserID) {
		return executeTransaction(new Transaction<StatisticsGlobal>() {
			@Override
			public StatisticsGlobal execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
						"SELECT Stats.plays, Stats.wins, Stats.loses, Stats.sOne" +
						"	FROM Stats" +
						"		WHERE Stats.user_id = ?" +
						"		AND Stats.gameKey = 'GLB'"
					);
					stmt.setInt(1, UserID);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int plays = resultSet.getInt("plays");
						int wins = resultSet.getInt("wins");
						int loses = resultSet.getInt("loses");
						int sOne = resultSet.getInt("sOne");
						StatisticsGlobal stat = new StatisticsGlobal();
						stat.SetGamesPlayed(plays);
						stat.SetGamesWon(wins);
						stat.SetGamesLost(loses);
						stat.SetRank(sOne);
						return stat;
					}
					throw new UserDoesNotExistException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get object GlobalStats from username
	public StatisticsGlobal getGlobalStats(String username) {
		return getGlobalStats(getUserIDfromUsername(username));
	}
}
