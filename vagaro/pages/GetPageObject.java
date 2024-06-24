package pages;

import testcases.AbstractComponent;
import testcases.BaseClass;

public class GetPageObject extends AbstractComponent{

	public LoginPage getLoginPage() {
		return (LoginPage) openPage(LoginPage.class);
	}
}



