package auto.framework;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.openqa.selenium.Capabilities;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


//@Listeners({ReportLog.Listener.class})
public class TestBase extends TestListenerAdapter{ 

	  public static class Regex {
		  
		  public static String findMatch(String string, String pattern){
				Matcher matcher = Pattern.compile(pattern).matcher(string);
				if (matcher.find()) return matcher.group(1);
				return null;
		  }
		  
		  public static Boolean matches(String string, String pattern){
				Matcher matcher = Pattern.compile(pattern).matcher(string);
				return matcher.matches();
		  }
		  
	  }
	  
	  @BeforeTest
	  public void startTest(ITestContext context) throws IOException {
		  
		  String executePref = TestManager.Preferences.getPreference("execute","true");
			Boolean executeScript = Boolean.valueOf(executePref);
			if(!executeScript && executePref!=null ) {
				String var = Regex.findMatch(executePref, "(.*?)(?=\\[)");
				String label = Regex.findMatch(executePref, "(?<=\\[)(.*?)(?=\\])");
				//System.out.println(var+" : "+label);
				if(var!=null && label!=null){
					String varValue = TestManager.Preferences.getPreference(var,"");
					if(Regex.matches(varValue, "^(?<=\\\")(.*?)(?=\\\")$")){
						varValue = varValue.substring(1, varValue.length());
					}
					String[] varValues = varValue.split(",");
					executeScript = Arrays.asList(varValues).contains(label);
					
				}
			}
			if(!executeScript){
				System.out.println("Test Skipped: "+context.getCurrentXmlTest().getName());
				throw new SkipException ("Skipping Test");	
			}
			  
			String runWebdriver = TestManager.Preferences.getPreference("runWebdriver","true");
			
			if(!runWebdriver.equalsIgnoreCase("false")) {
			
				Boolean defaultSuite = context.getCurrentXmlTest().getSuite().getName().equals("Default suite");
				Boolean defaultTest = defaultSuite && context.getCurrentXmlTest().getName().equals("Default test");
				Boolean runAsMethod = defaultTest && context.getCurrentXmlTest().toXml("").contains("<methods>");
		
				String browserName = TestManager.Preferences.getPreference("browser");
				if(defaultTest){
					System.err.println( "Test Run mode" );
					//browserName = runAsMethod ? "firefox -debug" : runAs();//"firefox -debug";
					browserName = runAsMethod ? "chrome" : runAs();//"firefox -debug";
					WebManager.startDriver(browserName,false);
				} else {
					WebManager.startDriver(browserName,true);
				}
				
				//WebDriver driver = WebManager.getDriver();
				Capabilities caps = WebManager.getCapabilities();//((RemoteWebDriver) driver).getCapabilities();
				System.out.println("Running: [" + WebManager.getProxySettings().getProxyType() +"] "+ caps.getBrowserName());

			}
	  }

	@AfterTest
	  public static void endTest(ITestContext context) throws InterruptedException {
		
		boolean executeScript;
		Boolean defaultSuite = context.getCurrentXmlTest().getSuite().getName().equals("Default suite");
		Boolean defaultTest = defaultSuite && context.getCurrentXmlTest().getName().equals("Default test");
		
		if(defaultTest){
			executeScript = Boolean.valueOf(TestManager.Preferences.getPreference("execute","true"));
			
			if(executeScript) {
				WebManager.endDriver();
			} 
		} else {
			WebManager.endDriver();
		}						
		
	  }
	  
	  private static String runAs() {
		  Object[] possibilities = {"firefox -debug", "firefox", "chrome", "ie", "browserStack" };
		  return (String) JOptionPane.showInputDialog(
		                      null,
		                      "Run using..",
		                      "Select WebDriver",
		                      JOptionPane.PLAIN_MESSAGE,
		                      null,
		                      possibilities,
		                      "firefox -debug");
	    }

}