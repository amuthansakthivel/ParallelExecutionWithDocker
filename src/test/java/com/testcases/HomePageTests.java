package com.testcases;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
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





	@Test(dataProviderClass=ExcelUtilities.class,dataProvider="dataProviderForIterations")
	public void validateSearchInHomePage(Hashtable<String,String> data) throws Exception {
		test=report.startTest("validateSearchInHomePage"+data.get("browser"));
		ExtentFactory.setExtentTest(test);
		
		openBrowser(data.get("browser"));
		navigateToURL(DriverManager.getDriver());
		homepage=new HomePage(DriverManager.getDriver());
		homepage.enterValueToSearch(data.get("searchvalue")).clickSearch();
		homepage.clickSignIn();

	}

	//@Test(dataProviderClass=ExcelUtilities.class,dataProvider="dataProviderForIterations")
	public void validateErrorMessageOnLogin(Hashtable<String,String> data) throws Exception {
		test=report.startTest("validateSearchInHomePage"+data.get("browser"));
		ExtentFactory.setExtentTest(test);
		
		System.out.println(data.get("browser"));
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
