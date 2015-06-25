

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;


public class BingSearch {
		
		private String query = "";
		private String bingUrl = Main.BingUrlPre + query + Main.BingUrlPost;
		
		// Default Constructor
		public BingSearch(){
			
		}
		
		// Constructor
		public BingSearch(String query) {
			this.query = query;
			bingUrl = Main.BingUrlPre + query + Main.BingUrlPost;
		}
				

		// Returns the xml texts from bing API as a string
		public String useAPI(String accountKey) throws Exception{
			if (query == ""){
				System.out.println("set query first");
			}
			byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
			String accountKeyEnc = new String(accountKeyBytes);

			URL url = new URL(bingUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
					
			InputStream inputStream = (InputStream) urlConnection.getContent();		
			byte[] contentRaw = new byte[urlConnection.getContentLength()];
			inputStream.read(contentRaw);
			String content = new String(contentRaw);
			
			return content;
		}
			
			
}
