package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

	@FindBy (how = How.XPATH , using ="abc")
	WebElement logintab;
	
	@FindBy(xpath="abc")
	private WebElement btn;
	
	
	
}
