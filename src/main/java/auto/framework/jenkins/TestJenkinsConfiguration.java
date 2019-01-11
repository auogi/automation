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
"Configuration",
"EmailDistribution",
"Name",
"TestSuite"
})
public class TestJenkinsConfiguration {

@JsonProperty("Configuration")
private Configuration configuration;
@JsonProperty("Name")
private String name;
@JsonProperty("TestSuite")
private TestSuite testSuite;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("Configuration")
public Configuration getConfiguration() {
return configuration;
}

@JsonProperty("Configuration")
public void setConfiguration(Configuration configuration) {
this.configuration = configuration;
}

@JsonProperty("Name")
public String getName() {
return name;
}

@JsonProperty("Name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("TestSuite")
public TestSuite getTestSuite() {
return testSuite;
}

@JsonProperty("TestSuite")
public void setTestSuite(TestSuite testSuite) {
this.testSuite = testSuite;
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