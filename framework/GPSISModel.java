package gpsis.framework;
/** GPSISModel
 * Superclass for all Models in GPSIS. For use when writing your Model.
 * 
 * @author Vijendra Patel
 * @date 16/01/2014
 */

public class GPSISModel extends GPSISFramework {
	protected int id;
		
	/** GPSISModel constructor for new objects
	 * 
	 */
	public GPSISModel()
	{
		
	}
	
	/** GPSISModel constructor for objects already in Data storage
	 *  
	 * @param id
	 */
	public GPSISModel(int id)
	{
		this.id = id;
	}
	
	/** save
	 * Save this object to the database
	 */
	protected void save()
	{
		
	}
		
}

/**
 * End of File: GPSISModel.java
 * Location: gpsis/framework
 */