package testcases;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.DriverManager;

public abstract class AbstractComponent<T>{

	public WebDriverWait wait;
	public WebDriver driver;
	public Actions actions;

	public AbstractComponent() {
		this.driver =DriverManager.getDriver();
		wait=new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	public T openPage(Class<T> clazz) {

		T page = null;
		try {
			driver =DriverManager.getDriver();
			AjaxElementLocatorFactory ajaxElemFactory = new AjaxElementLocatorFactory(driver, 20);
			page =PageFactory.initElements(driver, clazz);
			PageFactory.initElements(ajaxElemFactory, page);
			wait=new WebDriverWait(this.driver, Duration.ofSeconds(10));
		} catch (NoSuchElementException e) {

			throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
		}
		return page;
	}
}
