package auto.framework.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class HeadlessDriver {
	
	public static WebDriver start(){		
		return new HtmlUnitDriver();
	}

}
