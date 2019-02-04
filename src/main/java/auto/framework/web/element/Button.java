package auto.framework.web.element;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import auto.framework.report.reporter.ReportLog;

public class Button extends AbstractElement{
	private final static String suffix = " Button";
	
	public Button(String name, By by) {
		super(name+suffix, by);
	}
	
	public Button(String name, By by, AbstractElement parent) {
		super(name+suffix, by, parent);
	}

}
