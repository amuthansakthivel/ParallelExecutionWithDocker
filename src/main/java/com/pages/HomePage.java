package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.utilities.TestUtils;

public class HomePage{
	

	@FindBy(xpath="//input[@id='search_query_top']")
	WebElement txtbox_search;
	
	@FindBy(xpath="//button[@name='submit_search']")
	WebElement btn_search;
	
	@FindBy(xpath="//a[contains(text(),'Sign in')]")
	WebElement btn_signin;
	
	TestUtils testutils= new TestUtils();
	private WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	public HomePage enterValueToSearch(String value) throws InterruptedException{
		
		testutils.sendKeys(txtbox_search, value,"searchBox");
		
		return this;
		
	}
	
	public void clickSearch() {
		testutils.click(btn_search, "Search box");
	}
	
	public SignInPage clickSignIn() {
		System.out.println(Thread.currentThread().getId());
		testutils.click(btn_signin, "Sign In");
		return new SignInPage(driver);
		
		
	}
	
	
}
