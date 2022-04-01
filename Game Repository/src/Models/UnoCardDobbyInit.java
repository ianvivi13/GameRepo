package Models;

import java.util.ArrayList;
import java.util.List;

public class UnoCardDobbyInit {
	private static List<List<String>> cardData;
	
	public static List<List<String>> unoCardArray() {
		cardData = new ArrayList<List<String>>();
		String color;
		String number;
		String imgPath = "../war/_view/images/UnoCards/";
		String fullImgPath;
		List<String> backCard = new ArrayList<>();
		backCard.add(imgPath + "back.png");
		backCard.add("Z");
		backCard.add("Z");
		cardData.add(backCard);
		for(int i = 0; i < 4; i++) {
			color = "B";
			if(i == 1) {
				color = "G";
			}
			else if(i == 2) {
				color = "R";
			}
			else if(i == 3) {
				color = "Y";
			}
			for(int j = 0; j < 12; j++) {
				number = Integer.toString(j + 1);
				if(j == 9) {
					number = "R";
				}
				else if(j == 10) {
					number = "S";
				}
				else if(j == 11) {
					number = "T";
				}
				fullImgPath = (imgPath + color.toLowerCase() + "_" + number.toLowerCase() + ".png");
				List<String> moreCardData = new ArrayList<>();
				moreCardData.add(fullImgPath);
				moreCardData.add(color);
				moreCardData.add(number);
				cardData.add(moreCardData);
			}
		}
		
		List<String> wildOne = new ArrayList<>();
		List<String> wildTwo = new ArrayList<>();
		List<String> wildThree = new ArrayList<>();
		fullImgPath = (imgPath + "w_f.png");
		wildOne.add(fullImgPath);
		wildOne.add("W");
		wildOne.add("F");
		fullImgPath = (imgPath + "w_h.png");
		wildTwo.add(fullImgPath);
		wildTwo.add("W");
		wildTwo.add("H");
		fullImgPath = (imgPath + "w_w.png");
		wildThree.add(fullImgPath);
		wildThree.add("W");
		wildThree.add("W");
		cardData.add(wildOne);
		cardData.add(wildTwo);
		cardData.add(wildThree);
		
		return cardData;
	}
}
