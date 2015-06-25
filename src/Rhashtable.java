import java.util.*;

public class Rhashtable {

	// store term_frequency and doc_frequency of all relevant terms into hashmap
	public Map<String, TermNode> Terms(List<String> Terms) {
		Map<String, TermNode> map = new HashMap<String, TermNode>();
		
		
		// count the term frequency of all terms in the relevant doc
		//for each relevant document
		for (String s : Terms) {
			String s_removestop = Stopwords.removeStopWords(s);
			String[] allTerms = s_removestop.split("\\s+");
			
			//for each term in a certain relevant document
			for (int i = 0; i < allTerms.length; i++) {
		
				// if the key exists
				if (map.containsKey(allTerms[i])) {
					TermNode duplicate = map.get(allTerms[i]);
					// increase term frequency
					duplicate.incrementFreq1();
					// record its previous words and next words
					if(i>0){
						if ( !allTerms[i-1].equals("*") ){
							duplicate.add_prev(allTerms[i-1]);
						}
					}
					if(i<allTerms.length-1){
						if ( !allTerms[i+1].equals("*") ){
							duplicate.add_next(allTerms[i+1]);
						}
					}
					// add to map
					map.put(allTerms[i], duplicate);

				} else {
					// create new TermNode
					TermNode newTerm = new TermNode(allTerms[i]);
					// record its previous words and next words
					if(i>0){
						if ( !allTerms[i-1].equals("*") ){
							newTerm.add_prev(allTerms[i-1]);
						}	
					}
					if(i<allTerms.length-1){
						if ( !allTerms[i+1].equals("*") ){
							newTerm.add_next(allTerms[i+1]);
						}
						
					}
					map.put(allTerms[i], newTerm);
				}
			}

		}
		
		/* count the document frequency of all terms*/
		//for each document
		for (String s : Terms) {
			String s_removestop =Stopwords.removeStopWords(s);
			String[] allTerms = s_removestop.split("\\s+");
			List<String> list = new ArrayList<String>(Arrays.asList(allTerms));
			list = RemoveDuplicates.remove(list);
			
			//for each term in a certain document
			for (String term : list) {
				TermNode temp = map.get(term);
				temp.incrementDoc1();
			}
		}
		map.remove("*");
		return map;

	}
	/* store term_frequency and doc_frequency of all non_relevant terms into hashmap*/
	public Map<String, TermNode> n_Terms(Map<String,TermNode> map, List<String> n_Terms){
		/*count the term frequency of all terms in the non_relevant doc*/
		//for each non-relevant doc
		for (String s : n_Terms) {
			String s_removestop = Stopwords.removeStopWords(s);
			String[] allTerms = s_removestop.split("\\s+");
			
			//for each term exists in both relevant and non_relevant doc
			for (int i = 0; i < allTerms.length; i++) {
				if (map.containsKey(allTerms[i])) {
					TermNode duplicate = map.get(allTerms[i]);
					duplicate.incrementFreq2();
					map.put(allTerms[i], duplicate);
					} 

			}

		}
		
		/* count the document frequency of all terms*/
		//for each non_relevant doc
		for (String s : n_Terms) {
			
			String s_removestop =Stopwords.removeStopWords(s);
			String[] allTerms = s_removestop.split("\\s+");
			List<String> list = new ArrayList<String>(Arrays.asList(allTerms));
			list = RemoveDuplicates.remove(list);
			//for each term exists in both relevant and non_relevant doc
			for (String term : list) {
				if(map.containsKey(term))
				map.get(term).incrementDoc2();
			}

		}
		
		return map;
		}
	
	
}
