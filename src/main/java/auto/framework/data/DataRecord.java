package auto.framework.data;
import java.util.Map;

public class DataRecord {
	
	private  Map<String, String> dataRecord;
	
	DataRecord(Map<String, String> DataRecord){
		this.dataRecord = DataRecord;
	}

	public String getField(String Column) {
		
		String value = dataRecord.get(Column);
		
		if (value!=null){
			return value;	
		}else{
			System.err.println(Column + " Column Does not exist");
			return "";
		}
			
		
		
	}
	
}
