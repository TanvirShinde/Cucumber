package stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pages.LoginPage;
import utilities.AbstractComponent;
import utilities.BaseClass;

public class StepDefinition extends BaseClass{
 
	 @Given("^I click on tab$")
	 public void i_click_on_tab() throws InterruptedException {
		 getPageObject.getLoginPage().applylogin();
	 }
	 
	 @Given("^I open URL$")
	 public void i_open_URL() throws Throwable{
		setUpDrivers();
	 }
	 
}
