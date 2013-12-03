import java.util.ArrayList;
import java.util.Arrays;

public class TestCSVReader {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "test.csv";
		int[] colNum1 = { 1 }; // reader column1 of CSV file;
		int[] colNum2 = { 2 }; // reader column1 of CSV file;

		CSVReader reader = new CSVReader(fileName);
		CSVReader reader2 = new CSVReader(fileName);
		ArrayList<String[]> valueList = new ArrayList<String[]>();
		valueList = reader.readColumnsAddIDString(colNum1, "1", 1);
		ArrayList<String[]> valueList2 = new ArrayList<String[]>();
		valueList2 = reader2.readColumnsAddIDString(colNum2, "2", 2);

		for (String[] value : valueList)
			System.out.println("List1: " + Arrays.toString(value));
		for (String[] value : valueList2)
			System.out.println("List22222: " + Arrays.toString(value));
	}

}
