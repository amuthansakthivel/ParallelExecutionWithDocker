package com.listener;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.reports.LogStatus;

import com.utilities.TestUtils;




/*
 * Listener class which is implementing ITestListener and hence we can use this to dynamically create reports, write logs.
 */
public class ListenerClass  implements ITestListener {
	public static String TestcaseName;

	public void onTestStart(ITestResult result) {
		
		TestcaseName=result.getName();
		
		
	}

	public void onTestSuccess(ITestResult result) {
		
		LogStatus.pass(result.getName()+ " test case is passed");
		LogStatus.pass("Passed",TestUtils.pullScreenshotPath());
	}

	public void onTestFailure(ITestResult result) {
		
		LogStatus.fail(result.getName()+" is failed");
		LogStatus.fail(result.getThrowable().toString());
		LogStatus.fail("Failed",TestUtils.pullScreenshotPath());
		
		
	}

	public void onTestSkipped(ITestResult result) {
		
		LogStatus.skip(result.getName()+ " is skipped");
		
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
	
		
	}

	public void onFinish(ITestContext context) {
	
		
	}



}
