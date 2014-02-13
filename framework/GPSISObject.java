package framework;
/** GPSISObject
 * All first level Objects should extend this class. 
 * 
 * @author Vijendra Patel
 * @version 2
 *
 */

public abstract class GPSISObject extends GPSISFramework {
	protected int id;
	
	public int getId()
	{
            return this.id;
	}
        
    public void setId(int id)
    {
        this.id = id;
    }
}

/**
 * End of File: GPSISObject.java
 * Location: gpsis/framework
 */