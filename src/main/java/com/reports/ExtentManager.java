package com.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.main.DriverFactory;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;


public class ExtentManager {
	
	private static ExtentReports extent;
	private String extentreportpath;
	
	public  ExtentReports getInstance() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy_ hh_mm_ss");
		Date date = new Date();
		String currentDate = formatter.format(date);
		if(DriverFactory.getOverrideResults().equalsIgnoreCase("yes")) 
		{
			if(DriverFactory.getResultPath().equals("")) 
			{
				extentreportpath=".\\ExtentReports\\Test Report.html";
			}
			else {
				extentreportpath=DriverFactory.getResultPath()+"\\ExtentReports\\Test Report.html";
			}
		}
		else 
		{
			if(DriverFactory.getResultPath().equals("")) 
			{
				extentreportpath=".\\ExtentReports\\Test Report_"+currentDate+".html";
			}
			
			else
			{
				extentreportpath=DriverFactory.getResultPath()+"\\ExtentReports\\Test Report_"+currentDate+".html";
				
			}

		}
		if(extent==null) {
			extent = new ExtentReports(extentreportpath,true,DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(DriverFactory.getExtentConfigLocation()));
		}
		return extent;
		//returns Extent obj
	}

}
