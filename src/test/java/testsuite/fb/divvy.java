package testsuite.fb;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import auto.framework.report.listeners.TestInit;
import auto.framework.report.reporter.ReportLog;
import auto.framework.webelement.WebControl;
import component.fb.SignUpPage;

public class divvy extends TestInit {

	@Test
	public void TC1_NegativeTest(){
		
		ReportLog.setTestCase("Validate Behavior when fields are blank during sign up");
			ReportLog.setTestStep("Validate that all the Fields that are blank are highlighted in red");
				WebControl.open("https://facebook.com");
				SignUpPage.SubmitButton.click();
				ReportLog.verifyTrue(SignUpPage.ToolTip.getText().equals("What’s your name?"), 
						"Verify that FirstName is focused and is showing a tooltip with Message: \"What's your name?\"");
				ReportLog.verifyTrue(SignUpPage.ErrorField.LastNameTextBox.isDisplayed(), "Verify that LastName field is highlighted in red");
				ReportLog.verifyTrue(SignUpPage.ErrorField.MobEmailTextBox.isDisplayed(), "Verify that Mobile/Email field is highlighted in red");
				ReportLog.verifyTrue(SignUpPage.ErrorField.PasswordTextBox.isDisplayed(), "Verify that Password field is highlighted in red");
				ReportLog.verifyTrue(SignUpPage.Field.PasswordTextBox.isDisplayed(), "Verify that Password field is not highlighted in red");
				ReportLog.verifyTrue(SignUpPage.ErrorGenderRadioButton.isDisplayed(), "Verify that Gender Radio Buttons are highlighted in red");
			
				
			ReportLog.setTestStep("Validate that if a textbox field is focused, that field will display a tooltip");
				SignUpPage.ErrorField.LastNameTextBox.click();
				ReportLog.verifyTrue(SignUpPage.ToolTip.getText().equals("What’s your name?")
						, "Verify that LastName is focused and is showing a tooltip with Message: \"What's your name?\"");
				ReportLog.verifyTrue(SignUpPage.ErrorField.FirstNameTextBox.isDisplayed(), "Verify that FirstName field is highlighted in red");
				SignUpPage.ErrorField.MobEmailTextBox.click();
				ReportLog.verifyTrue(SignUpPage.ToolTip.getText().equals("You'll use this when you log in and if you ever need to reset your password.")
				, "Verify that Mobile/Email is focused and is showing a tooltip with Message: \"You’ll use this when you log in and if you ever need to reset your password.\"");
				ReportLog.verifyTrue(SignUpPage.ErrorField.LastNameTextBox.isDisplayed(), "Verify that LastName field is highlighted in red");
				
			ReportLog.setTestStep("Validate that if a Gender is selected, the red highlight disappears");
				SignUpPage.ErrorGenderRadioButton.Male.click();
				ReportLog.verifyTrue(SignUpPage.GenderRadioButton.isDisplayed(), "Verify that Gender Radio Buttons are not highlighted in red after selecting a gender");
		
			ReportLog.setTestStep("Validate that if all the text fields are populated the red highlight disappears");
				SignUpPage.ErrorField.FirstNameTextBox.sendKeys("Tester");
				SignUpPage.ErrorField.LastNameTextBox.sendKeys("Tester");
				SignUpPage.ErrorField.MobEmailTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.Field.MobEmailRepeatTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.BirthDayField.Day.selectByVisibleText("2");
				SignUpPage.BirthDayField.Month.selectByVisibleText("Aug");
				SignUpPage.BirthDayField.Year.selectByVisibleText("1986");
				SignUpPage.ErrorField.PasswordTextBox.sendKeys("Password1!");
				SignUpPage.GenderRadioButton.Male.click();		
				ReportLog.verifyTrue(SignUpPage.Field.LastNameTextBox.isDisplayed(), "Verify that FirstName field is not highlighted in red");
				ReportLog.verifyTrue(SignUpPage.Field.LastNameTextBox.isDisplayed(), "Verify that LastName field is not highlighted in red");
				ReportLog.verifyTrue(SignUpPage.Field.MobEmailTextBox.isDisplayed(), "Verify that Mobile/Email field is not highlighted in red");
				ReportLog.verifyTrue(SignUpPage.Field.PasswordTextBox.isDisplayed(), "Verify that Password field is not highlighted in red");

		ReportLog.setTestCase("Validate FirstName and LastName Field Signup Requirement");
				ReportLog.setTestStep("Validate that if you enter numbers as FirstName, error should be displayed");
					SignUpPage.Field.FirstNameTextBox.sendKeys("1234567");
					SignUpPage.SubmitButton.click();
					WebControl.delay(1);
					ReportLog.verifyTrue(SignUpPage.ErrorBox.getText().equals("It looks like you entered a mobile "
							+ "number or email. Please enter your name.")
							, "Verify that if you enter a number in the first name, error should show upon sign up");
				ReportLog.setTestStep("Validate that if you enter numbers as LastName, error should be displayed");
					SignUpPage.Field.LastNameTextBox.sendKeys("1234567");
					SignUpPage.SubmitButton.click();
					WebControl.delay(1);
					ReportLog.verifyTrue(SignUpPage.ErrorBox.getText().equals("It looks like you entered a mobile "
							+ "number or email. Please enter your name.")
							, "Verify that if you enter a number in the last name, error should show upon sign up");
					SignUpPage.Field.FirstNameTextBox.sendKeys("Tester");
					SignUpPage.Field.LastNameTextBox.sendKeys("Tester");
				
		ReportLog.setTestCase("Validate Email/Mobile Number Field Signup Requirement");
				ReportLog.setTestStep("Validate that if the value for the Mob/Email is a mobile number that doesnt have the right number of digits it should throw an error");	
					SignUpPage.Field.MobEmailTextBox.sendKeys("1");
					ReportLog.verifyTrue(!SignUpPage.ErrorField.MobEmailRepeatTextBox.isDisplayed(), "Verify that Confirmation Mob/Email field is highlighted in red when number of digit is less than 10");	
					SignUpPage.Field.MobEmailTextBox.sendKeys("42589254161111");
					ReportLog.verifyTrue(!SignUpPage.ErrorField.MobEmailRepeatTextBox.isDisplayed(), "Verify that Confirmation Mob/Email field is highlighted in red when number of digit is greater than 10");
					SignUpPage.Field.MobEmailTextBox.sendKeys("425892541A");
					ReportLog.verifyTrue(!SignUpPage.ErrorField.MobEmailRepeatTextBox.isDisplayed(), "Verify that Confirmation Mob/Email field is highlighted in red when there is a letter on the set of numbers");
					ReportLog.setTestStep("Validate that if an invalid email is entered, the field should display a tooltip");
					SignUpPage.Field.MobEmailTextBox.sendKeys("invalidemailaddress"+Keys.TAB);
					ReportLog.verifyTrue(SignUpPage.ErrorField.MobEmailTextBox.isDisplayed(), "Verify that Mobile/Email field is highlighted in red");
				ReportLog.setTestStep("Validate that if an emasil is entered, a new field will appear below the email where you have to enter your email again for confirmation");
					SignUpPage.ErrorField.MobEmailTextBox.sendKeys("validEmailAddress@gmail.com"+Keys.TAB);
					WebControl.delay(1);
					ReportLog.verifyTrue(SignUpPage.Field.MobEmailRepeatTextBox.isDisplayed()
							, "Verify that Confirmation Mob/Email field is shown");
				ReportLog.setTestStep("Validate that if the confirmation Mob/Email field is not the same as the Mob/Email field, the confirmation field should be highlighted");	
					SignUpPage.Field.MobEmailRepeatTextBox.sendKeys("validEmailAddresDifferents@gmail.com"+Keys.TAB);
					WebControl.delay(1);
					ReportLog.verifyTrue(SignUpPage.ErrorField.MobEmailRepeatTextBox.isDisplayed()
							, "Verify that Confirmation Mob/Email field is highlighted in red");
				ReportLog.setTestStep("Validate that if the confirmation Mob/Email field is the same as the Mob/Email field, the confirmation field should not be highlighted");	
					SignUpPage.ErrorField.MobEmailRepeatTextBox.sendKeys("validEmailAddress@gmail.com");
					SignUpPage.Field.MobEmailTextBox.sendKeys("validEmailAddress@gmail.com");	
					ReportLog.verifyTrue(SignUpPage.Field.MobEmailRepeatTextBox.isDisplayed()
							, "Verify that Confirmation Mob/Email field is not highlighted in red");
				ReportLog.setTestStep("Validate that if the value for the Mob/Email is changed to a Mobile number, the Confirmation field should disappear");	
					SignUpPage.Field.MobEmailTextBox.sendKeys("4258925416");
					WebControl.delay(1);
					ReportLog.verifyTrue(!SignUpPage.Field.MobEmailRepeatTextBox.isDisplayed()
							, "Verify that Confirmation Mob/Email field is not displayed");
				
		ReportLog.setTestCase("Validate Password Field Signup Requirement");
			ReportLog.setTestStep("Validate that if password is less than 6 characters, error should show upon sign up");
				SignUpPage.Field.PasswordTextBox.sendKeys("a");
				SignUpPage.SubmitButton.click();
				WebControl.delay(1);
				ReportLog.verifyTrue(SignUpPage.ErrorBox.getText().equals("Your password must be at least 6 characters long. Please try another.")
						, "Verify that if password is less than 6 characters, error should show upon sign up");
				
			ReportLog.setTestStep("Validate that if password contains only letters, error should show upon sign up");
				SignUpPage.Field.PasswordTextBox.sendKeys("aaaaaaaaaaaaaaa");
				SignUpPage.SubmitButton.click();
				WebControl.delay(1);
				ReportLog.verifyTrue(SignUpPage.ErrorBox.getText().equals("Please choose a more secure password."
						+ " It should be longer than 6 characters, unique to you, and difficult for others to guess.")
						, "Verify that if password contains only letters, error should show upon sign up");
			
			ReportLog.setTestStep("Validate that if password contains only numbers, error should show upon sign up");
				SignUpPage.Field.PasswordTextBox.sendKeys("111111111111");
				SignUpPage.SubmitButton.click();
				WebControl.delay(1);
				ReportLog.verifyTrue(SignUpPage.ErrorBox.getText().equals("Please choose a more secure password."
						+ " It should be longer than 6 characters, unique to you, and difficult for others to guess.")
						, "Verify that if password contains only numbers, error should show upon sign up");
			
				ReportLog.assertFailed("Sample of a Failure");

	}
}
