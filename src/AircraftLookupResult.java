
public class AircraftLookupResult {
	@Override
	public String toString() {
		return "AircraftLookupResult [nNumber=" + nNumber + ", model=" + model
				+ ", manf=" + manf + "]";
	}

	private String nNumber = "" ;
	private String model = "" ;
	private String manf = "" ;
	
		public AircraftLookupResult ( ) {
	
		}
		
		public AircraftLookupResult ( String nNumber, String manf, String model) {
			this.nNumber = nNumber.trim ( ) ;
			this.model = model.trim ( ) ;
			this.manf = manf.trim () ;
		}

		public String getnNumber() {
			return nNumber;
		}

		public void setnNumber(String nNumber) {
			this.nNumber = nNumber;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getManf() {
			return manf;
		}

		public void setManf(String manf) {
			this.manf = manf;
		}
}
