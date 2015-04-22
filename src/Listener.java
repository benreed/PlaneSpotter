import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Listener {

	private BufferedReader in;
	private Socket client;

	public Listener() {
		try {
			//client = new Socket("192.168.1.50", 30003);
			
			// Test externally from LAN
			client = new Socket("snoozecat.duckdns.org", 30003);
			
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (Exception e) {
			System.out.println("Exception in listener constructor.");
		}

	}

	// Returns data as a whole string
	public String getNextString() throws Exception {
		String str;
		str = in.readLine();
		// System.out.println(str);
		return str;
	}

	// Returns data as a list of delineated fields
	public List<String> getNextList() throws Exception {
		String str;
		str = in.readLine();
		List<String> items = Arrays.asList(str.split(","));
		// System.out.println(items);
		return items;
	}

	// Uses data to create a Flight object to be recorded and updated
	// in the FlightMap
	public Flight getNextFlight(Researcher res) {
		boolean done = false;
		List<String> list = null;
		int type = 0; // Records what MSG type we get from the radio, for
						// assignment to field of Flight object

		while (!done) {
			try {
				list = getNextList();
			} catch (Exception e) {
				System.out
						.println("Exception in getNextFlight() (returning null)");
				done = true; // DEBUG!!!
				return null;
			}

			if (list.get(0).equals("MSG")) {
				type = Integer.parseInt(list.get(1));
				done = true;
				// System.out.println(list);
			}

		}

		// Use parsed data to instantiate a Flight with that data as fields
		Flight flight = new Flight(type, list.get(4));
		// Set last seen unconditionally; info is available across all radio MSG
		// types
		flight.setLastSeen(list.get(6), list.get(7));

		// Store the result of the FAA file check on the aircraft
		AircraftLookupResult lookup = new AircraftLookupResult();

		switch (type) {

		// Get flight number from type 1 MSG
		case 1:
			// System.out.println(list.get(10));
			flight.setFlightNumber(list.get(10));
			break;

		// Get other info from type 2/3 MSG
		case 2:
			flight.setAltitude(Double.parseDouble(list.get(11)));
			flight.setLatitude(Double.parseDouble(list.get(14)));
			flight.setLongitude(Double.parseDouble(list.get(15)));

			// Get detailed aircraft information from FAA lookup
			lookup = res.faaLookup(flight);
			System.out.println(lookup);
			flight.setManufacturer(lookup.getManf());
			flight.setAircraftType(lookup.getModel());
			flight.setTailNumber(lookup.getnNumber());
			break;
		case 3:
			flight.setAltitude(Double.parseDouble(list.get(11)));
			flight.setLatitude(Double.parseDouble(list.get(14)));
			flight.setLongitude(Double.parseDouble(list.get(15)));

			// Get detailed aircraft information from FAA lookup
			lookup = res.faaLookup(flight);

			if (lookup != null) {
				System.out.println(lookup);
				flight.setManufacturer(lookup.getManf());
				flight.setAircraftType(lookup.getModel());
				flight.setTailNumber(lookup.getnNumber());
			}
			break;
		case 4:
			flight.setSpeed(list.get(12));
			flight.setHeading(list.get(13));
			flight.setVerticalRate(list.get(16));
			break;
		case 5:
			break;
		case 7:
			break;
		case 8:
			break;
		default:
			System.out
					.println("Oops in Listener.getNextFlight(). Type created from is "
							+ flight.getTypeCreatedFrom());
			break;
		}

		// if (list.get(1).equals("1")) {
		//
		//
		//
		// } else if (list.get(1).equals("2") || list.get(1).equals("3")) {
		//
		// }

		return flight;
	}
}
