import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// FlightMap has responsibility to remember iterator position so that caller (Observer) 
// doesn't have to

public class FlightMap {
	
	private HashMap map = new HashMap();
	private Iterator i = null;
	
	public void add(String index, Flight flight) {
		Flight temp = null;
		
		if (map.containsKey(index)) {
			
			switch (flight.getTypeCreatedFrom()) {
			case 1:
				temp = (Flight)map.get(index);
				System.out.println( "in map.add, case 1 " + temp ) ;
				temp.setFlightNumber(flight.getFlightNumber());
				map.put(index, temp);
				break;
			case 2:
				temp = (Flight)map.get(index);
				System.out.println( "in map.add, case 2 " + temp ) ;
				temp.setAircraftID(flight.getAircraftID());
				temp.setManufacturer(flight.getManufacturer());
				temp.setAircraftType(flight.getAircraftType());
				temp.setTailNumber(flight.getTailNumber());
				temp.setLatitude(flight.getLatitude());
				temp.setLongitude(flight.getLongitude());
				temp.setAltitude(flight.getAltitude());
				map.put(index, temp);
				break;
			case 3:
				temp = (Flight)map.get(index);
				System.out.println( "in map.add, case 3 " + temp ) ;
				temp.setAircraftID(flight.getAircraftID());
				temp.setManufacturer(flight.getManufacturer());
				temp.setAircraftType(flight.getAircraftType());
				temp.setTailNumber(flight.getTailNumber());
				temp.setLatitude(flight.getLatitude());
				temp.setLongitude(flight.getLongitude());
				temp.setAltitude(flight.getAltitude());
				//System.out.println( "in map.add, case 3, manf=" +temp.getManufacturer() ) ;
				map.put(index, temp);
				break;
			case 4:
				temp = (Flight)map.get(index);
				System.out.println( "in map.add, case 4 " + temp ) ;
				temp.setSpeed(flight.getSpeed());
				temp.setHeading(flight.getHeading());
				temp.setVerticalRate(flight.getVerticalRate());
				map.put(index, temp);
				break;
			case 5:
				break;
			case 7:
				break;
			case 8:
				break;
			default:
				System.out.println("Oops in FlightMap.add(). Type created from is " + flight.getTypeCreatedFrom());
				break;
			}
			
//			temp = (Flight)map.get(index);
//			temp.setFlightNumber(flight.getFlightNumber());
//			map.put(index, temp);
		} else {
		map.put(index, flight);
		}
	}
	
	// Called by Observer
	public void getFirst() {
		Flight result = new Flight();
		
		Set set = map.entrySet();
		i = set.iterator();
		
	}
	
	// Called by Observer
	public Flight getNext() {
		//Flight result = new Flight();
		Flight result = null;
		
		if(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			result = (Flight)me.getValue();
		} else {
			return null;
		}
		
		return result;
		
	}
	
	public void listContents() {
		Set set = map.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());
		}
	}
	
	public void pruneMap(int milliseconds) {
		Set set = map.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			long date = System.currentTimeMillis();
			Flight thisFlight = (Flight)me.getValue();
			Date lastSeen = thisFlight.getLastSeen();
			//System.out.println("Time for Flight " + thisFlight.getAircraftID() + ": " + (date - lastSeen.getTime()));
			if ((date - lastSeen.getTime()) > milliseconds) {
				System.out.println("Removing flight " + thisFlight.getAircraftID());
				i.remove();
			}
		}
		
	}
	
//	public void update(String index, Flight flight) {
//		
//	}

}
