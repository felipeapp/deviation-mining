/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class work with the map file of UFRN system version and  publication date
 * 
 * @author jadson
 *
 */
public class VersionMapUtil {
	
	//Default path for the file where we have the system versions release dates //
	private String csvFile = "src/main/resources/versions_map.csv";
	
	private static List<String[]> cvsLines = new ArrayList<>();
	
	public VersionMapUtil(){	
		if(cvsLines.size() == 0 ){
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ";";
			
			try {
	
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] data = line.split(cvsSplitBy);
					cvsLines.add( data );
				}
	
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	
	
	/**
	 * @param version
	 * @return The initial date of a version was pushed in the UFRN
	 */
	public Date getInitialDateOfVersion(String version){
		
		try {
			for (String[] data : cvsLines) {
				if(data[0].equals(version)){
					DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
					Date date = (Date) formatter.parse(data[1]);
					return date;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * @param version
	 * @return The final date of a version was pushed in the UFRN
	 */
	public Date getFinalDateOfVersion(String version){
		
		try {
			for (String[] data : cvsLines) {
				if(data[0].equals(version)){
					DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
					Date date = (Date) formatter.parse(data[2]);
					return date;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	// Allow define a different file
	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

}
