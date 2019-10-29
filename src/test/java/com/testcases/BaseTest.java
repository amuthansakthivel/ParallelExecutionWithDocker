package com.testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.BeforeSuite;


import com.main.DriverFactory;
import com.main.DriverManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.reports.ExtentManager;
import com.utilities.ExcelUtilities;

public class BaseTest {

	private WebDriver driver;
	public Properties config =new Properties(); 
	public FileInputStream inputstream;
	public WebDriverWait wait;
	public ExtentManager extentmanager;
	public static ExtentReports  report;
	public ExtentTest test;

	public String browser;
	public ExcelUtilities excel;


	@BeforeSuite
	public void setUp() throws Exception {

		setUpConfigFile();
		setUpExecutablesPath();
		setUpExecutionMode();
		setUpOtherProperties();
		setUpRunModeForTestCases();
		setUpDocker();


	}

	private void setUpRunModeForTestCases() {

	}

	private void setUpDocker() throws IOException, Exception {
		if (DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			Runtime runtime=Runtime.getRuntime();
			if(DriverFactory.getRemoteMode().equalsIgnoreCase("Selenium")) {

				runtime.exec("cmd /c start dockerUp.bat");
				verifyDockerIsUp();
				runtime.exec("cmd /c start scaleChrome.bat");
				Thread.sleep(10000);
				runtime.exec("cmd /c start scaleFirefox.bat");
				Thread.sleep(10000);
				runtime.exec("taskkill /f /im cmd.exe") ;
			}
			else {
				runtime.exec("cmd /c start zaleniumUp.bat");
				Thread.sleep(20000);
			}
		}
	}



	private void verifyDockerIsUp() throws FileNotFoundException, Exception {
		Thread.sleep(10000);
		boolean flag=false;
		String file="output.txt";
		BufferedReader reader= new BufferedReader(new FileReader(file));

		String currentline=reader.readLine();

		while(currentline!=null) {
			if(currentline.contains("The node is registered to the hub and ready to use")) {
				flag=true;
				break;
			}
			currentline=reader.readLine();
		}
		reader.close();

		if(!flag) {
			throw new SkipException("Docker have not started. Please try again or try manually.");
		}
	}

	@BeforeClass
	public void setUpExtentReports() {
		extentmanager=new ExtentManager();
		report=extentmanager.getInstance();
	}

	@AfterSuite
	public void afterSuite() throws Exception {
		if (DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			Runtime runtime=Runtime.getRuntime();
			if(DriverFactory.getRemoteMode().equalsIgnoreCase("Selenium")) {
				
				runtime.exec("cmd /c start dockerDown.bat");
				File file=new File("output.txt");
				if(file.exists()) {
					System.out.println("file deleted");
					file.delete();
				}
			}
			else {
				runtime.exec("cmd /c start zaleniumDown.bat");
			}
		}
	}

	private void setUpOtherProperties() {
		if(DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			DriverFactory.setGridPath(config.getProperty("RemoteURL"));
			DriverFactory.setRemoteMode(config.getProperty("RemoteMode"));
		}
		DriverFactory.setTestDataLocation(config.getProperty("TestDataLocation"));
		DriverFactory.setWaitTime(Integer.parseInt(config.getProperty("WaitTime")));
		DriverFactory.setOverrideResults(config.getProperty("OverrideResults"));
		DriverFactory.setIsScreenshotRequired(config.getProperty("OverrideResults"));
		DriverFactory.setResultPath(config.getProperty("ResultPath"));
		DriverFactory.setScreenshotPath(config.getProperty("ScreenshotPath"));
		DriverFactory.setExtentConfigLocation(config.getProperty("ExtentConfigLocation"));
		DriverFactory.setTestURL(config.getProperty("url"));
		DriverFactory.setPassedStepsScreenshots(config.getProperty("PassedStepsScreenshots"));
		DriverFactory.setFailedStepsScreenshots(config.getProperty("FailedStepsScreenshots"));
		DriverFactory.setSkippedStepScreenshots(config.getProperty("SkippedStepsScreenshots"));

	}

	private void setUpExecutionMode() {
		DriverFactory.setExecutionMode(config.getProperty("ExecutionMode"));
	}

	private void setUpExecutablesPath() {
		if (System.getProperty("os.name").contains("mac")) {
			DriverFactory.setChromeDriverExePath(".//src/test//resources//chromedriver");
			DriverFactory.setGeckoDriverExePath(".//src//test//resources//geckodriver");
		}
		else {
			DriverFactory.setChromeDriverExePath(".//src/test//resources//chromedriver.exe");
			DriverFactory.setGeckoDriverExePath(".//src//test//resources//geckodriver.exe");
		}
	}

	private void setUpConfigFile() {

		try 
		{

			inputstream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			config.load(inputstream);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void navigateToURL(WebDriver driver) {


		driver.get(DriverFactory.getTestURL());
	}

	public void openBrowser(String browser) throws MalformedURLException {

		DesiredCapabilities capability=null;

		if(DriverFactory.getExecutionMode().equalsIgnoreCase("Local")){
			ChromeOptions options=new ChromeOptions();
			switch(browser){

			case "chrome":
				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				options.addArguments("--incognito");
				driver=new ChromeDriver(options);
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());
				FirefoxOptions FFoptions= new FirefoxOptions();
				FFoptions.addArguments("--incognito");
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"C:\\temp\\logs.txt");

				driver= new FirefoxDriver(FFoptions);
				break;
			default:
				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				options.addArguments("--incognito");
				driver=new ChromeDriver(options);
				break;
			}
		}
		else {
			switch(browser){

			case "chrome":
				capability = DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			case "firefox":
				capability = DesiredCapabilities.firefox();
				capability.setBrowserName("firefox");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			default:
				capability = DesiredCapabilities.firefox();
				capability.setBrowserName("firefox");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			}


		}
		DriverManager.setWebDriver(driver);
		maximiseWindow();
		setUpImplicitWait();

	}

	private void setUpImplicitWait() {

		DriverManager.getDriver().manage().timeouts().implicitlyWait(DriverFactory.getWaitTime(), TimeUnit.SECONDS);
	}

	private void maximiseWindow() {

		DriverManager.getDriver().manage().window().maximize();
	}

}
