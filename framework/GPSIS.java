package framework;

import java.util.Locale;

/** GPSIS
 * Initial loading Class.
 * 
 * @author Vijendra Patel
 * @date 16/01/2014
 */

public class GPSIS {
	
	/** main
	 * Where the application will start.
	 */
	public static void main(String[] args)
	{
		System.out.println(Locale.getDefault().getDisplayCountry());
		GPSISFramework GPSIS = new GPSISFramework();		
		GPSIS.initialise();
	}
}

/**
 * End of File: GPSIS.java
 * Location: gpsis/framework
 */