package auto.framework.data;
import java.util.Map;

public class DataHandle {
	

	
	private static ThreadLocal<DataHolder> dataHolder = new InheritableThreadLocal<DataHolder>(){
        @Override
        protected DataHolder initialValue() { return new DataHolder(); }
	};
	
	public static void setDataTable(String path) {
		System.setProperty("env.data", path);
	}	
	
	   public static DataRecord getRowData(String sheet, String where){
	    	try {
				return dataHolder.get().executeQuery(sheet, where);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }

	  
	
	   
	   
	   
	/*private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   

   String[][] tabArray = null;

   try {  	   

	   FileInputStream ExcelFile = new FileInputStream(FilePath);

	   // Access the required test data sheet
	   ExcelWBook = new XSSFWorkbook(ExcelFile);
	   ExcelWSheet = ExcelWBook.getSheet(SheetName);

	   int startRow = 1;
	   int startCol = 0;
	   int ci,cj;
	   int totalRows = ExcelWSheet.getLastRowNum();
	   int totalCols = 2;

	   tabArray=new String[totalRows][totalCols+1];

	   ci=0;

	   for (int i=startRow;i<=totalRows;i++, ci++) {           	   

		  cj=0;

		   for (int j=startCol;j<=totalCols;j++, cj++){

			   tabArray[ci][cj]=getCellData(i,j);
		   		}
			}
		}

	catch (FileNotFoundException e){
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
		}

	catch (IOException e){
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
		}

	return(tabArray);
	}


public static Object[][] getTableDirectory(String DirectoryPath, String SheetName) throws Exception {   

	   File folder = new File(DirectoryPath);
	   File[] listOfFiles = folder.listFiles();
	   Object[][] datalist = new Object[0][0];	   
			   
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {	    	  
	    	  datalist = append(datalist,getTableArray(listOfFiles[i].getCanonicalPath(), "Sheet1"));	    	  
	    	
	        System.out.println("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }
		return datalist;	  
	}

public static Object[][] append(Object[][] a, Object[][] b) {
	Object[][] result = new Object[a.length + b.length][];
    System.arraycopy(a, 0, result, 0, a.length);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
}

public static Object[][] getTableArray(String SheetName) throws Exception {   

	   String[][] tabArray = null;

	   try {
		   
		   String dataPath = "./src/test/resources/data/"+TestManager.Preferences.getPreference("data","DataTable.xls");

		   FileInputStream ExcelFile = new FileInputStream(dataPath);

		   // Access the required test data sheet

		   ExcelWBook = new XSSFWorkbook(ExcelFile);

		   ExcelWSheet = ExcelWBook.getSheet(SheetName);

		   int startRow = 1;

		   int startCol = 0;

		   int ci,cj;

		   int totalRows = ExcelWSheet.getLastRowNum();

		   // you can write a function as well to get Column count

		   int totalCols = 2;

		   tabArray=new String[totalRows][totalCols];

		   ci=0;

		   for (int i=startRow;i<=totalRows;i++, ci++) {           	   

			  cj=0;

			   for (int j=startCol;j<=totalCols;j++, cj++){

				   tabArray[ci][cj]=getCellData(i,j);

				   //System.out.println(tabArray[ci][cj]);  

					}

				}

			}

		catch (FileNotFoundException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

			}

		catch (IOException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

			}

		return(tabArray);

		}


public static String getCellData(int RowNum, int ColNum) throws Exception {


	try{

		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	
        switch (Cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:        	
			return Cell.getStringCellValue();
        case HSSFCell.CELL_TYPE_NUMERIC:
        	Double  cellValue = Cell.getNumericCellValue();
        	Integer i = cellValue.intValue();
			return String.valueOf(i);
		default : 
			return Cell.getStringCellValue();
        }
	}
		
		catch (Exception e){

		System.out.println(e.getMessage());

		throw (e);

		}

	}
*/

}
