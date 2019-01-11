package auto.framework.drivers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import auto.framework.TestManager;
import auto.framework.UserAgentList;
import auto.framework.WebManager;
import auto.framework.WebManager.WinRegistry;

public class IEBrowserDriver extends RemoteWebDriver implements DriverReportListener {

	/*@Override
	public void setTestStatus(ITestResult tr, int status) {
		// TODO Auto-generated method stub
		
	}*/
	
	static ProfilesIni profile = new ProfilesIni();
	static FirefoxProfile myprofile = profile.getProfile("default");
	static DesiredCapabilities capabilities;
	static FirefoxBinary binary;
	
	public IEBrowserDriver(){	
		System.setProperty("webdriver.firefox.useExisting", "false");
    	System.setProperty("webdriver.reap_profile", "false");
    	String userAgent= TestManager.Preferences.getPreference("UserAgent");
    	
    	profile = new ProfilesIni();
    	myprofile = profile.getProfile("default");
    	
    	if (userAgent!=null) {
    		myprofile.setPreference("general.useragent.override", UserAgentList.valueOf(userAgent).toString()); // here, the user-agent is 'Yahoo Slurp'
    	}
    	
    	File ffPath = new File(DriverSettings.getProperty(DriverSetting.FIREFOX_BINARY, "C:\\Program Files (x86)\\Mozilla Firefox36\\firefox.exe"));
    	
    	if(ffPath.exists()){//test
        	System.out.println("Loading custom driver..");
    		binary = new FirefoxBinary(ffPath);
    	}else{
    		binary = new FirefoxBinary();
    	}
    	capabilities = DesiredCapabilities.firefox();
	}
	
	public static WebDriver start() throws IOException{
		try {
    		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
    		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

    		String realArch = arch.endsWith("64")
    		                  || wow64Arch != null && wow64Arch.endsWith("64")
    		                      ? "64" : "32";
    		
    		String ieDriverPath;
    	
    		switch(realArch){
    		case "64":
    			ieDriverPath = JarFileToLocal.copyTmp("/webdriver/IEDriverServer_64.exe").getCanonicalPath();
    			break;
    		case "32":
    		default:
    			ieDriverPath = JarFileToLocal.copyTmp("/webdriver/IEDriverServer_32.exe").getCanonicalPath();
    			break;
    		}
    		
    		//String ieDriverPath = JarFileToLocal.copyTmp("/webdriver/IEDriverServer.exe").getCanonicalPath();//WebManager.class.getClass().getResource("/webdriver/IEDriverServer.exe").getFile().replace("%20", " ");
    		System.setProperty("webdriver.ie.driver",ieDriverPath);
    		System.out.println("IE Driver: " + ieDriverPath);
    	} catch(Error error){
    		throw new Error("IE Driver not found");
    	}
		
    	DesiredCapabilities capabilities = getIECapabilities();
    	
    	//capabilities.setCapability(InternetExplorerDriver.LOG_LEVEL, "FATAL");
    
    	//HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE
    	WinRegistry.add(WinRegistry.HKEY_CURRENT_USER, "software\\microsoft\\internet explorer\\main\\FeatureControl\\FEATURE_BFCACHE", "iexplore.exe", WinRegistry.REG_DWORD, 0);
    	//HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE
    	WinRegistry.add(WinRegistry.HKEY_CURRENT_USER, "software\\wow6432node\\microsoft\\internet explorer\\main\\FeatureControl\\FEATURE_BFCACHE", "iexplore.exe", WinRegistry.REG_DWORD, 0);
    	
    	Proxy proxy = WebManager.getProxySettings();
		
		capabilities.setCapability(CapabilityType.PROXY, proxy);
    	
		WebDriver driver;
		
    	try {
    		driver = new InternetExplorerDriver(capabilities);
    	} catch(Error e){
    		if(e.getMessage().contains("TabProcGrowth")){
    			System.err.println("Setting TabProcGrowth to 0");
    			//reg add "hkcu\software\microsoft\internet explorer\main" /v TabProcGrowth /t reg_dword /d 0 /f
    			WinRegistry.add(WinRegistry.HKEY_CURRENT_USER, "software\\microsoft\\internet explorer\\main", "TabProcGrowth", WinRegistry.REG_DWORD, 0);
    		}
    		driver = new InternetExplorerDriver(capabilities);
    	}
    	WinRegistry.add(WinRegistry.HKEY_CURRENT_USER, "software\\microsoft\\windows\\currentversion\\internet settings", "ProxyOverride", WinRegistry.REG_SZ, proxy.getNoProxy());
    	return driver;
	}
	
	private static DesiredCapabilities getIECapabilities(){
    	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
    	capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
    	capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); //test (orig=false)
    	capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false); //test
    	capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false); //test

    	capabilities.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
    	capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
    	capabilities.setCapability(InternetExplorerDriver.SILENT, true);
    	capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
    	return capabilities;
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
