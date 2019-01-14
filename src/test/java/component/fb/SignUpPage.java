package component.fb;

import org.openqa.selenium.By;

import auto.framework.webelement.Element;

public class SignUpPage {
	public static Fields ErrorField = new Fields("Error Field", By.xpath("//div[@class='_5dbb _5634']"));
	public static Fields Field = new Fields("Field", By.xpath("//div[@class='_5dbb']"));
	public static BirthDay BirthDayField = new BirthDay("Birthday Field", By.xpath("//span[@data-name='birthday_wrapper']"));
	public static Gender GenderRadioButton = new Gender("Birthday Field", By.xpath("//div[@class='mtm _5wa2 _5dbb']//span[@data-name='gender_wrapper']"));
	public static Gender ErrorGenderRadioButton = new Gender("Birthday Field", By.xpath("//div[@class='mtm _5wa2 _5dbb _5634']//span[@data-name='gender_wrapper']"));
	public static Element SubmitButton = new Element("Submit Button", By.name("websubmit"));
	public static Element ToolTip = new Element("Submit Button", By.xpath("//div[@class = 'uiContextualLayerPositioner _572t uiLayer']//div[@class='_5633 _5634 _53ij']"));
	public static Element ErrorBox = new Element("Error Message", By.id("reg_error"));
	
	
	public static class Fields extends Element{
		public Element FirstNameTextBox = new Element("FirstName TextBox", By.name("firstname"),this);
		public Element LastNameTextBox = new Element("LastName TextBox", By.name("lastname"),this);
		public Element MobEmailTextBox = new Element("Mobile or Email TextBox", By.name("reg_email__"),this);
		public Element MobEmailRepeatTextBox = new Element("Mobile or Email Confirm TextBox", By.name("reg_email_confirmation__"),this);
		public Element PasswordTextBox = new Element("Password TextBox", By.name("reg_passwd__"),this);
		
		public Fields(String name, By by) {
			super(name, by);
		}
	}
	
	public static class BirthDay extends Element{
		public Element Month = new Element("Birthday Month", By.name("birthday_month"),this);
		public Element Day = new Element("Birthday Day", By.name("birthday_day"),this);
		public Element Year = new Element("Birthday Year", By.name("birthday_year"),this);

		public BirthDay(String name, By by) {
			super(name, by);
		}
	}
	
	public static class Gender extends Element{
		public Element Female = new Element("Female Radio Button", By.xpath("//input[@value='1']"),this);
		public Element Male = new Element("Male Radio Button", By.xpath("//input[@value='2']"),this);
		

		public Gender(String name, By by) {
			super(name, by);
			// TODO Auto-generated constructor stub
		}
	}



}



