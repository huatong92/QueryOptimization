
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
	public static final int NumOfResult = 10;
	public static final String BingUrlPre = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%27";
	public static final String BingUrlPost = "%27&$top=" + NumOfResult + "&$format=Atom";
	public static float beta = (float) 0.75;
	public static float gamma = (float) 0.15;
	
	public static void main(String[] args) throws Exception {
		
		String query;
		double precision;
		String accountKey;
		double newPrecision = 1;
		
		//get precision, query from command line 
		accountKey = args[0];
		precision = Double.parseDouble(args[1]);
		query = args[2];
		
		query = FormatString.spaceToPlus(query);
		
		// print bing search information
		UserInterface.printInfo(query, precision, accountKey);
		
		
		// iterate until goal precision reached
		do{
			// use bing api 
			BingSearch bing = new BingSearch(query);
			String content = bing.useAPI(accountKey);
		
			// get list of urls returned from bing api
			XmlAnalyser xml = new XmlAnalyser();
			List<String> url = xml.parse(content, "d:Url");
			List<String> title = xml.parse(content, "d:Title");
			List<String> summary = xml.parse(content, "d:Description");
			
			
			// if search result less than 10, stop the program
			if ( url.size() < Main.NumOfResult ){
				System.out.println("Number of results: "+ url.size() + "\r\nLess than expected. Please search again!");
				break;
			}
			
			// output result for users, and get precision feedback
			List<List<Integer>> index = UserInterface.getPrecision(query, url, title, summary);
			// relevant page indexes
			List<Integer> relIndex = index.get(0);
			// non relevant page indexes
			List<Integer> nrelIndex = index.get(1);
			// relevant page content
			List<String> rel_title_summary = new ArrayList<String>();
			// non relevant page content
			List<String> nrel_title_summary = new ArrayList<String>();
			
			newPrecision = (double)relIndex.size() / (double)Main.NumOfResult;
			
			// if stop requirement not reached
			if (newPrecision < precision && newPrecision != 0){
				
				for (int i = 0; i < relIndex.size(); i++){
					String ts = title.get(relIndex.get(i)) + " * " + summary.get(relIndex.get(i));
					ts = FormatString.format(ts);
					rel_title_summary.add(ts);
				}
				for (int i = 0; i < nrelIndex.size(); i++){
					String ts = title.get(nrelIndex.get(i)) + " * " + summary.get(nrelIndex.get(i));
					ts = FormatString.format(ts);
					nrel_title_summary.add(ts);
				}
				
				// create a hash table, parse in content and compute the weight of each word 
				Rhashtable hash = new Rhashtable();
				Map<String, TermNode> rmap = hash.Terms(rel_title_summary);
				rmap= hash.n_Terms(rmap,nrel_title_summary);
				
				// Calculate scores of all terms
				for (String s : rmap.keySet()) {
					int doc_r = rel_title_summary.size();
					int doc_n = nrel_title_summary.size();
					rmap.get(s).calculateScore(doc_r,doc_n);
				}
				
				// return the two max weighted word, add to the query
				String[] max = SortMap.getMax(rmap, query);
				List<String> q = FormatString.splitQuery(query);
				// change the order of query words
				String newQuery = Order.order(q, max[0], max[1], rmap);
				
				// print feedback to users
				UserInterface.printFeedback(query, precision, newPrecision, max[0], max[1]);
				UserInterface.printInfo(newQuery, precision, accountKey);
				
				// update query
				query = newQuery;
			}
			else{
				// stop the program
				UserInterface.printFeedback(query, precision, newPrecision, "", "");
			}
		
		}while(newPrecision < precision && newPrecision != 0);
		
		
	}

		
	
}
