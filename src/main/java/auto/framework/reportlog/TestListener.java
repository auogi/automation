package auto.framework.reportlog;

import java.text.DateFormat;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import auto.framework.TestManager;
import auto.framework.drivers.DriverSetting;

public class TestListener  implements ITestListener {
	
	private DateFormat timestamp = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
	protected static DateFormat dateFormatTimeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");	
	protected static ReportLogConsolidator reportConsolidator = new ReportLogConsolidator();	
	
	@Override
    public void onTestStart(ITestResult result) {
	}

	@Override
	  public void onTestFailure(ITestResult tr) {
		Reporter.setCurrentTestResult(tr);
		//setStatus(tr, ITestResult.FAILURE);
		ReportLog.setTCSummaryDetails("Time Ended", timestamp.format(new Date()));				
		ReportLog.setTCSummaryDetails(
				"Time Elapsed", 
				String.format(
						"%.2f mins",
						(float)(Reporter.getCurrentTestResult().getEndMillis() - Reporter.getCurrentTestResult().getStartMillis()
								)/(60 * 1000)));
		
		Throwable error = tr.getThrowable();
		if(error instanceof SkipException) return;
		
	    if (error!=null) {
	    	ReportLogInstance.getInstance().setAttribute("status", ReportLog.Status.ERROR);
	    	ReportLog.failed(error.getLocalizedMessage());
	    }
	  }
	
	
	@Override
	public void onTestSkipped(ITestResult tr) {
		Reporter.setCurrentTestResult(tr);
	}
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		Reporter.setCurrentTestResult(tr);			
		ReportLog.setTCSummaryDetails("Time Ended", timestamp.format(new Date()));				
		ReportLog.setTCSummaryDetails(
				"Time Elapsed", 
				String.format(
						"%.2f mins",
						(float)(Reporter.getCurrentTestResult().getEndMillis() - Reporter.getCurrentTestResult().getStartMillis()
								)/(60 * 1000)));
		try{
			
	    	ReportLogInstance.getInstance().setAttribute("status", ReportLog.Status.PASSED);
			//setStatus(tr, ITestResult.SUCCESS);
		} catch(AssertionError error){
	    	ReportLogInstance.getInstance().setAttribute("status", ReportLog.Status.FAILED);
			tr.setThrowable(error);
			tr.setStatus(ITestResult.FAILURE);
			ReportLog.failed("An unexpected error occured"+error.getLocalizedMessage());
			Reporter.setCurrentTestResult(tr);
		} finally {
			
		}
		
		ReportLogInstance.getInstance().setAttribute("status", ReportLog.Status.PASSED);
	  }
	
	@Override
	public void onStart(ITestContext context) {
		  	Boolean defaultTest =  context.getCurrentXmlTest().getName().equals("Default test");
			Boolean runAsMethod = defaultTest &&  context.getCurrentXmlTest().toXml("").contains("<methods>");		  			
			String browserName = TestManager.Preferences.getPreference("browser");
			
			if(Boolean.valueOf(TestManager.Preferences.getPreference("turnOffSelenium"))){									
				browserName = "";
			} else {					
				if(defaultTest){														
					//browserName = runAsMethod ? "firefox -debug" : runAs();//"firefox -debug";
					browserName = runAsMethod ? browserName : runAs();//"firefox -debug";
					System.setProperty("browser", browserName);
				} 
			}
		
			ReportLogInstance reporter = new ReportLogInstance();
			reporter.setTestName(context.getCurrentXmlTest().getName());
			ReportLogInstance.setInstance(reporter);
			
			if(browserName.equalsIgnoreCase("")){
				reporter.setBrowser("Not Applicable");
			}else if (browserName.equalsIgnoreCase("None")){
						reporter.setBrowser("Not Applicable");						
			}
			else if (browserName.equalsIgnoreCase("browserStack")){
				reporter.setBrowser("Browser Stack["+DriverSetting.BS_BROWSER.getValue() + " " +DriverSetting.BS_BROWSER_VERSION.getValue()+"]");
				
			} else{
				reporter.setBrowser(browserName);
			}
			
			if(TestManager.Preferences.getPreference("envConfig").contains("test")){
				reporter.setEnvironment("Test Environment");	
			}else if(TestManager.Preferences.getPreference("envConfig").contains("sta")){
				reporter.setEnvironment("Staging Environment");
			}else if(TestManager.Preferences.getPreference("envConfig").contains("prod")){
				reporter.setEnvironment("Production Environment");
			}
			else{
				reporter.setEnvironment(TestManager.Preferences.getPreference("envConfig"));
			}			
			reporter.setStartTime(timestamp.format(new Date()));
			reporter.setEndTime("N/A");			
			reportConsolidator.addReport(reporter);
	 }

	@Override
	public void onFinish(ITestContext context) {
		//ReportLogInstance.getInstance().assertAll();		
		reportConsolidator.save();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	private static String runAs() {
			  Object[] possibilities = {"firefox", "chrome", "ie", "browserStack","headless","none" };
			  return (String) JOptionPane.showInputDialog(
			                      null,
			                      "Run using..",
			                      "Select WebDriver",
			                      JOptionPane.PLAIN_MESSAGE,
			                      null,
			                      possibilities,
			                      "firefox");
	}	
	
}
