package Utility;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.DriverManager;

public class ElementUtil {
	private WebDriver driver=DriverManager.getDriver();
	
	public static ElementUtil eu = new ElementUtil();
	public void waitForPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver,Contants.small_wait);
	    wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver wdriver) {
	            return ((JavascriptExecutor) driver).executeScript(
	                "return document.readyState").equals("complete");
	        }
	    });
	    /*Wait<WebDriver> wait = new WebDriverWait(driver, 30);
	    wait.until(new Function<WebDriver, Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            System.out.println("Current Window State       : "
	                + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
	            return String
	                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
	                .equals("complete");
	        }
	    });*/
	}
	public void scrollTillElementByJS(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true)", ele);
	}
	public void scrollVertTillEnd() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("windows.scrollBy(0,document.body.scrollHeight)");
	}
	public void scrollByPageDown(WebDriver driver, int num) {
		for(int i=1; i<=num; i++) {
			driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
		}
	}
	public void clickByJS(WebElement ele, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		js.executeScript("arguments[0].click();", ele);
	}
	public ArrayList<String> getAllTextFromDDL(WebElement ele) {
		ArrayList<String> ar = new ArrayList<String>();
		Select sel = new Select(ele);
		for(int i=0; i<sel.getOptions().size(); i++) {
			ar.add(sel.getOptions().get(i).getText());
		}
		return ar;
	}
	public List<WebElement> getAllOptionsFromDDL(WebElement ele) {
		Select sel = new Select(ele);
		return sel.getOptions();
	}
	public int getNumberOfOptionsFromDDL(WebElement ele) {
		Select sel = new Select(ele);
		return sel.getOptions().size();
	}
	public void waitForVisiblityOfElementLocated(WebDriver driver, Duration time, String type, String locator) {
		WebDriverWait wt = new WebDriverWait(driver, time);
		switch(type) {
			case "id": wt.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
			break;
			case "clss": wt.until(ExpectedConditions.visibilityOfElementLocated(By.className(locator)));
			break;
			case "xpath": wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			break;
			case "css": wt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
			break;
		}
	}
	public boolean isAlertPresent(WebDriver driver, Duration time) {
		try {
			WebDriverWait wt = new WebDriverWait(driver,time);
			wt.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean waitForStaleElement(WebDriver driver, WebElement ele) {
		try {
			WebDriverWait wt = new WebDriverWait(driver,Contants.small_wait);
			wt.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(ele)));
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public void clickOnElement(WebElement element) {
		waitForElementToBeClickable(element);
		element.click();
	}
	public void waitForElementToBeClickable(WebElement element) {
		
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(element));
	}
	public void waitForElementDisplayed(WebElement element) {

		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(element));
	}
	public void typeInput(WebElement element, String input) {
		waitForElementDisplayed(element);
		element.clear();
		element.sendKeys(input);
	}
	public boolean isElementExist(WebElement element) {
		waitForElementDisplayed(element);
		return element.isDisplayed();
	}
	public void updateCellValue(String path, String sheetName, int rowNum, int cellNum, String input) {
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    Workbook workbook = null;

	    try {
	        fis = new FileInputStream(path);
	        workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        Row row = sheet.getRow(rowNum);
	        if (row == null) {
	            row = sheet.createRow(rowNum);
	        }

	        Cell cell = row.getCell(cellNum);
	        if (cell == null) {
	            cell = row.createCell(cellNum);
	        }
	        cell.setCellValue(input);

	        fos = new FileOutputStream(path);
	        workbook.write(fos);
	        fos.flush();

	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (fos != null) fos.close();
	            if (fis != null) fis.close();
	            if (workbook != null) workbook.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


	public String getCellValue(String fileName, String sheetName1, int rownum,int cellnum) {
		FileInputStream fis = null;
		try {
			fis=new FileInputStream(".\\src\\test\\resources\\org\\Excel\\"+fileName);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		Workbook workbook =null;
		try {
			workbook=new XSSFWorkbook(fis);
		}catch(IOException e) {
			e.printStackTrace();
		}
		Sheet sheet=workbook.getSheet(sheetName1);
		Row row =sheet.getRow(rownum);
		Cell cell=row.getCell(cellnum);
		return cell.getStringCellValue();
	}
	public void staleElementExceptionHandle(WebElement element) {
		for (int i=0; i<5; i++) {
			try {
				waitForElementDisplayed(element);
			}catch(Exception e) {
//				System.out.println("Exception");
			}
		}
	}
}
