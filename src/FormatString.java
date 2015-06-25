import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FormatString {
	
	// change all space into plus sign
	public static String spaceToPlus(String query){
		String q = "";
		if (query.contains(" ")){
			q = query.replaceAll("\\s+", "+");
		}
		else{
			q = query;
		}
		return q;
	}
	
	// format the string
	public static String format(String str){
		String newStr = str;
		newStr = newStr.trim().toLowerCase();
		// replace all punctuation with *
		newStr = newStr.replaceAll("[^a-zA-Z ]", " * ");
		newStr = newStr.replaceAll("\\s+", " ");
		return newStr;
	}
	
	// split the query string into separate words
	public static List<String> splitQuery(String query){
		List<String> list = new ArrayList<String>();
		if (query.contains("+")){
			String[] q = query.split("\\+");
			list = Arrays.asList(q);
		}
		else{
			list.add(query);
		}
		return list;
		
	}
}
