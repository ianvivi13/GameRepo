package Models;

import java.util.ArrayList;


public class UsernameGenerator {
	private ArrayList<String> mod;
	private ArrayList<String> adj;
	private ArrayList<String> noun;
	
	// initializer
	public UsernameGenerator() {
		// initialize modifiers
		this.mod = new ArrayList<String>();
		this.mod.add("Epicly");
		this.mod.add("Golden");
		this.mod.add("Legendary");
		this.mod.add("Master");
		this.mod.add("Mega");
		this.mod.add("r/");
		this.mod.add("Super");
		this.mod.add("Ultra");
		this.mod.add("Smoldering");
		this.mod.add("Godly");
		this.mod.add("Massive");
		this.mod.add("Twitch.tv/");
		this.mod.add("Gargantuan");
		this.mod.add("Giga");
		this.mod.add("Micro");
		this.mod.add("Nano");
		this.mod.add("Yocto");
		this.mod.add("Pico");
		this.mod.add("Floppy");
		this.mod.add("RGB");
		this.mod.add("Div");
		this.mod.add("Vampirical");
		this.mod.add("discord.gg/");
		this.mod.add("ArrayList");
		
		// initialize adjectives
		this.adj = new ArrayList<String>();
		this.adj.add("Funky");
		this.adj.add("Slimy");
		this.adj.add("Funny");
		this.adj.add("Flirty");
		this.adj.add("Rough");
		this.adj.add("Blue");
		this.adj.add("Stinky");
		this.adj.add("Scary");
		this.adj.add("Kinky");
		this.adj.add("Sexy");
		this.adj.add("Lanky");
		this.adj.add("Dreamy");
		this.adj.add("Bouncing");
		this.adj.add("Little");
		this.adj.add("Dry");
		this.adj.add("Huge");
		this.adj.add("Sleepy");
		this.adj.add("Techy");
		this.adj.add("Bratty");
		this.adj.add("Wiggly");
		this.adj.add("Gentle");
		this.adj.add("Grimey");
		this.adj.add("Flimsy");
		this.adj.add("Athletic");
		this.adj.add("Walking");
		this.adj.add("Powerful");
		this.adj.add("Hard");
		this.adj.add("Playful");
		this.adj.add("Illegal");
		this.adj.add("Attractive");
		this.adj.add("Colossal");
		this.adj.add("Scary");
		this.adj.add("Witty");
		this.adj.add("Plump");
		this.adj.add("Lazy");
		this.adj.add("Grumpy");
		this.adj.add("Clumsy");
		this.adj.add("Jealous");
		this.adj.add("Step");
		this.adj.add("Scruffy");
		this.adj.add("Purple");
		this.adj.add("Dead");
		this.adj.add("Bubbly");
		this.adj.add("Murderous");
		this.adj.add("Invisible");
		this.adj.add("Sweaty");
		this.adj.add("Speedy");
		this.adj.add("Strange");
		this.adj.add("Derpy");
		this.adj.add("Shiny");
		this.adj.add("Hateful");
		this.adj.add("Loving");
		this.adj.add("Lonely");
		this.adj.add("Dizzy");
		this.adj.add("Officer");
		this.adj.add("Forbidden");
		this.adj.add("Gummy");
		this.adj.add("Vanilla");
		this.adj.add("Spicy");
		this.adj.add("Tasty");
		
		// initialize noun
		this.noun = new ArrayList<String>();
		this.noun.add("Dragon");
		this.noun.add("Computer");
		this.noun.add("Bot");
		this.noun.add("Plant");
		this.noun.add("Judge");
		this.noun.add("Penguin");
		this.noun.add("Braden");
		this.noun.add("Potato");
		this.noun.add("Fridge");
		this.noun.add("Ian");
		this.noun.add("Paul");
		this.noun.add("Mark");
		this.noun.add("Hake");
		this.noun.add("RickAstley");
		this.noun.add("Worm");
		this.noun.add("AI");
		this.noun.add("Professor");
		this.noun.add("Balls");
		this.noun.add("Steak");
		this.noun.add("Lizzy");
		this.noun.add("Pikachu");
		this.noun.add("Anthony");
		this.noun.add("Caroline");
		this.noun.add("Nun");
		this.noun.add("Tummy");
		this.noun.add("Dog");
		this.noun.add("Raccoon");
		this.noun.add("Squidward");
		this.noun.add("Bread");
		this.noun.add("Dino");
		this.noun.add("Spaghetti");
		this.noun.add("Taco");
		this.noun.add("Turtle");
		this.noun.add("Bat");
		this.noun.add("GUI");
		this.noun.add("Firewall");
		this.noun.add("Tyrant");
		this.noun.add("IPV4");
		this.noun.add("Dobby");
		this.noun.add("Database");
		this.noun.add("Frog");
		this.noun.add("Wizard");
		this.noun.add("Witch");
		this.noun.add("Kirby");
		this.noun.add("Yogurt");
		this.noun.add("Tool");
		this.noun.add("Sis");
		this.noun.add("Bro");
	}
	
	// Random getters
	private String randomMod() {
		int i = (int)(mod.size() * Math.random());
		return mod.get(i);
	}
	
	private String randomAdj() {
		int i = (int)(adj.size() * Math.random());
		return adj.get(i);
	}
	
	private String randomNoun() {
		int i = (int)(noun.size() * Math.random());
		return noun.get(i);
	}
	
	// Get longest names
	private int longestMod() {
		int i = 0;
		for (String x : mod) {
			int l = x.length();
			if (l > i) {
				i = l;
			}
		}
		return i;
	}
	
	private int longestAdj() {
		int i = 0;
		for (String x : adj) {
			int l = x.length();
			if (l > i) {
				i = l;
			}
		}
		return i;
	}
	
	private int longestNoun() {
		int i = 0;
		for (String x : noun) {
			int l = x.length();
			if (l > i) {
				i = l;
			}
		}
		return i;
	}
	
	// Getters
	public String GenerateName() {
		String name;
		double choice = Math.random();
		if(choice > 0.5) {
			name = randomMod() + randomNoun();
		}
		name = randomAdj() + randomNoun();
		return name;
	}
	
	public int TotalCount() {
		return mod.size() * adj.size() * noun.size();
	}
	
	public int maxLength() {
		return longestMod() + longestAdj() + longestNoun() + 1;
	}
	
}
