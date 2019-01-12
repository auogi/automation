package auto.framework.webelement;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import auto.framework.managers.TestManager;
import auto.framework.managers.WebManager;
import auto.framework.report.reporter.ReportLog;
import auto.framework.webelement.Conditions.Condition;

public class Element {
	//ELEMENT
	protected String name;
	protected Element element;
	protected By by;	
	
	public Element(String name, By by){
		this.name = name;
		this.by = by;
	}

	public Element(By by){
		this.name = "Element";
		this.by = by;
	}
	
	
	
	public WebElement toWebElement(){ 
		return WebManager.getDriver().findElement(by);	
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


	
	public Boolean sendKeys(String string){
		Boolean prereq = isExisting(); 
		Boolean success = false;
		if(prereq) { 
			WebElement webElement = toWebElement();
			webElement.sendKeys(string);
			success = true;
		}
		ReportLog.verifyTrue(success, "[" + name + "] Type \"" + string + "\"");
		return success;
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
	
//text	
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
			
		} catch(NoSuchElementException|StaleElementReferenceException|TimeoutException e){
			return false;
		} catch(WebDriverException e){
			if(e.getMessage().contains("Error determining if element is displayed")) return false;
			throw e;
		}
	}

}
