import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {
	private Bearing bearingToFlight;
	private String flightNumber, aircraftID, tailNumber, manufacturer,
			aircraftType, speed, heading, verticalRate;

	private Date lastSeen;

	private long lastSpoken;
	private Location location;
	private boolean sayHeading = true;

	private int typeCreatedFrom; // Indicates the radio MSG type used to
									// instantiate this Flight in order to
									// update object values properly

	public Flight() {
		location = new Location();
		// this.setLastSpoken(System.currentTimeMillis());
	}

	public Flight(int typeCreatedFrom, String aircraftID) {
		this.typeCreatedFrom = typeCreatedFrom;
		this.aircraftID = aircraftID;
		location = new Location();
		this.setLastSpoken(System.currentTimeMillis());
	}

	public Flight(int typeCreatedFrom, String flightNumber, String aircraftID) {
		this.typeCreatedFrom = typeCreatedFrom;
		this.flightNumber = flightNumber;
		this.aircraftID = aircraftID;
		location = new Location();
		this.setLastSpoken(System.currentTimeMillis());
	}

	public Flight(String flightNumber, String aircraftID) {
		this.flightNumber = flightNumber;
		this.aircraftID = aircraftID;
		location = new Location();
		this.setLastSpoken(System.currentTimeMillis());
	}

	public String getAircraftID() {
		return aircraftID;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public double getAltitude() {
		return location.getAltitude();
	}
	
	public Bearing getBearingToFlight() {
		return bearingToFlight;
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}

	public String getHeading() {
		return heading;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public String getLastSeenDay() {
		SimpleDateFormat sm = new SimpleDateFormat("dd");
		return sm.format(lastSeen);
	}

	public String getLastSeenMonth() {
		SimpleDateFormat sm = new SimpleDateFormat("MM");
		return sm.format(lastSeen);
	}
	
	public String getLastSeenYear() {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy");
		return sm.format(lastSeen);
	}

	public long getLastSpoken() {
		return lastSpoken;
	}

	public double getLatitude() {
		return location.getLatitude();
	}

	public Location getLocation() {
		return location;
	}

	public double getLongitude() {
		return location.getLongitude();
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getSpeed() {
		return speed;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public int getTypeCreatedFrom() {
		return typeCreatedFrom;
	}

	public String getVerticalRate() {
		return verticalRate;
	}

	public boolean isSayHeading() {
		return sayHeading;
	}

	public void setAircraftID(String aircraftID) {
		this.aircraftID = aircraftID;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public void setAltitude(double altitude) {
		location.setAltitude(altitude);
	}

	public void setBearingToFlight(Bearing bearingToFlight) {
		this.bearingToFlight = bearingToFlight;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	public void setLastSeen(String date, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:m:s.S");
		try {
			lastSeen = sdf.parse(date + " " + time);
		} catch (ParseException e) {

		}
	}

	public void setLastSpoken(long lastSpoken) {
		this.lastSpoken = lastSpoken;
	}

	public void setLatitude(double latitude) {
		location.setLatitude(latitude);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLongitude(double longitude) {
		location.setLongitude(longitude);
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setSayHeading(boolean sayHeading) {
		this.sayHeading = sayHeading;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public void setTypeCreatedFrom(int typeCreatedFrom) {
		this.typeCreatedFrom = typeCreatedFrom;
	}

	public void setVerticalRate(String verticalRate) {
		this.verticalRate = verticalRate;
	}

	@Override
	public String toString() {
		return "Flight [flightNumber=" + flightNumber + ", aircraftID="
				+ aircraftID + ", tailNumber=" + tailNumber + ", manufacturer="
				+ manufacturer + ", aircraftType=" + aircraftType + ", speed="
				+ speed + ", heading=" + heading + ", verticalRate="
				+ verticalRate + ", lastSeen=" + lastSeen + ", lastSpoken="
				+ lastSpoken + ", location=" + location + ", typeCreatedFrom="
				+ typeCreatedFrom + "]";
	}

}
