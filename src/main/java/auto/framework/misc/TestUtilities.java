package auto.framework.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class TestUtilities {
		
		public static String regExString(String string){
			return "concat('"+string.replace("'", "', \"'\", '") + "', '')";
		}

		
			public static XMLGregorianCalendar toGregorianDate(String dateFormat){
				
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
				Date sDate = null;
				XMLGregorianCalendar gDateUnformated = null;
				try {
					try {
						sDate = formatter.parse(dateFormat);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					GregorianCalendar gCal = new GregorianCalendar();	    
			        gCal.setTime(sDate);
					gDateUnformated = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}		
				
				return gDateUnformated;
				
			}

			public static Date toDateTime(String strDate){
				String YYYMMDDT = "yyyy-MM-dd'T'hh:mm:ss";
				DateFormat format = new SimpleDateFormat(YYYMMDDT);
				Date date=null;
				try {
					date = format.parse(strDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return date;	
			}
			
			public static long randomGenerator(){
				Random rand = new Random(System.currentTimeMillis());		
				return Math.abs(rand.nextLong());		
			}
			
			public static String randomGenerator(int digits){
				Random rand = new Random(System.currentTimeMillis());
				long randNumber = Math.abs(rand.nextLong());
				String randString = String.valueOf(randNumber);
				
				return randString.substring(0, digits);		
			}
			
			
}


