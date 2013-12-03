import java.util.ArrayList;
import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Read a parsed XML file with <replique></replique>, and store the content into
 * table Context in database
 * 
 * @author tang
 * 
 */
public class XMLContextReader extends DefaultHandler {

	private String docID = null;
	private int contextID;
	private ArrayList<String[]> contextList;
//	private Context contx;
	
/*	private class Context {	
		int contxID = 0;
		String spkID = null;
		String QA = null;
		String text = new String();
		
		private Context(int cID, String spkID, String QA) {
			this.contxID = cID;
			this.spkID = spkID;
			this.QA = QA;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		public String[] getString() {
			return new String[]{String.valueOf(contxID), docID, spkID, QA, text}; 
		}
	}
*/	

	public XMLContextReader(int docID, int startContID) {
		super();
		this.docID = String.valueOf(docID);
		this.contextID = startContID;
		this.contextList = new ArrayList<String[]>();
	}
	
	public void startDocument() throws SAXException {
		System.out.println("> Parsing begins");
	}
	
	
	public void endDocument() throws SAXException {
		System.out.println("> Parsing finished");
	
	}
	
	
	public void characters(char ch[], int start, int length)
			throws SAXException {
		text = new String(ch, start, length);
		System.out.println( "char : " + text);
//		contx.setText(text);
	}


	String speaker = null, spkID = null, QA = null, text = null;
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
				
		
		System.out.println("> Begin element");
		
		// If its a replique tag, look for "locuteur" attribute
		if (qName.equalsIgnoreCase("replique")) {
			
			speaker = attributes.getValue("locuteur");

			switch(Integer.parseInt(docID)){	// different persons
			case(1): { // doc 1981
				if (speaker.equalsIgnoreCase("Valéry GISCARD D'ESTAING")) {			
					spkID = "1";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("François MITTERRAND")) {
					spkID = "2";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("J. BOISSONNAT")) {
					spkID = "4";
					QA = "Q";
				} else if (speaker.equalsIgnoreCase("COTTA")) {
					spkID = "3";
					QA = "Q";
				}
				break;
			}	
			case(2): { // doc 1988
				if (speaker.equalsIgnoreCase("MITTERRAND")) {			
					spkID = "2";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("CHIRAC")) {
					spkID = "5";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("VANNIER")) {
					spkID = "13";
					QA = "Q";
				} else if (speaker.equalsIgnoreCase("COTTA")) {
					spkID = "3";
					QA = "Q";
				}
				break;
			}
			case(3): { // doc 1995
				if (speaker.equalsIgnoreCase("JOSPIN")) {			
					spkID = "2";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("CHIRAC")) {
					spkID = "5";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("DUHAMEL")) {
					spkID = "7";
					QA = "Q";
				} else if (speaker.equalsIgnoreCase("DURAND")) {
					spkID = "8";
					QA = "Q";
				}
				break;
			}
			case(4): { // doc 2007
				if (speaker.equalsIgnoreCase("Ségolène Royal")) {			
					spkID = "9";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("Nicolas Sarkozy")) {
					spkID = "10";
					QA = "A";
				} else if (speaker.equalsIgnoreCase("Arlette Chabot")) {
					spkID = "11";
					QA = "Q";
				} else if (speaker.equalsIgnoreCase("Patrick Poivre d'Arvor")) {
					spkID = "12";
					QA = "Q";
				}
				break;
			}
			} // switch
			

			//contx = new Context(contextID, spkID, QA);
		}
	}

	// When the parser encounters the end of an element, it calls this method
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String[] values = new String[5];
	
		if (qName.equalsIgnoreCase("replique")) {
			contextID++;
			// generate SQL, insert
			values[0] = String.valueOf(contextID);
			values[1] = docID;
			values[2] = spkID;
			values[3] = QA;
			values[4] = text;
			this.contextList.add(values);
		//	System.out.println(Arrays.toString(values));
		}

		System.out.println("> End element --" + values[0] + values[2] );

	}

	public ArrayList<String[]> getContextList() {
		return this.contextList;
	}

}
