
public class Bearing {
	private double azimuth, distance, elevation;
	
	public Bearing() {
		
	}

	public Bearing(double azimuth, double distance, double elevation) {
		super();
		this.azimuth = azimuth;
		this.distance = distance;
		this.elevation = elevation;
	}

	public double getAzimuth() {
		return azimuth;
	}

	public double getDistance() {
		return distance;
	}

	public double getElevation() {
		return elevation;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	@Override
	public String toString() {
		return "Bearing [azimuth=" + azimuth + ", distance=" + distance
				+ ", elevation=" + elevation + "]";
	}
	
	
}
