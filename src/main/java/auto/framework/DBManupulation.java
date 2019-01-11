package auto.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import auto.framework.reportlog.ReportLog;

public class DBManupulation {
	
	public static void insertRecordIntoDbUserTable(String sqlQuery, String strSQLServerName, String... whereValue) throws SQLException {
		//String... where
		//"(1,'mkyong','system', " + "to_date('"+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";
		try {
			sqlQuery = replaceValueForVariableInSQLInsert(sqlQuery, whereValue);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement statement = null;
		Connection objSQLConnection = null;
		
		try {
			objSQLConnection = establishConnectionForSQLServer(strSQLServerName);
			statement = objSQLConnection.createStatement();

			// execute insert SQL stetement
			statement.executeUpdate(sqlQuery);

			ReportLog.passed("Record is inserted into SignedUpClients Database!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ReportLog.failed("Record is inserted into SignedUpClients Database!");

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (objSQLConnection != null) {
				objSQLConnection.close();
			}

		}
		
	}

	public static String getDBValueFromSQLServer(String sqlQuery, String whereValue, String strSQLServerName) {
		String strResults = " ";
		try {
			String strQuery;
			Connection objSQLConnection = establishConnectionForSQLServer(strSQLServerName);
			strQuery = replaceValueForVariableInSQL(sqlQuery, whereValue);
			strResults = executeSQLDBQuery(objSQLConnection, strQuery);
			//ReportLog.addInfo("Db results for the query" + strQuery + " is " + strResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
		return strResults;
	}
	
	public static int getDBCount(String sqlQuery, String whereValue, String strServerName) {
		int strResults = 0;

		try {
			String strQuery;
			Connection objSQLConnection = establishConnection(strServerName);
			strQuery = replaceValueForVariableInSQL(sqlQuery, whereValue);
			strResults = executeSQLDBQueryGetCount(objSQLConnection, strQuery);
			ReportLog.addInfo("Db results for the query : " + strQuery + " is :" + strResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
			return 0;
		}
		return strResults;
	}

	public static String getDBValue(String sqlQuery, String whereValue, String strServerName) {
		String strResults = " ";

		try {
			String strQuery;
			Connection objSQLConnection = establishConnection(strServerName);
			strQuery = replaceValueForVariableInSQL(sqlQuery, whereValue);
			strResults = executeSQLDBQuery(objSQLConnection, strQuery);
			//ReportLog.addInfo("Db results for the query : " + strQuery + " is :" + strResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
		return strResults;
	}
	
	public static String getDBValueFromMarketingTable(String sqlQuery, String whereValue, String strServerName) {
		String strResults = " ";

		try {
			String strQuery;
			Connection objSQLConnection = establishConnection(strServerName);
			strQuery = replaceValueForVariableInSQL(sqlQuery, whereValue);
			strResults = executeSQLDBQuery(objSQLConnection, strQuery);
			ReportLog.addInfo("Db results for the query : " + strQuery + " is :" + strResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
		return strResults;
	}
	
	public static String getDBValueFromTrackingTable(String sqlQuery, String whereValue1, String whereValue2, String strServerName) {
		String rsResults= null;	
		try {			
			Connection objSQLConnection = establishConnection(strServerName);
			
			sqlQuery = sqlQuery.replaceAll("<temp_value1>", whereValue1);
			sqlQuery = sqlQuery.replaceAll("<temp_value2>", whereValue2);			
			rsResults = executeSQLDBQuery(objSQLConnection, sqlQuery);			
			ReportLog.addInfo("Db results for the query : " + sqlQuery + " is : " + rsResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
		
		if(rsResults.equalsIgnoreCase("")){
			ReportLog.addInfo("Information is missing in the Dyson Tracking Table: "+whereValue1);
		}
		
		return rsResults;
	}
	

	public static void updateDBSSNUsageTrackingTable(String sqlQuery, String whereValue1, String strServerName) {
		String rsResults= null;	
		try {			
			Connection objSQLConnection = establishConnection(strServerName);
			
			sqlQuery = sqlQuery.replaceAll("<ssn_value1>", whereValue1);
			executeSQLDBInsert(objSQLConnection, sqlQuery);			
			ReportLog.addInfo("Db results for the query : " + sqlQuery + " is : " + rsResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
	}

	public static Connection establishConnection(String strConnectionInfo) {
		Connection objConnection = null;
		String strDriverInfo, strUserName = null, strPassword = null;
		try {
			strDriverInfo = "com.mysql.jdbc.Driver";
			Class.forName(strDriverInfo);
			objConnection = DriverManager.getConnection(strConnectionInfo);
			return objConnection;
		} catch (Exception objException) {
			ReportLog.failed("Exception occurred while establishing connection for SQL" + objException.getMessage());
		}
		return objConnection;
	}
	
	public static String updateDBValueSSNCreationDate(String sqlQuery, String whereValue1, String strServerName) {
		String rsResults= null;	
		try {			
			Connection objSQLConnection = establishConnection(strServerName);
			
			sqlQuery = sqlQuery.replaceAll("<ssn_value>", whereValue1);				
			rsResults = executeSQLDBQuery(objSQLConnection, sqlQuery);			
			ReportLog.addInfo("Db results for the query : " + sqlQuery + " is : " + rsResults);
		} catch (Exception e) {
			ReportLog.failed("Exception thrown while executing DB query " + e.getMessage());
		}
		
		if(rsResults.equalsIgnoreCase("")){
			ReportLog.failed("Failed to update creation date of SSN Entry: "+whereValue1);
		}
		
		return rsResults;
	}


	public static Connection establishConnectionForSQLServer(String strConnectionInfo) {
		Connection objConnection = null;
		String strDriverInfo;
		try {
			// strDriverInfo = "oracle.jdbc.driver.OracleDriver";
			strDriverInfo = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(strDriverInfo);
			//Properties properties = new Properties();
			//properties.put("user", "ausori");
			//properties.put("password", "Chloe1219!");
			//objConnection = DriverManager.getConnection(strConnectionInfo,properties);
			objConnection = DriverManager.getConnection(strConnectionInfo);
			return objConnection;
		} catch (Exception objException) {
			ReportLog.failed(
					"Exception occurred while establishing connection for SQLServer" + objException.getMessage());
		}
		return objConnection;
	}

	public static String replaceValueForVariableInSQL(String strSQLQuery, String strReplace)
			throws UnknownHostException {
		if (StringUtils.containsIgnoreCase(strSQLQuery, "<temp_value>")) {
			String strRowNumber = null;
			strSQLQuery = strSQLQuery.replaceAll("<temp_value>", strReplace);
		}
		return strSQLQuery;
	}
	
	public static String replaceValueForVariableInSQLInsert(String strSQLQuery, String... strReplace) throws UnknownHostException {
		
		String tempValue = "<temp_value";
		
		for(int i=0; i < strReplace.length;i++)
		{
			strSQLQuery = strSQLQuery.replaceAll(tempValue+i+">", strReplace[i]);
		}	
	
		return strSQLQuery;
	}

	/**
	 ***************** Creation History *****************
	 * Method name 	: executeSQLDBQuery
	 * Description 	: 
	 * 	Method to close connection created  
	 * @param		: 
	 * @author 		: vebala
	 * created On	:			
	 */
	public static String executeSQLDBQuery(Connection objConnection, String strQuery) {
		Statement objSQLStatement;
		String strPlaceHolder = ",";
		String strResultContent = "";
		ResultSet objSQLResultSet;
		ResultSetMetaData objSQLRSMetaData;
		int intColumnCount;
		try {
			objSQLStatement = objConnection.createStatement();
			objSQLResultSet = objSQLStatement.executeQuery(strQuery);
			objSQLRSMetaData = objSQLResultSet.getMetaData();
			intColumnCount = objSQLRSMetaData.getColumnCount();
			while (objSQLResultSet.next()) {
				for (int intLpCnt = 1; intLpCnt <= intColumnCount; intLpCnt++) {
					strResultContent = strResultContent + strPlaceHolder + objSQLResultSet.getString(intLpCnt);
					strPlaceHolder = ",";
				}
				strResultContent = strResultContent + "~";
				strPlaceHolder = "";
			}
			if (!strResultContent.equalsIgnoreCase(""))
				strResultContent = strResultContent.substring(1, strResultContent.length() - 1);
			objSQLResultSet.close();
			closeConnection(objSQLResultSet, objSQLStatement, objConnection);
			return strResultContent;
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static int executeSQLDBQueryGetCount(Connection objConnection, String strQuery) {
		Statement objSQLStatement;
		ResultSet objSQLResultSet;

		try {
			objSQLStatement = objConnection.createStatement();
			objSQLResultSet = objSQLStatement.executeQuery(strQuery);
		
			int ctr =0;
			while (objSQLResultSet.next()) {
				ctr++;
			}

			return ctr;
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public static void executeSQLDBInsert(Connection objConnection, String strQuery){
		Statement objSQLStatement =null;
		try {
		
			objSQLStatement = objConnection.createStatement();

			// execute insert SQL stetement
			objSQLStatement.executeUpdate(strQuery);
		
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (objSQLStatement != null) {
				try {
					objSQLStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (objConnection != null) {
				try {
					objConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
	
	public static ResultSet executeSQLDBQueryRecord(Connection objConnection, String strQuery) {
		Statement objSQLStatement;
		ResultSet objSQLResultSet;
		try {
			objSQLStatement = objConnection.createStatement();
			objSQLResultSet = objSQLStatement.executeQuery(strQuery);
			objSQLResultSet.close();
			closeConnection(objSQLResultSet, objSQLStatement, objConnection);
			return objSQLResultSet;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 ***************** Creation History *****************
	 * Method name 	: closeConnection
	 * Description 	: 
	 * 	Method to close connection created  
	 * @param		: 
	 * @author 		: vebala
	 * created On	:			
	 */
	public static void closeConnection(ResultSet objSQLResultSet, Statement objSQLStatement,
			Connection objSQLConnection) {
		try {
			if (objSQLResultSet != null)
				objSQLResultSet.close();
		} catch (Exception e) {
		}
		try {
			if (objSQLStatement != null)
				objSQLStatement.close();
		} catch (Exception e) {
		}
		try {
			if (objSQLConnection != null)
				objSQLConnection.close();
		} catch (Exception e) {
		}
	}

	/**
	 ***************** Creation History *****************
	 * Method name 	: getPropertyValue
	 * Description 	: 
	 * 	To retrieve  sql query from the repository file created for different application depdnds on the app 
	 * @param		: application/team name, propery key for which we need to obtain query
	 * @author 		: vebala
	 * created On	:			
	 */
	public static String getPropertyValue(String app, String propertyname) {
		String testRoot;
		String Propertyvalue = null;
		String prop_file = app + "_sql_queries.properties";
		Properties properties = new Properties();
		testRoot = System.getProperty("user.dir") + File.separator + "src/test/resources/sql";
		try {
			FileInputStream in = new FileInputStream(testRoot + File.separator + prop_file);
			properties.load(in);
			in.close();
			Propertyvalue = properties.getProperty(propertyname);
		} catch (IOException e) {
			ReportLog.failed("Failed to read from " + testRoot + File.separator + prop_file + " file.");
		}
		return Propertyvalue;
	}
}
