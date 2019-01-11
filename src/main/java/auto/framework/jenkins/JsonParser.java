package auto.framework.jenkins;

public class JsonParser {

	public static String parseToValidJson(String parseJson){		
		parseJson = parseJson.replaceAll("\\s",""); //Remove white spaces
		parseJson = parseJson.replaceAll("(\\[\\{)", "\\{"); //Remove open brackets
		parseJson = parseJson.replaceAll("(\\}\\])", "\\}"); //Remove close brackets		
		
		parseJson = parseJson.replaceAll("([\\w]+)[ ]*:", "\"$1\" :"); //Add opening double quote
			parseJson = parseJson.replaceAll(":[ ]*([\\w@\\.]+)", ": \"$1\""); //Add closing double quote
		parseJson = parseJson.replaceAll("\\[([\\w]+)[ ]*\\]", "\\[\"$1\"\\]"); //Add middle double quote
		parseJson = parseJson.replaceAll("\\[([\\w]+)[ ]*,", "\\[\"$1\"\\,"); //Adds open brackets
		parseJson = parseJson.replaceAll(",([\\w]+)[ ]*\\]", "\\,\"$1\"\\]"); //Remove open brackets
		parseJson = parseJson.replaceAll(",([\\w]+),", "\\,\"$1\"\\,"); //Remove open brackets		
		parseJson = parseJson.replaceAll(",([\\w]+),", "\\,\"$1\"\\,"); //Remove open brackets
		
/*
    String awesomeJson = parseJson
    .replaceAll("([a-zA-Z0-9]*\\s?[a-zA-Z0-9]*\\s?[a-zA-Z0-9]*\\s?:+)", "\"$1\"")
    .replaceAll(":\"", "\":")
    .replaceAll("\"\\s+", "\"")
    .replaceAll(", ", "\", \"")
    .replaceAll("(\\[{1})([a-zA-Z])", "[\"$2")
    .replaceAll("([a-zA-Z])(\\]{1})", "$1\"]")
    .replaceAll(":\\s([a-zA-Z0-9\\s]*\\s?)(,|})", ": \"$1\"$2")
    .replaceAll("([a-zA-Z0-9])\\s+\"", "$1\"");
*/		
	return parseJson;
	}

}
