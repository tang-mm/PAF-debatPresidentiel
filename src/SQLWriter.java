import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SQLWriter {

	private String db = null;
	private PreparedStatement pst = null;
	private int timeout = 30;  
	private final int batchSize = 1000; // execute Batch every batchSize statements

	private SQLWriter() {}
	
	public SQLWriter(String db) { 
		this.db = db;
	}
	
	public SQLWriter(String dbPath, String dbName) {
		this.db = dbPath + dbName;
	}
	
	/**
	 * establish and return an SQLite connection
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getSQLiteConnection() throws ClassNotFoundException, SQLException {
		// jdbc driver registration
		String driver = "org.sqlite.JDBC"; 
		Class.forName(driver);
		
		// establish connection
		String dbUrl = "jdbc:sqlite:" + this.db;
		Connection conn = DriverManager.getConnection(dbUrl);
		
		return conn;
	}
	
	/**
	 * Generate an Insert SQL statement in the form of PrepraredStatement
	 * final form: INSERT OR REPLACE INTO table (attr0, attr1, .., attrN) VALUES (?, ?, .., ?)"
	 * @param table
	 * @param attributes
	 * @return
	 */
	public String geneInsertStmt(String table, String...attributes) {
		String sql = "INSERT OR REPLACE INTO " + table + " (" + attributes[0];  
		
		int length = attributes.length;  // number of columns		
		for (int i = 1; i < length; i++) {
		 sql += ", " + attributes[i];
		}
		sql += ") VALUES (?";  // except the first column
		for (int i = 1; i < length; i++) {
			 sql += ", ?";
		}
		sql += ")";
		
		System.out.println(sql);
		return sql;		
	}
	
	/**
	 * SQL query to update Weight column in context
	 * @param table
	 * @param weight
	 * @param contxID
	 * @return
	 */
	public String geneUpdateContxWeightStmt(String table, String nmCol, String idLine) {
		
		String sql = "UPDATE " + table + " SET " + nmCol+ " = ? WHERE "+ idLine + "= ?";
		System.out.println(sql);
		return sql;				
	}
	
	/**
	 * 
	 * @param conn
	 * @param sql
	 * @param valueList
	 * @param types
	 * @throws SQLException
	 */
	public void execInsertStmt(Connection conn, String sql, int tableID, 
			ArrayList<String[]> valueList) {
		try {
			this.pst = conn.prepareStatement(sql);
			pst.setQueryTimeout(timeout);
			conn.setAutoCommit(false);
		} catch (SQLException e2) {
			System.out.println("Error: cannot insert statements! ");
			e2.printStackTrace();
		}
		
		// get corresponding table-insertion method
		SQLWriter wr = new SQLWriter(); 
		Class<? extends SQLWriter> cl = wr.getClass();
		Method method = null;  
		Class<?>[] arg = {PreparedStatement.class, String[].class};
		
		try {
			switch (tableID) {
			case 1: method = cl.getDeclaredMethod("fnPstTag", arg); break;
			case 2: method = cl.getDeclaredMethod("fnPstTheme", arg); break;
			case 3: method = cl.getDeclaredMethod("fnPstContext", arg); break;
			case 4: method = cl.getDeclaredMethod("fnPstDistance", arg); break;
			case 5: method = cl.getDeclaredMethod("fnPstFreqTagDoc", arg); break; //Freq_Tag_Doc, Freq_Tag_Theme
			case 6: method = cl.getDeclaredMethod("fnPstFreqTagSpkDoc", arg); break;
			case 7: method = cl.getDeclaredMethod("fnPstReTagCtx", arg); break;
			case 8: method = cl.getDeclaredMethod("fnPstReCtxThm", arg); break;
			case 9: method = cl.getDeclaredMethod("fnPstReDocSpk", arg); break;
			case 10: method = cl.getDeclaredMethod("fnPstReTagLem", arg); break;
			case 11: method = cl.getDeclaredMethod("fnPstUpdateContxWeight", arg); break;
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		if (method != null)
			System.out.println("> (Writer) Invoke Method found");	
		else System.out.println("> (Writer) Invoke Method failed");	


		// fill in SQL statement with values
		try {
			int count = 0;
			for (String[] values: valueList) {			
				Object isValidObj = method.invoke(wr, pst, values); // test if value = null
				boolean isValid = Boolean.valueOf(isValidObj.toString());
				if (isValid == false)  continue; // do not insert this line
				pst.addBatch();	
				count++ ;

				if(count % batchSize == 0) {
					pst.executeBatch();		// insert batchSize statements	
					System.out.println("-- Execute batch " + count); // -----
					conn.commit();
				} // fi
			} // for
			
			pst.executeBatch(); // insert remaining records
			System.out.println("-- Execute batch - Total : " + count); // -----
			conn.commit();
			//	conn.setAutoCommit(true);
			pst.close();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
		
	/**
	 * PreparedStatement setting, define data types corresponding to the table Tag
	 * tableID = 1  
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstTag (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setLong(1, Long.parseLong(values[0]));
		pst.setString(2, values[1]);
		
		return true;
	}
	
	/**
	 * PreparedStatement setting -- table Theme, tableID = 2
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstTheme (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setInt(1, Integer.parseInt(values[0]));
		pst.setString(2, values[1]);
		
		return true;
	}

	/**
	 * tableID = 3
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstContext (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setInt(1, Integer.parseInt(values[0]));
		pst.setInt(2, Integer.parseInt(values[1]));
		pst.setInt(3, Integer.parseInt(values[2]));
		pst.setString(4, values[3]);
		pst.setString(5, values[4]);
		
		System.out.println("insert context: " + values[0]);
		
		return true;
	}

	/**
	 * tableID = 4
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstDistance (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setInt(1, Integer.parseInt(values[0]));
		pst.setInt(2, Integer.parseInt(values[1]));
		pst.setDouble(3, Double.parseDouble(values[2]));
		
		return true; 
	}
	
	/**
	 * Freq_Tag_Doc, Freq_Tag_Theme, tableID = 5
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstFreqTagDoc  (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		double freq = Double.parseDouble(values[2]);
		if (freq != 0.0) {
			pst.setLong(1, Long.parseLong(values[0]));
			pst.setInt(2, Integer.parseInt(values[1]));
			pst.setDouble(3, freq);
			
			return true;
		}else return false;
		
	}

	/**
	 * Freq_Tag_Speaker_Doc, tableID = 6
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstFreqTagSpkDoc (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		double freq = Double.parseDouble(values[2]);
		if (freq != 0.0){
			pst.setLong(1, Long.parseLong(values[0]));
			pst.setInt(2, Integer.parseInt(values[1]));	
			pst.setDouble(3, freq); 

			return true;
		}else return false;
	}
		
	/**
	 * Freq_tag_context, tableID = 7
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstReTagCtx (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {

	//	double freq = Double.parseDouble(values[2]);
	//	if (freq != 0.0){
			pst.setLong(1, Long.parseLong(values[0]));
			pst.setInt(2, Integer.parseInt(values[1]));
	//		pst.setDouble(3, freq); 

			return true;
	//	}else return false;
	}

	/**
	 * tableID = 8
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstReCtxThm (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setInt(1, Integer.parseInt(values[0]));
		pst.setInt(2, Integer.parseInt(values[1]));
		
		return true;
	}

	/**
	 * tableID = 9
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstReDocSpk (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {
		pst.setInt(1, Integer.parseInt(values[0]));
		pst.setInt(2, Integer.parseInt(values[1]));
		
		return true;
	}
	
	/**
	 * Re_org_lem, tableID = 10
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstReTagLem(PreparedStatement pst, String[] values)
			throws NumberFormatException, SQLException {

		pst.setLong(1, Long.parseLong(values[0]));
		pst.setLong(2, Long.parseLong(values[1]));

		return true;
	}
	
	/**
	 * Update column in Context, tableID = 11
	 * @param pst
	 * @param values
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private boolean fnPstUpdateContxWeight (PreparedStatement pst, String[] values) 
			throws NumberFormatException, SQLException {		
		pst.setInt(1, Integer.parseInt(values[1]));  // set weight = ?
		pst.setInt(2, Integer.parseInt(values[0])); // where id_context = ?
		
		return true;
	}

}
	
