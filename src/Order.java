import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Order {
	
	// change the order of the query words
	public static String order(List<String> query, String word1, String word2, Map<String,TermNode> map){
		// if a word has added or not
	
		List<String> temp = add(query, word1, map);
		temp = add(temp, word2, map);
		
		// change the list of query words to a string
		String result = "";
		for (String s : temp)
		{
		    result += s + "+";
		}
		
		return result.substring(0,result.length()-1);
		
	}
	
	public static List<String> add(List<String> query, String word, Map<String,TermNode> map){
		List<String> temp = new ArrayList<String>(query);
		boolean add = false;
		// add the first new word
		for(String s: temp){
			// count the number of times word1 appears in the previous position of word s
			int countPrev = map.get(s).cPrevWord(word);
			// count the number of times word1 appears in the next position of word s
			int countNext = map.get(s).cNextWord(word);
			// decide where to put word1
			if ( countPrev > countNext ){
				add = true;
				temp.add(temp.indexOf(s), word);
				break;
			}
			else if ( countPrev < countNext ){
				add = true;
				temp.add(temp.indexOf(s) + 1, word);
				break;
			}
			else if (countPrev != 0){
				add = true;
				temp.add(temp.indexOf(s), word);
				break;
			}
			
		}
		// if the word is not added at this point, add it at the last
		if (add == false){
			temp.add(word);	
		}
		return temp;
	}
	
}
