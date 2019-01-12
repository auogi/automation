package auto.framework.report.reporter;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.w3c.dom.Node;

import auto.framework.managers.TestManager;


/**
 * Work In Progress
 * @author jeremiah.d.mendoza, augusto soriano
 */
public class ReportLog {//extends ReportLog_ForRevamp {
	
	protected static DateFormat dateFormatTimeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");	

//Static Methods:    
	public static void logEvents(Boolean value) {
		ReportLogInstance.getInstance().logEvents(value);
	}

	public static Boolean logEvents() {
		return ReportLogInstance.getInstance().logEvents();
	}
    
	public static TestScenario setTestName(String testName){
		return ReportLogInstance.getInstance().setTestName(testName);
	}
	
	public static TestCase setTestCase(String testCase){
    	return ReportLogInstance.getInstance().setTestCase(testCase);
	}
	
	public static TestStep setTestStep(String testStep) {
    	return ReportLogInstance.getInstance().setTestStep(testStep);
	}
	
	public static WebServiceStep setWebService(String webStep) {
    	return ReportLogInstance.getInstance().setWebServiceStep(webStep);
	}
	
	public static TestLog logEvent(Boolean status, String description){
		return ReportLogInstance.getInstance().logEvent(status, description);
	}
	
	public static TestLog logEvent(String status, String description){
		return ReportLogInstance.getInstance().logEvent(status, description);
	}

	public static ReportNode setSummaryDetails(String name, String desc){	
		return ReportLogInstance.getInstance().setSummaryDetails(name, desc);
	}
	
	public static ReportNode setTCSummaryDetails(String name, String desc){	
		return ReportLogInstance.getInstance().setTCSummaryDetails(name, desc);
	}
	
	public static TestLog addInfo(String info){
		return ReportLogInstance.getInstance().addInfo(info);
	}
	
	public static TestLog addInfoWebservice(String info){
		return ReportLogInstance.getInstance().infoWebService(info);
	}

