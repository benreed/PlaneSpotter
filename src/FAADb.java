import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FAADb {

	private String fname = "" ;
	private HashMap<String, AircraftLookupResult> theMap = null ;
	
	public FAADb ( String fname ) {
		theMap = new HashMap<String, AircraftLookupResult>();
		this.fname = fname ;
		loadFile ( ) ;
	}
	
	public AircraftLookupResult lookup ( String hexCode ) {
		return theMap.get( hexCode ) ;
	}
	private void loadFile ( ) {
		boolean result = true ;
		BufferedReader br = null ;
		int numRecs = 0 ;
		
		try {
			br = new BufferedReader(new FileReader(fname));
		} 
		catch ( IOException e ) {
			e.printStackTrace() ;
		}
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	        	numRecs ++ ;
	        	String[] dataArray = line.split(",");
	        	AircraftLookupResult ac = new AircraftLookupResult ( dataArray[2], dataArray[3],
	        			dataArray[4] ) ;
	        	theMap.put(dataArray[1].trim (), ac ) ;
	            line = br.readLine();
	        }
	    }
	    catch ( Exception e ) {
	    	e.printStackTrace() ;
	    } finally {
	    	try {
	    		br.close();
	    	}
	    	catch ( Exception e ) {
	    		e.printStackTrace() ;
	    	}
	    }

	    System.out.println ( "recs read =" + numRecs ) ;
		
	}
	
	

}
