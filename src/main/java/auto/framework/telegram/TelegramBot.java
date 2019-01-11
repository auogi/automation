package auto.framework.telegram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.transform.stream.StreamSource;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import auto.framework.SSLHandle;
import auto.framework.TestManager;
import auto.framework.reportlog.ReportLog;

public class TelegramBot {
	
	public static void sendMessage(String Message) throws IOException{
		
		String url = "https://api.telegram.org/bot"+TestManager.Preferences.getPreference("telegram.token").toString()+"/sendMessage?chat_id="+TestManager.Preferences.getPreference("telegram.groupChatIds").toString()+"&text="+Message+"&parse_mode=MARKDOWN";
		System.err.println(url);
		
		SSLHandle.connect();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.err.println("Sent the report to Telegram");
		
	}
}
