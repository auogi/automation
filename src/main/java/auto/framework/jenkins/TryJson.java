package auto.framework.jenkins;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TryJson {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, ParseException {
		// TODO Auto-generated method stub
		
	 
/*		String jsonAsString ="{" 
		    		+ "Configuration: {BrowserType: Firefox,OSType: Windows},"
		    		+ "EmailDistribution: {EmailList: asoriano@progrexion.com},"
		    		+ "Name: TestEnvironment,"
		    		+ "TestSuite: {"
		    		+ "		CreditAccess: {"
		    		+ "			TestCase: [TC11_CA_Verify_SignUP],TestCoverageDetails: \"\" "
		    		+ "},"
		    		+ "		CreditAdvantage: {"
		    		+ "			TestCase: [TC13_CR_Verify_Sign_Up,TC13_CR_Verify_Sign_Up,TC14_CR_Verify_FDH_Sign_Up],TestCoverageDetails: \"\" "
		    		+ "}"		   
		    		+ "}"		    		
		    		+ "}";

*/		
		String jsonAsString = new String(Files.readAllBytes(Paths.get("./src/test/resources/sql/jsonFile"))); 
	    
	    jsonAsString = parseJson(jsonAsString);
	    System.out.println("After:" +jsonAsString);		
	    ObjectMapper mapper = new ObjectMapper();
	 
	    TestJenkinsConfiguration readValue = mapper.readValue(jsonAsString, TestJenkinsConfiguration.class);
	    
	    System.out.println("Environment1: "+readValue.getName().toString());
	    
	    /*
	    List<TestJenkinsConfiguration> pojoList = Arrays.asList(readValue);
	    System.out.println("Environment1: "+pojoList.get(0).getName().toString());
	    System.out.println("Environment2: "+pojoList.get(1).getName().toString());
	    System.out.println("Environment3: "+pojoList.get(2).getName().toString());
	    */

	    System.out.println(readValue.getConfiguration().getBrowserType().toString());
		//System.out.println("Before: "+jsonAsString); 
		    		
	}

	public static String parseJson(String parseJson){		
		parseJson = parseJson.replaceAll("\\s",""); //Remove white spaces
		parseJson = parseJson.replaceAll("(\\[\\{)", "\\{"); //Remove open brackets
		parseJson = parseJson.replaceAll("(\\}\\])", "\\}"); //Remove close brackets
		parseJson = parseJson.replaceAll("([\\w]+)[ ]*:", "\"$1\" :"); //Add opening double quote
		parseJson = parseJson.replaceAll(":[ ]*([\\w@\\.]+)", ": \"$1\""); //Add closing double quote
		parseJson = parseJson.replaceAll("\\[([\\w]+)[ ]*\\]", "\\[\"$1\"\\]"); //Remove open brackets
		parseJson = parseJson.replaceAll("\\[([\\w]+)[ ]*,", "\\[\"$1\"\\,"); //Remove open brackets
		parseJson = parseJson.replaceAll(",([\\w]+)[ ]*\\]", "\\,\"$1\"\\]"); //Remove open brackets
		parseJson = parseJson.replaceAll(",([\\w]+)[ ]*,", "\\,\"$1\"\\,"); //Remove open brackets
		parseJson = "["+parseJson+"]" ;
	return parseJson;
	}
	
}


