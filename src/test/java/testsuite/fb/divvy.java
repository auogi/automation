package testsuite.fb;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import auto.framework.report.listeners.TestInit;
import auto.framework.webelement.Element;
import auto.framework.webelement.WebControl;


public class divvy extends TestInit {

	@Test
	public void test(){
		
		WebControl.open("https://facebook.com");
		Element emailTextBox = new Element("Email TextBox", By.id("email"));
		emailTextBox.sendKeys("auogie@gmail.com");
		WebControl.delay(10);

	}

}
