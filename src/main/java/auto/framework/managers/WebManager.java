package auto.framework.managers;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import auto.framework.drivers.Drivers;


public class WebManager {

    private static ThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<WebDriver>();
    private static Boolean debugMode = false;
    
    public static WebDriver getDriver() {    	
        return webDriver.get();
    }   
    
       
    public static void endDriver() {    	
    	if(!debugMode){
	    	WebDriver driver = WebManager.getDriver();
	    	if(driver!=null)
				driver.quit();    		    	
    	}
    }
        
    private static String getBrowserName(WebDriver driver){
    	Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
    	return browserName;
    }
    
    public static String getBrowserName() {
    	return getBrowserName(WebManager.getDriver());
    }
    
    public static WebDriver startDriver() {
    	return startDriver(getBrowserName());
    }
    
    public static WebDriver startDriver(String browserName) {
    	return startDriver(browserName, true);
    }
    
    protected static WebDriver startDriver(String browserName, Boolean runLocal) {
    	
    	System.err.println("Create Driver: "+browserName);	
    	WebDriver driver = null;
    	
		switch(browserName){
    		case "chrome":
    			driver = Drivers.chrome.start();
    			break;
    		case "firefox":
    			driver = Drivers.firefox.start();
    			break;		    			
    		case "windows":
    			driver = Drivers.windowsApp.start();
    			break;		    			
    		default:
    			throw new Error("No WebDriver was set. Please check the config file and assign a driver for the run");   		
	}

    	setDefault(driver);
    	return setDriver(driver);
    }
    
    private static WebDriver setDefault(WebDriver driver) {
     	Timeouts timeouts = driver.manage().timeouts();
    	Window window = driver.manage().window();
    	try {
	    	timeouts.pageLoadTimeout(Preferences.pageLoadTimeOut(), TimeUnit.SECONDS);
	    	timeouts.implicitlyWait(Preferences.implicitlyWait(), TimeUnit.SECONDS);
	    	timeouts.setScriptTimeout(Preferences.setScriptTimeout(), TimeUnit.SECONDS);
			driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL,"0")); //zoom to 100%
    		if(Preferences.maximize()) window.maximize();
    	} catch(WebDriverException e){
    		System.err.println("Error setting configured properties. Test will proceed with Selenium default properties");
    	}
    	
    	return driver;
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
    		return Long.valueOf(TestManager.Preferences.getPreference("webdriver.timeouts.pageLoadTimeOut","-1")
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
}



	
