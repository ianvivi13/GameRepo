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
		for(int i = 0; i < 4; i++) {
			suit = "s";
			fullSuit = "spades";
			if(i == 1) {
				suit = "h";
				fullSuit = "hearts";
			}
			if(i == 2) {
				suit = "c";
				fullSuit = "clubs";
			}
			if(i == 3) {
				suit = "d";
				fullSuit = "diamonds";
				
			}
			for(int j = 0; j < 13; j++) {
				rank = Integer.toString(j + 1);
				number = Integer.toString(j + 1);
				if(j == 0) {
					rank = "a";
					number = "ace";
				}
				if(j == 9) {
					rank = "t";
				}
				if(j == 10) {
					rank = "j";
					number = "jack";
				}
				if(j == 11) {
					rank = "q";
					number = "queen";
				}
				if(j == 12) {
					rank = "k";
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
