import java.io.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import com.sun.speech.freetts.*;

public class Reporter implements Runnable {
	private static final String VOICENAME = "kevin16";
	private Voice voice;
	private VoiceManager vm;
	private Phoneticizer phone;
	private String looking = "Looking ";
	private String toThe = " to the ";
	private String iSeeFlight = ", I see flight ";
	private String ofType = " of type ";
	private String heading = ", heading ";
	private long delay = 60000;
	// TODO: find a way to prune this Vector when it gets too full
	private Vector<Flight> flightsSeen = new Vector<Flight>();
	private ArrayBlockingQueue<Flight> messageQueue = new ArrayBlockingQueue<Flight>(
			2000);
	//private ComPortWriter comWriter;

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Reporter() {
		phone = new Phoneticizer();
		vm = VoiceManager.getInstance();
		getClass();
		voice = vm.getVoice(VOICENAME);
		voice.allocate();
		new Thread(this).start();
	}

	public Reporter(ComPortWriter comWriter) {
		phone = new Phoneticizer();
		vm = VoiceManager.getInstance();
		getClass();
		voice = vm.getVoice(VOICENAME);
		voice.allocate();
		//this.comWriter = comWriter;
		new Thread(this).start();
	}

	public synchronized void queueMessage(Flight f) throws InterruptedException {
		messageQueue.put(f);
	}

	private void reportFlight() throws InterruptedException {
		// Determine which compass direction the observer should look to to see
		// the flight
		Flight flight = messageQueue.take();

		String elevation = "elevation", direction = "direction", bearing = "bearing", type = "type", distance = "distance",
				altitude = "altitude";
		boolean found = false;

		long currentTime = System.currentTimeMillis();

		// Iterator it = flightsSeen.iterator();

		// while (it.hasNext()) {
		// if (flight.getAircraftID().equals(flightsSeen.get(0))) {
		// }
		// }

		// Check if flight is already in vector before speaking
		for (Flight myFlight : flightsSeen) {

			// If you find a matching aircraft ID...
			// if (flight.getAircraftID().equals(myFlight.getAircraftID())) {
			if (flight.getAircraftID().equals(myFlight.getAircraftID())
					&& flight.getFlightNumber() != null) {

				// Set found to true if already in map
				found = true;
				// System.out.println("Delta is " + (currentTime -
				// flight.getLastSpoken()) + " for flight " +
				// flight.getAircraftID());

				// If enough time has elapsed between time last spoken, speak
				if (currentTime - myFlight.getLastSpoken() > delay) {
					// Get bearing to flight
					Bearing bearingToMyFlight = myFlight.getBearingToFlight();
					double azimuth = bearingToMyFlight.getAzimuth();
					int bearingAsInt = (int) azimuth;
					direction = Talker.interpretDirection(bearingAsInt);

					// Get elevation
					double elevationToFlight = bearingToMyFlight.getElevation();
					int elevationAsInt = (int) elevationToFlight;
					elevation = Talker.interpretHeight(elevationAsInt);
					bearing = Talker.interpretDirection((Integer
							.parseInt(myFlight.getHeading())));

					// Get distance to aircraft
					double distanceToFlight = bearingToMyFlight.getDistance();
					// Convert kilometer distance to miles
					distanceToFlight = Observer.kmToMiles(distanceToFlight);
					// Truncate distance to speak-friendly int value
					int distanceAsInt = (int) distanceToFlight;
					
					// Get altitude
					int roundedAltitude = (int) myFlight.getAltitude();
					Integer altAsBigInteger = roundedAltitude;
					altitude = altAsBigInteger.toString();

					// comWriter.sendMessage("t"); // Trigger mode
					// comWriter.sendMessage("x"); // Sends a trigger to start
					// flashing
					if (myFlight.isSayHeading()) {
						voice.speak("At an altitude of " + altitude + " feet" + toThe + direction
						//voice.speak(looking + elevation + toThe + direction
								+ iSeeFlight + myFlight.getFlightNumber()
								// + ofType
								// + myFlight.getAircraftType()
								+ heading + bearing);
						myFlight.setSayHeading(false);
					} else {
						voice.speak("At an altitude of " + altitude + " feet" + toThe + direction
						//voice.speak(looking + elevation + toThe + direction
								+ iSeeFlight + myFlight.getFlightNumber()
								// + ofType
								// + myFlight.getAircraftType()
								+ + distanceAsInt + " miles out");
						myFlight.setSayHeading(true);
					}
					myFlight.setLastSpoken(System.currentTimeMillis());
				}
			}
		}

		// If we don't find the Flight already in the vector, we need to add it
		if (found == false) {
			flightsSeen.add(flight);
		}

		// if (((currentTime - flight.getLastSpoken()) % 15000) < 10 ) {
		// voice.speak(lookingToThe + direction + iSeeFlight +
		// flight.getAircraftID() + "heading"
		// + bearing);
		// flight.setLastSpoken(System.currentTimeMillis());
		// }
	}

	public void speak(String text) {
		voice.speak(text);
	}

	@Override
	public void run() {
		while (true) {
			try {
				reportFlight();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
