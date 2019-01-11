package auto.framework.drivers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import auto.framework.TestManager;

public class AndroidDriver extends RemoteWebDriver {

	protected static URL getAndroidTestingUrl(String HOST){
		try {
			System.err.println("Connecting to http://"+HOST+"/wd/hub");
			return new URL("http://"+HOST+"/wd/hub");
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public AndroidDriver(String host, Capabilities desiredCapabilities,Capabilities requiredCapabilities) {
		super();
		//Proxy proxy = WebManager.getProxySettings(); If proxy is needed can be set here
	}
	
	public static AndroidDriver trial() {
		//set host
		String host = TestManager.Preferences.getPreference("mob.hostURL"); // will change depending on the location of the device
		//set capabilities
		DesiredCapabilities capabilities = getDesiredCapabilities();

		return new AndroidDriver(host, capabilities,null);
	}
	
	protected static DesiredCapabilities getDesiredCapabilities(){
		// C:\Users\augusto.p.soriano\workspace\Framework\src\main\java\auto\framework\mobileapps
		File appDir = new File("./src/test/resources/mobileapps");
		File app = new File(appDir, TestManager.Preferences.getPreference("ios.app")); //"com.whatsapp.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", TestManager.Preferences.getPreference("ios.device"));
		capabilities.setCapability("deviceName", TestManager.Preferences.getPreference("ios.deviceName"));
		capabilities.setCapability("platformName", TestManager.Preferences.getPreference("ios.platform"));
		capabilities.setCapability("platformVersion", TestManager.Preferences.getPreference("ios.platformVersion"));
		capabilities.setCapability("fullReset","false");
		capabilities.setCapability("noReset","true");
		capabilities.setCapability("app", app.getAbsolutePath());
	    return capabilities;
	}
	
}

