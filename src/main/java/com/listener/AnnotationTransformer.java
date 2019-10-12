package com.listener;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.utilities.Constants;
import com.utilities.ExcelUtilities;


public class AnnotationTransformer implements IAnnotationTransformer{
	int count=0;

	ExcelUtilities excel= new ExcelUtilities();


	ArrayList<String> testcases= new ArrayList<String>();
	ArrayList<String> testDescription= new ArrayList<String>();
	ArrayList<String> runStatus= new ArrayList<String>();


	@Override
	public void transform(ITestAnnotation annotation, Class arg1, Constructor arg2, Method testMethod) {
		try {

			excel.fis=new FileInputStream(Constants.testDataLocation);
			excel.workbook= new XSSFWorkbook(excel.fis);
			excel.sheet=excel.workbook.getSheet("RunManager");
			loadTestCases();
			for(int i=0;i<testcases.size();i++) {
				if(testMethod.getName().equalsIgnoreCase(testcases.get(i)))
				{	
					annotation.setDataProvider("dataProviderForIterations");								//sets the dataprovider to all the test methods
					annotation.setDataProviderClass(ExcelUtilities.class);

					annotation.setRetryAnalyzer(RetryFailedTestCases.class); 								//sets the retry analyser for all the test cases
					annotation.setDescription(testDescription.get(i)); 							//sets the description for all the test cases based on the excel sheet input
					if(runStatus.get(i).equalsIgnoreCase("no")) {
						annotation.setEnabled(false);														//sets the enabled parameter for all the test cases based on the excel sheet input
						break;
					}
				} 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}



	private void loadTestCases() {
		if(count==0) {
			for(int i=1;i<=excel.getLastRowNum("RunManager");i++) {
				
				testcases.add(excel.getCellContent("RunManager", i, "TestCaseName"));
				testDescription.add(excel.getCellContent("RunManager", i, "Test Case Description"));
				runStatus.add(excel.getCellContent("RunManager", i, "Execute"));
			}
		}
	}

}
