package auto.framework.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import auto.framework.reportlog.ReportLog;

public class TextBox extends FormElement {

	public TextBox(By by) {
		super(by);
	}
	
	public TextBox(String name, By by) {
		super(name, by);
	}
	
	public TextBox(String name, By by, Element parent) {
		super(name, by, parent);
	}
	
	public TextBox(By by, Element parent) {
		super(by, parent);
	}
	
	public Boolean type(String text){
		String type;
		try{ 
			WebElement element = toWebElement();
			type=element.getAttribute("type").toLowerCase();
			switch(type){
			case "file":
				break;
			default:
				element.click();
				element.clear();
				break;
			}
		} catch(Error e){}
		return sendKeys(text);
	}
	
	public void verifyType(String string){
		verifyType(string, string);
	}
	
	public void verifyType(String string, final String expected){
		ReportLog.verifyTrue( type(string) && expected.equals(getValue()), "[" + name + "] Verify that typed value is \"" + expected + "\"");
	}
	
	public void assertType(String string){
		assertType(string, string);
	}
	
	public void assertType(String string, String expected){
		ReportLog.assertTrue( type(string) && expected.equals(getValue()), "[" + name + "] Verify that typed value is \"" + expected + "\"");
		
	}
}
