package auto.framework.web.element;

import org.openqa.selenium.By;

public class Element extends AbstractElement{
	
	public Element(String name, By by) {
		super(name, by);
	}
	
	public Element(String name, By by, AbstractElement parent) {
		super(name, by, parent);
	}
}
