package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utility.ElementUtil;

public class DmartToValidation {
	
	private WebDriver driver;
	
	public DmartToValidation(WebDriver driver) {
		this.driver =driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="pincodeInput")
	private WebElement inputcity;
	
	@FindBy(css="div[class*=\"pincode-widget_pincode-right\"] b")
	private WebElement listLocation;
	
	@FindBy(xpath="//button[text()='START SHOPPING']")
	private WebElement startShopping;
	
	@FindBy(id="scrInput")
	private WebElement searchContent;
	
	@FindBy(xpath="//button[text()='Search']")
	private WebElement searchButton;
	
	@FindBy(css="div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3")
	private WebElement results;
	
	@FindBy(xpath="//button[text()='CONFIRM LOCATION']")
	private WebElement confirmButton;
	
	
	public void searchCity(String Location) {
	    ElementUtil.eu.clickOnElement(inputcity);
	    ElementUtil.eu.typeInput(inputcity, Location);
	    ElementUtil.eu.waitForStaleElement(driver, listLocation);

	    List<WebElement> cities = driver.findElements(By.cssSelector("div[class*=\"pincode-widget_pincode-right\"] b"));

	    for (WebElement cityElement : cities) {
	        String city = cityElement.getText();
	        if ("Gandhar Nagar, Khadakpada".equals(city)) {
	            cityElement.click();
	            break;
	        }
	    }

	    ElementUtil.eu.waitForStaleElement(driver, confirmButton);
	    ElementUtil.eu.clickOnElement(confirmButton);
	}

	
	public void searchGroc(String groc) {//cookies
		ElementUtil.eu.waitForStaleElement(driver ,searchContent);
		ElementUtil.eu.clickOnElement(searchContent);
		ElementUtil.eu.typeInput(searchContent, groc);
		
		ElementUtil.eu.clickOnElement(searchButton);//
	}
	
	public void cookiesDetails() {
	    // Wait for results container to be visible before interacting
	    ElementUtil.eu.waitForElementDisplayed(results);

	    // Scroll down to ensure all products are rendered
	    ElementUtil.eu.scrollByPageDown(driver, 50);

	    // Locators based on provided comments
	    String productContainer = "div[class*='vertical-card_card-vertical']";
	    String productName = "div[class*='vertical-card_title'] div";
	    String priceLocator = "div[class*='vertical-card_price-left'] > :nth-child(1) > :nth-child(2) > :nth-child(2)";
	    String dmartPriceLocator = "div[class*='vertical-card_price-left'] > :nth-child(2) > :nth-child(2) > :nth-child(2)";
	    String offPriceLocator = "div[class*='vertical-card_section-right'] p[class*='vertical-card_value'] > :nth-child(2)";

	    // Fetch product containers
	    List<WebElement> products = driver.findElements(By.cssSelector(productContainer));

	    for (int i = 0; i < products.size(); i++) {
	        WebElement product = products.get(i);

	        // Extract product name
	        String name = "";
	        try {
	            name = product.findElement(By.cssSelector(productName)).getText();
	        } catch (Exception e) {
	            name = "N/A";
	        }

	        // Extract MRP price
	        String mrp = "";
	        try {
	            mrp = product.findElement(By.cssSelector(priceLocator)).getText();
	        } catch (Exception e) {
	            mrp = "N/A";
	        }

	        // Extract Dmart price
	        String dmartPrice = "";
	        try {
	            dmartPrice = product.findElement(By.cssSelector(dmartPriceLocator)).getText();
	        } catch (Exception e) {
	            dmartPrice = "N/A";
	        }

	        // Extract Off price (if available)
	        String offPrice = "";
	        try {
	            offPrice = product.findElement(By.cssSelector(offPriceLocator)).getText();
	        } catch (Exception e) {
	            offPrice = "N/A";
	        }

	        int row = i + 1;
	        ElementUtil.eu.updateCellValue(".\\src\\test\\resources\\org\\Excel\\AppData.xlsx", "Sheet3", row, 1, name);
	        ElementUtil.eu.updateCellValue(".\\src\\test\\resources\\org\\Excel\\AppData.xlsx", "Sheet3", row, 2, mrp);
	        ElementUtil.eu.updateCellValue(".\\src\\test\\resources\\org\\Excel\\AppData.xlsx", "Sheet3", row, 3, dmartPrice);
	        ElementUtil.eu.updateCellValue(".\\src\\test\\resources\\org\\Excel\\AppData.xlsx", "Sheet3", row, 4, offPrice);

	        System.out.println(name + "   MRP: " + mrp + "   Dmart Price: " + dmartPrice + "   Off Price: " + offPrice);
	    }
	}

}
