package Database.elves;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import Models.sqlTranscoder;
import Models.Color;
import Models.Value;
import Models.ExplodingKittensCard;
import Models.User;
import Models.StatisticsBlackjack;
import Models.StatisticsUno;
import Models.StatisticsUnoFlip;
import Models.Suit;
import Models.TurnOrder;
import Models.Type;
import Models.UnoCard;
import Models.StatisticsGlobal;
import Models.StatisticsExplodingKittens;
import Models.BlackJackCardDobbyInit;
import Models.ExplodingKittensCardDobbyInit;
import Models.Game;
import Models.Pile;
import Models.Player;
import Models.Rank;
import Models.UnoCardDobbyInit;
import Models.UnoFlipCard;
import Models.StandardCard;
import Models.Bot;

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
		File DobbyPath = new File("Dobby.db");
		deleteDirectory(DobbyPath);
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
					
					// Create Standard Cards Table
					stmt = conn.prepareStatement(
							"CREATE TABLE BlackJackCards ("
							+ "	card_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,"
							+ "	imagePath VARCHAR(55) NOT NULL ,"
							+ "	suit VARCHAR(1) NOT NULL ,"
							+ " rank VARCHAR(1) NOT NULL)"
							);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Exploding Kittens Cards Table
					stmt = conn.prepareStatement(
						"CREATE TABLE ExplodingKittensCards ("
						+ "	card_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,"
						+ "	imagePath VARCHAR(65) NOT NULL ,"
						+ "	type VARCHAR(2) NOT NULL)"
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create pile table
					stmt = conn.prepareStatement(
						"CREATE TABLE Pile (" +
						"	pile_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						gameKeyNotGlobal +
						"	exposeIndex INTEGER DEFAULT 0 ," +
						"	cards VARCHAR(600))"
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Uno Flip Side Table
					stmt = conn.prepareStatement(
						"CREATE TABLE UnoFlipSide (" +
						"	uno_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						"	imagePath VARCHAR(300) ," +
						"	color VARCHAR(1) ,"
						+ "	type VARCHAR(1))"
						//CHANGE VARCHAR(300) TO MATCH MAX CHAR LEN OF IMAGE PATH
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Uno Flip Cards Table
					stmt = conn.prepareStatement(
						"CREATE TABLE UnoFlipCards (" +
						"	card_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						"	uno_id INTEGER, FOREIGN KEY (uno_id) REFERENCES UnoFlipSide(uno_id) ," +
						"	uno_id_flip INTEGER, FOREIGN KEY (uno_id_flip) REFERENCES UnoFlipSide(uno_id))"
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Uno Cards Table
					stmt = conn.prepareStatement(
						"CREATE TABLE UnoCards (" +
						"	card_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						"	imagePath VARCHAR(300) ," +
						"	color VARCHAR(1) ,"
						+ "	type VARCHAR(1))"
						//CHANGE VARCHAR(300) TO MATCH MAX CHAR LEN OF IMAGE PATH
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Player Table
					stmt = conn.prepareStatement(
						"CREATE TABLE Player (" +
						"	player_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						"	player_bot_id INTEGER ," +
						"	human BOOLEAN ,"
						+ "	pile_id INTEGER, FOREIGN KEY (pile_id) REFERENCES Pile(pile_id) ,"
						+ "	alt_pile_id INTEGER, FOREIGN KEY (pile_id) REFERENCES Pile(pile_id))"
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Turn Table
					stmt = conn.prepareStatement(
						"CREATE TABLE Turn (" +
						"	turn_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						"	pointer INTEGER ," +
						"	adder INTEGER ,"
						+ "	turn_order VARCHAR(600))"
					);
					stmt.executeUpdate();
					stmt.close();
					
					// Create Game Table
					stmt = conn.prepareStatement(
						"CREATE TABLE Game (" +
						"	game_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ," +
						gameKeyNotGlobal +
						"	turn_id INTEGER, FOREIGN KEY (turn_id) REFERENCES Turn(turn_id) ,"
						+ "	players VARCHAR(600) ,"
						+ "	code VARCHAR(11) ," +
						"	pile_id INTEGER, FOREIGN KEY (pile_id) REFERENCES Pile(pile_id) ,"
						+ "	alt_pile_id INTEGER, FOREIGN KEY (pile_id) REFERENCES Pile(pile_id) ,"
						+ "	cardSideA BOOLEAN ,"
						+ "	wildColor VARCHAR(1))"
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
				StatisticsBlackjack statOne = new StatisticsBlackjack();
				StatisticsUno statTwo = new StatisticsUno();
				StatisticsUnoFlip statThree = new StatisticsUnoFlip();
				StatisticsExplodingKittens statFour = new StatisticsExplodingKittens();
				StatisticsGlobal statFive = new StatisticsGlobal();
				try {
					int userOne = db.createUser("NewUser","password still");
					int userTwo = db.createUser("User","password still");
					db.createAllStats(userOne);
					db.createAllStats(userTwo);
					initializeBlackJackCards();
					initializeExplodingKittensCards();
					initializeUnoCards();
					
					/*
					 create game with game stuffs in it to test display
					 */				
					
					// create piles
					Pile empty = new Pile();
					Pile main = new Pile();
					main.populate();
					main.shuffle();
					Pile pileOne = new Pile();
					Pile pileTwo = new Pile();
					pileOne.addCards(main.removeCards(2));
					pileTwo.addCards(main.removeCards(3));
					
					// create players
					Player playOne = new Player(true, userOne);
					Player playTwo = new Player(true, userTwo);
					playOne.setAltPile(empty);
					playTwo.setAltPile(empty);
					playOne.setPile(pileOne);
					playTwo.setPile(pileTwo);
					
					// initialize players in database
					int p1 = db.createPlayer(playOne);
					int p2 = db.createPlayer(playTwo);
					
					// create game
					Game game = new Game("BLJ");
					game.setMainPile(main);
					game.setAltPile(empty);
					game.addPlayer(p1);
					game.addPlayer(p2);
					
					// initialize game in database
					int g = db.createGame(game);
					System.out.println(g);
					/*
					end fake game creation
					 */
					
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
		return  executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					int i = getUserIDfromUsername(username);
					return null;
				} catch (Exception e) {
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
				
			}
		});
		
	}
	
	// create new bot in database
	public int createBot(Bot bot) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
						"INSERT INTO Bots (name, gameKey, difficulty)" +
						"	VALUES (?, ?, ?)"
					);
				
					stmt.setString(1, bot.getName());
					stmt.setString(2, bot.getGameKey());
					stmt.setInt(3, bot.getDifficulty());
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
						"SELECT MAX(bot_id) AS bot_id" +
						"	FROM Bots"
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
	
	// Creates a player and adds it to the Player table
	public int createPlayer(Player player) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				int pileId = createPile(player.getPile());
				int altPileId = createPile(player.getAltPile());
				
				try {
					stmt = conn.prepareStatement(
						"INSERT INTO Player (player_bot_id, human, pile_id, alt_pile_id)" +
						"	VALUES (?, ?, ?, ?)"
					);
					stmt.setInt(1, player.getUserBotID());
					stmt.setBoolean(2, player.getIsHuman());
					stmt.setInt(3, pileId);
					stmt.setInt(4, altPileId);
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
						"SELECT MAX(player_id) AS player_id" +
						"	FROM Player"
					);
					resultSet = stmt.executeQuery();
					resultSet.next();
					int ret = resultSet.getInt("player_id");
					stmt.close();
					
					return ret;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Determines if a given player is human
	public boolean isHuman(int PlayerId) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"SELECT Player.human" +
							"	FROM Player"
							+ "		WHERE Player.player_id = ?" 
							);
					stmt.setInt(1, PlayerId);
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						
						return resultSet.getBoolean("human");
					}
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Creates a new pile in the database and populates it with cards
	private int createPile(String gameKey, int exposeIndex, List<Object> cards) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String encodedIds = encodeCardIds(cards);
				try {
					stmt = conn.prepareStatement(
							"INSERT INTO Pile(gameKey, exposeIndex, cards)"
							+ " VALUES(?, ?, ?)"
						);
					
					stmt.setString(1, gameKey);
					stmt.setInt(2, exposeIndex);
					stmt.setString(3, encodedIds);
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
							"SELECT MAX(pile_id) AS pile_id" +
							"	FROM Pile"
						);
						resultSet = stmt.executeQuery();
						resultSet.next();
						int ret = resultSet.getInt("pile_id");
						stmt.close();
						return ret;
						
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}
	
	// Creates a new, empty pile in the database
	public int createPile(Pile pile) {
		String gameKey = IDatabase.Key_Blackjack;
		if(pile.getType() == "UnoCard") {
			gameKey = IDatabase.Key_Uno;
		}
		else if(pile.getType() == "ExplodingKittensCard") {
			gameKey = IDatabase.Key_ExplodingKittens;
		}
		else if(pile.getType() == "UnoFlipCard") {
			gameKey = IDatabase.Key_UnoFlip;
		}
		return createPile(gameKey, pile.getVisibleIndex(), pile.getPile());
	}
	
	// Creates a game object and populates it
	public int createGame(Game game) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
