package com.testcases;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;


import org.testng.annotations.Test;

import com.main.DriverManager;
import com.pages.HomePage;
import com.pages.SignInPage;
import com.relevantcodes.extentreports.LogStatus;
import com.reports.ExtentFactory;
import com.utilities.ExcelUtilities;


public class HomePageTests extends BaseTest{

	HomePage homepage;
	SignInPage signinpage;


	


	@Test
	public void validateSearchInHomePage(Hashtable<String,String> data) throws Exception {
		test=report.startTest("validate Search In HomePage"+data.get("browser"));
		ExtentFactory.setExtentTest(test);
		openBrowser(data.get("browser"));
		navigateToURL(DriverManager.getDriver());
		homepage=new HomePage(DriverManager.getDriver());
		homepage.enterValueToSearch(data.get("searchvalue")).clickSearch();
		homepage.clickSignIn();

	}

	@Test
	public void validateErrorMessageOnLogin(Hashtable<String,String> data) throws Exception {
		
		
		test=report.startTest("validate Error Message On Login"+" :"+data.get("browser"));
		ExtentFactory.setExtentTest(test);
		openBrowser(data.get("browser"));
		navigateToURL(DriverManager.getDriver());
		homepage= new HomePage(DriverManager.getDriver());
		signinpage=homepage.clickSignIn();
		signinpage.validateErrorMessage(data.get("email"),data.get("password"),data.get("errormessage"));

	}


	@AfterMethod
	public void quitBrowser() {
		if(report!=null) {
			report.endTest(ExtentFactory.getExtTest());
			
		}
		
		if(DriverManager.getDriver()!=null)
			DriverManager.getDriver().quit();
	}

	@AfterSuite
	public void tearDown() {
		report.flush();
	}





}
