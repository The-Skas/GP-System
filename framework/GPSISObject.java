/** GPSISObject 
 * All first level Objects should extend this class.
 * 
 * @author Vijendra Patel (vp302)
 * 
 */
package framework;

public abstract class GPSISObject extends GPSISFramework {
	protected int id;

	/** getId
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/** setId
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}

/**
 * End of File: GPSISObject.java 
 * Location: framework
 */
