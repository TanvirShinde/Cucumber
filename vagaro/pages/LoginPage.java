package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import testcases.BaseClass;
import testcases.AbstractComponent;

public class LoginPage extends AbstractComponent {

	@FindBy (how = How.XPATH , using ="abc")
	WebElement logintab;
	
	@FindBy(xpath="abc")
	private WebElement btn;
	
	public void clickonTab() {
		System.out.println("Print1");
	
		System.out.println("Print2");
		logintab.click();
	}
	
}
