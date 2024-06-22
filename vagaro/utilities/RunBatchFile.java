package utilities;

import java.io.IOException;

import gherkin.deps.net.iharder.Base64.OutputStream;

public class RunBatchFile {

	public static void batchBeforeExecute() {

		try {
			//Execute command

			String command = "cmd /c start " + System.getProperty("user.dir")+"\\TestCucumberFramework\\utilities\\beforeExecution.bat";

			Process child =Runtime.getRuntime().exec(command);
			//Get output stream to write from it

			OutputStream out = (OutputStream) child.getOutputStream();

			out.write("cd C:/ /r/n".getBytes());

			out.flush();

			out.write("cd C:/ /r/n".getBytes());

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void batchAfterExecute() {

		try {
			//Execute command

			String command = "cmd /c start mvn verify -DskipTests";
			Process child =Runtime.getRuntime().exec(command);

			//Get output stream to write from it
			OutputStream out = (OutputStream) child.getOutputStream();

			out = (OutputStream) child.getOutputStream();

			out.write("exit".getBytes());

			out.write("cd C:/ /r/n".getBytes());

			out.flush();

			out.write("cd C:/ /r/n".getBytes());

			Thread.sleep(5000);

			command ="cmd /c start %windir%\\explorer.exe %cd%\"\\target\\cucumber-JVM-reports\\cucumber-html-reports\\feature-overview.html\"";
			child=Runtime.getRuntime().exec(command);

			out.write("cd C:/ /r/n".getBytes());

			out.flush();

			out.write("cd C:/ /r/n".getBytes());

			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
