package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollUpScrollDown extends BaseClass {

	public static WebDriver dr;

	public static void scroll () {
		JavascriptExecutor jk = ( JavascriptExecutor ) dr;
		jk.executeScript("scrollBy (0 ,200)");
	}

	public static void scrollupEle (WebElement ele) {
		JavascriptExecutor jk = ( JavascriptExecutor ) dr;
		jk.executeScript("arguments[0].scrollIntoView(true);", ele); 
	}
}
