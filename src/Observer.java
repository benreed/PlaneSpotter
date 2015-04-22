import java.util.Iterator;
import java.util.Set;

// Credit to Don Cross at
// http://cosinekitty.com/compass.html

//DEBUG: Coordinates of our house in Apopka
// 28.688955, -81.515261, elevation 30 m

public class Observer {
	
	private Bearing currentBearing; // Bearing of last flight under consideration
	// Min and max azimuth determine possible viewing angle in a degree range
	// Min elevation indicates observer's needed altitude to view a flight from the ground
	private float minAzimuth, maxAzimuth, maxDistance, minElevation;
	private Location myLocation;
	
	public Observer() {
		this.myLocation = new Location();
		this.maxDistance = 16;		
	}
	
	public Observer(Location myLocation) {
		this.myLocation = new Location();
		this.myLocation = myLocation;
		this.maxDistance = 16;
	}

	public Bearing calculate(Location a, Location b) {
		Bearing bearing = new Bearing();
		
		Coordinates ap = locationToPoint(a);
		Coordinates bp = locationToPoint(b);
		double distKm = 0.001 * Math.round(distance(ap, bp));
		//System.out.println("dist(mi)= " + kmToMiles(distKm));
		bearing.setDistance(distKm);
		
		// Let's use a trick to calculate azimuth:
        // Rotate the globe so that point A looks like latitude 0, longitude 0.
        // We keep the actual radii calculated based on the oblate geoid,
        // but use angles based on subtraction.
        // Point A will be at x=radius, y=0, z=0.
        // Vector difference B-A will have dz = N/S component, dy = E/W component.
		
		Coordinates br = rotateGlobe (b, a, bp.getRadius(), ap.getRadius());
		double theta = Math.atan2 (br.getZ(), br.getY()) * 180.0 / Math.PI;
		double azimuth = 90.0 - theta;
		if (azimuth < 0.0) {
            azimuth += 360.0;
        }
        if (azimuth > 360.0) {
            azimuth -= 360.0;
        }
        //System.out.println("azimuth= " + (Math.round(azimuth*10)/10));
        bearing.setAzimuth(azimuth);
		
        // Calculate altitude, which is the angle above the horizon of B as seen from A.
        // Almost always, B will actually be below the horizon, so the altitude will be negative.
        
        double shadow = Math.sqrt ((br.getY() * br.getY()) + (br.getZ() * br.getZ()));
        double altitude = Math.atan2(br.getX() - ap.getRadius(), shadow) * 180.0 / Math.PI;
        //System.out.println("elevation= " + altitude);
        bearing.setElevation(altitude);
        return bearing;
        
	}

	public double distance (Coordinates ap, Coordinates bp) {
		double dx = ap.getX() - bp.getX();
        double dy = ap.getY() - bp.getY();
        double dz = ap.getZ() - bp.getZ();
        return Math.sqrt (dx*dx + dy*dy + dz*dz);
	}

	private double earthRadiusInMeters(double latitudeRadians) {
		// http://en.wikipedia.org/wiki/Earth_radius
        double a = 6378137.0;  // equatorial radius in meters
        double b = 6356752.3;  // polar radius in meters
        double cosx = Math.cos(latitudeRadians);
        double sinx = Math.sin(latitudeRadians);
        double t1 = a * a * cosx;
        double t2 = b * b * sinx;
        double t3 = a * cosx;
        double t4 = b * sinx;
        return Math.sqrt((t1*t1 + t2*t2) / (t3*t3 + t4*t4));
	}

	public double feetToMeters (double feet) {
		return feet / 3.2808 ;
	}
	
	public Bearing getCurrentBearing() {
		return currentBearing;
	}
	
	public float getMaxDistance() {
		return maxDistance;
	}
	
	public float getMinElevation() {
		return minElevation;
	}
	
	public Flight getVisible(FlightMap fm, Researcher res, Reporter rep) throws InterruptedException {
		//Flight result = new Flight();
		Flight result = null;
		
		fm.getFirst();
		// Priming in case of an empty list, like reading a file
		result = fm.getNext();
		while(result != null) {
			if (isVisible(result)) {
				result.setBearingToFlight(getCurrentBearing());
				//System.out.println("Send to Reporter.");
				//System.out.println(result.getLastSpoken());
				rep.queueMessage(result); // DEBUG
				//res.lookup(result);
			}
			result = fm.getNext();
		}
		
		return result;
	}
	
	public boolean isVisible(Flight flight) {
		boolean result = false;
		currentBearing = calculate(myLocation, flight.getLocation());
		
		if(currentBearing.getDistance() < maxDistance) {
			// TO-DO: Add nested logic for better accuracy
			result = true;
			//System.out.println(currentBearing);
		}
		//System.out.println(currentBearing.getDistance());
		
		return result;
	}

	public static double kmToMiles (double km) {
		return km * 0.62137;
	}

	public Coordinates locationToPoint(Location c) {
		Coordinates retCoord = new Coordinates();
		
		double lat = c.getLatitude() * Math.PI / 180.0;
		double lon = c.getLongitude() * Math.PI / 180.0;
		retCoord.setRadius(c.getAltitude() + earthRadiusInMeters(lat));
		double cosLon = Math.cos (lon);
        double sinLon = Math.sin (lon);
        double cosLat = Math.cos (lat);
        double sinLat = Math.sin (lat);
        retCoord.setX(cosLon * cosLat * retCoord.getRadius());
        retCoord.setY(sinLon * cosLat * retCoord.getRadius());
        retCoord.setZ(sinLat * retCoord.getRadius());
        return retCoord;
	}
	
	public Coordinates rotateGlobe(Location b, Location a, double bradius, double aradius) {
		// Get modified coordinates of 'b' by rotating the globe so that 'a' is at lat=0, lon=0.
		Location br = new Location();
		Coordinates retCoord = new Coordinates();
		
		br.setLatitude(b.getLatitude());
		br.setLongitude(b.getLongitude() - a.getLongitude());
		br.setAltitude(b.getAltitude());
		
		Coordinates brp = locationToPoint(br);
		
		// scale all the coordinates based on the original, correct geoid radius
		brp.setX(brp.getX() * (bradius / brp.getRadius()));
		brp.setY(brp.getY() * (bradius / brp.getRadius()));
		brp.setZ(brp.getZ() * (bradius / brp.getRadius()));
		brp.setRadius(bradius);
		
		// Rotate brp cartesian coordinates around the z-axis by a.lon degrees,
        // then around the y-axis by a.lat degrees.
        // Though we are decreasing by a.lat degrees, as seen above the y-axis,
        // this is a positive (counterclockwise) rotation (if B's longitude is east of A's).
        // However, from this point of view the x-axis is pointing left.
        // So we will look the other way making the x-axis pointing right, the z-axis
        // pointing up, and the rotation treated as negative.
		
		double alat = -a.getLatitude() * Math.PI / 180.0;
		double acos = Math.cos (alat);
		double asin = Math.sin (alat);
		
		retCoord.setX((brp.getX() * acos) - (brp.getZ() * asin));
		retCoord.setY(brp.getY());
		retCoord.setZ((brp.getX() * asin) + (brp.getZ() * acos));
		
		return retCoord;
	}
	
	public void setCurrentBearing(Bearing currentBearing) {
		this.currentBearing = currentBearing;
	}
	
	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public void setMinElevation(float minElevation) {
		this.minElevation = minElevation;
	}
	
	//public getVisiblePosition() {
		
	//}
	
	//public String coordinates() {
		
	//}
	
	public String toString() {
		return myLocation.toString();
	}
}
