import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class RemoveDuplicates {
	
	//Return a list without duplicates
	public static List<String> remove(List<String> list) {
		// Store unique items in result
		ArrayList<String> result = new ArrayList<String>();
		// Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();

		// Loop over argument list.
		for (String item : list) {
			// If String is not in set, add it to the list and the set.
			if (!set.contains(item)) {
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}
}
