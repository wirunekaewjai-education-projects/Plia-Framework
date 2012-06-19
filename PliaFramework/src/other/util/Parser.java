package other.util;

public class Parser
{
	// Fastest Parse Integer
	// http://nadeausoftware.com/articles/2009/08/java_tip_how_parse_integers_quickly
	public static int parseInt( final String s )
	{
	    // Check for a sign.
	    int num  = 0;
	    int sign = -1;
	    final int len  = s.length( );
	    final char ch  = s.charAt( 0 );
	    if ( ch == '-' )
	        sign = 1;
	    else
	        num = '0' - ch;

	    // Build the number.
	    int i = 1;
	    while ( i < len )
	    {
	        num = num*10 + '0' - s.charAt( i++ );
	    }

	    return sign * num;
	} 
}
