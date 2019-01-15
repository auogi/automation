package testsuite.fb;

import org.testng.annotations.Test;
import auto.framework.report.listeners.TestInit;
import auto.framework.report.reporter.ReportLog;
import auto.framework.webelement.WebControl;
import component.fb.SignUpPage;

public class PerfTest extends TestInit {

	@Test(invocationCount=10)
	public void TC1_PerfTest(){
		ReportLog.setTestCase("Validate Behavior when fields are blank during sign up");
			ReportLog.setTestStep("Validate that all the Fields that are blank are highlighted in red");
				WebControl.open("https://facebook.com");
				SignUpPage.Field.FirstNameTextBox.sendKeys("Tester");
				SignUpPage.Field.LastNameTextBox.sendKeys("Tester");
				SignUpPage.Field.MobEmailTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.Field.MobEmailRepeatTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.BirthDayField.Day.selectByVisibleText("2");
				SignUpPage.BirthDayField.Month.selectByVisibleText("Aug");
				SignUpPage.BirthDayField.Year.selectByVisibleText("1986");
				SignUpPage.Field.PasswordTextBox.sendKeys("Password1!");
				SignUpPage.GenderRadioButton.Male.click();
				//SignUpPage.SubmitButton.click();
			
	}
	@Test(invocationCount=10)
	public void TC2_PerfTest(){
		ReportLog.setTestCase("Validate Behavior when fields are blank during sign up");
			ReportLog.setTestStep("Validate that all the Fields that are blank are highlighted in red");
				WebControl.open("https://facebook.com");
				SignUpPage.ErrorField.FirstNameTextBox.sendKeys("Tester");
				SignUpPage.ErrorField.LastNameTextBox.sendKeys("Tester");
				SignUpPage.ErrorField.MobEmailTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.Field.MobEmailRepeatTextBox.sendKeys("auogie323@gmail.com");
				SignUpPage.BirthDayField.Day.selectByVisibleText("2");
				SignUpPage.BirthDayField.Month.selectByVisibleText("Aug");
				SignUpPage.BirthDayField.Year.selectByVisibleText("1986");
				SignUpPage.ErrorField.PasswordTextBox.sendKeys("Password1!");
				SignUpPage.GenderRadioButton.Male.click();
				//SignUpPage.SubmitButton.click();
			
	}
}
