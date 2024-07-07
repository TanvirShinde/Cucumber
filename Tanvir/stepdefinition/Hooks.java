package stepdefinition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LogEntry;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.BaseClass;
import utilities.DriverManager;

public class Hooks{
	String ScenarioSteps="";
	static int count =1;
	File myObj;
	String logEntity ="logs : ";

	@Before
	synchronized public void execute_before_every_scenario(Scenario scenario) {
		System.out.println("\n\n****************" + (count++) + " ***********\n\n");

	}

	@After
	public void execute_after_every_scenario(Scenario scenario) throws InterruptedException ,IOException{
		if(scenario.isFailed()) {
			String path = scenario.getUri().toString().split(":")[1];
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(".//"+"/Tanvir/"+path)));
				String line = br.readLine();
				boolean start = false;
				while((line=br.readLine()) !=null) {
					if(line.contains("Scenario:")) {
						start = false;
						if(line.contains(scenario.getName())) {
							ScenarioSteps += "<br />";
							ScenarioSteps += "<br />" + line;
							start =true;
							break;
						}
					}
					if(!line.contains("Scenario:") && !line.contains("Background") && !line.contains("Given")
							&& !line.contains("And") && !line.contains("When") && !line.contains("Then") && !line.contains("|"))
						start =false;
					if(line.contains("Background:"))
						start=true;
					if(start)
						ScenarioSteps += "<br />" + line;
				}
				while((line=br.readLine()) !=null) {
					if(line.contains("Scenario:"))
						if(ScenarioSteps.contains("Scenario:"))
							break;
					if(!line.contains("Scenario:") && !line.contains("Background") && !line.contains("Given")
							&& !line.contains("And") && !line.contains("When") && !line.contains("Then") && !line.contains("|"))
						if(ScenarioSteps.contains("Scenario:"))
							break;
					if(start)
						ScenarioSteps +="<br />" + line;
				}
			} catch (Exception e) {

			}
			LogEntries browserLogs = DriverManager.getDriver().manage().logs().get(LogType.BROWSER);
			int i=1;
			try {
				new File(".//"+"/Tanvir/"+"/logs_"+java.time.LocalDate.now().toString().replace("-", "_").replace(" ", "_")).mkdirs();
				myObj = new File(".//"+"/Tanvir/"+"/logs_"+java.time.LocalDate.now().toString().replace("-", "_").replace(" ", "_")+"/"+"logs_"+"_"+scenario.getName()+".txt");
				if(myObj.createNewFile())
					System.out.println("File created : " + myObj.getName());
				else {
					System.out.println("File already exits. ");
					myObj.delete();
					myObj = new File(".//"+"/Tanvir/"+"/logs_"+java.time.LocalDate.now().toString().replace("-", "_").replace(" ", "_")+"/"+"logs_"+"_"+scenario.getName()+".txt");
				}
				FileWriter fwTitle = new FileWriter(myObj.getAbsoluteFile(),true);
				BufferedWriter bfTitle = new BufferedWriter(fwTitle);
				bfTitle.write("\n\n***************************************************"
						+ "\nTestcase ID : " + scenario.getName()
						+"\n*************************************************************\n\n");
				bfTitle.close();
				for(LogEntry entry : browserLogs) {
					try {
						AddLogInFiles(scenario, entry);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (Exception e) {

			}
			if(BaseClass.threadcount >1)
				DriverManager.closeBrowser();
		}
		else if(BaseClass.threadcount >1)
			DriverManager.closeBrowser();
	}

	public void AddLogInFiles(Scenario scenario, LogEntry entry) {

		try {
			FileWriter fw = new FileWriter(myObj.getAbsoluteFile(),true);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write("LEVEL : " + entry.getLevel() + "\n");
			bf.write("MESSAGE : " + entry.getMessage() + "\n\n");
			bf.write("Time Stamp : " + entry.getTimestamp() + "\n\n");
			bf.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
