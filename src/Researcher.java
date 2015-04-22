import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.json.parsers.*;

public class Researcher {

	private JsonParserFactory factory;
	private JSONParser parser;
	private String appID = "70aed31f";
	private String appKey= "95f2c3479c95a6dc47ec519896c5809e";
	private FAADb db = null ;
	
	public Researcher() {
		
		try {
			db = new FAADb ( "C:\\Pet Project Workspace\\PlaneSpotter\\faa_lookup.out") ;
		}
		catch ( Exception e ) {
			e.printStackTrace() ;
		}
		factory = JsonParserFactory.getInstance();
		parser = factory.newJsonParser();
	}
	
	public AircraftLookupResult faaLookup(Flight flight) {
		AircraftLookupResult result = db.lookup(flight.getAircraftID());
		return result;
	}
	
	public AircraftLookupResult faaLookup(String hexCode) {
		AircraftLookupResult result = db.lookup(hexCode);
		return result;
	}
	
	public void lookup(Flight flight) {
		String flightNumber = flight.getFlightNumber();
		String carrier = flightNumber.substring(0, 2);
		String justTheNumber = flightNumber.substring(3); // Just the number from the six-char flight # returned
		String day = flight.getLastSeenDay();
		String month = flight.getLastSeenMonth();
		String year = flight.getLastSeenYear();
		String depOrArr = "departing";
		String urlString = "https://api.flightstats.com/flex/schedules/rest/v1/json/flight/" 
			+ carrier + "/" 
			+ justTheNumber + "/" 
			+ depOrArr +"/" 
			+ year + "/" 
			+ month + "/" 
			+ day + "?"
			+ "appId=" + appID
			+ "&appKey=" + appKey;
		
		try {
			URL url = new URL(urlString);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			inputLine = in.readLine();
			if (inputLine != null) {
				System.out.println(inputLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
