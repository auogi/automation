package auto.framework;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import auto.framework.drivers.DriverSetting;

public class ApplicationUtilities
{
    public static Boolean runApplication(String applicationFilePath) throws IOException, InterruptedException
    {
        File application = new File(applicationFilePath);
        String applicationName = application.getName();

        if (!isProcessRunning(applicationName))
        {
        	String cmd = "src/main/resources/webdriver/BrowserStackLocal.exe -forcelocal "+DriverSetting.BS_ACCESS_KEY.getValue();
   	        Process p = Runtime.getRuntime().exec(cmd);
   	       	return p.isAlive();
        }
        
        return true;	     
    }

    // http://stackoverflow.com/a/19005828/3764804
    private static boolean isProcessRunning(String processName) throws IOException, InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
        Process process = processBuilder.start();
        String tasksList = toString(process.getInputStream());

        return tasksList.contains(processName);
    }

    // http://stackoverflow.com/a/5445161/3764804
    private static String toString(InputStream inputStream)
    {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return string;
    }
}