package Models;

import java.util.ArrayList;
import java.util.List;

public class ExplodingKittensCardDobbyInit {
	private static List<List<String>> cardData;
	
	public static List<List<String>> explodingKittensCardArray() {
		cardData = new ArrayList<List<String>>();
		String type;
		String imgPath = "../war/_view/images/ExplodingKittens/";
		String fullImgPath;
		List<String> backCard = new ArrayList<>();
		backCard.add(imgPath + "back.jpg");
		backCard.add("Z");
		cardData.add(backCard);
		
		for(int i = 0; i < 17; i++) {
			List<String> moreCardData = new ArrayList<>();
			if(i == 0) {
				type = "AF";
				fullImgPath = imgPath + "alter_the_future.jpg";
			}
			else if(i == 1) {
				type = "AT";
				fullImgPath = imgPath + "attack.jpg";
			}
			else if(i == 2) {
				type = "BC";
				fullImgPath = imgPath + "beard_cat.jpg";
			}
			else if(i == 3) {
				type = "CM";
				fullImgPath = imgPath + "cattermelon.jpg";
			}
			else if(i == 4) {
				type = "DF";
				fullImgPath = imgPath + "defuse.jpg";
			}
			else if(i == 5) {
				type = "DB";
				fullImgPath = imgPath + "draw_from_the_bottom.jpg";
			}
			else if(i == 6) {
				type = "EK";
				fullImgPath = imgPath + "exploding_kitten.jpg";
			}
			else if(i == 7) {
				type = "FA";
				fullImgPath = imgPath + "favor.jpg";
			}
			else if(i == 8) {
				type = "FC";
				fullImgPath = imgPath + "feral_cat.jpg";
			}
			else if(i == 9) {
				type = "HP";
				fullImgPath = imgPath + "hairy_potato_cat.jpg";
			}
			else if(i == 10) {
				type = "NO";
				fullImgPath = imgPath + "nope.jpg";
			}
			else if(i == 11) {
				type = "RC";
				fullImgPath = imgPath + "rainbow_ralphing_cat.jpg";
			}
			else if(i == 12) {
				type = "SF";
				fullImgPath = imgPath + "see_the_future.jpg";
			}
			else if(i == 13) {
				type = "SH";
				fullImgPath = imgPath + "shuffle.jpg";
			}
			else if(i == 14) {
				type = "SK";
				fullImgPath = imgPath + "skip.jpg";
			}
			else if(i == 15) {
				type = "TC";
				fullImgPath = imgPath + "taco_cat.jpg";
			}
			else {
				type = "TA";
				fullImgPath = imgPath + "targeted_attack.jpg";
			}
			moreCardData.add(fullImgPath);
			moreCardData.add(type);
			cardData.add(moreCardData);
		}
		
		return cardData;
	}
}
