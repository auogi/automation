package auto.framework.webelement;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;
import auto.framework.report.reporter.ReportLog;

public class WebControl {
	

	
	
	private static void takeScreenshot64(){
		RemoteWebDriver driver = WebManager.getRemoteDriver();		       
        if(!(driver instanceof TakesScreenshot)) {
        	driver = (RemoteWebDriver) new Augmenter().augment(driver);
        }
        try {
        	 TakesScreenshot scrShot =((TakesScreenshot)driver);
        	 String screenshot64 = scrShot.getScreenshotAs(OutputType.BASE64);
        	 ReportLog.attachScreenshot("screenshot", screenshot64);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void takeScreenshot() {
		
		if( Boolean.valueOf(TestManager.Preferences.getPreference("reporter.screenshots.embed", "false")) ){
			takeScreenshot64();
			return;
		}
		
		RemoteWebDriver driver = WebManager.getRemoteDriver();
	
	    if(!(driver instanceof TakesScreenshot)) {
        	driver = (RemoteWebDriver) new Augmenter().augment(driver);
        }
        try {
        	if (!(Boolean) driver.getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
        		return;
        	}
            	
        	File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        	DateFormat dateFormatTimeStamp = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss ");
        	String timestamp = dateFormatTimeStamp.format(new Date());
        	String filename = timestamp+ FilenameUtils.getName(screenshot.getCanonicalPath());
        	File tmpDir = new File("./src/test/resources/screenshots");
			File sessionDir = new File(tmpDir, ((RemoteWebDriver) driver).getSessionId().toString() );
			if(sessionDir.exists() || sessionDir.mkdir()){
				tmpDir = sessionDir;
			}
			File tmpFile = new File(tmpDir,filename);
        	
        	FileUtils.copyFile(screenshot, tmpFile);
        	
        	ReportLog.attachFile("screenshot", tmpFile.toURI().toURL().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void open(String url) {
		
				WebManager.getDriver().navigate().to(url);
				ReportLog.logEvent(true, "Navigate to " + url);
	
	}
	
	public static String getCurrentUrl(){
		WebDriver driver = WebManager.getDriver();
		return driver.getCurrentUrl();
	}
	
	
	public static void delay(int seconds){
		
		long miliSec = seconds*1000;
		
		try {
			Thread.sleep(miliSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
