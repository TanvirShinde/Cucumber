package utilities;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractComponent<T>{

	public WebDriverWait wait;
	public static WebDriver driver;
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

	protected boolean waitForElementToAppear(WebElement element) {
		boolean webElementPresence = false;
		try {
			Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
					.withTimeout(Duration.ofSeconds(60)).ignoring(NoSuchElementException.class);
			fluentwait.until(ExpectedConditions.visibilityOf(element));
			if(element.isDisplayed()) {
				webElementPresence = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return webElementPresence;	
	}

	protected boolean waitForElementToEnable(WebElement element) {
		boolean webElementPresence = false;
		try {
			Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
					.withTimeout(Duration.ofSeconds(8)).ignoring(NoSuchElementException.class);
			fluentwait.until(ExpectedConditions.visibilityOf(element));
			if(element.isDisplayed()) {
				webElementPresence = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return webElementPresence;	
	}

	protected void waitForFrameToBeAvailableAndSwitch(final WebElement element) {
		try {
			Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
					.withTimeout(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class).ignoring(org.openqa.selenium.NoSuchElementException.class);
			fluentwait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));	
		} catch (Exception e) {
			e.getStackTrace();
		}		
	}

	protected boolean waitForListofElementToAppear(List<WebElement> element) {
		boolean webElementPresence = false;
		try {
			Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
					.withTimeout(Duration.ofSeconds(60)).ignoring(NoSuchElementException.class).ignoring(org.openqa.selenium.NoSuchElementException.class);
			fluentwait.until(ExpectedConditions.visibilityOfAllElements(element));
			if(element.get(element.size()-1).isDisplayed()) {
				webElementPresence = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return webElementPresence;	
	}

	protected void selectOptionByVisibleText(WebElement element,String optionToSelect) {
		if(element !=null) {
			Select option = new Select(element);
			if(option !=null) {
				option.selectByVisibleText(optionToSelect);
			}
		}
	}

	protected void selectOptionByValue(WebElement element,String value) {
		if(element !=null) {
			Select option = new Select(element);
			if(option !=null) {
				option.selectByValue(value);
			}
		}
	}

	protected void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

	protected void switchToFrame(String ele) {
		driver.switchTo().frame(ele);
	}

	protected void switchToActiveElement() {
		driver.switchTo().activeElement();
	}

	protected void selectElementFromList(List<WebElement> list ,String name) {
		for(WebElement temp : list) {
			if(temp.getText().contains(name)) {
				temp.click();
				break;
			}
		}
	}

	protected void Wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected String generateRandomEmailId(String email) {
		String [] arr = email.split("@");
		String emailS = arr[0]+ThreadLocalRandom.current().nextInt(10000,10000)+"@"+arr[1];
		return emailS;
	}

	protected int generateRandomNumber() {
		return Integer.max(ThreadLocalRandom.current().nextInt(1,999999999), ThreadLocalRandom.current().nextInt(1,999999999));
	}

	protected String generateRandomString(int length) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for(int i=0;i<length;i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	protected void UrlContains (String txt) {
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(60)).ignoring(NoSuchElementException.class);
		fluentwait.until(ExpectedConditions.urlContains(txt));
	}

	protected void UrlMatch(String txt) {
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(20)).ignoring(NoSuchElementException.class);
		fluentwait.until(ExpectedConditions.urlMatches(txt));
	}

	protected void waitForElementToClicable(WebElement ele) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(80)).ignoring(NoSuchElementException.class).ignoring(ElementNotInteractableException.class).ignoring(IllegalStateException.class).ignoring(StaleElementReferenceException.class);
		try {
			//	fluentwait.until(WebDriver -> j.executeScript("return document.ready state").toString().equals("complete"));
			fluentwait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		fluentwait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	protected void navigateTo(WebElement e) {
		String temp =e.getAttribute("href");
		driver.navigate().to(temp);
	}

	protected void clickToElement(WebElement ele) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
		ele.click();
	}

	protected void clickToElementAndSendInput(WebElement ele ,String txt) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
		ele.sendKeys(txt);
	}

	protected void clickByJavaScript(WebElement ele) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click();", ele);	
	}

	protected void selectToElementFromListByJavaScript(List<WebElement> element ,String option) {
		for(int i=0;i<element.size();i++) {
			if(element.get(i).getText().contains(option)) {
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("arguments[0].click();", element.get(i));	
			}
		}
	}

	public void isAlertPresent() {
		try {
			Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(3))
					.withTimeout(Duration.ofSeconds(10)).ignoring(UnhandledAlertException.class);
			if(fluentwait.until(ExpectedConditions.alertIsPresent()) != null) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rightClickOnElement(WebElement ele) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
		actions = new Actions(driver);
		actions.moveToElement(ele).contextClick();
		actions.build().perform();
	}

	public void hoverOnElementFromList(List<WebElement> list ,String ele) {
		actions = new Actions(driver);
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contains(ele)) {
				actions.moveToElement(list.get(i)).perform();
				break;	
			}
		}	
	}

	protected void scrollelement(WebElement ele) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	protected void hoverOnWebelement(WebElement element) {
		actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	protected String getFirstSelectedOption(WebElement element) {
		Select option = new Select(element);
		String Selectedtext = option.getFirstSelectedOption().getText();
		return Selectedtext;
	}

	protected void waitForPageToLoad() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(100)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class);
		//		fluentwait.until(WebDriver -> j.executeScript("return document.ready state").toString().equals("complete"));
	}

	protected void doubleClickOnelement(WebElement element) {
		actions = new Actions(driver);
		actions.doubleClick(element).click().perform();
	}

	protected void clickusingActionClass(WebElement element) {
		actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void waitForElementToDisappear(WebElement ele) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(100)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			fluentwait.until(ExpectedConditions.invisibilityOf(ele));
			//	fluentwait.until(Boolean->(boolean)ele.getLocation().toString().equals("(0, 0)"));
			//	fluentwait.until(ExpectedConditions.and(ExpectedConditions.invisibilityOf(ele),driver -> !ele.isDisplayed()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Boolean isElemAttribEqualTo(WebElement element, String attribute, String attributevalue) {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			return fluentwait.until(ExpectedConditions.attributeContains(element, attribute, attributevalue));
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForListOfElementToDisappear(List<WebElement> ele) {
		long start = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(50)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			fluentwait.until(ExpectedConditions.and(ExpectedConditions.invisibilityOfAllElements(ele),driver -> !ele.get(0).isDisplayed()));
			fluentwait.until(Boolean->(boolean)ele.get(0).getLocation().toString().equals("(0, 0)"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long finish = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		long timeElapsed = finish -start;
	}
	//Need to check method argument again
	public void waitUntilLoaderDisabled(List<WebElement> ele) {
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(50)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			fluentwait.until(ExpectedConditions.and(ExpectedConditions.invisibilityOfAllElements(ele)));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//Need to check method argument again
	public void waituntilLoaderIsGone(List<WebElement> ele) {
		Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofSeconds(5))
				.withTimeout(Duration.ofSeconds(50)).ignoring(NoSuchElementException.class).ignoring(IllegalStateException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			for (int i=0;i<ele.size();i++) {
				fluentwait.until(ExpectedConditions.invisibilityOf(ele.get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
