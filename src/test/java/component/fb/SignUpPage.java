package component.fb;

import org.openqa.selenium.By;

import auto.framework.web.element.Button;
import auto.framework.web.element.DropDown;
import auto.framework.web.element.Element;
import auto.framework.web.element.RadioButton;
import auto.framework.web.element.TextBox;

public class SignUpPage {
	public static TextBox FirstNameTextBox = new TextBox("FirstName", By.name("firstname"));
	
	public static Fields ErrorField = new Fields("Error Field", By.xpath("//div[@class='_5dbb _5634']"));
	public static Fields Field = new Fields("Field", By.xpath("//div[@class='_5dbb']"));
	public static BirthDay BirthDayField = new BirthDay("Birthday Field", By.xpath("//span[@data-name='birthday_wrapper']"));
	public static Gender GenderRadioButton = new Gender("Birthday Field", By.xpath("//div[@class='mtm _5wa2 _5dbb']//span[@data-name='gender_wrapper']"));
	public static Gender ErrorGenderRadioButton = new Gender("Birthday Field", By.xpath("//div[@class='mtm _5wa2 _5dbb _5634']//span[@data-name='gender_wrapper']"));
	public static Button SubmitButton = new Button("Submit Button", By.name("websubmit"));
	public static Element ToolTip = new Element("Tool Tip", By.xpath("//div[@class = 'uiContextualLayerPositioner _572t uiLayer']//div[@class='_5633 _5634 _53ij']"));
	public static Element ErrorBox = new Element("Error Message", By.id("reg_error"));
	
	
	public static class Fields extends Element{
		public TextBox FirstNameTextBox = new TextBox("FirstName TextBox", By.name("firstname"),this);
		public TextBox LastNameTextBox = new TextBox("LastName TextBox", By.name("lastname"),this);
		public TextBox MobEmailTextBox = new TextBox("Mobile or Email TextBox", By.name("reg_email__"),this);
		public TextBox MobEmailRepeatTextBox = new TextBox("Mobile or Email Confirm TextBox", By.name("reg_email_confirmation__"),this);
		public TextBox PasswordTextBox = new TextBox("Password TextBox", By.name("reg_passwd__"),this);
		
		public Fields(String name, By by) {
			super(name, by);
		}
	}
	
	public static class BirthDay extends Element{
		public DropDown Month = new DropDown("Birthday Month", By.name("birthday_month"),this);
		public DropDown Day   = new DropDown("Birthday Day", By.name("birthday_day"),this);
		public DropDown Year  = new DropDown("Birthday Year", By.name("birthday_year"),this);

		public BirthDay(String name, By by) {
			super(name, by);
		}
	}
	
	public static class Gender extends Element{
		public RadioButton Female = new RadioButton("Female Radio Button", By.xpath("//input[@value='1']"),this);
		public RadioButton Male = new RadioButton("Male Radio Button", By.xpath("//input[@value='2']"),this);
		

		public Gender(String name, By by) {
			super(name, by);
			// TODO Auto-generated constructor stub
		}
	}



}



