package framework;
/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel
 * @version 2
 */

public class GPSISFramework {

	private static final GPSIS instance = new GPSIS(); // Singleton implementation
	
	/** getInstance() 
	 * returns the ONLY instance of GPSIS
	 * @return the instance of GPSIS
	 */
	public static GPSIS getInstance()
	{
		return instance;
	}
	
	/** initialise
	 * Initialises the Framework
	 * Initialisation List: 
	 *  - Database Connection
	 *  	- Check tables
	 *  		- If not, initialise tables
	 *  	- Check StaffMembers (if none, fresh installation, execute installation)
	 *  - Load Login Module
	 *  - Load Menu Module
	 */
	public void initialise()
	{		
		System.out.println("GPSIS Main Framework");
		System.out.print("\t- Connecting to Database");
		if ((GPSISDataMapper.connectToDatabase()))
			System.out.println("\t\tSuccess.");
		else
			System.out.println("\t\tFailed.");
		
		
	}
	
}

/**
 * End of File: GPSISFramework.java
 * Location: gpsis/framework
 */
