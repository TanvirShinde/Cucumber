package pages;

import testcases.AbstractComponent;

public class GetPageObject extends AbstractComponent{

	public LoginPage getLoginPage() {
		return (LoginPage) openPage(LoginPage.class);
	}
}



