package Models;

import java.util.*;

public class Pile{
	
	private ArrayList<Card> pile;
	private int visibleIndex;
	
	public Pile() {
		pile = new ArrayList<>();
		visibleIndex = 0;
	}
	
	public int getVisibleIndex() {
		return visibleIndex;
	}
	
	public void setVisibleIndex(int visible) {
		visibleIndex = visible;
	}
	
	public void addCard(Card card) {
		pile.add(card);
	}
	
	public int getNumCards() {
		return pile.size();
	}
	
	//think about how to get cards using a string
	
	public int getIndexOfTopCard() {
		if(pile.isEmpty()){
			return -1;
		}
		else{
			return pile.size()-1;
		}
	}
	
	public boolean isEmpty() {
		if(pile.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void shuffle() {
		Collections.shuffle(pile);
	}
	
	public Card drawCard() {
		if(pile.isEmpty()){
			throw new NoSuchElementException("Its Empty Hoe");
		}
		return pile.remove(pile.size()-1);
	}
	
	public ArrayList<Card> removeCards(int numCards) {
		if(pile.size()<numCards){
			throw new IllegalArgumentException("Not Enough Cards");
		}
		ArrayList<Card> cardsRemoved = new ArrayList<>();
		int size = pile.size();
		for(int i=0;i<numCards;i++){
			cardsRemoved.add(pile.remove(size-numCards));
		}
		return cardsRemoved;
	}
	
	public void addCards(ArrayList<Card> cardsToAdd) {
		pile.addAll(cardsToAdd);
	}
	
	
}