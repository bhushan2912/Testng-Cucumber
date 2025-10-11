package Utility;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumUtility {

	public static WebDriver driver;
//	public static SoftAssert sA;
	protected static Properties properties;
	protected static Actions action;
	protected FileInputStream fis;
	protected static String filePath;
	protected static WebDriverWait wait;
	protected static String browserName;
	protected static String appUrl;
	
	/**
	 * using this method we can load our property and also open desired browser
	 */
	//{
	//	filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\testAppData.properties";
	//}
	
	public WebDriver setUp() {
		filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\testAppData.properties";
		properties = new Properties();
		try {
			fis = new FileInputStream(filePath);
			properties.load(fis);
		}catch(Exception e) {
			e.printStackTrace();		
			}
		browserName=properties.getProperty("browser");
		appUrl=properties.getProperty("url");
		
		if(browserName.equalsIgnoreCase("chrome")){
			WebDriverManager.chromedriver().setup();
//			System.setProperty("webdriver.chrome.driver", ".\\Executables\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			
		}else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(appUrl);
		wait= new WebDriverWait(driver, Duration.ofSeconds(60));
		action =new Actions(driver);
		return driver;
		
	}
	
	public String readPropertyFile(String key) {
		filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\testAppData.properties";
		properties = new Properties();
		try {
			fis = new FileInputStream(filePath);
			properties.load(fis);
		}catch(Exception e) {
			e.printStackTrace();		
			}
		return properties.getProperty(key);
	}
	
	public void mouseHoverOnElement(WebElement element) {
		action.moveToElement(element);
		
	}
	
	public void waitForElementToBeClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void waitForElementToBeVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void typeInput(WebElement element, String input) {
		waitForElementToBeVisible(element);
		element.clear();
		element.sendKeys(input);
	}
	public void clickOnElement(WebElement element) {
		waitForElementToBeVisible(element);
		element.click();
	}
	
	public void scrollDownPage(String pageDownValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,"+pageDownValue+")", "");
	}
	
	public void scrollUpPage(String pageUpValue) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,"+pageUpValue+")", "");
	}
	
	public void clickOnElementByjs(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
//	public SoftAssert softAssertion() {
//		SoftAssert sA =new SoftAssert();
//		return sA;
//	}
	
	public void staleElementExceptionHandle(WebElement element) {
		for (int i=0; i<5; i++) {
			try {
				waitForElementToBeVisible(element);
			}catch(Exception e) {
//				System.out.println("Exception");
			}
		}
	}
	
	

}
