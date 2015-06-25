
import java.util.List;
import java.util.Map;


public class SortMap {
	
	// get the two words with max scores that has not showed in current query
	public static String[] getMax(Map<String, TermNode> map, String query){
		double max = 0;
		double secondMax = 0;
		String maxS = "";
		String secondMaxS = "";
		String[] twoMax = new String[2];
		List<String> existingQuery = FormatString.splitQuery(query);
		
		// iterate over all keys
		for (String s: map.keySet()){
			TermNode term = map.get(s);
			double temp = term.getScore();
			// the key with largest score
			if (temp > max){
				if (!exist(s, existingQuery)){
					max = temp;
					maxS = s;
				}	
			}
			// the key with second largest score
			else if (temp > secondMax){
				if (!exist(s, existingQuery)){
					secondMax = temp;
					secondMaxS = s;
				}
			}
		}	
		
		twoMax[0] = maxS;
		twoMax[1] = secondMaxS;
		
		return twoMax;
		
	}
	

	
	// check if the word is in the exiting query list
	public static boolean exist(String s, List<String> query){
		for (int i = 0; i < query.size(); i ++){
			if (s.equals(query.get(i))){
				return true;
			}
		}
		return false; 
	}
}

