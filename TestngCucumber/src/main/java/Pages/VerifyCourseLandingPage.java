package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import Utility.SeleniumUtility;

import org.testng.Assert;


public class VerifyCourseLandingPage extends SeleniumUtility {
	
	WebDriver driver;
	
	SoftAssert sa= new SoftAssert();
	
	public VerifyCourseLandingPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="#skillProgramPageOverview>div>:nth-child(4) p")
	private WebElement miniCourseOverviewTabContent;
	
	@FindBy(css="#skillProgramPageSyllabus div.skill-course-desc>h2")
	private WebElement curriculumTitle;
	
	@FindBy(css="#skillProgramPageInstructors>div>h2")
	private WebElement instructorTitle;
	
	@FindBy(css="div.course-selling__banner div.course-title h1")
	private WebElement classTitle;
	
	@FindBy(css="div#freeCertificationCarousel0>:last-child>div>div>div>a")
	private WebElement miniCourses;// use it by findElements to find all mini courses
	
	@FindBy(css="skill-certificate#skillProgramPagePlacements>div>div>h3")
	private WebElement certificateTitle;
	
	@FindBy(css="skill-certificate#skillProgramPagePlacements div.card>img")
	private WebElement certificates;
	
	@FindBy(css="#skillProgramPageFreeDemo .skill-course-desc>h2")
	private WebElement demoTitle;
	
	@FindBy(css="div.snap-scroll #cardC>div .card")
	private WebElement demoModules;
	
	@FindBy(css="#skillProgramPageFaqs>div>h2")
	private WebElement faqTitle;
	
	@FindBy(css="#skillProgramPageFaqs>div div.card.faq__content.ui-js-faq-section-content.active")
	private WebElement faqActiveTab;
	
	public void verifyMiniCoursePage(String courseSlug, String user) {
		String url=driver.getCurrentUrl();
		driver.get(url+courseSlug+"/online-coaching-course");
		String classTitleh=classTitle.getText();
		Assert.assertEquals("Course Title as expected and actual not matched", readPropertyFile(courseSlug), classTitleh);
		List <WebElement> miniCourseTabs=driver.findElements(By.cssSelector("div.tabs__list.tabs__list--skill div"));
		
		for(int i=0; i<miniCourseTabs.size();i++) {//miniCourseTabs.size()
			WebElement tab=miniCourseTabs.get(i);
			tab.click();
			String tabName=tab.getText();
			System.out.println(tabName);
			System.out.println("Tab "+tab.getText()+(" clicked"));
			
			switch(tabName) {//using switch case for switch verification as per tabs
			  case "Overview":
				  String overviewContent=miniCourseOverviewTabContent.getText();
//				  boolean status1;
//					if(!(overviewContent.equals("")) || !(overviewContent.equals(null))) {
//						status1=true;
//					}else {
//						status1=false;
//					}
					sa.assertTrue((!(overviewContent.equals("")) || !(overviewContent.equals(null))), "Overview may contains empty string or null");
					System.out.println();
			    break;
			  case "Curriculum":
				    int subjectCount=0;
					int moduleCount=0;
					Assert.assertEquals("Course Curriculum & Syllabus", curriculumTitle.getText());
					List<WebElement> subjects=driver.findElements(By.cssSelector("div.curriculum>:first-child div"));
					for(int s=0;s<subjects.size();s++) {
						clickOnElementByjs(subjects.get(s));
						System.out.println("Subject "+(s+1)+": "+subjects.get(s).getText());
						List<WebElement> modules=driver.findElements(By.cssSelector("div.accordion__body div.course-panel__title"));
						for(int m=0;m<modules.size();m++) {
							System.out.println("Module "+(m+1)+" :"+modules.get(m).getText());
							moduleCount++;
						}
						subjectCount++;
					}
//					if(subjectCount!=0 && moduleCount!=0) {
//						status2=true;
//					}else {
//						status2=false;					
//					}
					sa.assertTrue((subjectCount!=0 && moduleCount!=0), "Curriculum is empty");
					System.out.println();
			    break;
			  case "Instructors":
				  String instructorTitleH =instructorTitle.getText();
				  System.out.println("Instructor title: "+instructorTitleH);
//				  boolean status3;
				  List <WebElement> instructors =driver.findElements(By.cssSelector("#skillProgramPageInstructors div.card__profile-content h3"));
				  for (int in=0;in<instructors.size();in++) {
					  System.out.println("Instructor "+(in+1)+" of "+classTitleh+" class :"+instructors.get(in).getText());
				  }
//				  if(instructorTitleH.equals("Meet Your Instructors") && instructors.size()>0) {
//					  status3=true;
//				  }else {
//					  status3=false;
//				  }
				  sa.assertTrue((instructorTitleH.equals("Meet Your Instructors") && instructors.size()>0), "Instructor title may not present or instructors may not added");
				  System.out.println();
				break;
			  case "Certificate":
//				  boolean status4;
				  String certificateTitleH=certificateTitle.getText();
//				  Assert.assertEquals("Earn Your Certificate", certificateTitleH);
				  String certificateLink=certificates.getAttribute("src");
				  String expectedCourseNamePng=readPropertyFile(courseSlug).replaceAll(" ", "%20");
//				  if(certificateLink.contains(expectedCourseNamePng) && certificateTitleH.equals("Earn Your Certificate")) {
//					  status4=true;
//				  }else{
//					  status4=false;
//				  };
				  sa.assertTrue((certificateLink.contains(expectedCourseNamePng) && certificateTitleH.equals("Earn Your Certificate")), "certificate title may not as expected or certificate not added");
				  System.out.println();
				  break;
			  case "Free Demo":
				  int demoCount=0;
				  String fDemoTitle =demoTitle.getText();
				  List<WebElement> fDemoModules=driver.findElements(By.cssSelector("div.snap-scroll #cardC>div .card"));
				  List<WebElement> fDemoModuleName = driver.findElements(By.cssSelector("div.snap-scroll #cardC>div .card>div>:nth-child(2)"));
				  for(int fd=0;fd<fDemoModules.size();fd++) {
					  demoCount++;
					  System.out.println("Demo modules "+(fd+1)+": "+fDemoModuleName.get(fd).getText());
				  }
				  sa.assertTrue(((fDemoTitle.equals("Free Demo")) && (demoCount!=0)), "Free demo title may not as expected or demo modules not added");
				  System.out.println();
				break;
			  case "FAQs":
				  String tFaqs=faqTitle.getText();
				  int cFaqType=0;
				  int cFaqQ=0;
				  List <WebElement> faqType = driver.findElements(By.cssSelector("#skillProgramPageFaqs .faq__type>div"));
				  for(int f=0;f<faqType.size();f++) {
					  System.out.println(faqType.get(f).getText());
					  if(f!=0) {
					  faqType.get(f).click();
					  }
					  List<WebElement> faqQ=driver.findElements(By.cssSelector("#skillProgramPageFaqs>div div.active>div>:first-child"));
					  for (int fq=0;fq<faqQ.size();fq++) {
						  System.out.println(faqQ.get(fq).getText());
						  cFaqQ++;
					  }
					  System.out.println();
					  cFaqType++;
					  }
				  sa.assertTrue((cFaqType==2) && (cFaqQ==14) && (tFaqs.equals("FAQs")), "FAQ title may not as expected or FAQs not added ");
				  
			  default:
			    System.out.println("Clicked tab is not present in expected tab list");
			}
		
		}
		sa.assertAll();
	}

}
