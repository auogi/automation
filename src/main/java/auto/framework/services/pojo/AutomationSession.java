/**
 * $Id$
 *
 * Copyright (c) 2016, Disney Enterprises, Inc.
 */
package auto.framework.services.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class AutomationSession.
 */


public final class AutomationSession {
	
	public final  automation_session automation_session;
	
	 @JsonCreator
     public AutomationSession(@JsonProperty("automation_session") final automation_session automation_session){
		 this.automation_session = automation_session;
	 }
	
	public final static class automation_session{

		public final String os_version;
		public final String hashed_id;
		public final String public_url;
		public final String project_name;
		public final String logs;
		public final String status;
		public final String reason;
		public final String name;
		public final String device;
		public final String browser_version;
		public final String browser;
		public final String build_name;
		public final String browser_url;
		public final String os;
		public final String duration;
		public final String browser_console_logs_url;
		public final String video_url;
		public final String har_logs_url;
		public final String appium_logs_url;
		
		//SessionHandler
		  @JsonCreator
		  @JsonIgnoreProperties(ignoreUnknown = true)
	      public automation_session(
	    		  @JsonProperty("name") final String name,
	              @JsonProperty("duration") final String duration,
	              @JsonProperty("os") final String os,
	    		  @JsonProperty("os_version") final String os_version,
	    		  @JsonProperty("browser_version") final String browser_version,
	              @JsonProperty("browser") final String browser,
	              @JsonProperty("device") final String device,
	              @JsonProperty("status") final String status,
	              @JsonProperty("hashed_id") final String hashed_id,	              
	              @JsonProperty("reason") final String reason,
	              @JsonProperty("build_name") final String build_name, 	              
	              @JsonProperty("project_name") final String project_name,
	              @JsonProperty("logs") final String logs,
	              @JsonProperty("browser_url") final String browser_url,
	              @JsonProperty("public_url") final String public_url,	              
	              @JsonProperty("browser_console_logs_url") final String browser_console_logs_url,
	              @JsonProperty("video_url") final String video_url,
	              @JsonProperty("har_logs_url") final String har_logs_url,
	              @JsonProperty("appium_logs_url") final String appium_logs_url	              
	               ){
	          this.os_version = os_version;
	          this.hashed_id = hashed_id;
	          this.public_url = public_url;
	          this.project_name = project_name;
	          this.logs = logs;
	          this.status = status;
	          this.reason = reason;
	          this.name = name;
	          this.device = device;
	          this.browser_version = browser_version;
	          this.build_name = build_name;
	          this.browser = browser;
	          this.browser_url = browser_url;
	          this.os = os;
	          this.duration = duration;
	          this.video_url = video_url;
	          this.browser_console_logs_url = browser_console_logs_url;
	          this.har_logs_url = har_logs_url;
	          this.appium_logs_url = appium_logs_url;
	           
	      }
    }
}