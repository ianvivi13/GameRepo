package Models;

import java.util.ArrayList;

public class sqlTranscoder {
	
	private static String removeLastChar(String str) {
		return str.substring(0,str.length() - 1);
	}
	
	public static String encode(ArrayList<Integer> intList) {
		if(!intList.isEmpty()) {
			String delimeter = "/";
			String encode = "";
			for (Integer idInt : intList) {
				encode += idInt.toString();
				encode += delimeter;
			}
			return removeLastChar(encode);
		}
		
		else {
			return "/";
		}
	}
	
	public static ArrayList<Integer> decode(String encode) {
		ArrayList<Integer> decode = new ArrayList<Integer>();
		if(encode != "/") {
			String[] encodeArray = encode.split("/");
			for (String encodedInt : encodeArray) {
				decode.add(Integer.valueOf(encodedInt));
			}
		}
		
		return decode;
		
	}
}
