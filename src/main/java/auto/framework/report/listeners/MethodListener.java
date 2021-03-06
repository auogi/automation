package auto.framework.report.listeners;

import java.util.Map;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;

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
		WebManager.startDriver(TestManager.Preferences.getPreference("browser"));
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
    	 } else{
    		 passCount++;
    	 }
    		     	 
    	 WebManager.endDriver();	    	
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