package auto.framework.reportlog;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Capabilities;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import auto.framework.TestManager;
import auto.framework.WebManager;
import auto.framework.drivers.DriverSetting;
import auto.framework.jenkins.JsonParser;
import auto.framework.jenkins.TestJenkinsConfiguration;
import auto.framework.telegram.TelegramBot;
import auto.framework.web.WebControl;

public class SuiteListener implements ISuiteListener {
	
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
		if(Boolean.valueOf(TestManager.Preferences.getPreference("telegram.enabled","false"))){
			System.setProperty("ExTestSuite", suite.getName());
			String message = "Started: "+suite.getName();			
			try {
				TelegramBot.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}


	@Override
	public void onFinish(ISuite suite) {
		
		if(Boolean.valueOf(TestManager.Preferences.getPreference("telegram.enabled","false"))){
			Map<String, ISuiteResult> tmpResults = suite.getResults();
			int failedCount = 0;
			int passedCount = 0;
			//[TestListenerAdapter passed=3 failed=1 skipped=0]
			
			System.out.println("FAILED: "+MethodListener.failedCount);
			System.out.println("PASSED: "+MethodListener.passCount);
			
			/*for (String tests : tmpResults.keySet()) 
			{
				
				ISuiteResult suiteResult = tmpResults.get(tests);
				Collection<ITestNGMethod> failedTests = suiteResult.getTestContext().getFailedTests().getAllMethods();
				Collection<ITestNGMethod> passTests = suiteResult.getTestContext().getPassedTests().getAllMethods();
				Iterator<ITestNGMethod> failedIterator = failedTests.iterator();
				Iterator<ITestNGMethod> passIterator = passTests.iterator();
				
				while (failedIterator.hasNext()) {
					failedCount++;
					//System.out.println("value= " + iterator.next());
					failedIterator.next();
				}
				
				while (passIterator.hasNext()) {
					passedCount++;
					passIterator.next();
					//System.out.println("value= " + iterator.next());
				}
			}*/
			
			/*if(failedCount!=0){
				status = "FAILED";
			}*/
			 
			// TODO Auto-generated method stub
			   //TELEGRAM            
	        if(Boolean.valueOf(TestManager.Preferences.getPreference("telegram.enabled","false"))){	        	
	        	  String testSuiteName =TestManager.Preferences.getPreference("ExTestSuite","Default Test");
	        	  String jenkinsURL = TestManager.Preferences.getPreference("BUILD_URL","");
	        	  
	        	  jenkinsURL = jenkinsURL.replace("http://kursk.pgx.local:8080/", "https://kursk.pgx.local/");
	        	  String message="";
	        	  //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        	  //Date date = new Date();
	        		
	        		if(jenkinsURL.equalsIgnoreCase("")){
	        			message = "Completed: "+testSuiteName+"\\[passed="+MethodListener.passCount+" failed="+MethodListener.failedCount+"]" ;
	        		}else{
	        			message = "Completed: "+testSuiteName+"\\[passed="+MethodListener.passCount+" failed="+MethodListener.failedCount+"] - [Click to View Report]("+jenkinsURL+"artifact/src/test/resources/reports/consolidated/report.html)" ;
	        		}
	        		
	        		

	        		try {					
						TelegramBot.sendMessage(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}                	
	            	//TelegramBot.sendMessage(TestManager.Preferences.getPreference("ExTestSuite").toString()+":"+"<a href=\""+outputFile.getCanonicalPath()+"\">Click to view report</a>" );	            
	        }
		}
		
	}
	
}