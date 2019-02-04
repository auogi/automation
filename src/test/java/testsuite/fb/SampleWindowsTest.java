package testsuite.fb;


import org.testng.annotations.Test;

import auto.framework.report.listeners.TestInit;
import auto.framework.web.WebControl;
import component.calculator.CalculatorApp;

public class SampleWindowsTest extends TestInit {

	@Test
	public void TC1_WindowsTest(){	
	
		CalculatorApp.One.click();
		CalculatorApp.Plus.click();
		CalculatorApp.Seven.click();
		CalculatorApp.Equals.click();
		WebControl.delay(10);
	}
}
