package auto.framework.drivers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import auto.framework.managers.TestManager;
import io.appium.java_client.windows.WindowsDriver;

public class Drivers {		
	public static final Driver<Object> firefox =  new Driver.Firefox();
	public static final Driver<Object> chrome =  new Driver.Chrome();
	public static final Driver<Object> windowsApp =  new Driver.WindowsApp();

	public static interface Driver<Expected> {
		
		public String name();
		public WebDriver start();

		static class Firefox implements Driver<Object> {
			
			private void clean() {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("taskkill /im geckodriver.exe /f /t");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public WebDriver start() {
				String firefoxDriverPath="";
				WebDriver driver = null;
				clean();
				try {
					firefoxDriverPath =new File("src/main/resources/webdriver/geckodriver.exe").getCanonicalPath();
					System.setProperty("webdriver.gecko.driver",firefoxDriverPath);
					driver = new FirefoxDriver();  
		    	} catch(Exception e){
		    		System.err.print("Failed to Start Firefox Driver");
		    		e.printStackTrace();
			    }
		  
		    	return driver;
			}

			@Override
			public String name() {
				return "firefox";
			}
		};
		
		static class Chrome implements Driver<Object> {	
			
			private void clean() {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("taskkill /im chrome.exe /f /t");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public WebDriver start() {
			   	String chromeDriverPath = "";
			    WebDriver driver = null;
			    clean();
		    	try {
		    		chromeDriverPath = new File("src/main/resources/webdriver/chromedriver.exe").getCanonicalPath();
		    		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		    		System.out.println("Chrome Driver: "+chromeDriverPath);
		    		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		      		
		    		ChromeOptions options = new ChromeOptions();				
		    		options.setAcceptInsecureCerts(true);		
		    		options.setHeadless(true);
		    		options.addArguments("--disable-gpu");
		    		options.addArguments("--start-maximized");		
		    		options.addArguments("--no-sandbox");
		    		options.addArguments("--ignore-ssl-errors=true");
		    		options.addArguments("--ssl-protocol=any");
		    		options.addArguments("--no-sandbox");
		    		options.addArguments("--allow-insecure-localhost"); 
		    	    options.addArguments("test-type");
		    	    driver = new ChromeDriver();
		    	} catch(Exception e){
		    		System.err.print("Failed to Start ChromeDriver");
		    		e.printStackTrace();    	
		    	}
				return driver;
			}

			@Override
			public String name() {
				return "chrome";
			}
		};

		static class WindowsApp implements Driver<Object> {	
			
			@Override
			public WebDriver start() {
			      DesiredCapabilities capabilities = new DesiredCapabilities();
			        WebDriver driver= null;

					try {
			            capabilities.setCapability("app", TestManager.Preferences.getPreference("windowsApp"));
			            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
					}catch(Exception e){
			    		System.err.print("Failed to Start Windows Driver");
			            e.printStackTrace();
			        }
					return driver;		
			}

			@Override
			public String name() {
				return "chrome";
			}
		};
	}
				
}
