package pages;

import utilities.AbstractComponent;
import utilities.BaseClass;

public class GetPageObject extends AbstractComponent{

	public LoginPage getLoginPage() {
		return (LoginPage) openPage(LoginPage.class);
	}
}



