
public class Location {
	private double altitude, latitude, longitude;
	
	public Location() {
		
	}
	
	public Location(double latitude, double longitude, double altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}
	
	public double getAltitude() {
		return altitude;
	}

	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	
//	public double parseAngle(String value, double limit) {
//		double angle = Float.parseFloat(value);
//		
//		if (angle == 0.0 || (angle < -limit) || (angle > limit)) {
//            return 0.0;
//        } else {
//            return angle;
//        }
//	}
	
//	public double parseElevation(String value) {
//		double angle = Float.parseFloat(value);
//		if ( angle == 0.0 ) {
//            return 0.0 ;
//        } else {
//            return angle;
//        }
//		
//	}
	
//	public Location parseLocation(String latString, String longString, String elvString) {
//		double latitude = parseAngle(latString, 90.0);
//		Location location = new Location();
//		if (latitude != 0.0) {
//			double longitude = parseAngle(longString, 180.0);
//			if (longitude != 0.0) {
//				double elevation = Float.parseFloat(elvString);
//				if (elevation != 0.0) {
//					location.setLatitude(latitude);
//					location.setLongitude(longitude);
//					location.setAltitude(elevation);
//				}
//			}
//		}
//		return location;
//	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [altitude=" + altitude + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