	public static TestLog webService(String info){
		return ReportLogInstance.getInstance().webService(info);
	}
	
	
	public static TestLog xmlInfo(String path, String name){		
		return ReportLogInstance.getInstance().xmlInfo(path, name);
	}
	
	
	public static TestLog xmlInfoWebService(String name, String response ){		
		
		Random rand = new Random(System.currentTimeMillis());		
		long randGen = Math.abs(rand.nextLong());		
		
		String fileURL =System.getProperty("user.dir")+"/src/test/resources/reports/consolidated/Response"+randGen+".json";
    	File file = new File(fileURL);
		
		try {
			file.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(response);
    		output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		    
    	if(Boolean.valueOf(TestManager.Preferences.getPreference("jenkins",""))){
    		fileURL = "./Response"+randGen+".json";
  	    }
	    	
		return ReportLogInstance.getInstance().xmlInfoWebService(name, fileURL);
	}
	
	
	public static ReportNode addDescription(String name, String desc){
		return ReportLogInstance.getInstance().addDescription(name, desc);
	}
	
	public static void attachScreenshot(String name, String base64){
		ReportLogInstance.getInstance().attachScreenshot(name, base64);
	}
	
	public static void attachFile(String name, String file){
		ReportLogInstance.getInstance().attachFile(name, file);
	}
	
	
	
	public static TestLog verifyManual(String description){
		return logEvent( "No Run" , description);
	}
	
	public static Boolean verifyTrue(Boolean status,String description){
		return ReportLogInstance.getInstance().verifyTrue(status, description);
	}
	
	public static void assertAll(){
		ReportLogInstance.getInstance().assertAll();
	}
	
	public static void passed(String Description){
		verifyTrue(true,Description);
	}

	public static void failed(String Description){
		verifyTrue(false,Description);
	}
	
	public static Boolean verifyWebServiceTrue(Boolean status,String description){
		return ReportLogInstance.getInstance().verifyWebServiceTrue(status, description);
	}
	
	
	public static Boolean assertTrue(Boolean status,String description){
		return ReportLogInstance.getInstance().assertTrue(status, description);
	}
	
	
	public static void passedWebService(String Description){
		verifyWebServiceTrue(true,Description);
	}

	public static void failedWebService(String Description){
		verifyWebServiceTrue(false,Description);
	}
	
	public static Boolean assertWebServiceTrue(Boolean status,String description){
		return ReportLogInstance.getInstance().assertWebServiceTrue(status, description);
	}
	
	public static void logError(Throwable error){
		failed(error.getClass().getSimpleName() + " : " + error.getMessage());
	}
	
	public static void assertFailed(String Description){
		assertTrue(false,Description);
	}  
	
	public static void warning(String Description){
		logEvent( "Warning" , Description);
	}
	
	@Deprecated public static void getScreenshot(String App) throws Exception {
		
	}
	
	@Deprecated public static ReportNode setSummaryDetails(String desc){	
		return ReportLogInstance.getInstance().setSummaryDetails("Test", desc);
	}

	public static class Console {
	
		public static void log(String text) {
			System.out.println(text);
		}
		
		public static void log(String format, Object ... args) {
			System.out.format(format, args);
		}
		
		public static void logE(String format, Object ... args) {
			System.err.format(format, args);
		}
		
	}

	public static class Status {
		
		public static final String PASSED = "Passed";
		public static final String FAILED = "Failed";
		public static final String ERROR = "Error";
		public static final String NOT_APPLICABLE = "N/A";
		public static final String NOT_AUTOMATED = "Not Automated";
		
	}
	
	public static class TestRoot extends ReportNode {
		public TestRoot(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
	}
	
	public static class ScenarioCase extends ReportNode {

		private SummaryDetails testSummary;
		private SummaryDetails testDetails;
		
		public ScenarioCase(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
				
		public TestLog addInfo(String info){
			String timestamp = dateFormatTimeStamp.format(new Date());
			TestLog testLog = new TestLog(xmlFactory.createElement("report"), xmlFactory);
			testLog.setAttribute("desc", info);
			testLog.setAttribute("status", "Info");
			Console.log("\t\t%s | %s | %s\n", timestamp, "Info", info);
			return (TestLog) appendChild(testLog);
		}
		
		public TestLog webService(String info){
			String timestamp = dateFormatTimeStamp.format(new Date());
			TestLog testLog = new TestLog(xmlFactory.createElement("report"), xmlFactory);
			testLog.setAttribute("desc", info);
			testLog.setAttribute("status", "webservice");
			Console.log("\t\t%s | %s | %s\n", timestamp, "webservice", info);
			return (TestLog) appendChild(testLog);
		}
		
	
		
		public TestLog xmlInfo(String info, String name){
			String timestamp = dateFormatTimeStamp.format(new Date());
			TestLog testLog = new TestLog(xmlFactory.createElement("report"), xmlFactory);
			testLog.setAttribute("desc", info);
			testLog.setAttribute("status", "xml");
			testLog.setAttribute("name", name);
			testLog.setAttribute("timestamp", timestamp);
			Console.log("\t\t\t%s | %s | %s\n", timestamp, "xml", info);
			return (TestLog) appendChild(testLog);
		}
		
		public void addDescription(String name, String desc){
			getSummary().addInfo(name, desc);
		}
		
		public void addPlayback(String name, String desc){
			getSummary().addHref(name, desc);
		}
		
		public SummaryDetails getSummary(){
			if(testSummary==null) {
				testSummary = new SummaryDetails(xmlFactory.createElement("summary"), xmlFactory);
				return (SummaryDetails) appendChild(testSummary);
			} return testSummary;
		}
		
		public SummaryDetails getDetails(){
			if(testDetails==null) {
				testDetails = new SummaryDetails(xmlFactory.createElement("details"), xmlFactory);
				return (SummaryDetails) appendChild(testDetails);
			} return testDetails;
		}
		
		public static class SummaryDetails extends ReportNode {

			public SummaryDetails(Node node, ReportXmlFactory xmlFactory) {
				super(node, xmlFactory);
			}
			
			public ReportNode addInfo(String name,String desc){
				ReportNode infoNode = new ReportNode(xmlFactory.createElement("info"), xmlFactory);
				infoNode.setAttribute("name", name).setAttribute("desc", desc);
				return appendChild(infoNode);
			}
			
			public ReportNode addHref(String name,String desc){
				ReportNode hrefNode = new ReportNode(xmlFactory.createElement("href"), xmlFactory);
				hrefNode.setAttribute("name", name).setAttribute("desc", desc);
				return appendChild(hrefNode);
			}
			
			public ReportNode setInfo(String name,String desc){
				Node infoDom = selectSingleNode(".//info[@name='"+name+"']");
				if(infoDom!=null){
					return new ReportNode(infoDom, xmlFactory).setAttribute("desc", desc);
				} return addInfo(name,desc);
			}
			
		}
		
		public ReportNode attachScreenshot(String name, String base64){
			String timestamp = dateFormatTimeStamp.format(new Date());
			ReportNode node = createChild("screenshot")
				.setAttribute("name", name)
				.setAttribute("base64", base64)
				.setAttribute("timestamp", timestamp);
			Console.log("\t\t%s | %s | %s \n", timestamp, "Screenshot", name);
			return node;
		}
		
		public ReportNode attachFile(String name, String file){
			
			//Random rand = new Random(System.currentTimeMillis());		
			//long randGen = Math.abs(rand.nextLong());
			String fileURL;
			
			if(Boolean.valueOf(TestManager.Preferences.getPreference("jenkins",""))){
	    		fileURL = "./"+file;
	  	    }else{
	  	    	fileURL = System.getProperty("user.dir")+"/src/test/resources/reports/consolidated/"+file;	
	  	    }
			
			String timestamp = dateFormatTimeStamp.format(new Date());
			ReportNode node = createChild("attachment")
				.setAttribute("file", fileURL)
				.setAttribute("name", name)
				.setAttribute("timestamp", timestamp);
			Console.log("\t\t%s | %s | %s \n", timestamp, "Attachment", name);
			return node;
		}
		
	}
	
	public static class TestScenario extends ScenarioCase {

		private TestCase currentCase;
		
		public TestScenario(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
		public TestScenario setName(String name){
			setAttribute("name", name);			
			return this;
		}
		
		
		public TestScenario setEnvironment(String name){
			setAttribute("environment", name);			
			return this;
		}
		
		public TestScenario setBrowser(String name){
			setAttribute("browser", name);
/*			Console.log("[Test Suite] %s\n", name);*/
			return this;
		}
		public TestScenario setStatus(String status){
			setAttribute("status", status);
			return this;
		}
		
		public TestScenario setStartTime(String name){
			setAttribute("startTime", name);			
			return this;
		}
		
		public TestScenario setEndTime(String name){
			setAttribute("endTime", name);			
			return this;
		}
		
		private DateFormat timestamp = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
	
		public TestCase addTestCase(String name){
			currentCase = new TestCase(xmlFactory.createElement("test-case"), xmlFactory);
			currentCase.setName(name);
			currentCase.addDescription("Time Started", timestamp.format(new Date()));
			currentCase.addDescription("Time Ended", "N/A");
			currentCase.addDescription("Time Elapsed", "N/A");
			
						
			
			Console.log("\t[Test Case] %s\n", name);
			return (TestCase) appendChild(getCurrentCase());
		}

		public TestCase getCurrentCase() {
			return currentCase;
		}

//		public void setCurrentCase(TestCase currentCase) {
//			this.currentCase = currentCase;
//		}
		
	}
	
	public static class TestCase extends ScenarioCase {
		
		private TestStep currentStep;
		
		public TestCase(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
		public TestCase setName(String name){
			setAttribute("name", name);
			return this;
		}
		
		public TestCase setStatus(String status){
			setAttribute("status", status);
			return this;
		}
		
		public TestStep addTestStep(String stepDesc){
			setCurrentStep(new TestStep(xmlFactory.createElement("test-step"), xmlFactory));
			if(stepDesc!=null){
				getCurrentStep().setAttribute("name",stepDesc);
				Console.log("\t\t[Test Step] %s\n", stepDesc);
			}
			return (TestStep) appendChild(getCurrentStep());
		}

		public TestStep getCurrentStep() {
			return currentStep;
		}

		public void setCurrentStep(TestStep currentStep) {
			this.currentStep = currentStep;
		}
		
	}
	
	public static class TestStep extends ScenarioCase {

		private WebServiceStep currentWebStep;
		
		public TestStep(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
		public TestStep setStatus(String status){
			setAttribute("status", status);
			return this;
		}
				
		public WebServiceStep addWebServiceTestStep(String stepDesc){
			setCurrentWebserviceStep(new WebServiceStep(xmlFactory.createElement("test-webservice"), xmlFactory));
			if(stepDesc!=null){
				getCurrentWebServiceStep().setAttribute("name",stepDesc);
				Console.log("\t\t\t[Test Webservice] %s\n", stepDesc);
			}
			return (WebServiceStep) appendChild(getCurrentWebServiceStep());
		}

		public WebServiceStep getCurrentWebServiceStep() {
			return currentWebStep;
		}

		public void setCurrentWebserviceStep(WebServiceStep currentWebServiceStep) {
			this.currentWebStep = currentWebServiceStep;
		}
		
		
		public TestStep setExpected(String expected){
			ReportNode expectedNode = new ReportNode(xmlFactory.createElement("expected"), xmlFactory);
			appendChild(expectedNode).setAttribute("desc", expected);;
			return this;
		}
		
		public TestLog logEvent(Boolean status, String description){			
				return logEvent((status ? "Passed" : "Failed"), description);
		}
		
		public TestLog logEvent(String status, String description){
			String timestamp = dateFormatTimeStamp.format(new Date());
			TestLog testLog = new TestLog(xmlFactory.createElement("report"), xmlFactory);
			testLog.setAttribute("desc", description);
			testLog.setAttribute("status", status);
			testLog.setAttribute("timestamp", timestamp);
			
			if(status=="Failed"){
				Console.logE("\t\t%s | %s | %s\n", timestamp, status, description);								
			} else {
				Console.log("\t\t%s | %s | %s\n", timestamp, status, description);
			}
			return (TestLog) appendChild(testLog);
		}
		
	}

	
	public static class WebServiceStep extends ScenarioCase {

		public WebServiceStep(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
		public WebServiceStep setStatus(String status){
			setAttribute("status", status);
			return this;
		}
		
		public WebServiceStep setExpected(String expected){
			ReportNode expectedNode = new ReportNode(xmlFactory.createElement("expected"), xmlFactory);
			appendChild(expectedNode).setAttribute("desc", expected);;
			return this;
		}
		
		public TestLog logEvent(Boolean status, String description){			
				return logEvent((status ? "Passed" : "Failed"), description);
		}
		
		public TestLog logEvent(String status, String description){
			String timestamp = dateFormatTimeStamp.format(new Date());
			TestLog testLog = new TestLog(xmlFactory.createElement("report"), xmlFactory);
			testLog.setAttribute("desc", description);
			testLog.setAttribute("status", status);
			testLog.setAttribute("timestamp", timestamp);
			
			if(status=="Failed"){
				Console.logE("\t\t\t%s | %s | %s\n", timestamp, status, description);								
			} else {
				Console.log("\t\t\t%s | %s | %s\n", timestamp, status, description);
			}
			return (TestLog) appendChild(testLog);
		}
		
		public TestLog logWebServiceEvent(Boolean status, String description){			
			return logWebServiceEvent((status ? "Passed" : "Failed"), description);
	}
	
	public TestLog logWebServiceEvent(String status, String description){
		String timestamp = dateFormatTimeStamp.format(new Date());
		TestLog testLog = new TestLog(xmlFactory.createElement("reportwebservice"), xmlFactory);
		testLog.setAttribute("desc", description);
		testLog.setAttribute("status", status);
		testLog.setAttribute("timestamp", timestamp);
		
		if(status=="Failed"){
			Console.logE("\t\t\t%s | %s | %s\n", timestamp, status, description);								
		} else {
			Console.log("\t\t\t%s | %s | %s\n", timestamp, status, description);
		}
		return (TestLog) appendChild(testLog);
	}
	
	public TestLog xmlWebServiceEvent(String info, String name){
		String timestamp = dateFormatTimeStamp.format(new Date());
		TestLog testLog = new TestLog(xmlFactory.createElement("reportwebservice"), xmlFactory);
		testLog.setAttribute("desc", name);
		testLog.setAttribute("status", "xml");
		testLog.setAttribute("name", info);
		testLog.setAttribute("timestamp", timestamp);
		Console.log("\t\t\t%s | %s | %s\n", timestamp, "xml", info);
		return (TestLog) appendChild(testLog);
	}
		
		
	}
	
	public static class TestLog extends ReportNode {

		public TestLog(Node node, ReportXmlFactory xmlFactory) {
			super(node, xmlFactory);
		}
		
		public TestLog setStatus(String status){
			setAttribute("status", status);
			return this;
		}
		
		public TestLog setTimeStamp(String timestamp){
			setAttribute("timestamp", timestamp);
			return this;
		}
		
		/**
		 * Sets validation target
		 * @param name
		 * @param action
		 */
		public TestLog setTarget(String name, String action){
			ReportNode testTarget = new ReportNode(xmlFactory.createElement("target"), xmlFactory);
			testTarget.setAttribute("name", name);
			testTarget.setAttribute("action", action);
			appendChild(testTarget);
			return this;
		}
		
		/**
		 * Sets validation details
		 * @param criteria
		 * @param expected
		 * @param actual
		 */
		public TestLog setValidation(String criteria, String expected, String actual){
			ReportNode testValidation = new ReportNode(xmlFactory.createElement("validation"), xmlFactory);
			testValidation.setAttribute("criteria", criteria);
			testValidation.setAttribute("expected", expected);
			testValidation.setAttribute("actual", actual);
			appendChild(testValidation);
			return this;
		}
		
	}
	
}
