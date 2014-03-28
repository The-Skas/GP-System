/** GPSIS
 * Initial loading Class.
 * 
 * @author Vijendra Patel (vp302)
 */
package framework;

public class GPSIS {
	
	/** main
	 * Where the application will start.
	 */
	public static void main(String[] args)
	{
		GPSISFramework GPSIS = GPSISFramework.getInstance();
		GPSIS.initialise();		
		
	}
}

/**
 * End of File: GPSIS.java
 * Location: framework
 */