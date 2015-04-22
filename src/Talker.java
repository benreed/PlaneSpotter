
public abstract class Talker {

	public static String interpretDirection( int heading ) {
		String phrase = "" ;
		
	    if ( heading >= 338 || heading < 23 ) {
			phrase = "North" ;
		}
		else if ( heading >= 23 && heading < 68 ) {
			phrase = "Northeast" ;
		}
		else if ( heading >= 68 && heading < 113 ) {
			phrase = "East" ;
		}
		else if ( heading >= 113 && heading < 157 ) {
			phrase = "Southeast" ;
		}
		else if ( heading >= 157 && heading < 202 ) {
			phrase = "South" ;
		}
		else if ( heading >= 202 && heading < 247 ) {
			phrase = "Southwest" ;
		}
		else if ( heading >= 247 && heading < 293 ) {
			phrase = "West" ;
		}
		else if ( heading >= 293 && heading < 338 ) {
			phrase = "Northwest" ;
		}
		return phrase ;
	}


	public static String interpretHeight( int elevation ) {
		String high = "" ;
		if ( elevation < 10 ) {
			high = "low" ;
		} else if ( elevation < 20 ) {
			high = "halfway up" ;
		} else if ( elevation < 30 ) {
			high = "high" ;
		} else {
			high = "overhead" ;
		}
		return high ;
	}

}
