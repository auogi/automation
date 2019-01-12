package auto.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Reporter;

import auto.framework.report.reporter.ReportLog;

public class TestManager  {

	public static void sleep(long millis){
		try{ Thread.sleep(millis); } 
		catch(InterruptedException e) {}
	}
	
	public static void sleep(long millis, int nanos){
		try{ Thread.sleep(millis, nanos); } 
		catch(InterruptedException e) {}
	}
	
	public static class Preferences {
		
		// envi > testng > config > default

		public static String getPreference(String property){
			return getPreference(property, null);
		}
		
		public static String getPreference(String property, String defaultValue){

			if( System.getProperties().containsKey( "env."+property ) ){
				return System.getProperty( "env."+property );
			}
			if( System.getProperties().containsKey(property) ){
				return System.getProperty( property );				
			}
			try {
				String parameterValue;
				if( (parameterValue=Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(property))!=null ){
					return parameterValue;
				} 
			} catch(NullPointerException e){}
			
			try {
				String parameterValue;
				
				if( (parameterValue=Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getAllParameters().get(property))!=null ){
					return parameterValue;
				} 
			} catch(NullPointerException e){}
			
			
/*			try {
				String parameterValue;
				if( (parameterValue=ReportLog.getCurrentSuite().getParameter(property))!=null ){
					return parameterValue;
				} 
			} catch(NullPointerException e){}*/
			String configValue;
			if( (configValue=readConfig(property))!=null ){
				return configValue;
			}
			if( System.getenv().containsKey( property ) ){
				return System.getenv( property );
			}
			return defaultValue;
		}
		
		private static String readConfig(String property) {
			String configPath = "./src/test/resources/config/defaults.properties";
			File configFile = new File( configPath );
			if(configFile.exists()){
				try {
					FileInputStream fileInput = new FileInputStream(configFile);
					Properties properties = new Properties();
					properties.load(fileInput);
					return properties.getProperty(property);
				} catch (Exception e) {
				}
			}
			return null;
		}
		
	}
	
	public static class EnvironmentPreferences {
		
		public static String getPreference(String property){
			return getPreference(property, null);
		}
		
		public static String getPreference(String property, String defaultValue){

			if( System.getProperties().containsKey(property) ){
				return System.getProperty( property );
			}
			try {
				String parameterValue;
				if( (parameterValue=Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(property))!=null ){
					return parameterValue;
				} 
			} catch(NullPointerException e){}
		/*	try {
				String parameterValue;
				if( (parameterValue=ReportLog.getCurrentSuite().getParameter(property))!=null ){
					return parameterValue;
				} 
			} catch(NullPointerException e){}
			*/
			String configValue;
			if((configValue=readConfig(property))!=null ){
				return configValue;
			}
			if(System.getenv().containsKey( property ) ){
				return System.getenv( property );
			}
			return defaultValue;
		}
		
		private static String readConfig(String property) {
			String configPath = "./src/test/resources/config/env/"+TestManager.Preferences.getPreference("envConfig");
			File configFile = new File( configPath );
			if(configFile.exists()){
				try {
					FileInputStream fileInput = new FileInputStream(configFile);
					Properties properties = new Properties();
					properties.load(fileInput);
					return properties.getProperty(property);
				} catch (Exception e) {
				}
			}
			return null;
		}
		
	}

}
