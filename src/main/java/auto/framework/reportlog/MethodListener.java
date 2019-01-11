package auto.framework.reportlog;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Capabilities;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import com.codoid.products.exception.FilloException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import auto.framework.TestManager;
import auto.framework.WebManager;
import auto.framework.drivers.DriverSetting;
import auto.framework.jenkins.JsonParser;
import auto.framework.jenkins.TestJenkinsConfiguration;
import auto.framework.web.WebControl;

public class MethodListener implements IInvokedMethodListener {
	
	public static int passCount=0;
	public static int failedCount=0;
	
	@Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		TestJenkinsConfiguration readValue = null;
		String TestSuiteName = "";
		String testSuite = "";
    	String testCases = "";
		String browserName = TestManager.Preferences.getPreference("browser");
    	Boolean executeScript = true;	
    	
		Reporter.setCurrentTestResult(testResult);
        if (!method.isTestMethod()) {
            return;
        }
                       
	    	Boolean jenkinsRun = Boolean.valueOf(TestManager.Preferences.getPreference("jenkins",""));
	   
	    	if(jenkinsRun){
	        	
	        	String triggerType = TestManager.Preferences.getPreference("BUILD_CAUSE","");        
	            if(triggerType.equals("MANUALTRIGGER")){
		      	    String jSonValue =  TestManager.Preferences.getPreference("jenkins.Json","");				    
				    if(!jSonValue.equalsIgnoreCase("")){		    		
					    	jSonValue = JsonParser.parseToValidJson(jSonValue);
					    	//System.out.println("I GOT A JSON FILE!!!");
					    	ObjectMapper mapper = new ObjectMapper();
			
							try {
								readValue = mapper.readValue(jSonValue, TestJenkinsConfiguration.class);
								
								if(browserName.equalsIgnoreCase("browserStack")){
									if(DriverSetting.BS_DEVICE.getValue() == null){
										System.setProperty("browserStack.browser", readValue.getConfiguration().getBrowserType().toString());
										System.setProperty("browserStack.browserVersion", readValue.getConfiguration().getBrowserVersion().toString());
										System.setProperty("browserStack.os", readValue.getConfiguration().getOSType().toString());
										System.setProperty("browserStack.osVersion", readValue.getConfiguration().getOSVersion().toString());
									}else{
										System.setProperty("browserStack.device", readValue.getConfiguration().getBrowserType().toString());																
										System.setProperty("browserStack.osVersion", readValue.getConfiguration().getOSVersion().toString());
									}
									
								}
								
								
								//System.out.println("I WAS ABLE TO PARSE THE CONFIGURATION!!!");
							} catch (JsonParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (JsonMappingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						    
										 
						 	
		  				    //GET THE CURRENT TEST SUITE
				    		TestSuiteName = method.getTestResult().getTestContext().getCurrentXmlTest().getName();
				    		//System.out.println("I GOT THE TEST SUITE NAME!!!");
					    	//VERIFY IF THIS TEST SUITE IS ON THE TEST SUITES THAT JENKINS SENT
					    	 if(!TestSuiteName.equalsIgnoreCase("")){
					    		 try {			
					    			 testSuite = readValue.getTestSuite().getAdditionalProperties().get(TestSuiteName.replaceAll("\\s","")).toString();
					    			 //System.out.println("THE TEST SUITE EXIST IN THE LIST OF TEST SUITE EXECUTION!!!");
					    		 }catch(NullPointerException e){
					    			executeScript = false;
					    		 }
					    		 
					    		 if(!testSuite.equalsIgnoreCase("")){
					    			Matcher m = Pattern.compile("\\[([^)]+)\\]").matcher(testSuite);
			     			     while(m.find()) {
			     			    	testCases = m.group(1);    
			     			     }
			     			     
			     			    // GET THE LIST OF TEST CASES IN THE TEST SUITE
			     			    testCases = testCases.replaceAll("\\s",""); //Remove white spaces
			     			   //System.out.println("I GOT THE LIST OF TEST CASES!!!");
			     			    //System.out.println("Test Case: "+testCases);
			     			    String[] varValues = testCases.split(",");
			     			   
			     			    //VERIFY IF THE TEST CASE(METHOD) IS ON THE LIST THAT JENKINS SENT
			     			    executeScript = Arrays.asList(varValues).contains(method.getTestMethod().getMethodName());
			     			   //System.out.println("I FOUND THE TEST CASE AND SET THE VALUE TO TRUE!!!");
		 			
					    		 }
					    	 }else{
					    		executeScript = false;
					    	 }	     				    	 
				    }else{
				    	executeScript = true;
				    }	
		         }	        
	        }
	    
	    if(!executeScript){	
			System.err.println("-------------------------------------------------------------");
			System.err.println("Skipping TestCase: "+method.getTestMethod().getMethodName());
			System.err.println("-------------------------------------------------------------");	     					
			throw new SkipException("Skipping the test case");
		}else {
			System.err.println("-------------------------------------------------------------");
	    	System.err.println("Initiating TestCase: "+method.getTestMethod().getMethodName());
	    	System.err.println("-------------------------------------------------------------");
		}
			
		if(Boolean.valueOf(TestManager.Preferences.getPreference("turnOffSelenium"))){									
			System.err.println( "Selenium Off Mode" );
		} else {							
			
				Capabilities caps =null;
				System.err.println( "Test Run Mode" );
				
				try {
					WebManager.startDriver(browserName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!browserName.equalsIgnoreCase("headless")){
					caps = WebManager.getCapabilities();	
				};
				
				//System.out.println("I WAS ABLE TO GET THE CAPABILITIES");

				if(browserName.equalsIgnoreCase("browserStack")){
					/*System.out.println("I WAS ABLE TO CHECK THE BROWSERNAME AND ITS BROWSERSTACK");
					System.out.println(DriverSetting.BS_OS.getValue());
					System.out.println(DriverSetting.BS_OS_VERSION.getValue());
					System.out.println(DriverSetting.BS_BROWSER.getValue());
					System.out.println(DriverSetting.BS_BROWSER_VERSION.getValue());*/
					
					if(DriverSetting.BS_DEVICE.getValue() == null ){
						System.out.println("Running: [BrowserStack] " + DriverSetting.BS_OS.getValue() +" "+ 
								DriverSetting.BS_OS_VERSION.getValue() + " " +
								DriverSetting.BS_BROWSER.getValue() + " " +
								DriverSetting.BS_BROWSER_VERSION.getValue());
					}else{
						System.out.println("Running: [BrowserStack] " + 
								DriverSetting.BS_DEVICE.getValue() +" "+ 
								DriverSetting.BS_OS_VERSION.getValue()); 								
					}					
				}else{
					if(!browserName.equalsIgnoreCase("headless")){
						System.out.println("Running: [SYSTEM] "+ caps.getBrowserName());
					}
				}
		} 				
		
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    	 if(!method.isTestMethod()) {
	            return;
	        }	
    	 
    	 if(testResult.getStatus()==ITestResult.SKIP){
    		 return;
    	 }else if(testResult.getStatus()==ITestResult.FAILURE){
    		 failedCount++;
    		 if(!DriverSetting.BROWSER.getValue().equals("headless")){
	    		 WebControl.takeScreenshot();    			 
    		 }
    	 } else{
    		 passCount++;
    	 }
    		 
    	 
    	 try {	    		    		 
    		 WebManager.endDriver();			    		 
    	 } catch (InterruptedException e) {e.printStackTrace();}	    	
    }
   
    
    
    public String getPreference(String property,Map<String, String> param, String defaultValue){

    	try {
			String parameterValue;
			if( (parameterValue=param.get(property))!=null ){									
				return parameterValue;
			} 
		} catch(NullPointerException e){}			
		return defaultValue;
	}
}