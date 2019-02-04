package auto.framework.web.element;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import auto.framework.report.reporter.ReportLog;

public class RadioButton extends AbstractElement{
	private final static String suffix = " RadioButton";
	
	public RadioButton(String name, By by) {
		super(name+suffix, by);
	}
	
	public RadioButton(String name, By by, AbstractElement parent) {
		super(name+suffix, by, parent);
	}

}
