package utilities;

import org.openqa.selenium.WebDriver;

public class DriverManager {

	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
	
	public static WebDriver getDriver () {
		
		return dr.get();
	}
	
	public static void setWebDriver (WebDriver driver) {
		
		dr.set(driver);
	}
	
	public static void closeBrowser() {
		
		dr.get().quit();
		dr.remove();
	}
}
