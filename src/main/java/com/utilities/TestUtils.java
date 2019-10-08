package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import com.listener.ListenerClass;
import com.main.DriverFactory;
import com.main.DriverManager;
import com.reports.LogStatus;



public class TestUtils {
	WebDriverWait wait;

	

	public void click(WebElement element,String fieldname) {
		explicitlyWait(element);
		element.click();
		LogStatus.pass(fieldname+ " is clicked successfully"); 
	}

	public void sendKeys(WebElement element,String value,String fieldname) {
		explicitlyWait(element);
		element.sendKeys(value);
		LogStatus.pass(value+" is entered successfully in the field "+fieldname);
	}
	
	public void explicitlyWait(WebElement element) {
		wait=new WebDriverWait(DriverManager.getDriver(),10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/*
	 * Captures screenshot and returns the screenshot path
	 */
	public static String pullScreenshotPath()  {

		String destination=null;
		if(DriverFactory.getIsScreenshotRequired().equalsIgnoreCase("yes")) {
			File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				if(DriverFactory.getScreenshotPath().equals("")) {

					destination=System.getProperty("user.dir")+"\\screenshots\\" +ListenerClass.TestcaseName+"\\"+ System.currentTimeMillis() + ".png";
					FileUtils.copyFile(scrFile, new File(destination));
				}
				else {
					destination=DriverFactory.getScreenshotPath()+"\\screenshots\\" +ListenerClass.TestcaseName.replaceAll(" ","")+"\\"+ System.currentTimeMillis() + ".png";
					FileUtils.copyFile(scrFile, new File(destination));
				}

			}
			catch(Exception e) {
				e.printStackTrace();

			}

		}
	
		return destination;

	}

	/*
	 * Gives a base64 image which is used to append the screenshots in the extent report.
	 * Converting to base64 format avoids screenshots broken image if sent the exent report through email.
	 */
	public static String getBase64Image(String screenshotpath) {
		String base64 = null;
		try {
			InputStream is= new FileInputStream(screenshotpath);
			byte[] imageBytes = IOUtils.toByteArray(is);
			base64 = Base64.getEncoder().encodeToString(imageBytes);
		}
		catch (Exception e) {

		}
		return base64;

	}
}
