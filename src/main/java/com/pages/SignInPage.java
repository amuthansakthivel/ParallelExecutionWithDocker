package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utilities.TestUtils;

public class SignInPage extends TestUtils{
	
	@FindBy(id="email")
	WebElement txtbox_email;
	
	@FindBy(id="passwd")
	WebElement txtbox_pwd;
	
	@FindBy(id="SubmitLogin")
	WebElement btn_submit;
	
	@FindBy(xpath="//*[@id=\"center_column\"]/div[1]/p")
	WebElement msg_error;
	
	TestUtils testutils= new TestUtils();
	private WebDriver driver;
	
	public SignInPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}


	public void validateErrorMessage(String email, String pwd,String errormessage) {
		
		testutils.sendKeys(txtbox_email, email, "Email");
		testutils.sendKeys(txtbox_pwd, pwd, "Password");
		testutils.click(btn_submit, "Sign in button");
		Assert.assertEquals(errormessage, msg_error.getText());
		
	}
	
	

}
