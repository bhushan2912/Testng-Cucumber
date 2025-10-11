package Pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utility.SeleniumUtility;

public class SkillAcademyLogin extends SeleniumUtility {
	
	WebDriver driver;
	
	public SkillAcademyLogin (WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css="div#top-header li>a[title=\"Sign Up\"]")
	private WebElement signUpButton;
	
	@FindBy(css="ul.main-nav>:nth-child(5) svg")
	private WebElement skillTabLoggedout;
	
	@FindBy(id="onBoardingIframe")
	private WebElement loginIframe;
	
	@FindBy(css="input[placeholder=\"Please Enter your mobile number\"]")
	private WebElement numberInputField;
	
	@FindBy(xpath="//button[text()=\"continue\"]")
	private WebElement continueButton;
	
	@FindBy(xpath="//input[@name=\"otp\"]")
	private WebElement otpInputField;
	
	@FindBy(xpath="//button[text()=\"LOGIN\"]")
	private WebElement LoginButton;
	
	@FindBy(css=".post-header__action-nav.d-none.d-xl-block .circle-img")
	private WebElement profilePicIcon;
	
	@FindBy(css=".btn.text-white.btn-success.outline-none.px-4.ng-scope")
	private WebElement getPassButton;
	
	@FindBy(css="li.opt.dropdown.ml-2.show ul[aria-labelledby=\"profileDropdown\"]>:nth-child(5)>a")
	private WebElement logoutCta;
	
	@FindBy(css="a.skills")
	private WebElement skillAcademy;
	
	@FindBy(css="li.dropdown div.dropdown-menu>:nth-child(2)")
	private WebElement settings;
	
	@FindBy(css="form[name=\"updateUserInfo\"]>:nth-child(2) div.ng-binding")
	private WebElement nameField;
	
	@FindBy(xpath="//a[text()=\" Skill Academy \"]")
	private WebElement skillAcademyTab;
	
	@FindBy(css="ul.header__sidebar__nav>:nth-child(3)>a")
	private WebElement superTab;
	
	@FindBy(css="ul.header__sidebar__nav>:nth-child(6)>a")
	private WebElement skillNavBar;
	
	@FindBy(css="div.header__post a[title=\"Sign Up\"]")
	private WebElement getStarted;
	
	@FindBy(xpath="//button[text()=\"Sign up for free demo\"]")
	private WebElement signUpForFreeDemo;
	
	@FindBy(id="sidebar-hamburger")
	private WebElement sidebar;
	
	@FindBy(css="app-tabs.cie-sidebar__tab")
	private WebElement studyTabs;
	
	@FindBy(css="div.categories")
	private WebElement courseCategory;
	
	@FindBy(css="div.courses div a>:nth-child(2)>:nth-child(2)")
	private WebElement staleHandleForCourses;
	
	@FindBy(xpath="//a[text()=\"Explore Programs\"]")
	private WebElement exploreButton;
	
	@FindBy(xpath="explore-by-tabs>div.card span")
	private WebElement test1;
		
	public void loginBySkill() {
		clickOnElement(skillAcademyTab);
	}
	
	public void login(String user) {
		clickOnElement(signUpButton);
		driver.switchTo().frame(loginIframe);
		String urlBeforeLogin = driver.getCurrentUrl();
		System.out.println("UrlBeforeLogin :"+urlBeforeLogin);
		clickOnElement(numberInputField);
		typeInput(numberInputField, readPropertyFile(user));
		clickOnElement(continueButton);
		clickOnElement(otpInputField);
		typeInput(otpInputField, readPropertyFile("otp"));
		clickOnElement(LoginButton);
		driver.switchTo().defaultContent();
		staleElementExceptionHandle(getPassButton);
		clickOnElement(getPassButton);
		staleElementExceptionHandle(skillNavBar);
		clickOnElement(skillNavBar);
		String urlAfterLogin = driver.getCurrentUrl();
		System.out.println("UrlAfterLogin :"+urlAfterLogin);
		assertEquals(urlBeforeLogin, urlAfterLogin);
		System.out.println("Successfully Login");
	}
	
