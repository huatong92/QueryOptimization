

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class XmlAnalyser {
	// parse in Xml, get content inside
	public List<String> parse (String xmlRecords, String tag) throws Exception{
		List<String> list = new ArrayList<String>();

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(xmlRecords));

	    Document doc = db.parse(is);
	    NodeList nodes = doc.getElementsByTagName(tag);
	    
         
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	Element element = (Element) nodes.item(i);
	    	list.add(getValueInTag(element));
	    	//System.out.println(i+": "+getValueInTag(element));
	    }
	    return list;
	}

	public String getValueInTag(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	    	CharacterData cd = (CharacterData) child;
	    	return cd.getData();
	    }
	    return "";
	}
	   
}


