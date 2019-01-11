package auto.framework;

import java.util.Map;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;

public class InvokedMethodListener implements IInvokedMethodListener {
	
		static Map<String, String> param;
		
	    @Override
	    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {   
	        if (! method.isTestMethod()) {
	            return;
	        }
	        
	        System.out.println(method.getTestMethod().getXmlTest().getAllParameters().toString());
	        	        
	        for (XmlClass test :  method.getTestResult().getTestContext().getCurrentXmlTest().getXmlClasses()) {	        	
	        	 for (XmlInclude method2 : test.getIncludedMethods()) {
	        		 if(method2.getName().equalsIgnoreCase(method.getTestMethod().getMethodName())){
	        		    param = method2.getAllParameters();
	        		    System.out.println(param.toString());
	     				Boolean executeScript = getPreference("execute",true);
	     				if(!executeScript){				
	     					throw new SkipException("Skipping the test case");
	     				}
	        			 break;
	        		 }	        	 
	        	 	}
	        	 }
	    }

	    @Override
	    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
	    }
	    
	    
	    public static Boolean getPreference(String property, Boolean defaultValue){

			try {
				String parameterValue;
				if( (parameterValue=param.get(property))!=null ){									
					return Boolean.valueOf(parameterValue);
				} 
			} catch(NullPointerException e){}
			

			return defaultValue;
		}
}