	public void logout() {
		String urlBeforeLogout = driver.getCurrentUrl();
		System.out.println("UrlBeforeLogout :"+urlBeforeLogout);
		clickOnElement(profilePicIcon);
		clickOnElement(logoutCta);
		waitForElementToBeClickable(getStarted);
		System.out.println("UrlAfteLogout :"+driver.getCurrentUrl());
		Assert.assertNotEquals(urlBeforeLogout, driver.getCurrentUrl());
		System.out.println("Successfully Logout");
		driver.close();
	}
	
	public void loginByMini(String slug, String user) {
		
		String url =driver.getCurrentUrl();
		System.out.println(url);
		driver.get(url+slug+"/online-coaching-course");
		String urlBeforeLogin = driver.getCurrentUrl();
		staleElementExceptionHandle(signUpForFreeDemo);
		clickOnElement(signUpForFreeDemo);
		driver.switchTo().frame(loginIframe);
		clickOnElement(numberInputField);
		typeInput(numberInputField, user);
		clickOnElement(continueButton);
		clickOnElement(otpInputField);
		typeInput(otpInputField, readPropertyFile("otp"));
		clickOnElement(LoginButton);
		driver.switchTo().defaultContent();
		waitForElementToBeVisible(studyTabs);
		String urlAfterLogin=driver.getCurrentUrl();
		Assert.assertNotEquals(urlBeforeLogin, urlAfterLogin);
		driver.close();
	}
	
	public void verifyCategorywiseCoursesLoggedOut(String user)  {
		clickOnElement(skillAcademyTab);
//		scrollDownPage("850");
		List <String> courseVerify=new ArrayList<String>();
		courseVerify.clear();
		clickOnElement(exploreButton);
		List <WebElement> listCategories=driver.findElements(By.cssSelector("div.sidebar-grid span.card-title"));//to find all categories
		for(int i=0; i<listCategories.size();i++) {
			WebElement category =listCategories.get(i);
			category.click();
//			System.out.println("Category :"+category.getText());
			String category1 =category.getText();
			Assert.assertEquals(category1, readPropertyFile("Category"+(i+1)));
			staleElementExceptionHandle(staleHandleForCourses);
			List <WebElement> listCourses =driver.findElements(By.cssSelector("skill-academy-program-card div.h3"));//To find all courses from individual category
			for(int k=0; k<listCourses.size();k++) {
				WebElement course = listCourses.get(k);
//				System.out.println("Course "+(k+1)+" :"+course.getText());
				courseVerify.add(course.getText());
			}
//			System.out.println();
			String getInitialOfCategory[]=category1.split("");
			for (int p =0;p<courseVerify.size();p++) {
				String initial=getInitialOfCategory[0];
				Assert.assertEquals(courseVerify.get(p), readPropertyFile("Course"+(p+1)+initial));
			}
			System.out.println("Courses verified for category in logged out state: "+category1);
			courseVerify.clear();
		}
		login(user);//will start from this line for logged in
		List <String> courseVerify1=new ArrayList<String>();
		courseVerify1.clear();
		clickOnElement(exploreButton);
		List <WebElement> listCategories1=driver.findElements(By.cssSelector("explore-by-tabs>div.card span"));
		for(int i=0; i<listCategories1.size();i++) {
			WebElement category =listCategories1.get(i);
			category.click();
//			System.out.println("Category :"+category.getText());
			String category1 =category.getText();
			Assert.assertEquals(category1, readPropertyFile("Category"+(i+1)));
			staleElementExceptionHandle(staleHandleForCourses);
			List <WebElement> listCourses1 =driver.findElements(By.cssSelector("skill-academy-program-card div.h3"));
			for(int k=0; k<listCourses1.size();k++) {
				WebElement course = listCourses1.get(k);
//				System.out.println("Course "+(k+1)+" :"+course.getText());
				courseVerify1.add(course.getText());
			}
//			System.out.println();
			String getInitialOfCategory[]=category1.split("");
			for (int p =0;p<courseVerify1.size();p++) {
				String initial=getInitialOfCategory[0];
				Assert.assertEquals(courseVerify1.get(p), readPropertyFile("Course"+(p+1)+initial));
			}
			System.out.println("Courses verified for category in logged in state: "+category1);
			courseVerify1.clear();
		}
		driver.close();;
	}
	
}
	
	