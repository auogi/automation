package auto.framework.drivers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import auto.framework.TestManager;

public class ChromeBrowserDriver extends RemoteWebDriver {
	
	public static WebDriver start() {
    	String chromeDriverPath;
    	
    	try {
    		chromeDriverPath = JarFileToLocal.copyTmp("/webdriver/chromedriver.exe").getCanonicalPath();
    		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    		System.out.println("Chrome Driver: "+chromeDriverPath);
    	} catch(Error | IOException error){
    		throw new Error("Chrome Driver not found");
    	}
    	
    	System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    	

		//DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		
		ChromeOptions options = new ChromeOptions();				
		options.addArguments("--disable-gpu");
		options.addArguments("--start-maximized");		
		//options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.setAcceptInsecureCerts(true);
		options.addArguments("--ignore-ssl-errors=true");
		options.addArguments("--ssl-protocol=any");			
		//options.addArguments("--remote-debugging-port=9222");
		options.addArguments("window-size=1400,600");
		options.setHeadless(true);
		
				options.addArguments("--no-sandbox");
		options.addArguments("--allow-insecure-localhost"); 
	    options.addArguments("test-type");	    
		options.addArguments("headless");
		options.addArguments("window-size=1200x600");
		//options.setHeadless(true);
		options.addArguments("disable-gpu");
		//capabilities.setCapability(CapabilityType.PROXY, getProxySettings());
		//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = null;
        try{
        	driver = new ChromeDriver();	
        	//driver = new ChromeDriver(options);
        }catch (Exception e) {
            e.printStackTrace();
        }
		
    	
		return driver;
		
		
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
	
	/*@Override
	public void setTestStatus(ITestResult tr, int status) {
		
	}
*/	
	 public static Proxy getProxySettings(){
	    	Proxy proxy = new org.openqa.selenium.Proxy();
	    	
	    	String proxyType = TestManager.Preferences.getPreference("webdriver.proxy.proxyType", "unspecified").toLowerCase();
	    	
	    	switch(proxyType){
	    	case "direct":
	    		proxy.setProxyType(ProxyType.DIRECT);
	        	break;
	    	case "pac":
	    		proxy.setProxyType(ProxyType.PAC)
	    			.setProxyAutoconfigUrl(TestManager.Preferences.getPreference("webdriver.proxy.proxyAutoconfigUrl"));
	    		break;
	    	case "manual":
	    		String allProxy, ftpProxy, httpProxy, sslProxy, socksProxy, socksUsername, socksPassword, noProxy;
	    		allProxy = TestManager.Preferences.getPreference("webdriver.proxy.allProxy",null);
	    		if(allProxy!=null){
	    			ftpProxy = httpProxy = sslProxy = socksProxy = allProxy;
	    		} else {
		    		ftpProxy = TestManager.Preferences.getPreference("webdriver.proxy.ftpProxy","");
		    		httpProxy = TestManager.Preferences.getPreference("webdriver.proxy.httpProxy","");
		    		sslProxy = TestManager.Preferences.getPreference("webdriver.proxy.sslProxy","");
		    		socksProxy = TestManager.Preferences.getPreference("webdriver.proxy.socksProxy","");
	    		}
	    		socksUsername = TestManager.Preferences.getPreference("webdriver.proxy.socksUsername","");
	    		socksPassword = TestManager.Preferences.getPreference("webdriver.proxy.socksPassword","");
	    		noProxy = TestManager.Preferences.getPreference("webdriver.proxy.noProxy","");
	        	proxy.setProxyType(ProxyType.MANUAL)
	        		.setNoProxy(noProxy)
	        		.setHttpProxy(httpProxy)
		    	    .setFtpProxy(ftpProxy)
		    	    .setSslProxy(sslProxy);
	        	proxy.setSocksProxy(socksProxy)
		        	.setSocksUsername(socksUsername).setSocksPassword(socksPassword);
	        	break;
	    	case "system":
	    		proxy.setProxyType(ProxyType.SYSTEM);
	    		break;
	    	default:
	    		proxy.setProxyType(ProxyType.UNSPECIFIED);
	    		break;
	    	}
	    	return proxy;
	    }
}

