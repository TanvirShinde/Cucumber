package testcases.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pages.LoginPage;
import testcases.AbstractComponent;
import testcases.BaseClass;

public class StepDefinition extends BaseClass{
 
	 @Given("^I click on tab$")
	 public void i_click_on_tab() {
		 getPageObject.getLoginPage().clickonTab();
	 }
	 
}
