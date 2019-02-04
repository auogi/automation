package auto.framework.web.element;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

import auto.framework.report.reporter.ReportLog;

public class DropDown extends AbstractElement{
	private final static String suffix = " Dropdown";
	
	public DropDown(String name, By by) {
		super(name+suffix, by);
	}
	
	public DropDown(String name, By by, AbstractElement parent) {
		super(name+suffix, by, parent);
	}

	
	public Boolean selectByVisibleText(String string) {
		try{
			Select webElement = new Select(toWebElement());
			webElement.selectByVisibleText(string);
			ReportLog.passed("[" + name + "] Select Text \"" + string + "\"");
		} catch(StaleElementReferenceException e){
			ReportLog.failed("[" + name + "] Select element");
			return false;
		}	

		return true;
	}

}
