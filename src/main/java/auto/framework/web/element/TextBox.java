package auto.framework.web.element;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import auto.framework.report.reporter.ReportLog;

public class TextBox extends AbstractElement{
	private final static String suffix = " TextBox";
	
	public TextBox(String name, By by) {
		super(name+suffix, by);
	}
	
	public TextBox(String name, By by, AbstractElement parent) {
		super(name+suffix, by, parent);
	}
	
	public Boolean sendKeys(String string) {
		WebElement elem = toWebElement();
		if(elem!=null) {
			try {
				elem.clear();
				elem.sendKeys(string);				
				ReportLog.passed("[" + name + "] Type \"" + string + "\"");
			} catch(StaleElementReferenceException e){
				ReportLog.failed("[" + name + "] Type element");
				return false;
			}
		}
		return true;		
	}

}
