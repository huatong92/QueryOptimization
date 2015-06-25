

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {
	
	// print out bing search information 
	public static void printInfo(String query, double precision, String accountKey){
		String queryF = query.replace('+', ' ');
		System.out.println("Parameters:");
		System.out.printf("%-14s%s%n", "Client key","= " + accountKey);
		System.out.printf("%-13s%s%n", "Query", " = " + queryF);
		System.out.printf("%-13s%s", "Precision", " = " + precision);
		System.out.println("\r\nURL: " + Main.BingUrlPre + query + Main.BingUrlPost + 
				"\r\nTotal no of results: " + Main.NumOfResult + "\r\nBing Search Result:" +
				"\r\n============================");
	}
	
	// print out feedback
	public static void printFeedback(String query, double precision, double newPrecision, String max1, String max2){
		String queryF = query.replace('+', ' ');
		System.out.println("============================\r\nFEEDBACK SUMMARY\r\n" + "Query " 
				+ queryF + "\r\nPrecision " + newPrecision);
		
		if (newPrecision < precision){
			System.out.println("Still bellow the desired precision of " + precision + 
			"\r\nIndexin results ..." + "\r\nIndexin results ...");
			if (newPrecision == 0){
				System.out.println("Augmenting by  \r\nBelow desired precision, but can no longer augment the query");
			}
			else{
				System.out.println("Augmenting by  " + max1 + " " + max2);
			}
		}
		else{
			System.out.println("Desired precision reached, done");
		}
	}
	
	// this function takes in a query string, uses bing API to get 10 web pages, gives the info to users,
	// gets feedback from users, and returns a list of integers recording the indexes of relevant web pages
	public static List<List<Integer>> getPrecision(String query, List<String> url, List<String> title, List<String> summary) throws Exception{
		
		// Record the index of relevant web pages
		List<Integer> rel = new ArrayList<Integer>();
		List<Integer> nrel = new ArrayList<Integer>();
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		
		
		for (int i = 0; i < Main.NumOfResult; i ++){
			// Print result i 
			System.out.println("Result "+ (i+1) + "\r\n" + "[\r\n URL: "+ url.get(i) + "\r\n" 
					+ " Title: "+ title.get(i) + "\r\n" + " Summary: "+ summary.get(i) + "\r\n]\r\n");
			if (relevant()){
				rel.add(i);
			}
			else
				nrel.add(i);
		}
		
		list.add(rel);
		list.add(nrel);
		return list;
	}
	
	
	// read from user input and return user's decision
	public static Boolean relevant(){
		System.out.println("Relevant (Y/N)?");
		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		String relevant = null;
 
		//  read the input
		try {
			relevant = br.readLine();
		} catch (IOException ioe) {
			System.exit(1);
		}
		
		
		// For wrong inputs, ask the users to input again
		while (!relevant.equals("Y") && !relevant.equals("y") && !relevant.equals("n") && !relevant.equals("N")){
			System.out.println("Relevant (Y/N)?");
			//  open up standard input
			br = new BufferedReader(new InputStreamReader(System.in));
	 
			//  read the input
			try {
				relevant = br.readLine();
			} catch (IOException ioe) {
				System.exit(1);
			}
			
		}
		
		if (relevant.equals("Y") || relevant.equals("y")){
			return true;
		}
		else{
			return false;
		}
			
	}
	
	
}
