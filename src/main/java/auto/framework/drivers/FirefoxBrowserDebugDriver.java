package auto.framework.drivers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxBrowserDebugDriver  {
	
	public static WebDriver start(){		

		final Socket socket = new Socket();
		
		Boolean isRunning = false;
    	try {
    		//http://localhost:10422/hub
    		//14562
    		//42532
    		socket.connect(new InetSocketAddress("localhost",8610));
    		socket.close();
    		isRunning = true;
    	} catch(final IOException io){
    		//io.printStackTrace();
    	}
    	if(!isRunning){
    		return FirefoxBrowserDriver.start();
    	} else {
    		
    		
    		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    		
    		
    		try {
    			final WebDriver existingWebDriver = new RemoteWebDriver(new URL("http://localhost:8610/hub"), capabilities);
    			System.out.println("Use existing firefox browser...");
    			return existingWebDriver;    			
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    
    	
    	System.out.println("Attempt to use existing broswer failed. Creating new browser for execution...");
		return FirefoxBrowserDriver.start();
	}
}
