package Models;

import java.util.ArrayList;
import java.util.List;

public class UnoFlipCardDobbyInit {
private static List<String[]> cardData;
	
	public static List<String[]> unoFlipCardArray() {
		/* Card Keys:
		 * 0-9 = itself
		 * Reverse = R
		 * Skip = S
		 * +1 = O
		 * Wild = W
		 * +2 Wild = TW
		 * Flip = F
		 * Draw To Match Wild = DW
		 * Skip All = SA
		 * +5 = FV
		 * 
		 * Color Keys:
		 * Blue = B
		 * Green = G
		 * Yellow = Y
		 * Red = R
		 * Pink = P
		 * Orange = O
		 * Violet = V
		 * Cyan = C
		 * Black = W
		 * White = I
		 */
		cardData = new ArrayList<String[]>();
		String color;
		String number;
		String imgPath = "../war/_view/images/UnoFlipCards/";
		//cardData.add(new String[imgPath + "B_R", "B", "R"]);
		return cardData;
	}
	
	private String[] createNewString(List<String> list) {
		String[] strings = new String[list.size()];
		int i = 0;
		for(String s : list) {
			strings[i] = s;
			i++;
		}
		return strings;
	}
}
