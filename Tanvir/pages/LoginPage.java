package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import utilities.AbstractComponent;
import utilities.BaseClass;
import utilities.ScrollUpScrollDown;

public class LoginPage extends AbstractComponent {

	@FindBy (how = How.XPATH , using ="//label[contains(text(),'Username')]")
	WebElement input_username;
	
	@FindBy (how = How.XPATH , using ="//input[@id='password']")
	WebElement input_pwd;
	
	@FindBy (how = How.XPATH , using ="//button[@id='submit']")
	WebElement btn_login;
	
	@FindBy (how = How.XPATH , using ="//a[contains(text(),'Log out')]")
	WebElement btn_logout;
	
	@FindBy (how = How.XPATH , using ="//h1[contains(text(),'Logged In Successfully')]")
	WebElement txt_successtext;
	
	@FindBy(xpath="abc")
	private WebElement btn;
	
	public void applylogin() throws InterruptedException {
		boolean flag =false;
		Thread.sleep(3000);
		ScrollUpScrollDown.scrollupEle(input_username);
		input_username.sendKeys("student");
		Thread.sleep(3000);
		ScrollUpScrollDown.scrollupEle(input_pwd);
		input_pwd.sendKeys("Password123");
		Thread.sleep(3000);
		btn_login.click();
		if(txt_successtext.isDisplayed())
			flag=true;
		if(flag=true)
			System.out.println("Login successfully done practice automation");
		Thread.sleep(3000);
		ScrollUpScrollDown.scrollupEle(btn_logout);
		btn_logout.click();

	}
	
}
