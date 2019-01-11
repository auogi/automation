package auto.framework.jenkins;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"BrowserType",
"BrowserVersion",
"OSType",
"OSVersion"
})
public class Configuration {

@JsonProperty("BrowserType")
private String browserType;
@JsonProperty("OSType")
private String oSType;


@JsonProperty("BrowserVersion")
private String browserVersion;
@JsonProperty("OSVersion")
private String oSVersion;

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("BrowserType")
public String getBrowserType() {
return browserType;
}

@JsonProperty("BrowserType")
public void setBrowserType(String browserType) {
this.browserType = browserType;
}

@JsonProperty("OSType")
public String getOSType() {
return oSType;
}

@JsonProperty("OSType")
public void setOSType(String oSType) {
this.oSType = oSType;
}

@JsonProperty("BrowserVersion")
public String getBrowserVersion() {
return browserVersion;
}

@JsonProperty("BrowserVersion")
public void setBrowserVersion(String browserVersion) {
this.browserVersion = browserVersion;
}

@JsonProperty("OSVersion")
public String getOSVersion() {
return oSVersion;
}

@JsonProperty("OSVersion")
public void setOSVersion(String oSVersion) {
this.oSVersion = oSVersion;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
