package auto.framework.report.listeners;

import java.util.Map;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;

import auto.framework.managers.TestManager;

public class SuiteListener implements ISuiteListener {
	
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub

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
	        		
	        		//TelegramBot.sendMessage(TestManager.Preferences.getPreference("ExTestSuite").toString()+":"+"<a href=\""+outputFile.getCanonicalPath()+"\">Click to view report</a>" );	            
	        }
		}
		
	}
	
}