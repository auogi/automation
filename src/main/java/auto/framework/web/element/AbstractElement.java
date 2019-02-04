package auto.framework.web.element;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;
import auto.framework.report.reporter.ReportLog;
import auto.framework.web.enums.Conditions;
import auto.framework.web.enums.Conditions.Condition;
import auto.framework.web.enums.ObjectProperty;

public abstract class AbstractElement {
	protected String name;
	protected AbstractElement parent;
	protected By by;
	
	public AbstractElement(String name, By by, AbstractElement parent){
		this.parent = parent;
		this.name = name;
		this.by = by;
	}
	
	public AbstractElement(String name, By by){
		this.name = name;
		this.by = by;
	}

	public AbstractElement(By by){
		this.name = "Element";
		this.by = by;
	}
	
	public WebElement toWebElement(){ 		
		WebManager.getDriver().manage().timeouts().implicitlyWait(0,TimeUnit.MICROSECONDS);
		WebElement searchedElement = null;

			if(this.parent==null){
				try {			
					return WebManager.getDriver().findElement(this.by);
				}catch(NullPointerException | NoSuchElementException e) {
				}
			}else{
				List<WebElement> elemList = WebManager.getDriver().findElements(this.parent.by);
				if(!elemList.isEmpty()) { 
					for(WebElement element : elemList) {
							try {			
								searchedElement = element.findElement(this.by);
								break;
							}catch(NullPointerException | NoSuchElementException e) {
						}
					}
				}
				WebManager.getDriver().manage().timeouts().implicitlyWait(Long.valueOf(
				TestManager.Preferences.getPreference("webdriver.timeouts.implicitlyWait","2")), TimeUnit.SECONDS);
				
			}
			
			if(searchedElement==null) {
				ReportLog.failed("[" + this.name + "] Unable to locate element");
			}
			
			return searchedElement;
	}
	
	public void click() {
		WebElement elem = toWebElement();
		if(elem!=null) {
			try {
				elem.click();				
			}catch(Exception e) {
				ReportLog.failed("[" + name + "] Click element");				
			}
			ReportLog.passed("[" + name + "] Click element");
		}
	}
	
	public String getText() {
		WebElement elem = toWebElement();
		return elem==null ? "" : elem.getText();
	}
	
	public Boolean isDisplayed() {
		WebElement elem = toWebElement();
		return elem==null ? false : elem.isDisplayed();
	}
	
	public Boolean waitForDisplay(int timeoutSeconds) {
		WebElement elem = toWebElement();
		return elem==null ? false : waitForDisplay(true, timeoutSeconds);
	}
	
	public Boolean waitForDisplay() {
		WebElement elem = toWebElement();
		return elem==null ? false : waitForDisplay(true, 5);
	}
	
	public String getAttribute(String name) {
		WebElement elem = toWebElement();
		return elem==null ? null : elem.getAttribute(name);
	}
	
	
	public Boolean waitForDisplay(final Boolean expected,long timeOutInSeconds){
		return waitForProperty(ObjectProperty.DISPLAYED, expected, timeOutInSeconds, Conditions.equals);
	}
	
	public <Expected> Boolean waitForProperty(final String property,final Expected expected,long timeOutInSeconds,final Condition<Expected> validation){
		WebDriverWait wait = new WebDriverWait(WebManager.getDriver(),timeOutInSeconds);
		Boolean success = true;
		
		//wait.ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		try {
			ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return validation.verify(expected, ObjectProperty.getProperty(property,toWebElement()));
				}
			};
			wait.until(expectedCondition);
		} catch(TimeoutException e){
			success = false;
		}
		return success;
	};
	

}
