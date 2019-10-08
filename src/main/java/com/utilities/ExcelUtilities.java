package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.main.DriverFactory;


public class ExcelUtilities {
	
	String path;
	String sheetname;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;

	
	/*
	 * Takes rowname and sheetname as parameter
	 * return row number based of rowname
	 */
		
	public  int getRowNumForRowName(String sheetname,String rowName) {
		int rownum=0;
		sheet=workbook.getSheet(sheetname);
		for(int i=1;i<=getLastRowNum(sheetname);i++) {
			if(rowName.equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue())) {
				rownum=i;
				break;
			}
		}

		return rownum;
	}

	
	/*
	 * Takes columnname and sheetname as parameter
	 * return column number based of columnheader
	 */

	public  int getColumnNumForColumnName(String sheetname, String columnname) {
		int colnum=0;
		sheet=workbook.getSheet(sheetname);
		for(int i=0;i<getLastColumnNum(sheetname, 0);i++) {
			if(columnname.equalsIgnoreCase(sheet.getRow(0).getCell(i).getStringCellValue())) {
				colnum=i;
				break;
			}
		}

		return colnum;

	}
	
	/*
	 * Takes sheetname as parameter
	 * return last row number of the sheet
	 */
	public  int getLastRowNum(String sheetname) {
		return workbook.getSheet(sheetname).getLastRowNum();
	}

	/*
	 * Takes sheetname, row number as parameter
	 * return last cell number of the row
	 */
	public  int getLastColumnNum(String sheetname, int rownum) {
		
		
		
		System.out.println(workbook);
		return workbook.getSheet(sheetname).getRow(rownum).getLastCellNum();
		
	}


	/*
	 * Takes sheetname, row number, column number as parameter
	 * return cell value
	 */
	public  String getCellContent(String sheetname,int rownum,int colnum) {
		sheet=workbook.getSheet(sheetname);
		
		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().concat("").toString();

		
	}

	/*
	 * Takes sheetname, row number, column name as parameter
	 * return cell value
	 */
	public  String getCellContent(String sheetname,int rownum,String columnname) {
		sheet=workbook.getSheet(sheetname);
		
		return sheet.getRow(rownum).getCell(getColumnNumForColumnName(sheetname, columnname)).getStringCellValue().concat("").toString();

	}

	/*
	 * Takes sheetname, row name, column name as parameter
	 * return cell value
	 */
	public  String getCellContent(String sheetname,String rowname,String columnname) {
		sheet=workbook.getSheet(sheetname);
		int rownum=getRowNumForRowName(sheetname, rowname);
		System.out.println(rownum);
		int colnum=getColumnNumForColumnName(sheetname, columnname);
		System.out.println(colnum);
		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().concat("").toString();

	}

	
	/*
	 * 
	 * DataProvider method used to provide data for multiple iterations.
	 * Never try to use multiple iterations when the invocation count is greater than 1. It may result in adhoc results.
	 * As long as the first name of the TestData has the same test case name it will be treated as iteration.
	 * 
	 */
	@DataProvider(name="dataProviderForIterations",parallel=true)
	public  Object[][] supplyDataForIterations(Method m){
		try {
		fis= new FileInputStream(DriverFactory.getTestDataLocation());
		
		workbook=new XSSFWorkbook(fis);
	
		sheet=workbook.getSheet(sheetname);
		
		}
		catch(Exception e) {
			
		}
		
		return getDataForDataprovider(DriverFactory.getTestDataLocation(),"TestData",m.getName());
	}

	/*
	 * Finding number of iterations available for test case and return the data accordingly.
	 * Using hashtable avoids multiple parameters entry to the test case.
	 * 
	 */
	public  Object[][] getDataForDataprovider(String testdata, String sheetname, String testcasename) {
		int totalcolumns=getLastColumnNum(sheetname, 0);
		ArrayList<Integer> rowscount=getNumberofIterationsForATestCase(sheetname, testcasename);
		Object[][] b=new Object[rowscount.size()][1];
		Hashtable<String,String> table =null;
		for(int i=1;i<=rowscount.size();i++) {
			table=new Hashtable<String,String>();
			for(int j=0;j<totalcolumns;j++){
				table.put(getCellContent(sheetname, 0, j), getCellContent(sheetname, rowscount.get(i-1), j));
				b[i-1][0]=table;
			}
		}
		return b;
	}
	
	/*
	 * Used to return the rownumber of the test cases for multiple iterations.
	 * Suppose if testcase 1 is available in row 4 and 7 is test data , it return the arraylist with values 4,7
	 */
	public  ArrayList<Integer> getNumberofIterationsForATestCase(String sheetname,String testcasename) {
		ArrayList<Integer> a=new ArrayList<Integer>();
		for(int i=1;i<=getLastRowNum(sheetname);i++) {
			if(testcasename.equalsIgnoreCase(getCellContent(sheetname, i, 0))) {
				a.add(i);
			}
		}
			
		return a;
	}

	
}
