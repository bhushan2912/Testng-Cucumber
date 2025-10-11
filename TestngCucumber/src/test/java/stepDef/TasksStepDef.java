package stepDef;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import Base.DriverManager;
import Pages.LoginPage;
import Pages.TasksPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TasksStepDef {
	
	private LoginPage lp_obj = new LoginPage(DriverManager.getDriver());
	private TasksPage tp_Obj;
	private String actualTitle;
	
	@Given("User has already logged in to application")
	public void user_has_already_logged_in_to_application(DataTable credTable) {
		List<Map<String, String>> credList = credTable.asMaps();
		String userName = credList.get(0).get("username");
		String password = credList.get(0).get("password");
		System.out.println("userName : "+userName);
		System.out.println("password : "+password);
		DriverManager.getDriver().get("http://localhost/login.do");
		tp_Obj = lp_obj.doLogin(userName, password);
	}

	@Given("User is on dashboard page")
	public void user_is_on_dashboard_page() {
		actualTitle = tp_Obj.getTasksPageTitle();
		System.out.println("Dashboard page title : "+actualTitle);
	}

	@Then("User gets dashboard")
	public void user_gets_dashboard(DataTable tabNamesTable) {
		List<String> expTabList = tabNamesTable.asList();
		System.out.println("Expected Tab Names: "+expTabList);
		List<String> actTabList = tp_Obj.getDashboardTabsList();
		System.out.println("Actual Tab Names: "+actTabList);
		Assert.assertTrue(expTabList.containsAll(actTabList));
	}

	@Then("Tabs count should be {int}")
	public void tabs_count_should_be(Integer expCount) {
		int actCount = tp_Obj.getDashboardTabsCount();
		System.out.println("Expected count = "+expCount);
		System.out.println("Actual count = "+actCount);
		Assert.assertTrue(expCount==actCount);
	}
}
