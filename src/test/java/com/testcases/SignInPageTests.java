package com.testcases;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.main.DriverManager;
import com.pages.HomePage;
import com.pages.SignInPage;
import com.reports.ExtentFactory;
import com.utilities.ExcelUtilities;


public class SignInPageTests extends BaseTest{
	
	HomePage homepage;
	SignInPage signinpage;
	
	
	//@Test(dataProviderClass=ExcelUtilities.class,dataProvider="dataProviderForIterations")
	public void validateErrorMessageOnLoginSignInPage(Hashtable<String,String> data) throws Exception {
		System.out.println("entered test case");
		test=report.startTest("validateErrorMessageOnLoginSignInPage"+data.get("browser"));
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
			report.flush();
		}
		if(DriverManager.getDriver()!=null)
			DriverManager.getDriver().quit();
	}




	

}
