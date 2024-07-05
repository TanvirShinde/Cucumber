package runner;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import utilities.BaseClass;
import utilities.RunBatchFile;






@CucumberOptions(features= {"classpath:testcases"}, glue= {"stepdefinition","Hooks"},

   tags = "@tanvir",
   plugin = {"pretty","html:target/Destination","json:target/cucumber/cucumber.json"},
   monochrome=true,
   dryRun=false
   )

// @Listeners(utilities.EmailWithAttachdReport.class)
public class Runner extends AbstractTestNGCucumberTests {
	
	private static TestNGCucumberRunner testNGCucumberRunner;
	
	@BeforeClass(alwaysRun = true)
	public void setUpClass(ITestContext context) {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		int count = 1;
          count = BaseClass.getthreadCount();
		context.getCurrentXmlTest().getSuite().setThreadCount(8);
		context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(count);
		context.getCurrentXmlTest().getSuite().setPreserveOrder(false);
	}
	
	 @SuppressWarnings("unused")
     @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario (PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}
	 
	 @DataProvider(parallel = true)
	 public Object[] [] scenarios () {
		 if (testNGCucumberRunner==null) {
			 return new Object [0] [0];
		 }
		 return testNGCucumberRunner.provideScenarios();
	 }
	 
	 @AfterClass(alwaysRun = true)
	 public void tearDownClass() {
		 if (testNGCucumberRunner == null) {
			 return;
		 }
		 testNGCucumberRunner.finish();
		 RunBatchFile.batchAfterExecute();
	 }
}
