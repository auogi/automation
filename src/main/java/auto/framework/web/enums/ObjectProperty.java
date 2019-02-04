package auto.framework.web.enums;

import org.openqa.selenium.WebElement;

public class ObjectProperty {
	public static final String DISPLAYED = "displayed";

	public static Object getProperty(String property,WebElement element){
		switch(property){
		case DISPLAYED:
			return element.isDisplayed();
		default:
			break;
		}
		return null;
	};
}
