import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Execute {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// settings
		String dbName = "/stud/users/promo15/tang/COURS-1A/PAF/database/debate.db"; // TODO
		String fileName = "data/mot_original/1_mot_original.csv"; // table Context: 1981, 1988, 1995, 2007
//		int docID = 1;	// table Context
//		int startContID = 0;	//table Context: 0, 289, 487, 805;
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		
		/****** Configuration *********/
	/*	String tableName = "Tag_lemme";
		int tableID = 1; // pre-defined
		String[] attrb = {"id_Tag", "nm_Tag"}; 
	
		String tableName = "Freq_tag_document";
		int tableID = 5; // pre-defined
		String[] attrb = {"id_Tag", "id_doc", "freq"}; 
		
		String tableName = "Freq_tag_org_speaker_doc"; // "Freq_tag_lem_speaker_doc"
		int tableID = 6; // pre-defined
		String[] attrb = {"id_Tag", "id_comb", "freq"}; 

		String tableName = "Context";	// insert
		int tableID = 3; // pre-defined
		String[] attrb = {"id_context", "id_doc", "id_spk", "Q_A", "text"};
	*/		
		String tableName = "Re_tag_context";
		int tableID = 7; // pre-defined
		String[] attrb = {"id_tag", "id_context"};		
	/*	
		String tableName = "Distance_lem";
		int tableID = 4; // pre-defined
		String[] attrb = {"id_spk_in_doc1", "id_spk_in_doc2", "dist"};
		
		String tableName = "Re_org_lem";
		int tableID = 10; // pre-defined
		String[] attrb = {"id_org", "id_lem"};	
	
		String tableName = "Re_context_theme";
		int tableID = 8 ;
		String[] attrb = {"id_context", "id_theme"};
		
		String tableName = "Context";  // update column Weight
		int tableID = 11;
	*/	
		/******* end ********/

		// open cible database
		SQLWriter writer = new SQLWriter(dbName);  
		Connection conn = writer.getSQLiteConnection();
		Statement st = conn.createStatement();

		// generate SQL insert statement model
		String sqlInsert = writer.geneInsertStmt(tableName, attrb);
		
		// generate SQL update Context Weight
//		String sqlUpdate = writer.geneUpdateContxWeightStmt(tableName, "weight", "id_context");

		/****** Configuratoin *********/
//		valueList = getValueListTag(writer, fileName);
//		valueList = getValueListFreqTagDoc(writer, fileName);
//		valueList = getValueListFreqTagSpkDoc(writer, "data/mot_original/", "_mot_original", 255);
//		valueList = getValueListContext(writer, fileName, docID, startContID);
//	  	valueList = getValueListDistance(writer, fileName);
//		valueList = getValueListReContxTheme(writer, fileName);
//		valueList = getValueListContxWeight(writer, fileName);
	
		// Relation Tag-Context
		ArrayList<String[]> tagList = getValueListTag(writer, fileName);
		int length = tagList.size();
		for(int i = 0; i < length; i++) { 
			System.out.println(Arrays.toString(tagList.get(i)));
			ArrayList<String[]> relationList = findTagInContext(tagList.get(i), "data/context.txt");
			valueList.addAll(relationList);
		}
		
	/*	// Relation origin-lemme
		ArrayList<String[]> corresList = getValueListCorresOrgLem(writer, "data/correspondance/correspondance.csv");
		int length = corresList.size();
		for(int i = 0; i < length; i++) {
			ArrayList<String[]> relationList = findTagID(corresList.get(i),"data/tag_org_id.csv");
			valueList.addAll(relationList);
		}
	*/		
		/******* end ********/
	
	
		/**** TO CSV*******/
	//	writeToCSV("context.csv", valueList);
		
		
		// insert values into table
		long startTime = System.currentTimeMillis();	
		System.out.println("> (test) Start inserting data.");

		writer.execInsertStmt(conn, sqlInsert, tableID, valueList); //TODO 
		
	//	for (String[] value: valueList) 
	//		System.out.println("print exec: " + Arrays.toString(value));
 
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("> (test) Data insertion finished. Duration: [" + totalTime
				+ " ms].");
			
		//selectTest(st);
		
		st.close();
		conn.close();
	}
	
	/**
	 * insert into Tag
	 * @param writer
	 * @param fileName
	 * @return
	 */
	private static ArrayList<String[]> getValueListTag(SQLWriter writer, String fileName) {
		/*** Configuration in main()******
		String tableName = "Tag";
		int tableID = 6; // pre-defined, Freq_tag_speaker_doc
		String[] attrb = {"id_tag", "nm_tag"}; 
	    */
		
		// read CSV file
		CSVReader reader = new CSVReader(fileName); 
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		int[] colNum= {0}; // reader column1 of CSV file;
		valueList = reader.readColumnsAddID(colNum);	

		return valueList;	
	}

	/**
	 * insert into Freq_tag_doc
	 * @param writer
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListFreqTagDoc(SQLWriter writer, String fileName) {
		/*** Configuration in main()******
		String tableName = "Freq_tag_document";
		int tableID = 5; // pre-defined, Freq_Tag_Document
		String[] attrb = {"id_Tag", "id_doc", "freq"}; 
	    */
		
		// read CSV file
		CSVReader reader1 = new CSVReader(fileName);
		CSVReader reader2 = new CSVReader(fileName);
		int[] colNum1 = { 1 }; // Tag, freq_tag_doc1
		int[] colNum2 = { 3 }; // Tag, freq_tag_doc2
		
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		ArrayList<String[]> valueList2 = new ArrayList<String[]>();
		valueList = reader1.readColumnsAddIDString(colNum1, "1", 1); // for  Freq_tag_doc
		//valueList2 = reader2.readColumnsAddIDString(colNum2, "2", 1); // for  Freq_tag_doc
		
	//	valueList.addAll(valueList2);
		System.out.println("> (test) ValueList size = " + valueList.size());

		return valueList;
	}
	
	/**
	 * insert into Freq_tag_speaker_doc
	 * @param writer
	 * @param maxFileNum
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListFreqTagSpkDoc(SQLWriter writer, String prefix, String suffix, int maxFileNum ) {
		/*** Configuration in main()******
		String tableName = "Freq_tag_speaker_doc";
		int tableID = 5; // pre-defined, Freq_Tag_Document
		String[] attrb = {"id_Tag", "id_comb", "freq"}; 
	    */
		String fileName = null;
		
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		ArrayList<String[]> valueList2 = new ArrayList<String[]>();
		
		// read file 1 ~ maxFileNum, id_combinaison
		for (int i = 1; i <= maxFileNum; i++) { 
			fileName = prefix + i + suffix + ".csv";

			// read CSV file
			CSVReader reader = new CSVReader(fileName);
			int[] colNum = { 1 }; // column 1 in file -- tfidf

			// read column1 (tfidf) in file, add id_tag and id_comb in new list
			valueList2 = reader.readColumnsAddIDString(colNum, String.valueOf(i), 1);

			valueList.addAll(valueList2);
			System.out.println("> (test) ValueList size = " + valueList.size());
		}
		
		return valueList;
	}

	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListContext(SQLWriter writer, String fileName, int docID, int startContID) {
		File file = new File(fileName);
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLContextReader handler = new XMLContextReader(docID, startContID);
			saxParser.parse(file, handler);

			list = handler.getContextList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	

	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListDistance(SQLWriter writer, String fileName) {
		/*** Configuration in main()******
	    */
		
		// read CSV file
		CSVReader reader = new CSVReader(fileName);
		int[] colNum = {1, 3, 4}; // spk1, spk2, dist
		
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		valueList = reader.readColumns(colNum);
	/*	ArrayList<Integer> idxToRemove = new ArrayList<Integer>();
		
		for (int i = 0; i < valueList.size(); i++) {	
			String[] value = valueList.get(i);
			if (Integer.valueOf(value[0]) >= Integer.valueOf(value[1]))
				idxToRemove.add(new Integer(i));
		}
		
		for (int i = 0; i < idxToRemove.size(); i++) {
			valueList.remove(idxToRemove.get(i).intValue());
		}
		
	*/	System.out.println("> (test) ValueList size = " + valueList.size());

		return valueList;
	}

	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListCorresOrgLem(SQLWriter writer, String fileName) {
		/*** Configuration in main()******
		    */
			
			// read CSV file
			CSVReader reader = new CSVReader(fileName);
			int[] colNum = {0, 2}; // mot_org, lemme_id
			
			ArrayList<String[]> valueList = new ArrayList<String[]>();
			valueList = reader.readColumns(colNum);
			
			return valueList;
	}
	

	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListReContxTheme(SQLWriter writer, String fileName) {
		/*** Configuration in main()******
		String tableName = "Freq_tag_speaker_doc";
		int tableID = 5; // pre-defined, Freq_Tag_Document
		String[] attrb = {"id_Tag", "id_comb", "freq"}; 
	    */

		ArrayList<String[]> valueList = new ArrayList<String[]>();
		ArrayList<String[]> lines = new ArrayList<String[]>();

		// read CSV file
		CSVReader reader = new CSVReader(fileName);
		int[] colNum = { 0, 2, 3, 4, 5, 6, 7, 8, 9 }; // column 1 in file -- tfidf
		int length = colNum.length;

		// read file
		lines = reader.readColumns(colNum);
		for (String[] line: lines){			
			for (int i = 1; i < length; i++){
				if (line[i].equalsIgnoreCase("true")) {
					String[] value = new String[2];
					value[0] = line[0];
					value[1] = String.valueOf(i); // column i - theme i
					valueList.add(value);
					
					System.out.println("getList: " + Arrays.toString(value));
				}
			}
		}
		System.out.println("> (test) ValueList size = " + valueList.size());

		return valueList;
	}
	
	/**
	 * context - weight
	 * @param writer
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<String[]> getValueListContxWeight(SQLWriter writer, String fileName){
		ArrayList<String[]> valueList = new ArrayList<String[]>(); 

		// read CSV file
		CSVReader reader = new CSVReader(fileName);
		int[] colNum = { 0, 1 }; // column 0: id_context, column 1: weight
		// read file
		valueList = reader.readColumns(colNum);
		
		return valueList;
	}
	
	
	@SuppressWarnings("unused")
	private static void selectTest(Statement st) {
		// select test
		String execSelect = "SELECT * FROM Tag WHERE id_Tag > 7250;";
		ResultSet res;
		int counter = 0;
		try {
			res = st.executeQuery(execSelect);
			System.out.println("id_Tag \t nm_Tag");
			System.out.println("-----" + "\t" + "-----");

			while (res.next()) {
				counter++;
				System.out.println(res.getInt("id_Tag") + "\t"
						+ res.getString("nm_Tag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("> Result: " + counter + " rows.");
	}
	
	/**
	 * write content to CSV file
	 * @param csvFile
	 * @param content
	 */
	@SuppressWarnings("unused")
	private static void writeToCSV(String csvFile, ArrayList<String[]> valueList) {
		try {
			// append to end of file
			PrintWriter out = new PrintWriter((new BufferedWriter(new FileWriter(csvFile, true))));
			
			for (String[] value: valueList) {
				String csv = value[0] + "\\" +value[1] + "\\" + value[2] + "\\" + value[3] + "\\" + value[4]+"\n";
				System.out.println(csv);
				out.print(csv);
			}
			out.close();
			
			System.out.println("write to CSV: Done - " + csvFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * find association between Tag(mot original) - Context
	 * @param tags
	 * @param contextFile
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<String[]> findTagInContext(String[] tagList, String contextFile) {

		String line = null;
		int lineCount = 0;
		boolean contains = false;
		ArrayList<String[]> relation = new ArrayList<String[]>();
		String tagID = tagList[0];
		String tagNm = tagList[1];


		try {
			FileReader fr = new FileReader(contextFile);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				lineCount++;
				String[] words = line.toLowerCase().split("[^a-zA-Z0-9\\p{L}]+"); // convert to case non-sensitive and split

				for (int i = 0; i < words.length; i++) {
					if (words[i].equalsIgnoreCase(tagNm)) {
			//			System.out.println(Arrays.toString(words));

						String[] value = new String[2];
						value[0] = tagID; // column 1: id_tag
						value[1] = String.valueOf(lineCount); // column 2: id_context
						relation.add(value);

						System.out.println("findTagInContext: found -" + tagNm + ": "+ Arrays.toString(value));
					} // fi
				} // for
			}// while
			br.close();
			fr.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return relation;
	}
	
	 
	/**
	 * find association between Tag(mot original) - Context
	 * @param tags
	 * @param contextFile
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ArrayList<String[]> findTagID(String[] tagLemmeList, String tagIDFile) {
	
		ArrayList<String[]> relation = new ArrayList<String[]>(); 
		String line, tagOrg, lemmeID;
		String[] items = new String[2]; // in tagIDFile: id_tag, nm_tag
		
		try {
			FileReader fr = new FileReader(tagIDFile);
			BufferedReader br = new BufferedReader(fr);

			tagOrg = tagLemmeList[0];
			lemmeID = tagLemmeList[1];

			while ((line = br.readLine()) != null) {
				items = line.split(",");
			//	System.out.println(Arrays.toString(items));
				if (items[1].equalsIgnoreCase(tagOrg)) {

					String[] value = new String[2];
					value[0] = items[0]; // column 1: id_tag
					value[1] = lemmeID; // column 2: id_context
					relation.add(value);	// add to list

					System.out.println("findTagID: found -" + Arrays.toString(value));
				} // fi
			}// while

			br.close();
			fr.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return relation;
	}
}
