import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
 
public class CSVReader {
 
	private BufferedReader br = null;
	
	public CSVReader (String fileName) {
		try {
			this.br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * read values in indicated columns
	 * @param colNum
	 * @return 
	 */
    public ArrayList<String[]> readColumns(int[] colNum) {       
    	ArrayList<String[]> valueList = new ArrayList<String[]>();
    	
    	int length = colNum.length;
    	String line = null;
    	String[] items = null;	// all items in a line read
    	int i = 0, j = 0;
    	
    	try {
    		br.readLine(); // ignore first line -> attribute title 
    		while((line=br.readLine())!=null){
    			System.out.println(line);
    			items = line.split(","); // separator
    			String[] values = new String[length]; // elements needed
     			
    			for (i = 0; i < length; i++) {
    				values[i] = items[colNum[i]];
    			}
    			valueList.add(values);
    			System.out.println("Row " + (j++) + ": " + Arrays.toString(values));
    		} //while
    	} catch (IOException e) {
    		e.printStackTrace();
    	} 

        return valueList;
    }
 
    /**
     * read values in indicated columns and add an auto-incremented ID to each element
     * @param colNum
     * @return
     */
    public ArrayList<String[]> readColumnsAddID(int[] colNum) {       
    	ArrayList<String[]> valueList = new ArrayList<String[]>();
    	
    	int length = colNum.length + 1; // add one column for ID
    	String line = null;
    	String[] items = null;	// all items in a line read
    	int id = 0, j;	// ID begins at 1
    	
		try {
			br.readLine(); // ignore first line -> attribute title
			while ((line = br.readLine()) != null) {
				id++;
				items = line.split(","); // separator
				String[] values = new String[length]; // elements needed
				values[0] = String.valueOf(id); // add ID, auto-increment

				for (j = 1; j < length; j++) {
					values[j] = items[colNum[j - 1]];
				} // for

				valueList.add(values); 
				System.out.println("Row " + id + " : " + Arrays.toString(values));
			} // while
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valueList;
    }
    
    /**
     * read values in indicated columns and add an ID and a String value in indicated position
     * to each element
     * @param colNum	column numbers in CSV file to read
     * @param str	String to add as a value in column strColNum in the target table 
     * @param strColNum 	Number of column in the target table to put str
     * @return
     */
    public ArrayList<String[]> readColumnsAddIDString(int[] colNum, String str, int strColNum) {       
    	ArrayList<String[]> valueList = new ArrayList<String[]>();

    	int length = colNum.length + 2; // add one column for str
    	String line = null;
    	String[] items = null;	// all items in a line read 
    	int id = 0, j = 0;
    	
    	try {
    		br.readLine(); // ignore first line -> attribute title
    		while ((line = br.readLine()) != null) { 
    			id ++;
    			items = line.split(","); // separator
    			String[] values = new String[length]; // elements needed
    			values[0] = String.valueOf(id);
    			values[strColNum] = str; // add str

    			for (j = 1; j < strColNum; j++) {
    				values[j] = items[colNum[j-1]];
    			}
    			for (j = strColNum + 1; j < length; j++) {
    				values[j] = items[colNum[j-2]];
    			} // for
    			
    			valueList.add(values);
    			
    			System.out.println("Row " + id + " : " + Arrays.toString(values));
    		} // while
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return valueList;
    }
}