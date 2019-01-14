package auto.framework.webelement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;
import auto.framework.report.reporter.ReportLog;

public class Element {
	//ELEMENT
	protected String name;
	protected Element element;
	protected By by;	
	
	public Element(String name, By by, Element parent){
		this.element = parent;
		this.name = name;
		this.by = by;
	}
	
	public Element(String name, By by){
		this.name = name;
		this.by = by;
	}

	public Element(By by){
		this.name = "Element";
		this.by = by;
	}
	
	public void focus() {		
		try {
			JavascriptExecutor exec = (JavascriptExecutor) WebManager.getDriver();
			exec.executeScript("arguments[0].blur();arguments[0].focus();", this.toWebElement());						
			} 
		catch(Error e) {
			}	
	}
	
	
	
	public WebElement toWebElement(){ 
		WebDriverWait wait = new WebDriverWait(WebManager.getDriver(), 0);
		if(this.element!=null) {
			List<WebElement> webElements = null;
			try {
				webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.element.by));
			}catch(TimeoutException e) {
				
			}
			
			if(webElements.isEmpty()) {
				return null;
			}else {
				WebElement foundElement = null;
				
				WebManager.getDriver().manage().timeouts().implicitlyWait(0,TimeUnit.MICROSECONDS);
				for(WebElement element : webElements) {
					try {
						foundElement = element.findElement(this.by);
						break;
					}catch(NoSuchElementException|NullPointerException e) {
						
					}
				}
				
				//Reset Implicit TimeOut to Default Runtime
				WebManager.getDriver().manage().timeouts().implicitlyWait(Long.valueOf(
						TestManager.Preferences.getPreference("webdriver.timeouts.implicitlyWait","2")), TimeUnit.SECONDS);
				return foundElement;
			}
		}else {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(this.by));		
		}
	}
	
	
	public WebElement toWebElementOld(){ 	
		if(this.element!=null) {
			 SearchContext searchContext = WebManager.getDriver().findElement(this.element.by);			
			return searchContext.findElement(this.by);
		}else {
			return WebManager.getDriver().findElement(this.by);			
		}
	}
	
	
	public Boolean click(){
		Boolean prereq = isExisting();
		Boolean success = false;

		if(prereq) {
			try{				
				toWebElement().click();
				success = true;
			} catch(StaleElementReferenceException|Error e){
				ReportLog.failed("[" + name + "] Click element");				
				throw e;
			} catch(Exception e){
				ReportLog.failed("[" + name + "] Click element");	
				throw e;
			}
		}
						
			ReportLog.passed("[" + name + "] Click element");
		return success;
	}
	
	public Boolean selectByVisibleText(String string) {
		Boolean prereq = isExisting(); 
		Boolean success = false;
		if(prereq) { 
			Select webElement = new Select(toWebElement());
			webElement.selectByVisibleText(string);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Select Text \"" + string + "\"");
		return success;
	}

	public Boolean selectByIndex(Integer index) {
		Boolean prereq = isExisting(); 
		Boolean success = false;
		if(prereq) { 
			Select webElement = new Select(toWebElement());
			webElement.selectByIndex(index);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Select Index \"" + index + "\"");
		return success;
	}
	
	public Boolean selectByValue(String value) {
		Boolean prereq = isExisting(); 
		Boolean success = false;
		if(prereq) { 
			Select webElement = new Select(toWebElement());
			webElement.selectByValue(value);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Select Value \"" + value + "\"");
		return success;
	}
	
	public Boolean sendKeys(String string){
		Boolean prereq = isDisplayed(); 
		Boolean success = false;
		if(prereq) { 
			WebElement webElement = toWebElement();
			webElement.clear();
			webElement.sendKeys(string);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Type \"" + string + "\"");
		return success;
	}
		
	public String getAttribute(String name) {
		return toWebElement().getAttribute(name);
	}
	
	public Boolean type(String string){				
		Boolean prereq = isExisting(); 
		Boolean success = false;
		if(prereq) { 
			WebElement webElement = toWebElement();
			JavascriptExecutor myExecutor = ((JavascriptExecutor) WebManager.getDriver());
			myExecutor.executeScript("arguments[0].value='"+string+"'", webElement);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Type \"" + string + "\"");
		return success;
	}
	
	public String getText() {
		Boolean prereq = isDisplayed();
		if(prereq){
			try {
				return toWebElement().getText();
			} catch(Error error) {
				return "";
			}		
		}		
		return "";
	}
	
	public Boolean isExisting(){
		List<WebElement> elements = WebManager.getDriver().findElements(by);
		return !elements.isEmpty();
	}
	
	public Boolean isDisplayed() {
		try {
			Boolean iDisplayed = toWebElement().isDisplayed(); 				
			return iDisplayed;
		} catch(NoSuchElementException|StaleElementReferenceException|TimeoutException|NullPointerException e){
			return false;
		} catch(WebDriverException e){
			if(e.getMessage().contains("Error determining if element is displayed")) return false;
			throw e;
		}
	}

}
