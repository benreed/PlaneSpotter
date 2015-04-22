
public class PlaneSpotter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int reportTimer = 0;
		int reportInterval = 6000;
		
		Listener listener = new Listener();
		//ComPortWriter comWriter = new ComPortWriter();
		Reporter reporter = new Reporter();
		//Reporter reporter = new Reporter(comWriter);
		Researcher researcher = new Researcher();
		Flight flight = new Flight();
		FlightMap map = new FlightMap();
		
		Bearing bearing = new Bearing();
		//Location a = new Location(28.688955, -81.515261, 30); // Apopka
		Location a = new Location(28.572564, -81.368307, 30); // Orlando Science Center
		Observer observer = new Observer(a);
		reporter.speak("Hello Plane Spotter");
//		Location b = new Location(28.27732, -82.00917, 10000); // Test flight
//		bearing = observer.calculate(a, b);
//		System.out.println(bearing);
		
		while (true) {
		//for(int i=0; i<6000; i++) {
			try {
				//listener.getNextString();
				//listener.getNextList();
				//System.out.println(flight = listener.getNextFlight());
				//System.out.println(flight.getLastSeen());
				flight = listener.getNextFlight(researcher);
				//System.out.println(flight.getLastSeen().toString());
				//System.out.println(flight.getLastSeenDay() + " " + flight.getLastSeenMonth() + " " + flight.getLastSeenYear());
				map.add(flight.getAircraftID(), flight);
				map.pruneMap(90000);
				observer.getVisible(map, researcher, reporter);
				reportTimer++;
				// Unoptimized resetting timer to speak occasionally, confirming audio is working
				if (reportTimer == reportInterval) {
					reporter.speak("Standing by");
					reportTimer = 0;
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}
		//reporter.speak("Loop is over");
		//map.listContents();
	}

}
