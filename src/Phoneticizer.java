import java.util.ArrayList;

// Spell out letters and numbers in ICAO phonetic alphabet
public class Phoneticizer {
	
	private ArrayList<String> numList ;
	private ArrayList<String> alphaList ;
	
	Phoneticizer ()
	{
		numList = new ArrayList<String> ( 10 ) ;
		numList.add ( '0' - '0', "zero") ;
		numList.add ( '1' - '0', "one" ) ;
		numList.add ( '2' - '0', "two" ) ;
		numList.add ( '3' - '0', "three" ) ;
		numList.add ( '4' - '0', "four" ) ;
		numList.add ( '5' - '0', "five" ) ;
		numList.add ( '6' - '0', "six" ) ;
		numList.add ( '7' - '0', "seven" ) ;
		numList.add ( '8' - '0', "eight" ) ;
		numList.add ( '9' - '0', "nine" ) ;
		
		alphaList = new ArrayList<String> ( 28 ) ;
		alphaList.add ( 'A' - 'A', "alfa" );
		alphaList.add ( 'B' - 'A', "bravo");
		alphaList.add ( 'C' - 'A', "charlie");
		alphaList.add ( 'D' - 'A', "delta") ;
		alphaList.add ( 'E' - 'A', "echo" ) ;
		alphaList.add ( 'F' - 'A', "foxtrot");
		alphaList.add ( 'G' - 'A', "golf");
		alphaList.add ( 'H' - 'A', "hotel");
		alphaList.add ( 'I' - 'A', "india");
		alphaList.add ( 'J' - 'A', "juliett");
		alphaList.add ( 'K' - 'A', "kilo");
		alphaList.add ( 'L' - 'A', "lima");
		alphaList.add ( 'M' - 'A', "mike");
		alphaList.add ( 'N' - 'A', "november");
		alphaList.add ( 'O' - 'A', "oss cah");
		alphaList.add ( 'P' - 'A', "pah pah");
		alphaList.add ( 'Q' - 'A', "keh beck");
		alphaList.add ( 'R' - 'A', "romeo");
		alphaList.add ( 'S' - 'A', "sierra");
		alphaList.add ( 'T' - 'A', "tango" );
		alphaList.add ( 'U' - 'A', "uniform");
		alphaList.add ( 'V' - 'A', "victor");
		alphaList.add ( 'W' - 'A', "whiskey");
		alphaList.add ( 'X' - 'A', "xray");
		alphaList.add ( 'Y' - 'A', "yankee");
		alphaList.add ( 'Z' - 'A', "zulu");
	}
	
	public String phoneticize ( String inStr )
	{
		String temp = inStr.toUpperCase ( ) ;
		StringBuilder str = new StringBuilder ( ) ;
		
		for ( int i = 0 ; i < temp.length ( ) ; i ++ )
		{
			char c = temp.charAt(i) ;
			if ( c >= 'A' && c <= 'Z' )
			{
				str.append ( alphaList.get( c - 'A' )  ) ;
				str.append( " " ) ;
			}
			else if ( c >= '0' && c <= '9'  )
			{
				str.append( numList.get ( c - '0' ) ) ;
				str.append( " " ) ;
			}
			else {
				if ( c == ' ' )
				{
					str.append( " " ) ;
				}
				else
				{
					str.append( c + " ") ;
				}
			}
		}
		return str.toString() ;
	}
	
}
