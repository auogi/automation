package auto.framework.drivers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import auto.framework.TestManager;
import auto.framework.UserAgentList;
import auto.framework.drivers.ChromeBrowserDriver.JarFileToLocal;

public class FirefoxBrowserDriver{
	
	public static WebDriver start(){
				
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile myprofile = new FirefoxProfile();
		//myprofile.setPreference(FirefoxProfile.PORT_PREFERENCE, 41176);
		FirefoxBinary binary = new FirefoxBinary();		
		String userAgent = TestManager.Preferences.getPreference("UserAgent");
		String firefoxVersion = TestManager.Preferences.getPreference("webdriver.firefox.version");
		String firefoxDriverPath="";
		File ffPath = null;
		System.setProperty("webdriver.firefox.useExisting", "true");
    	System.setProperty("webdriver.reap_profile", "false");		
    	//System.setProperty("webdriver.firefox.port", "41176");    	
    	FirefoxOptions options = new FirefoxOptions();
    	
    	if (userAgent!=null) {
    		myprofile.setPreference("general.useragent.override", UserAgentList.valueOf(userAgent).toString()); // here, the user-agent is 'Yahoo Slurp'
    	}
    	
    	if(DriverSettings.getProperty(DriverSetting.FIREFOX_BINARY)!=null){
			ffPath = new File(DriverSettings.getProperty(DriverSetting.FIREFOX_BINARY));
			if(ffPath.exists()){ 
				System.out.println("Loading custom driver..");
				binary = new FirefoxBinary(ffPath);							
				if(firefoxVersion!=null){
					if(Integer.parseInt(firefoxVersion)<48){
						DesiredCapabilities capabilities = DesiredCapabilities.firefox(); //50382,22837
						capabilities.setCapability("marionette", false);						
						options.addCapabilities(capabilities);
					}				
				}
			}
		}
    	
    	try {
			firefoxDriverPath = JarFileToLocal.copyTmp("/webdriver/geckodriver.exe").getCanonicalPath();
			System.setProperty("webdriver.gecko.driver",firefoxDriverPath);
			} catch (IOException e) {}
										
		options.setProfile(myprofile);	
		options.setBinary(binary);	   											    	    	       	  	
    	FirefoxDriver driver = new FirefoxDriver(options);    	    	
    	driver.setLogLevel(Level.OFF);    	    	    
    	driver.getCommandExecutor();
		return driver;
	}
}