package auto.framework.webelement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
		WebManager.getDriver().manage().timeouts().implicitlyWait(0,TimeUnit.MICROSECONDS);
		if(this.element!=null) {
			List<WebElement> webElements = null;
			try {
				webElements = WebManager.getDriver().findElements(this.element.by);
			}catch(TimeoutException|NullPointerException e) {
				
			}
			
			if(webElements.isEmpty()) {
				return null;
			}else {
				WebElement foundElement = null;
				

				for(WebElement element : webElements) {
					try {
						foundElement = element.findElement(this.by);
						break;
					}catch(TimeoutException|NoSuchElementException|NullPointerException e) {
						
					}
				}
				
				//Reset Implicit TimeOut to Default Runtime
				WebManager.getDriver().manage().timeouts().implicitlyWait(Long.valueOf(
						TestManager.Preferences.getPreference("webdriver.timeouts.implicitlyWait","2")), TimeUnit.SECONDS);
				return foundElement;
			}
		}else {
			WebElement foundElement = null;
			
			try {
				foundElement = WebManager.getDriver().findElement(this.by);
			}catch(TimeoutException|NoSuchElementException|NullPointerException e) {
				
			}
			return foundElement;		
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
		Boolean success = false;

		try{				
			toWebElement().click();
			success = true;
		} catch(StaleElementReferenceException e){
			ReportLog.failed("[" + name + "] Click element");	
			WebControl.takeScreenshot();
		} catch(Exception e){
			ReportLog.failed("[" + name + "] Click element");
			WebControl.takeScreenshot();
		}
						
		ReportLog.passed("[" + name + "] Click element");
		return success;
	}
	
	public Boolean selectByVisibleText(String string) {
		Boolean success = false;
		try{
			Select webElement = new Select(toWebElement());
			webElement.selectByVisibleText(string);
			success = true;
		} catch(StaleElementReferenceException e){
			ReportLog.failed("[" + name + "] Select element");
			WebControl.takeScreenshot(this.toWebElement());
		} catch(Exception e){
			ReportLog.failed("[" + name + "] Select element");
			WebControl.takeScreenshot();
		}
			
		ReportLog.verifyTrue(success, "[" + name + "] Select Text \"" + string + "\"");
		return success;
	}
	
	public Boolean sendKeys(String string){
		Boolean success = false;
		try{				
			WebElement webElement = toWebElement();
			webElement.clear();
			webElement.sendKeys(string);
			success = true;
		} catch(StaleElementReferenceException|NullPointerException e){
			ReportLog.failed("[" + name + "] Type element");				
			WebControl.takeScreenshot();
		} catch(Exception e){
			ReportLog.failed("[" + name + "] Type element");	
			WebControl.takeScreenshot();
		}
						

		
		ReportLog.verifyTrue(success, "[" + name + "] Type \"" + string + "\"");
		return success;
	}
	
		
	public String getText() {
		return toWebElement().getText();
	}
		
	public String getAttribute(String name) {
		return toWebElement().getAttribute(name);
	}	
	
	public Boolean isDisplayed() {
		Boolean iDisplayed = false;
		
		try {
			iDisplayed = toWebElement().isDisplayed(); 				
		} catch(Exception e){
			return iDisplayed;
		}		
		return iDisplayed;

	}

}