//				System.out.println(game.getTurnOrder());
//				System.out.println(game.getMainPile());
//				System.out.println(game.getAltPile());
				
				
				int turnId = createTurnOrder(game.getTurnOrder());
				int pileId = createPile(game.getMainPile());
				int altPileId = createPile(game.getAltPile());
				
				try {
					stmt = conn.prepareStatement(
							"INSERT INTO Game(gameKey, turn_id, players, code, pile_id, alt_pile_id, cardSideA, wildColor)"
							+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
						);
					stmt.setString(1, game.getGameKey());
					stmt.setInt(2, turnId);
					stmt.setString(3, sqlTranscoder.encode(game.getPlayerIds()));
					stmt.setString(4, game.getGameCode());
					stmt.setInt(5, pileId);
					stmt.setInt(6, altPileId);
					stmt.setBoolean(7, game.getCardSideA());
					stmt.setString(8, game.getWildColor());
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
							"SELECT MAX(game_id) AS game_id" +
							"	FROM Game"
						);
						resultSet = stmt.executeQuery();
						resultSet.next();
						int ret = resultSet.getInt("game_id");
						stmt.close();
						
						return ret;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
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
	
	// Checks if the gamecode is already in the database
	public int gameCodeValid(String gameCode) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
						"SELECT Game.game_id" +
						" FROM Game"
						+ " WHERE Game.code = ?"
					);
					
					stmt.setString(1, gameCode);
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("game_id");
					}
					
					stmt.close();
					
					return -1;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// populates the BlackJackCards table with cards
	private void initializeBlackJackCards() {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					for (List<String> cardData : BlackJackCardDobbyInit.blackjackCardArray()) {
						String imgPath = cardData.get(0);
						String suit = cardData.get(1);
						String rank = cardData.get(2);
						stmt = conn.prepareStatement(
								"INSERT INTO BlackJackCards(imagePath, suit, rank)" +
								" 	VALUES(?, ?, ?)"
						);
						stmt.setString(1, imgPath);
						stmt.setString(2, suit);
						stmt.setString(3, rank);
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
	
	// populates the ExplodingKittensCards table with cards
	private void initializeExplodingKittensCards() {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					for (List<String> cardData : ExplodingKittensCardDobbyInit.explodingKittensCardArray()) {
						String imgPath = cardData.get(0);
						String type = cardData.get(1);
						stmt = conn.prepareStatement(
								"INSERT INTO ExplodingKittensCards(imagePath, type)" +
								" 	VALUES(?, ?)"
						);
						stmt.setString(1, imgPath);
						stmt.setString(2, type);
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
	
	// populates the UnoCards table with cards
	private void initializeUnoCards() {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					for (List<String> cardData : UnoCardDobbyInit.unoCardArray()) {
						String imgPath = cardData.get(0);
						String color = cardData.get(1);
						String type = cardData.get(2);
						stmt = conn.prepareStatement(
								"INSERT INTO UnoCards(imagePath, color, type)" +
								" 	VALUES(?, ?, ?)"
						);
						stmt.setString(1, imgPath);
						stmt.setString(2, color);
						stmt.setString(3, type);
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
						int res = resultSet.getInt("User_id");
						return res;
					} else {
						throw new UserExistsException("User already exists in database");
					}
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
	
	// gets a pile id from a pile id
	public Pile getPileFromPileId(int pileID) {
		return executeTransaction(new Transaction<Pile>() {
			@Override
			public Pile execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Pile.cards, Pile.gameKey"
							+ " FROM Pile"
							+ "	WHERE Pile.pile_id = ?"
							);
					
					stmt.setInt(1, pileID);
					
					resultSet = stmt.executeQuery();
					resultSet.next();
					String cardIDString = resultSet.getString("cards");
					String gameKey = resultSet.getString("gameKey");
					
					List<Integer> cardIDs = new ArrayList<Integer>();
					cardIDs = sqlTranscoder.decode(cardIDString);
					
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
					
					ArrayList<Object> cards = new ArrayList<Object>();
					
					for(int cardID : cardIDs) {
						if(gameKey.equals(IDatabase.Key_Blackjack)) {
							cards.add(getStandardCardFromCardId(cardID));
						}
						
						else if(gameKey.equals(IDatabase.Key_ExplodingKittens)) {
							cards.add(getExplodingKittensCardFromCardId(cardID));
						}
						
						else if(gameKey.equals(IDatabase.Key_Uno)) {
							cards.add(getUnoCardFromCardId(cardID));
						}
						
						else {
							cards.add(getUnoFlipCardFromCardId(cardID));
						}
					}
					
					Pile pile = new Pile();
					pile.addCards(cards);
					return pile;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns a standard card given a card id
	public StandardCard getStandardCardFromCardId(int cardID) {
		return executeTransaction(new Transaction<StandardCard>() {
			@Override
			public StandardCard execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT b.suit, b.rank "
							+ "	FROM BlackJackCards AS b "
							+ "	WHERE b.card_id = ?"
							);
					
					stmt.setInt(1, cardID);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return new StandardCard(Rank.fromString(resultSet.getString("rank")), Suit.fromString(resultSet.getString("suit")));
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns the card id of a given standard card
	public int getCardIdFromStandardCard(StandardCard card) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT b.card_id "
							+ "	FROM BlackJackCards AS b "
							+ "	WHERE b.rank = ? AND b.suit = ?"
							);
					
					stmt.setString(1, card.getRank().getSymbol());
					stmt.setString(2, card.getSuit().toString());
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("card_id");
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns an exploding kitten card given a card id
		public ExplodingKittensCard getExplodingKittensCardFromCardId(int cardID) {
			return executeTransaction(new Transaction<ExplodingKittensCard>() {
				@Override
				public ExplodingKittensCard execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					try {
						stmt = conn.prepareStatement(
								"SELECT e.type "
								+ "	FROM ExplodingKittensCards AS e "
								+ "	WHERE e.card_id = ?"
								);
						
						stmt.setInt(1, cardID);
						
						resultSet = stmt.executeQuery();
						
						if(resultSet.next()) {
							return new ExplodingKittensCard(Type.fromString(resultSet.getString("type")));
						}
						
						return null;
						
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(stmt);
					}
				}
			});
		}
		
	// Returns the card id of a given exploding kittens card
	public int getCardIdFromExplodingKittensCard(ExplodingKittensCard card) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT e.card_id "
							+ "	FROM ExplodingKittensCards AS e "
							+ "	WHERE e.type = ?"
							);
					
					stmt.setString(1, card.getType().getSymbol());
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("card_id");
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns an uno card given a card id
	public UnoCard getUnoCardFromCardId(int cardID) {
		return executeTransaction(new Transaction<UnoCard>() {
			@Override
			public UnoCard execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT u.color, u.type "
							+ "	FROM UnoCards AS u "
							+ "	WHERE u.card_id = ?"
							);
					
					stmt.setInt(1, cardID);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return new UnoCard(Color.fromString(resultSet.getString("color")), Value.fromString(resultSet.getString("type")));
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns the card id of a given uno card
	public int getCardIdFromUnoCard(UnoCard card) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT u.card_id "
							+ "	FROM UnoCards AS u "
							+ "	WHERE u.type = ? AND u.color = ?"
							);
					
					stmt.setString(1, card.getValues().getSymbol());
					stmt.setString(2, card.getColor().getSymbol());
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("card_id");
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns an uno flip card given a card id
	public UnoFlipCard getUnoFlipCardFromCardId(int cardID) {
		throw new UnsupportedOperationException("FIX LATER");
	}
	
	// Returns the card id of a given uno flip card
	public int getCardIdFromUnoFlipCard(UnoFlipCard card) {
		throw new UnsupportedOperationException("FIX LATER");
	}
	
	// Returns a player given a player id
	public Player getPlayerFromPlayerId(int playerId) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				Player player;
				int playerBotId;
				boolean isHuman;
				int pileId;
				int altPileId;
				
				try {
					stmt = conn.prepareStatement(
							"SELECT p.player_bot_id, p.human, p.pile_id, p.alt_pile_id "
							+ "	FROM Player AS p "
							+ "	WHERE p.player_id = ?"
							);
					
					stmt.setInt(1, playerId);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						playerBotId = resultSet.getInt("player_bot_id");
						isHuman = resultSet.getBoolean("human");
						pileId = resultSet.getInt("pile_id");
						altPileId = resultSet.getInt("alt_pile_id");
						player = new Player(isHuman, playerBotId);
						player.setPile(getPileFromPileId(pileId));
						player.setAltPile(getPileFromPileId(altPileId));
						return player;
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns a player id given a player
	public int getPlayerIdFromPlayer(Player player) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				int playerBotId = player.getUserBotID();
				boolean isHuman = player.getIsHuman();
				
				try {
					stmt = conn.prepareStatement(
							"SELECT p.player_id "
							+ "	FROM Player AS p "
							+ "	WHERE p.player_bot_id = ? AND p.human = ?"
							);
					
					stmt.setInt(1, playerBotId);
					stmt.setBoolean(2, isHuman);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("player_id");
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns a game id given a game object
	public int getGameIdFromGame(Game game) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String gameCode = game.getGameCode();
				
				try {
					stmt = conn.prepareStatement(
							"SELECT game_id "
							+ "	FROM Game "
							+ "	WHERE code = ?"
							);
					
					stmt.setString(1, gameCode);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						return resultSet.getInt("game_id");
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns a game id given a game object
	public Game getGameFromGameId(int gameId) {
		return executeTransaction(new Transaction<Game>() {
			@Override
			public Game execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				Game game;
				String gameKey;
				int turnId;
				String playerIds;
				String gameCode;
				int pileId;
				int altPileId;
				boolean cardSideA;
				String wildColor;
				
				try {
					stmt = conn.prepareStatement(
							"SELECT * "
							+ "	FROM Game "
							+ "	WHERE game_id = ?"
							);
					
					stmt.setInt(1, gameId);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						 gameKey = resultSet.getString("gameKey");
						 turnId = resultSet.getInt("turn_id");
						 playerIds = resultSet.getString("players");
						 gameCode = resultSet.getString("code");
						 pileId = resultSet.getInt("pile_id");
						 altPileId = resultSet.getInt("alt_pile_id");
						 cardSideA = resultSet.getBoolean("cardSideA");
						 wildColor = resultSet.getString("wildColor");
						 
						 game = new Game(gameCode, gameKey);
						 game.setWildColor(wildColor);
						 game.setMainPile(getPileFromPileId(pileId));
						 game.setAltPile(getPileFromPileId(altPileId));
						 game.setTurnOrder(getTurnOrderFromTurnOrderId(turnId));
						 game.setPlayerIds(sqlTranscoder.decode(playerIds));
						 ArrayList<Player> players = new ArrayList<>();
						 for(int id : sqlTranscoder.decode(playerIds)) {
							 Player player = getPlayerFromPlayerId(id);
							 players.add(player);
						 }
						 game.setPlayers(players);
						 if(cardSideA != game.getCardSideA()) {
							 game.flip();
						 }
						 
						 return game;
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Updates the given game
	public void updateGame(int gameId, Game game) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				int turnId;
				int pileId;
				int altPileId;

				try {
					stmt = conn.prepareStatement(
							"SELECT turn_id, pile_id, alt_pile_id" +
							" FROM Game"
							+ "	WHERE game_id = ?"
					);
					
					stmt.setInt(1, gameId);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						turnId = resultSet.getInt("turn_id");
						pileId = resultSet.getInt("pile_id");
						altPileId = resultSet.getInt("alt_pile_id");
						
						updateTurnOrder(turnId, game.getTurnOrder());
						updatePile(pileId, game.getMainPile());
						updatePile(altPileId, game.getAltPile());
						for(Player player : game.getPlayers()) {
							updatePlayer(getPlayerIdFromPlayer(player), player);
						}
						
						stmt.close();
						
						stmt = conn.prepareStatement(
								"UPDATE Game"
								+ " SET players = ?, cardSideA = ?, wildColor = ?"
								+ "	WHERE game_id = ?"
						);
						
						stmt.setString(1, sqlTranscoder.encode(game.getPlayerIds()));
						stmt.setBoolean(2, game.getCardSideA());
						stmt.setString(3, game.getWildColor());
						
						stmt.executeUpdate();
					}
					
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from an Uno statistics object and a user id
	public void updateUnoStats(StatisticsUno stat, int user_id) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					int plays = stat.GetGamesPlayed();
					int wins = stat.GetGamesWon();
					int losses = stat.GetGamesLost();
					int sOne = stat.GetWildCards();
					int sTwo = stat.GetPlusFours();
					int sThree = stat.GetSwaps();
				
					stmt = conn.prepareStatement(
							"UPDATE Stats" +
							" 	SET plays = ?, wins = ?, loses = ?, sOne = ?, sTwo = ?, sThree = ?"
							+ "	WHERE user_id = ? and"
							+ "	gameKey = 'UNO'"
					);
					
					stmt.setInt(1, plays);
					stmt.setInt(2, wins);
					stmt.setInt(3, losses);
					stmt.setInt(4, sOne);
					stmt.setInt(5, sTwo);
					stmt.setInt(6, sThree);
					stmt.setInt(7, user_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from a Blackjack statistics object and a username
	public void updateUnoStats(StatisticsUno stat, String username) {
		updateUnoStats(stat, getUserIDfromUsername(username));
	}
	
	// updates user's stats from an Uno statistics object and a user id
	public void updateUnoFlipStats(StatisticsUnoFlip stat, int user_id) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					int plays = stat.GetGamesPlayed();
					int wins = stat.GetGamesWon();
					int losses = stat.GetGamesLost();
					int sOne = stat.GetFlips();
					int sTwo = stat.GetSkipAlls();
					int sThree = stat.GetPlusFives();
				
					stmt = conn.prepareStatement(
							"UPDATE Stats" +
							" 	SET plays = ?, wins = ?, loses = ?, sOne = ?, sTwo = ?, sThree = ?"
							+ "	WHERE user_id = ? and"
							+ "	gameKey = 'UNF'"
					);
					
					stmt.setInt(1, plays);
					stmt.setInt(2, wins);
					stmt.setInt(3, losses);
					stmt.setInt(4, sOne);
					stmt.setInt(5, sTwo);
					stmt.setInt(6, sThree);
					stmt.setInt(7, user_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from a Blackjack statistics object and a username
	public void updateUnoFlipStats(StatisticsUnoFlip stat, String username) {
		updateUnoFlipStats(stat, getUserIDfromUsername(username));
	}
	
	// updates user's stats from a Blackjack statistics object and a user id
	public void updateBlackjackStats(StatisticsBlackjack stat, int user_id) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					int plays = stat.GetGamesPlayed();
					int wins = stat.GetGamesWon();
					int losses = stat.GetGamesLost();
					int sOne = stat.GetBlackjacks();
					int sTwo = stat.GetSplits();
					int sThree = stat.GetFiveCardWins();
				
					stmt = conn.prepareStatement(
							"UPDATE Stats" +
							" 	SET plays = ?, wins = ?, loses = ?, sOne = ?, sTwo = ?, sThree = ?"
							+ "	WHERE user_id = ? and"
							+ "	gameKey = 'BLJ'"
					);
					
					stmt.setInt(1, plays);
					stmt.setInt(2, wins);
					stmt.setInt(3, losses);
					stmt.setInt(4, sOne);
					stmt.setInt(5, sTwo);
					stmt.setInt(6, sThree);
					stmt.setInt(7, user_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from a Blackjack statistics object and a username
	public void updateBlackjackStats(StatisticsBlackjack stat, String username) {
		updateBlackjackStats(stat, getUserIDfromUsername(username));
	}
	
	public void updateExplodingKittensStats(StatisticsExplodingKittens stat, int user_id) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					int plays = stat.GetGamesPlayed();
					int wins = stat.GetGamesWon();
					int losses = stat.GetGamesLost();
					int sOne = stat.GetDefuses();
					int sTwo = stat.GetFavors();
					int sThree = stat.GetFutures();
				
					stmt = conn.prepareStatement(
							"UPDATE Stats" +
							" 	SET plays = ?, wins = ?, loses = ?, sOne = ?, sTwo = ?, sThree = ?"
							+ "	WHERE user_id = ? and"
							+ "	gameKey = 'EXP'"
					);
					
					stmt.setInt(1, plays);
					stmt.setInt(2, wins);
					stmt.setInt(3, losses);
					stmt.setInt(4, sOne);
					stmt.setInt(5, sTwo);
					stmt.setInt(6, sThree);
					stmt.setInt(7, user_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from a Blackjack statistics object and a username
	public void updateExplodingKittensStats(StatisticsExplodingKittens stat, String username) {
		updateExplodingKittensStats(stat, getUserIDfromUsername(username));
	}
	
	public void updateGlobalStats(StatisticsGlobal stat, int user_id) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					int plays = stat.GetGamesPlayed();
					int wins = stat.GetGamesWon();
					int losses = stat.GetGamesLost();
					int sOne = stat.GetRank();
				
					stmt = conn.prepareStatement(
							"UPDATE Stats" +
							" 	SET plays = ?, wins = ?, loses = ?, sOne = ?"
							+ "	WHERE user_id = ? and"
							+ "	gameKey = 'GLB'"
					);
					
					stmt.setInt(1, plays);
					stmt.setInt(2, wins);
					stmt.setInt(3, losses);
					stmt.setInt(4, sOne);
					stmt.setInt(5, user_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// updates user's stats from a Blackjack statistics object and a username
	public void updateGlobalStats(StatisticsGlobal stat, String username) {
		updateGlobalStats(stat, getUserIDfromUsername(username));
	}
	
	// Updates an existing pile's expose index and/or cards
	public void updatePile(int pile_id, Pile pile) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String encodedIds = encodeCardIds(pile.getPile());
				
				try {
					stmt = conn.prepareStatement(
							"UPDATE Pile"
							+ " SET exposeIndex = ?, cards = ?"
							+ "	WHERE pile_id = ?"
							);
					
					stmt.setInt(1, pile.getVisibleIndex());
					stmt.setString(2, encodedIds);
					stmt.setInt(3, pile_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}
	
	public void updatePlayer(int player_id, Player player) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				int pileId;
				int altPileId;
				
				try {
					stmt = conn.prepareStatement(
							"SELECT Player.pile_id, Player.alt_pile_id"
							+ " FROM Player "
							+ "WHERE Player.player_id = ?"
							);
					
					stmt.setInt(1, player_id);
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						pileId = resultSet.getInt("pile_id");
						altPileId = resultSet.getInt("alt_pile_id");
						updatePile(pileId, player.getPile());
						updatePile(altPileId, player.getAltPile());
					}
					
					stmt.close();
					
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes the stats of a user given a user id
	public void deleteStats(int userId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Stats" +
							"	WHERE Stats.user_id = ?"
							);
					stmt.setInt(1, userId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes the stats of a user given a username
	public void deleteStats(String username) {
		deleteStats(getUserIDfromUsername(username));
	}
	
	// Deletes a user given a user id
	public void deleteUser(int userId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Users" +
							"	WHERE Users.user_id = ?"
							);
					stmt.setInt(1, userId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes a user given a username
	public void deleteUser(String username) {
		deleteUser(getUserIDfromUsername(username));
	}
	
	// Deletes a bot given a bot id
	public void deleteBot(int botId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Bots" +
							"	WHERE Bots.bot_id = ?"
							);
					stmt.setInt(1, botId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes a player given a player id
	public void deletePlayer(int playerId) {
		if (!isHuman(playerId)) {
			deleteBot(getUserBotIdFromPlayerId(playerId));
		}
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Player" +
							" WHERE Player.player_id = ?"
							);
					stmt.setInt(1, playerId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes a player given a player
	public void deletePlayer(Player player) {
		deletePlayer(getPlayerIdFromPlayer(player));
	}
	
	// Deletes a TurnOrder given a TurnOrder id
	public void deleteTurnOrder(int turnOrderId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Turn" +
							"	WHERE Turn.turn_id = ?"
							);
					stmt.setInt(1, turnOrderId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Deletes a pile given a pile id
	public void deletePile(int pileId) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"DELETE FROM Pile" +
							"	WHERE Pile.pile_id = ?"
							);
					stmt.setInt(1, pileId);
					stmt.executeUpdate();
					return null;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// return boolean if User exists from username and password
    public boolean login(String username, String password) {
        return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSet = null;
                
                try {                        
                    stmt = conn.prepareStatement(
                            "SELECT Users.User_id" +
                            "    FROM Users" +
                            "        WHERE LOWER(Users.username) = LOWER(?)" +
                            "        AND Users.password = ?"
                    );
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        return true;
                    }
                    return false;
                } finally {
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }
   
   // encodes a list of cards using SqlTranscoder
   private String encodeCardIds(List<Object> cards) {
	   return executeTransaction(new Transaction<String>() {
           @Override
           public String execute(Connection conn) throws SQLException {
				ArrayList<Integer> cardIds = new ArrayList<>();
				Pile pile = new Pile();
				pile.addCards((ArrayList<Object>)cards);
				if(pile.getType() == "StandardCard") {
					for(Object card : cards) {
							int cardId = getCardIdFromStandardCard((StandardCard)card);
							cardIds.add(cardId);
					}
				}
				
				else if(pile.getType() == "ExplodingKittensCard") {
					for(Object card : cards) {
						int cardId = getCardIdFromExplodingKittensCard((ExplodingKittensCard)card);
						cardIds.add(cardId);
					}
				}
				
				else if(pile.getType() == "UnoCard") {
					for(Object card : cards) {
						int cardId = getCardIdFromUnoCard((UnoCard)card);
						cardIds.add(cardId);
					}
				}
				
				//TODO ADD UNO FLIP CARDS
				return sqlTranscoder.encode(cardIds);
           }
	   });
   }
   
   // Creates TurnOrder and adds it to the Turn table
	public int createTurnOrder(TurnOrder turn) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
						"INSERT INTO Turn (pointer, adder, turn_order)" +
						"	VALUES (?, ?, ?)"
					);
					stmt.setInt(1, turn.getPointer());
					stmt.setInt(2, turn.getAdder());
					stmt.setString(3, sqlTranscoder.encode(turn.getTurnList()));
					stmt.executeUpdate();
					stmt.close();
					
					stmt = conn.prepareStatement(
						"SELECT MAX(turn_id) AS turn_id" +
						"	FROM Turn"
					);
					resultSet = stmt.executeQuery();
					resultSet.next();
					int ret = resultSet.getInt("turn_id");
					stmt.close();
					
					return ret;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// Returns a TurnOrder given a turn id
	public TurnOrder getTurnOrderFromTurnOrderId(int turnId) {
		return executeTransaction(new Transaction<TurnOrder>() {
			@Override
			public TurnOrder execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				TurnOrder turn = new TurnOrder();
				int pointer;
				int adder;
				ArrayList<Integer> TurnList;
				
				try {
					stmt = conn.prepareStatement(
							"SELECT t.pointer, t.adder, t.turn_order "
							+ "	FROM Turn AS t "
							+ "	WHERE t.turn_id = ?"
							);
					
					stmt.setInt(1, turnId);
					
					resultSet = stmt.executeQuery();
					
					if(resultSet.next()) {
						pointer = resultSet.getInt("pointer");
						turn.setPointer(pointer);
						adder = resultSet.getInt("adder");
						turn.setAdder(adder);
						TurnList = sqlTranscoder.decode(resultSet.getString("turn_order"));
						turn.setTurnList(TurnList);
						return turn;
					}
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
   
	// Updates an existing TurnOrder
	public void updateTurnOrder(int turn_id, TurnOrder turn) {
		executeTransaction(new Transaction<Void>() {
			@Override
			public Void execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"UPDATE Turn"
							+ " SET pointer = ?, adder = ?, turn_order = ?"
							+ "	WHERE turn_id = ?"
							);
					
					stmt.setInt(1, turn.getPointer());
					stmt.setInt(2, turn.getAdder());
					stmt.setString(3, sqlTranscoder.encode(turn.getTurnList()));
					stmt.setInt(4, turn_id);
					stmt.executeUpdate();
					stmt.close();
					
					return null;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}
	
	// get UserBotId from player_id
	public int getUserBotIdFromPlayerId(int PlayerId) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					
					stmt = conn.prepareStatement(
							"SELECT player_bot_id" +
							"	FROM Player" +
							"		WHERE Player.player_id = ?"
					);
					stmt.setInt(1, PlayerId);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						return resultSet.getInt("player_bot_id");
					}
					throw new DobbyException("User does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			
			}
		});
	}
	
	// get User from player_id
	public User getUserFromPlayerId(int PlayerId) {
        if (isHuman(PlayerId)) {
            return getUser(getUserBotIdFromPlayerId(PlayerId));
        } else {
            throw new IncorrectPlayerTypeException("Player " + PlayerId + " is a Bot not a User");
        }
    }
	
	// get Bot object from bot_id
	public Bot getBot(int BotId) {
		return executeTransaction(new Transaction<Bot>() {
			@Override
			public Bot execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Bots.gameKey, Bots.name, Bots.difficulty" +
							"	FROM Bots" +
							"		WHERE Bots.bot_id = ?"
					);
					stmt.setInt(1, BotId);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						String gameKey = resultSet.getString("gameKey");
						String name = resultSet.getString("name");
						int difficulty = resultSet.getInt("difficulty");
						
						return new Bot(difficulty, gameKey, name);
					}
					throw new UserDoesNotExistException("Bot does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get Bot from player_id
	public Bot getBotFromPlayerId(int PlayerId) {
        if (!isHuman(PlayerId)) {
            return getBot(getUserBotIdFromPlayerId(PlayerId));
        } else {
            throw new IncorrectPlayerTypeException("Player " + PlayerId + " is a User not a Bot");
        }
    }
	
	// get Bot name from bot_id
	public String getBotNameFromBotId(int BotId) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement(
							"SELECT Bots.name" +
							"	FROM Bots" +
							"		WHERE Bots.bot_id = ?"
					);
					stmt.setInt(1, BotId);
					resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						return resultSet.getString("name");
					}
					throw new UserDoesNotExistException("Bot does not exist");
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// get name from player_id
	public String getNameFromPlayerId(int PlayerId) {
		if (isHuman(PlayerId)) {
            return getUsernamefromUserID(getUserBotIdFromPlayerId(PlayerId));
        } else {
        	return getBotNameFromBotId(getUserBotIdFromPlayerId(PlayerId));
        }
	}
}
