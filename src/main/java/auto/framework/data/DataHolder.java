package auto.framework.data;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import auto.framework.TestManager;

public class DataHolder {
		
	protected static XSSFSheet ExcelWSheet;
	protected static XSSFWorkbook ExcelWBook;
	
	protected DataRecord executeQuery(String SheetName,String rowIdentfier) throws Exception {   
		Map<String, String> dataSet = new HashMap<String, String>();
		Row  row = null;

			String dataPath = "";
			String tempPath = TestManager.Preferences.getPreference("data2","");
		
			if(tempPath.equalsIgnoreCase("")){
				   dataPath = "./src/test/resources/data/"+TestManager.Preferences.getPreference("data","DataTable.xls");
			}else{
				   dataPath = "./src/test/resources/data/"+tempPath;				
			}

		   FileInputStream ExcelFile = new FileInputStream(dataPath);
		   //FILLO
		   // Access the required test data sheet
		   ExcelWBook = new XSSFWorkbook(ExcelFile);
		   ExcelWSheet = ExcelWBook.getSheet(SheetName);

		   
		   for (int rowIndex = 0; rowIndex <= ExcelWSheet.getLastRowNum(); rowIndex++) {
			   row = ExcelWSheet.getRow(rowIndex);
			   if (row != null) {
			     Cell cell = row.getCell(0);
			     if (cell != null) {
			       if(rowIdentfier.equals(cell.getStringCellValue())){
			    	   break;
			       }
			     }
			   }
			 }
	
		   

		   Row  headerrow = ExcelWSheet.getRow(0);
		   
		   //Get the headers
		   
		   for(int colIndex = 0;colIndex<row.getPhysicalNumberOfCells();colIndex++){
			   DataFormatter formatter = new DataFormatter();
			   
			   dataSet.put(formatter.formatCellValue(headerrow.getCell(colIndex)), formatter.formatCellValue(row.getCell(colIndex)));
		   }
			   
	return (new DataRecord(dataSet));
	 
	}
}	