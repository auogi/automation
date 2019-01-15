package auto.framework.managers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import auto.framework.drivers.ChromeBrowserDriver;
import auto.framework.drivers.DriverSettings;
import auto.framework.drivers.FirefoxBrowserDebugDriver;
import auto.framework.drivers.FirefoxBrowserDriver;


public class WebManager {

    private static ThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<WebDriver>();
    private static Boolean debugMode = false;
    
    public static WebDriver getDriver() {    	
        return webDriver.get();
    }   
    
    public static HtmlUnitDriver  getHtmlUnitDriver() {
    	WebDriver driver = WebManager.getDriver();
    
    	return (HtmlUnitDriver) driver;
    }
    
    public static RemoteWebDriver getRemoteDriver() {
    	WebDriver driver = WebManager.getDriver();
    
    	return (RemoteWebDriver) driver;
    }
    
 
    
       
    public static void endDriver() throws InterruptedException{    	
    	if(!debugMode){
	    	WebDriver driver = WebManager.getDriver();
	    	if(driver!=null)
				driver.quit();    		    	
    	}
    }
    
    public static Capabilities getCapabilities(){
		return getRemoteDriver().getCapabilities();
    }
    
    public static SessionId getSessionId(){
		return getRemoteDriver().getSessionId();
    }
    
    private static String getBrowserName(WebDriver driver){
    	Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
    	return browserName;
    }
    
    
    
    public static String getBrowserName() {
    	return getBrowserName(WebManager.getDriver());
    }
    
    public static WebDriver startDriver() throws IOException{
    	return startDriver(getBrowserName());
    }
    
    public static WebDriver startDriver(String browserName) throws IOException {
    	return startDriver(browserName, true);
    }
 
    
    /** 
     * Experimental. Do not use.
     */
    protected static WebDriver startLocalDriver(String browserName) throws IOException {
    	return startDriver(browserName, true);
    }
    
    protected static WebDriver startDriver(String browserName, Boolean runLocal) throws IOException {
    	
    	if(browserName==null) {
    		browserName="chrome";
    	}

    	String forceBrowser = TestManager.Preferences.getPreference("browser.override","false");
    	if(!forceBrowser.trim().equalsIgnoreCase("false")){
    		browserName = forceBrowser;
    	}
    	

    	System.err.println("Create Driver: "+browserName);
    	System.setProperty("webdriver.reap_profile", "true");
    	
    	WebDriver driver = null;
    	
    	try {    
    			DriverSettings.BrowserInfo browserInfo = new DriverSettings.BrowserInfo(browserName);
    			debugMode = browserInfo.debugMode;
    			switch(browserInfo.browserType.trim().toLowerCase()){
		    		case "chrome":
		    			driver = ChromeBrowserDriver.start();
		    			break;
		    		case "firefox":
		    			driver = debugMode? FirefoxBrowserDebugDriver.start() : FirefoxBrowserDriver.start();
		    			break;		    			
		    		default:
		    			driver = FirefoxBrowserDriver.start();
		    			break;    		
    		}
    	} catch(Error error){
    		System.out.println("Error: "+error.getMessage());
    		throw error;
    	}

     	Timeouts timeouts = driver.manage().timeouts();
    	Window window = driver.manage().window();
    	try {
	    	timeouts.pageLoadTimeout(Preferences.pageLoadTimeOut(), TimeUnit.SECONDS);
	    	timeouts.implicitlyWait(Preferences.implicitlyWait(), TimeUnit.SECONDS);
	    	timeouts.setScriptTimeout(Preferences.setScriptTimeout(), TimeUnit.SECONDS);
    	} catch(WebDriverException e){}
    	try {
    		driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL,"0")); //zoom to 100%
    	} catch(WebDriverException e){}
    	try {
    		if(Preferences.maximize()) window.maximize();
    	} catch(WebDriverException e){}
    	
    	return setDriver(driver);
    }
    
    public static WebDriver setDriver(String browserName, String profile) throws IOException {
    	return startDriver(browserName);
    }
 
    public static WebDriver setDriver(WebDriver driver) {
    	try {
    		assert(driver!=null);
    		webDriver.set(driver);
   
    	} catch(Error error){
    		throw new Error("Driver is null");
    	}
        
        return getDriver();
    }
    
    protected static class Preferences {
    	
    	public static long pageLoadTimeOut(){
    		return Long.valueOf(
    			TestManager.Preferences.getPreference("webdriver.timeouts.pageLoadTimeOut","-1")
    		);
    	}
    	
    	public static long implicitlyWait(){
    		return Long.valueOf(
    			TestManager.Preferences.getPreference("webdriver.timeouts.implicitlyWait","2")
    		);
    	}
    	
    	public static long setScriptTimeout(){
    		return Long.valueOf(
    			TestManager.Preferences.getPreference("webdriver.timeouts.setScriptTimeout","-1")
    		);
    	}
    	
    	
    	
    	public static boolean maximize(){
    		return Boolean.valueOf(
    			TestManager.Preferences.getPreference("webdriver.window.maximize","false")
    		);
    	}
    	
    }
    
    protected static class JarFileToLocal {
    	
    	public static File copyTmp(String jarPath) throws IOException {
    		//String fileName = new File(jarPath).getName();
    		File tmpDir = FileUtils.getTempDirectory();
    		File file = new File(tmpDir,jarPath);
    		if(file.exists()) return file;
    		
    		File fileDir = file.getParentFile();
    		
    		if(fileDir.exists()||fileDir.mkdir()){
    			InputStream stream = JarFileToLocal.class.getResourceAsStream(jarPath);
    			if (stream == null) {
    		        //warning
    		    }
    		    OutputStream resStreamOut = null;
    		    int readBytes;
    		    byte[] buffer = new byte[4096];
    		    try {
    		        resStreamOut = new FileOutputStream(file);
    		        while ((readBytes = stream.read(buffer)) > 0) {
    		            resStreamOut.write(buffer, 0, readBytes);
    		        }
    		    } catch (IOException e1) {
    		        e1.printStackTrace();
    		    } finally {
    		        stream.close();
    		        if(resStreamOut!=null) resStreamOut.close();
    		    }
    		}
    		return file;
    	}
    	
    }    
}



	
