package runner;

import org.testng.TestNG;

public class ForExeJars {

	static TestNG testng;

	public static void main(String[] args) {
		testng = new TestNG();
		testng.setTestClasses(new Class [] { Runner.class});
		testng.run();
	}
}
