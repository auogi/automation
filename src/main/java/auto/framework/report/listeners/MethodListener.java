package auto.framework.report.listeners;

import java.io.IOException;
import java.util.Map;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import auto.framework.drivers.DriverSetting;
import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;
import auto.framework.webelement.WebControl;

public class MethodListener implements IInvokedMethodListener {
	
	public static int passCount=0;
	public static int failedCount=0;
	
	@Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    	Reporter.setCurrentTestResult(testResult);
        if (!method.isTestMethod()) {
            return;
        }                   
	    
		System.err.println( "Test Run Mode" );		
		try {
			WebManager.startDriver(TestManager.Preferences.getPreference("browser"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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