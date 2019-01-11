package auto.framework;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.codoid.products.exception.FilloException;

import auto.framework.reportlog.MethodListener;
import auto.framework.reportlog.ReportLog;
import auto.framework.reportlog.SuiteListener;
import auto.framework.reportlog.TestListener;

@Listeners({TestListener.class,MethodListener.class,SuiteListener.class})
public class TestInit{
	

	  
}