import java.util.*;
public class TermNode {
     private String name;
     private int termFreq_r;
     private int termFreq_n;
     private int docFreq_r;
     private int docFreq_n;
     private double score;
     private List<String> prev = new ArrayList<String>();
     private List<String> next = new ArrayList<String>();
    
     //private double score_n;
     
     //Constructor
     public TermNode(String name){
    	 this.name = name;
    	 termFreq_r = 1;
    	 docFreq_r = 1;
    	 termFreq_n = 1;
    	 docFreq_n = 1;
    	 
     }
     
     public String getName(){
    	 return name;
     }
     
     public void incrementFreq1(){
    	 termFreq_r++;
     }
     public void incrementFreq2(){
    	 termFreq_n++;
     }
     
     public void incrementDoc1(){
    	 docFreq_r++;
     }
     public void incrementDoc2(){
    	 docFreq_n++;
     }
     public void add_prev(String s){
    	 prev.add(s);
     }
     public void add_next(String ss){
    	 next.add(ss);
     }
     public int cPrevWord(String word){
    	 int count = 0;
    	 for (String s: prev){
    		 if(s.equals(word)){
    			 count ++;
    		 }
    	 }
    	 return count;
     }
     public int cNextWord(String word){
    	 int count = 0;
    	 for (String s: next){
    		 if(s.equals(word)){
    			 count ++;
    		 }
    	 }
    	 return count;
     }
     
     // calculate score based on tf idf scheme
     public void calculateScore(int r_doc, int n_doc){
    	 score = (Main.beta * termFreq_r * Math.log((double)Main.NumOfResult/docFreq_r)/r_doc) -
    			 Main.gamma * termFreq_n *Math.log((double)Main.NumOfResult/docFreq_n)/n_doc;
    }
     
    public double getScore(){
    	return score;
    }
      
}
