package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import io.cucumber.datatable.DataTable;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable;
import pages.GetPageObject;

public class BaseClass {

	public WebDriver d;
	public static WebDriver dr;
	public static Logger logger;
	public Properties propertyFileObj;
	public static GetPageObject getPageObject;
	public static Hashtable<String, String> data = new Hashtable<String, String>();
	public static int threadcount;
	ArrayList id = new ArrayList();
	static int countNameOfThread = 1;
	public static ThreadLocal<DevTools> chromeDevTOOLS = new ThreadLocal<DevTools> ();


	public static int getthreadCount() {
		String threadCount ="1";
		try {
			BufferedReader bfr = new BufferedReader(
					new FileReader(".\\batchFileWithAllDetails.bat"));                     
			String line;
			while ((line = bfr.readLine()) != null) {
				if(line.contains("threadcount"))
					threadCount = line.split("=")[1];				 
			} 
			bfr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(threadCount ==null) {
			threadCount="1";
			System.out.println(threadCount);
		}
		threadcount =Integer.parseInt(threadCount);
		return Integer.parseInt(threadCount);
	}

	@BeforeTest(alwaysRun=true)
	public void setUpDrivers() {
		System.out.println("Current Thread ID:" + Thread.currentThread().getId());
		id.add(Thread.currentThread().getId());
		if(countNameOfThread>threadcount)
			countNameOfThread=1;
		Thread.currentThread().setName(String.valueOf(countNameOfThread++));
		String browserType =null;
		Logger.getLogger("Current Thread ID:" + Thread.currentThread().getId());
		try {
			BufferedReader bfr =new BufferedReader(new FileReader(".\\batchFileWithAllDetails.bat"));
			String line;
			while((line = bfr.readLine()) !=null) {
				if(line.contains("browser"))
					browserType = line.split("=")[1];
			}
			bfr.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		System.out.println(threadcount);
		if(threadcount<2 || threadcount==0) {
			System.out.println("If condition" + threadcount);
		if(dr!=null) {
			StoreCommonData.drivelink=true;
		}
		else if(browserType.equalsIgnoreCase("chrome")){
			WebDriverManager.chromedriver().setup();
			dr =new ChromeDriver();
			dr.manage().window().maximize();
			DriverManager.setWebDriver(dr);
			getPageObject = new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			dr =new FirefoxDriver();
			dr.manage().window().maximize();
			DriverManager.setWebDriver(dr);
			getPageObject = new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equalsIgnoreCase("IE")){
			WebDriverManager.iedriver().setup();
			dr =new InternetExplorerDriver();
			dr.manage().window().maximize();
			DriverManager.setWebDriver(dr);
			getPageObject = new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equalsIgnoreCase("edge")){
			WebDriverManager.edgedriver().setup();
			dr =new EdgeDriver();
			dr.manage().window().maximize();
			DriverManager.setWebDriver(dr);
			getPageObject = new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equalsIgnoreCase("headless")){
			WebDriverManager.chromedriver().setup();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			option.addArguments("--headless=new","--window-size=1920,1080","--disable-gbu","--ignore-certificate-error","--silent");
			capabilities.setCapability(ChromeOptions.CAPABILITY, option);
			option.merge(capabilities);
			d=new ChromeDriver(option);
			DriverManager.setWebDriver(dr);
			getPageObject = new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
		}
		}
		else {
			if(browserType.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--disable-notifications");
				option.addArguments("--disable-extensions","--disable-extensions-file-access-check","--disable-extensions-http-throttling","--enable-automation","--disable-infobars");
				capabilities.setCapability("unhandledPromptBehavior", "accept");
				capabilities.setCapability("unhandledAlertBehavior", "accept");
//				option.addArguments("--incongnito");
//				option.addArguments("--blink-settings=imagesEnabled=false");
//				capabilities.setCapability(ChromeOptions.CAPABILITY, option);
				option.merge(capabilities);
				d=new ChromeDriver(option);
				d.manage().window().maximize();
				DriverManager.setWebDriver(dr);
				getPageObject = new GetPageObject();
//				logs();
				dr.get(getDataFromPropertyFile("env"));
				d.manage().deleteAllCookies();
//				DevTools chromeDevTools = ((ChromeDriver)d).getDevTools();
//				this.chromeDevTOOLS.set(((ChromeDriver)d).getDevTools());
			}
			else if(browserType.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				d = new FirefoxDriver();
				d.manage().window().maximize();
				DriverManager.setWebDriver(d);
				getPageObject = new GetPageObject();
				d.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equalsIgnoreCase("IE")) {
				WebDriverManager.iedriver().setup();
				d =new InternetExplorerDriver();
				d.manage().window().maximize();
				DriverManager.setWebDriver(d);
				getPageObject = new GetPageObject();
				d.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				d =new EdgeDriver();
				d.manage().window().maximize();
				DriverManager.setWebDriver(d);		
				getPageObject = new GetPageObject();
				d.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equalsIgnoreCase("headless")) {
				WebDriverManager.chromedriver().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--disable-extensions","--disable-extensions-file-access-check","--disable-extensions-http-throttling","--enable-automation","--disable-infobars");
				option.addArguments("--disable-notifications");
            	option.addArguments("--blink-settings=imagesEnabled=false");
            	option.addArguments("--headless=new","--window-size=1920,1080","--disable-gbu","--ignore-certificate-error","--silent");
            	capabilities.setCapability("unhandledPromptBehavior", "accept");
				capabilities.setCapability("unhandledAlertBehavior", "accept");
				capabilities.setCapability(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, option);
				option.merge(capabilities);
				d=new ChromeDriver(option);
			//	d=new ChromeDriver(capabilities);
				DriverManager.setWebDriver(d);
				getPageObject = new GetPageObject();
				d.get(getDataFromPropertyFile("env"));
				d.manage().deleteAllCookies();
			//	this.chromeDevTOOLS.set(((ChromeDriver)d).getDevTools());
			}
		}
	}
	
	public String getDataFromPropertyFile(String data) {
		String getProperty = null;
		if (data.equals("fromEmail") || data.equals("password")) {
			propertyFileObj = new Properties();
			try {
				FileInputStream objfile = new FileInputStream(".//utilities/configuration.properties");
				propertyFileObj.load(objfile);
				getProperty = propertyFileObj.getProperty(data);
			} catch (Exception e) {

			}
		}
		else {
			try {
				BufferedReader bfr = new BufferedReader(new FileReader( ".//batchFileWithAllDetails.bat" ));
				String line;
				while((line = bfr.readLine()) !=null) {
					if(line.contains(data))
						getProperty =line.split("=")[1];
				}
				bfr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getProperty;
	}
	
	public void logs() {	
		logger = Logger.getLogger("TestCucumberFramework");
  		PropertyConfigurator.configure(".//log4j.properties");
	}
	
	public void quiteDriver() {
	utilities.DriverManager.closeBrowser();
	}
	
	protected void openNewTab(DataTable data) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<List<String>> url = data.asLists();
		if(threadcount<2) {
			String mainWindow = d.getWindowHandle();
			((JavascriptExecutor)d).executeScript("window.open()");

			for (String newWindow : d.getWindowHandles())
				if(newWindow!=mainWindow)
					d.switchTo().window(newWindow);
			d.get(url.get(0).get(0));
		}
		else {
			String mainWindow = d.getWindowHandle();
			((JavascriptExecutor)d).executeScript("window.open()");
			for (String newWindow : d.getWindowHandles())
				if(newWindow!=mainWindow)
					d.switchTo().window(newWindow);
			d.get(url.get(0).get(0));
		}
	}
	
	public void setURL () {
		DriverManager.getDriver().get(getDataFromPropertyFile("env"));
	}
}
