package testcases;

import io.cucumber.java.en.And;

public class StepDefinition extends BaseClass{

	 @And("I_click_on_tab")
	 public void clickonTab() {
		 
		 getPageObject.getLoginPage().clickonTab();
	 }
}
