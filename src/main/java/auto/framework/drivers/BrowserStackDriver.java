package auto.framework.drivers;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import com.browserstack.local.Local;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import auto.framework.services.pojo.AutomationSession;

public class BrowserStackDriver extends RemoteWebDriver {
	
	private static String userName=DriverSetting.BS_USERNAME.getValue();
	private static String password=DriverSetting.BS_ACCESS_KEY.getValue();
	private static String basicAuth = userName+":"+password;
	public static String sessionID;


	protected static URL getBrowserStackTestingUrl(String HOST){
		try {
			System.err.println("Connecting to http://"+HOST+"/wd/hub");
			return new URL("http://"+HOST+"/wd/hub");
			
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	private static HttpURLConnection getConnection(String webPage, String method){
		try{
			URL obj = new URL(webPage);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();												
			con.setDoOutput(true);					
			con.setRequestMethod(method);		
			String userpass = basicAuth;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			con.setRequestProperty ("Authorization", basicAuth);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();
			
			return con;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		}
	
	public static AutomationSession getBrowserStackInfos() {		
		String webPage = "https://www.browserstack.com/automate/sessions/"+sessionID+".json";		
		HttpURLConnection httpcon = getConnection(webPage, "GET");
		InputStream is = null;
		try {
			is = httpcon.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(is);

		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = sb.toString();		
		AutomationSession adminSession = null;

		// convert string response to java object
        ObjectMapper mapper = new ObjectMapper();
        try {
			adminSession = mapper.readValue(sb.toString(), AutomationSession.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return adminSession;
		
}
	
	private BrowserStackDriver(String host, Capabilities desiredCapabilities,Capabilities requiredCapabilities) {
		super(getBrowserStackTestingUrl(host), desiredCapabilities);
	}
	
	public static BrowserStackDriver start(Boolean runLocal) throws IOException {
		//set host
		String host = basicAuth+"@hub-cloud.browserstack.com";     	
		
		//set capabilities
		if(!runLocal){
			JOptionPaneMultiInput.openDialogue();	
		}
		
		DesiredCapabilities capabilities = getDesiredCapabilities();
		BrowserStackDriver d = new BrowserStackDriver(host, capabilities,null);				
		sessionID = d.getSessionId().toString();
		return d;
	}
	
	private static Boolean createTunnel(){
	
		Local loc = new Local();
		Map<String,String> opts = null;
		
		try {
			if(loc.isRunning()){				
				opts.put("key", DriverSetting.BS_ACCESS_KEY.getValue().toString());
				loc.start(opts);
				System.err.println("Local tunnel to BrowserStack has been established");
				return true;
			}else{
				System.err.println("Using existing Local tunnel to BrowserStack");
				return true;
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			System.err.println("Local Tunnelling failed. All scripts will be executed on firefox local");
		}
		
	return false;
	}
	
	private static class JOptionPaneMultiInput {
		private static void openDialogue() {
		   	String browsersLst[] = { "Firefox", "Chrome", "IE", "Safari" };
		   	String osLst[] = { "WINDOWS", "OS X" };
		   	
		   	JPanel contentPane = new JPanel();
	        contentPane.setOpaque(true);
	        contentPane.setBackground(Color.WHITE);
	        contentPane.setLayout(null);
	        
		  //Use combobox to create drop down menu
	          JLabel browserLabel = new JLabel("Browser: ",JLabel.LEFT);			   	 	
		      browserLabel.setLocation(20, 15);			  
		      browserLabel.setSize(100, 20);
		      final JComboBox browserBox=new JComboBox(browsersLst);
		      browserBox.setLocation(120, 15);
		      browserBox.setSize(135, 20);
		      
		      
	          JLabel browserVersionLabel = new JLabel("BrowserVersion: ",JLabel.LEFT);			   	 	
	          browserVersionLabel.setLocation(20, 40);			  
	          browserVersionLabel.setSize(100, 20);
		      final JTextField browserVersionTBox = new JTextField();
		      browserVersionTBox.setLocation(120, 40);			  
		      browserVersionTBox.setSize(135, 20);
		      browserVersionTBox.setText("46");
		      
		      JLabel osLabel = new JLabel("OS: ",JLabel.LEFT);
		      osLabel.setLocation(20, 65);			  
		      osLabel.setSize(100, 20);
		      final JComboBox osBox=new JComboBox(osLst);
		      osBox.setLocation(120, 65);
		      osBox.setSize(135, 20);
		      
		      JLabel osVersionLabel = new JLabel("OSVersion: ",JLabel.LEFT);			   	 	
		      osVersionLabel.setLocation(20, 90);			  
		      osVersionLabel.setSize(100, 20);
		      final JTextField osVersionTBox = new JTextField();
		      osVersionTBox.setLocation(120, 90);			  
		      osVersionTBox.setSize(135, 20);
		      osVersionTBox.setText("7");
		      
		      JButton submitBtn = new JButton("Submit");
		      submitBtn.setLocation(55, 120);
		      submitBtn.setSize(135, 20);
		      final JFrame frame=new JFrame("Configuration Setting");
		      final JDialog frame2 = new JDialog(frame, "Configuration Setting", true);		      
		      frame2.setAlwaysOnTop(true);
		      frame2.setLocationRelativeTo(null);
		      frame2.setDefaultCloseOperation(0);			      
		      frame2.setSize(280,200); 
		      frame2.setResizable(false);
		      frame2.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		      frame2.add(contentPane);
		      contentPane.add(browserLabel);
		      contentPane.add(browserBox);
		      contentPane.add(browserVersionLabel);
		      contentPane.add(browserVersionTBox);
		      contentPane.add(osLabel);
		      contentPane.add(osBox);
		      contentPane.add(osVersionLabel);
		      contentPane.add(osVersionTBox);
		      contentPane.add(submitBtn);
		      
		      submitBtn.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent arg0) {
		            	String[] listValue = {browserBox.getSelectedItem().toString(),osBox.getSelectedItem().toString()};
		            	System.err.println("Browser: "+listValue[0]);
		            	System.err.println("Operating System: "+listValue[1]);
		            	System.setProperty("browserStack.browser", listValue[0]);
		            	System.setProperty("browserStack.os", listValue[1]);
		            	frame.dispose();
		            }
		        });
		      		      
		      frame.setVisible(true);	
		      frame2.setVisible(true);
	   }
	}
	
	/*@Override
	public void setTestStatus(ITestResult tr, int status) {
		
	}*/
	
	protected static DesiredCapabilities getDesiredCapabilities(){		
		DesiredCapabilities capability = new DesiredCapabilities(); //DesiredCapabilities.firefox();		
		
		if(DriverSetting.BS_MOBILE.getValue() == null){
			capability.setCapability("browser", DriverSetting.BS_BROWSER.getValue());
		    capability.setCapability("browser_version", DriverSetting.BS_BROWSER_VERSION.getValue());
		    capability.setCapability("os", DriverSetting.BS_OS.getValue());
		    capability.setCapability("os_version", DriverSetting.BS_OS_VERSION.getValue());
		}else{
			capability.setCapability("browserName", "android");			
			capability.setCapability("os_version", DriverSetting.BS_OS_VERSION.getValue());
			capability.setCapability("device", DriverSetting.BS_DEVICE.getValue());			
			capability.setCapability("real_mobile", DriverSetting.BS_REALMOBILE.getValue());
			capability.setCapability("browserstack.appium_version", "1.6.5");
		}
	    capability.setCapability("browserstack.user", DriverSetting.BS_USERNAME.getValue());
	    capability.setCapability("browserstack.key", DriverSetting.BS_ACCESS_KEY.getValue());	 
	    capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	    capability.setCapability("browserstack.local", "true");
	    capability.setCapability("browserstack.autoWait", "0");
	    return capability;
	}
	
}



