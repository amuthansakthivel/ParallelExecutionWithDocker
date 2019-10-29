package com.main;

public class DriverFactory {
	
	private static String chromeDriverExePath;
	private static String geckoDriverExePath;
	private static String configPropertyFilePath;
	private static String gridPath;
	private static String executionMode;
	private static Integer waitTime;
	private static String overrideResults;
	private static String isScreenshotRequired;
	private static String testURL ;
	private static String resultPath;
	private static String screenshotPath;
	private static String testDataLocation;
	private static String extentConfigLocation;
	private static String passedStepsScreenshots;
	private static String failedStepsScreenshots;
	private static String skippedStepsScreenshots;
	private static String remoteMode;
	
	public static String getRemoteMode() {
		return remoteMode;
	}
	public static void setRemoteMode(String remoteMode) {
		DriverFactory.remoteMode = remoteMode;
	}
	public static String getPassedStepsScreenshots() {
		return passedStepsScreenshots;
	}
	public static void setPassedStepsScreenshots(String passedStepsScreenshots) {
		DriverFactory.passedStepsScreenshots = passedStepsScreenshots;
	}
	public static String getFailedStepsScreenshots() {
		return failedStepsScreenshots;
	}
	public static void setFailedStepsScreenshots(String failedStepsScreenshots) {
		DriverFactory.failedStepsScreenshots = failedStepsScreenshots;
	}
	public static String getSkippedStepScreenshots() {
		return skippedStepsScreenshots;
	}
	public static void setSkippedStepScreenshots(String skippedStepScreenshots) {
		DriverFactory.skippedStepsScreenshots = skippedStepScreenshots;
	}
	public static String getExtentConfigLocation() {
		return extentConfigLocation;
	}
	public static void setExtentConfigLocation(String extentConfigLocation) {
		DriverFactory.extentConfigLocation = extentConfigLocation;
	}
	public static String getTestDataLocation() {
		return testDataLocation;
	}
	public static void setTestDataLocation(String testDataLocation) {
		DriverFactory.testDataLocation = testDataLocation;
	}
	public static String getGridPath() {
		return gridPath;
	}
	public static void setGridPath(String gridPath) {
		DriverFactory.gridPath = gridPath;
	}
	private static boolean isRemote;
	
	
	public static String getConfigPropertyFilePath() {
		return configPropertyFilePath;
	}
	public static void setConfigPropertyFilePath(String configPropertyFilePath) {
		DriverFactory.configPropertyFilePath = configPropertyFilePath;
	}
	public static String getExecutionMode() {
		return executionMode;
	}
	public static void setExecutionMode(String executionMode) {
		DriverFactory.executionMode = executionMode;
	}
	public static Integer getWaitTime() {
		return waitTime;
	}
	public static void setWaitTime(Integer waitTime) {
		DriverFactory.waitTime = waitTime;
	}
	public static String getOverrideResults() {
		return overrideResults;
	}
	public static void setOverrideResults(String string) {
		DriverFactory.overrideResults = string;
	}
	public static String getIsScreenshotRequired() {
		return isScreenshotRequired;
	}
	public static void setIsScreenshotRequired(String string) {
		DriverFactory.isScreenshotRequired = string;
	}
	public static String getTestURL() {
		return testURL;
	}
	public static void setTestURL(String testURL) {
		DriverFactory.testURL = testURL;
	}
	public static String getResultPath() {
		return resultPath;
	}
	public static void setResultPath(String resultPath) {
		DriverFactory.resultPath = resultPath;
	}
	public static String getScreenshotPath() {
		return screenshotPath;
	}
	public static void setScreenshotPath(String screenshotPath) {
		DriverFactory.screenshotPath = screenshotPath;
	}
	public static boolean isRemote() {
		return isRemote;
	}
	public static void setRemote(boolean isRemote) {
		DriverFactory.isRemote = isRemote;
	}
	public static String getChromeDriverExePath() {
		return chromeDriverExePath;
	}
	public static void setChromeDriverExePath(String chromeDriverExePath) {
		DriverFactory.chromeDriverExePath = chromeDriverExePath;
	}
	public static String getGeckoDriverExePath() {
		return geckoDriverExePath;
	}
	public static void setGeckoDriverExePath(String geckoDriverExePath) {
		DriverFactory.geckoDriverExePath = geckoDriverExePath;
	}
	
	

}
