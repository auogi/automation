package auto.framework.webelement;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	
	public static void takeScreenshot(WebElement element){
	        //highlight drop down element by red color
       // ((JavascriptExecutor)WebManager.getDriver()).executeScript("arguments[0].style.border='3px solid red'", element.toWebElement());
 
        // Take screen shot of whole web page
        File screenShot = ((TakesScreenshot) WebManager.getDriver()).getScreenshotAs(OutputType.FILE);
 
        // Calculate the width and height of the drop down element
        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
 
        // Create Rectangle
        Rectangle rect = new Rectangle(width + 300, height + 300);
 
        BufferedImage img = null;
        try {
			img = ImageIO.read(screenShot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        //Crop Image of partial web page which includes the "Month" drop down web element
        BufferedImage dest = img.getSubimage(p.getX()-150, p.getY()-150, rect.width, rect.height);
        
        final String base64String = imgToBase64String(dest, "png");
        
       	 ReportLog.attachScreenshot("screenshot", base64String);
	}
	
	private static String imgToBase64String(final RenderedImage img, final String formatName) {
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
	        return os.toString(StandardCharsets.ISO_8859_1.name());
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	}
	
	public static void open(String url) {
		
				WebManager.getDriver().navigate().to(url);
				WebManager.getDriver().manage().window().maximize();
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
