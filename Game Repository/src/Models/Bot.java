package Models;

public class Bot {
    private int difficulty;
    private String gameKey;
    private String name;

    public Bot(int difficulty, String gameKey, String name) {
        this.name = name;
        this.difficulty = difficulty;
        this.gameKey = gameKey;
    }

    public Bot(int difficulty, String gameKey) {
    	BotNameGenerator botName = new BotNameGenerator();
        this.difficulty = difficulty;
        this.gameKey = gameKey;
        this.name = botName.GenerateName();
    } 

	public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getGameKey() {
        return gameKey;
    }
    
    public boolean equals(Bot bot) {
    	if (!this.name.equals(bot.name)) {
    		return false;
    	}
    	
    	if (this.difficulty != bot.difficulty) {
    		return false;
    	}
    	
    	if (!this.gameKey.equals(bot.gameKey)) {
    		return false;
    	}
    	
    	return true;
    }
}