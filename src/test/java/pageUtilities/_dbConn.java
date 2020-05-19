package pageUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class _dbConn {
	
	private static _dbConn instance;
	private static final Object lock = new Object();
	public static Connection con;
	public static Statement st;
	
	public static _dbConn getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new _dbConn();
				instance.openDbConn();
			}
		}
		return instance;
	}
	
	private void openDbConn() {
		String DB_URL ="jdbc:sqlserver://35.167.107.202:3421;databaseName=StorTrack_Optimize_Staging";
		
		try {
			//Class.forName("net.sourceforge.jtds.jdbc.Driver");	
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(DB_URL, "surendravasan364", "WD2wmu8b");
			
			//Creating statement object
			} catch (Exception e) {
				System.out.println("OpenDbConn: Database Connection Failed "+ e);
			}
		}
	
	public static HashMap<String, String> getColumnValues(String query) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		
		try {
		st = con.createStatement();
		String selectquery = query;
		//Executing the SQL Query and store the results in ResultSet
		ResultSet rs = st.executeQuery(selectquery);
		//While loop to iterate through all data and print results
		int count = rs.getMetaData().getColumnCount();
		while(rs.next()) {

			for(int i=1; i<=count; i++) {
				map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
			}
		} 
		} catch (Exception e) {
			System.out.println("getColumnValues: Database Connection Failed "+e);
			
			
		}
		return map;
	}
	
	public static ArrayList<Integer> getRowValues(String query) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		
		try {
		st = con.createStatement();
		String selectquery = query;
		//Executing the SQL Query and store the results in ResultSet
		ResultSet rs = st.executeQuery(selectquery);
		//While loop to iterate through all data and print results
		while(rs.next()) {
			int result;

			result = rs.getInt(1); 
	        list.add(result);
			}
		} catch (Exception e) {
			System.out.println("getRowValues: Database Connection Failed "+e);
			
			
		}
		
		return list;
	}
	
	public static HashMap<String, Integer> getRowColumn(String query) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		try {
		st = con.createStatement();
		String selectquery = query;
		//Executing the SQL Query and store the results in ResultSet
		ResultSet rs = st.executeQuery(selectquery);
		//While loop to iterate through all data and print results
		while(rs.next()) {
				map.put(rs.getString(1), rs.getInt(2));
		} 
		} catch (Exception e) {
			System.out.println("getColumnValues: Database Connection Failed "+e);
		}
		return map;
		
		
	}
	
	public static TreeSet<Integer> getValue(String query) {
		TreeSet<Integer> ts = new TreeSet<Integer>();
		try {
			st = con.createStatement();
			String selectquery = query;
		
			//Executing the SQL Query and store the results in ResultSet
			ResultSet rs = st.executeQuery(selectquery);
			//While loop to iterate through all data and print results
			while(rs.next()) {
				ts.add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println("getSingleValue: Database Connection Failed "+e);
		}
		return ts;
	}
}
