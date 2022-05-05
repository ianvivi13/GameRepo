package Models;

import java.util.*;

public class Pile{
	
	private ArrayList<Object> pile;
	private int visibleIndex;
	
	public Pile() {
		pile = new ArrayList<>();
		visibleIndex = 0;
		
	}
	
  public int getValueStandard() {
   boolean isAce = false;
   int val = 0;
	  
	for(Object card : pile) {
		Rank r = ((StandardCard) card).getRank();
		if(r == Rank.ACE) {
			isAce = true;
			val++;
		} else {
			try {
				val+= Integer.valueOf(r.toString());
			} catch (Exception e){
				val+=10;
			}
		}
	} 
	if(val <= 11 && isAce) {
		val+= 10;
	}
	return val;
}
	
	public int getVisibleIndex() {
		return visibleIndex;
	}
	
	public void setVisibleIndex(int visible) {
		visibleIndex = visible;
	}
	
	public void addCard(Object card) {
		pile.add(card);
	}
	
	public int getNumCards() {
		return pile.size();
	}
	
	public boolean isEmpty() {
		if (pile.isEmpty()){
			return true;
		}
		else {
			return false;
		}
	}
	
	public Object getCard(int index) {
		if(index>=pile.size()||index<0){
			throw new NoSuchElementException();
		}
		else {
			return pile.get(index);
		}
	}
	
	public Object getTopCard() {
		if(pile.isEmpty()){
			throw new NoSuchElementException("No such element in pile");
		}
		else{
			return pile.get(pile.size()-1);
		}
	}

	public int getIndexOfTopCard() {
		if(pile.isEmpty()){
			return -1;
		}
		else{
			return pile.size()-1;
		}
	}
	
	public ArrayList<Object> removeCards(int numCards) {
		if(pile.size()<numCards){
			throw new IllegalArgumentException("Not Enough Cards");
		}
		ArrayList<Object> cardsRemoved = new ArrayList<>();
		int size = pile.size();
		for(int i=0;i<numCards;i++){
			cardsRemoved.add(pile.remove(size-numCards));
		}
		return cardsRemoved;
	}
	
	public void addCards(ArrayList<Object> cardsToAdd) {
		pile.addAll(cardsToAdd);
	}
	
	public void populate() {
		Suit[] allSuits = Suit.values();
		Rank[] allRanks = Rank.values();
		for (int j = 0; j < allSuits.length; j++) {
			for (int i = 0; i < allRanks.length; i++) {
				// use allSuits[j] and allRanks[i] to create a Card
				pile.add(new StandardCard(allRanks[i],allSuits[j]));
			}
		}
	}
	
	public void populateExplodingKittens(int numPlayers) {
		for(int i = 0; i < numPlayers - 1; i++) {
			pile.add(new ExplodingKittensCard(Type.ExplodingKitten));
		}
		
		for(int i = 0; i < 6; i++) {
			pile.add(new ExplodingKittensCard(Type.SeeTheFuture));
			pile.add(new ExplodingKittensCard(Type.AlterTheFuture));
			pile.add(new ExplodingKittensCard(Type.Shuffle));
			pile.add(new ExplodingKittensCard(Type.Favor));
			pile.add(new ExplodingKittensCard(Type.FeralCat));
		}
		
		for(int i = 0; i < 7; i++) {
			pile.add(new ExplodingKittensCard(Type.BeardCat));
			pile.add(new ExplodingKittensCard(Type.Cattermelon));
			pile.add(new ExplodingKittensCard(Type.HairyPotatoCat));
			pile.add(new ExplodingKittensCard(Type.RainbowRalphingCat));
			pile.add(new ExplodingKittensCard(Type.TacoCat));
			pile.add(new ExplodingKittensCard(Type.DrawFromBottom));
		}
		
		for(int i = 0; i < 7; i++) {
			pile.add(new ExplodingKittensCard(Type.BeardCat));
			pile.add(new ExplodingKittensCard(Type.Cattermelon));
			pile.add(new ExplodingKittensCard(Type.HairyPotatoCat));
			pile.add(new ExplodingKittensCard(Type.RainbowRalphingCat));
			pile.add(new ExplodingKittensCard(Type.TacoCat));
			pile.add(new ExplodingKittensCard(Type.DrawFromBottom));
		}
		
		for(int i = 0; i < 10; i++) {
			pile.add(new ExplodingKittensCard(Type.Nope));
			pile.add(new ExplodingKittensCard(Type.Skip));
		}
		
		for(int i = 0; i < 11; i++) {
			pile.add(new ExplodingKittensCard(Type.Attack));
		}
		
		for(int i = 0; i < 10; i++) {
			pile.add(new ExplodingKittensCard(Type.Defuse));
		}
	}
	
	public void populateUno() {
		Color[] allColors = Color.values();
		Value[] allValues = Value.values();
		
		//populates zeros
		for(int j = 0; j < allColors.length-1; j++) {
			pile.add(new UnoCard(allColors[j], Value.Zero));
		}
		
		//populates all number cards, skips, reverses, and draw 2s
		for(int x = 0; x < 2; x++) {
			for (int j = 0; j < allColors.length-1; j++) {
				for (int i = 0; i < allValues.length-4; i++) {
					// use allSuits[j] and allRanks[i] to create a Card
					pile.add(new UnoCard(allColors[j],allValues[i]));
				}
			}
		}
		
		//populates draw 4s and wild cards
		for(int k = 0; k < 4; k++) {
			pile.add(new UnoCard(Color.BLACK, Value.Wild));
			pile.add(new UnoCard(Color.BLACK, Value.Wild_Four));
		}
	}
	
	
	public void shuffle() {
		Collections.shuffle(pile);
	}
	
	public StandardCard drawCard() {
		if(pile.isEmpty()){
			throw new NoSuchElementException("Its Empty Hoe");
		}
		return (StandardCard) pile.remove(pile.size()-1);
	}
	
	public ArrayList<Object> getPile() {
        return pile;
    }
    
    public String getType() {
        if(!pile.isEmpty()) {
        	if(this.getCard(0) instanceof UnoCard) {
        		return "UnoCard";
        	}
        	else if(this.getCard(0) instanceof ExplodingKittensCard) {
        		return "ExplodingKittensCard";
        	}
        	else if(this.getCard(0) instanceof UnoFlipCard) {
        		return "UnoFlipCard";
        	}
        	
        	return "StandardCard";
        }
        return null;
    }
    
    public boolean equals(Pile pile) {
    	if (this.visibleIndex != pile.visibleIndex) {
    		return false;
    	}
    	
    	if (this.getType() != pile.getType() ) {
    		return false;
    	}
    	
    	if (this.getNumCards() != pile.getNumCards()) {
    		return false;
    	}
    	
    	for (int i = 0 ; i < this.getNumCards() ; i ++) {
    		if (!this.getCard(i).equals(pile.getCard(i))) {
    			return false;
    		}
    	}
    	
    	return true;
    }
	
}