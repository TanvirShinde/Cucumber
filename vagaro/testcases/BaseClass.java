package testcases;

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
	public Properties propertyFileobj;
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
					new FileReader(System.getProperty("user.dir")+ "\\batchFileWithAllDetails.bat"));
			String line;
			while ((line = bfr.readLine()) != null) {

				if(line.contains("threadCount"))
					threadCount = line.split("=")[1];				 
			} 
			bfr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(threadCount ==null) {
			threadCount="1";
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
		//Logger.getlogger("Current Thread ID:" + Thread.currentThread().getId());
		try {
			BufferedReader bfr =new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\batchFileWithAllDetails.bat"));
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
	    //	StoreCommonData.drivelink()=true;
		}
		else if(browserType.equals("chrome")){
			WebDriverManager.chromedriver().setup();
			dr =new ChromeDriver();
			dr.manage().window().maximize();
			utilities.DriverManager.setWebDriver(dr);
			getPageObject =new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equals("firefox")){
			WebDriverManager.firefoxdriver().setup();
			dr =new FirefoxDriver();
			dr.manage().window().maximize();
			utilities.DriverManager.setWebDriver(dr);
			getPageObject =new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equals("IE")){
			WebDriverManager.iedriver().setup();
			dr =new InternetExplorerDriver();
			dr.manage().window().maximize();
			utilities.DriverManager.setWebDriver(dr);
			getPageObject =new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equals("edge")){
			WebDriverManager.edgedriver().setup();
			dr =new EdgeDriver();
			dr.manage().window().maximize();
			utilities.DriverManager.setWebDriver(dr);
			getPageObject =new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
			dr.manage().deleteAllCookies();
		}
		else if(browserType.equals("headless")){
			WebDriverManager.chromedriver().setup();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			option.addArguments("--headless=new","--window-size=1920,1080","--disable-gbu","--ignore-certificate-error","--silent");
			capabilities.setCapability(ChromeOptions.CAPABILITY, option);
			option.merge(capabilities);
			d=new ChromeDriver(option);
			utilities.DriverManager.setWebDriver(dr);
			getPageObject =new GetPageObject();
			dr.get(getDataFromPropertyFile("env"));
		}
		}
		else {
			if(browserType.equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--disable-notifications");
				option.addArguments("--headless=new","--window-size=1920,1080","--disable-gbu","--ignore-certificate-error","--silent");
				capabilities.setCapability(ChromeOptions.CAPABILITY, option);
				option.merge(capabilities);
				d=new ChromeDriver(option);
				utilities.DriverManager.setWebDriver(dr);
				getPageObject =new GetPageObject();
				dr.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				d = new FirefoxDriver();
				d.manage().window().maximize();
				utilities.DriverManager.setWebDriver(dr);
				getPageObject =new GetPageObject();
				dr.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equals("IE")) {
				WebDriverManager.iedriver().setup();
				d =new InternetExplorerDriver();
				d.manage().window().maximize();
				utilities.DriverManager.setWebDriver(dr);
				getPageObject =new GetPageObject();
				dr.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equals("edge")) {
				WebDriverManager.edgedriver().setup();
				d =new EdgeDriver();
				d.manage().window().maximize();
				utilities.DriverManager.setWebDriver(dr);		
				getPageObject =new GetPageObject();
				dr.get(getDataFromPropertyFile("env"));
			}
			else if(browserType.equals("headless")) {
				WebDriverManager.chromedriver().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--disable-notifications");
				option.addArguments("--headless=new","--window-size=1920,1080","--disable-gbu","--ignore-certificate-error","--silent");
				capabilities.setCapability(ChromeOptions.CAPABILITY, option);
				option.merge(capabilities);
				d=new ChromeDriver(option);
				utilities.DriverManager.setWebDriver(dr);
				getPageObject =new GetPageObject();
				dr.get(getDataFromPropertyFile("env"));
			}
		}
	}
	
	public String getDataFromPropertyFile(String data) {
		String getProperty = null;

		if (data.equals("fromEmail") || data.equals("password")) {
			propertyFileobj = new Properties();
			try {
				FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"vagaro/utilities/configuration.properties");
				propertyFileobj.load(objfile);
				getProperty =propertyFileobj.getProperty(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				BufferedReader bfr = new BufferedReader(new FileReader(System.getProperty("user.dir")+"vagaro/utilities/batchFileWithAllDetails.bat"));
				String line;
				while((line = bfr.readLine()) !=null) {
					if(line.contains(data))
						getProperty =line.split("=")[1];
				}
				bfr.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return getProperty;
	}
	
	public void logs() {	
		logger = Logger.getLogger("TestCucumberFramework");
  		PropertyConfigurator.configure("vagaro/log4j.properties");
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
}
