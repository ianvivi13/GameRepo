package Models;

import java.util.ArrayList;
import java.util.List;

public class BlackJackCardDobbyInit {
	private static List<List<String>> cardData;
	
	public static List<List<String>> blackjackCardArray() {
		cardData = new ArrayList<List<String>>();
		String suit;
		String rank;
		String imgPath = "../war/_view/images/StandardCards/";
		String number;
		String fullSuit;
		String fullImgPath;
		List<String> backCard = new ArrayList<>();
		backCard.add(imgPath + "back-sm.png");
		backCard.add("Z");
		backCard.add("Z");
		cardData.add(backCard);
		for(int i = 0; i < 4; i++) {
			suit = "S";
			fullSuit = "spades";
			if(i == 1) {
				suit = "H";
				fullSuit = "hearts";
			}
			else if(i == 2) {
				suit = "C";
				fullSuit = "clubs";
			}
			else if(i == 3) {
				suit = "D";
				fullSuit = "diamonds";
				
			}
			for(int j = 0; j < 13; j++) {
				rank = Integer.toString(j + 1);
				number = Integer.toString(j + 1);
				if(j == 0) {
					rank = "A";
					number = "ace";
				}
				else if(j == 9) {
					rank = "T";
				}
				else if(j == 10) {
					rank = "J";
					number = "jack";
				}
				else if(j == 11) {
					rank = "Q";
					number = "queen";
				}
				else if(j == 12) {
					rank = "K";
					number = "king";
				}
				fullImgPath = (imgPath + number + "_" + fullSuit + ".png");
				List<String> moreCardData = new ArrayList<>();
				moreCardData.add(fullImgPath);
				moreCardData.add(suit);
				moreCardData.add(rank);
				cardData.add(moreCardData);
			}
			
		}
		return cardData;
	}
}
