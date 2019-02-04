package auto.framework.report.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import auto.framework.managers.TestManager;
import auto.framework.report.reporter.ReportLog;
import auto.framework.report.reporter.ReportLogConsolidator;
import auto.framework.report.reporter.ReportLogInstance;

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
			
	/*		if(Boolean.valueOf(TestManager.Preferences.getPreference("turnOffSelenium"))){									
				browserName = "";
			} else {					
				if(defaultTest){														
					browserName = runAsMethod ? browserName : runAs();//"firefox -debug";
					System.setProperty("browser", browserName);
				} 
			}
		
	*/		ReportLogInstance reporter = new ReportLogInstance();
			reporter.setTestName(context.getCurrentXmlTest().getName());
			ReportLogInstance.setInstance(reporter);
			
			reporter.setBrowser(browserName);
			reporter.setStartTime(timestamp.format(new Date()));
			reporter.setEndTime("N/A");			
			reportConsolidator.addReport(reporter);
	 }

	@Override
	public void onFinish(ITestContext context) {
		reportConsolidator.save();
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

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}	
	
}
