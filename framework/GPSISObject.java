package framework;
/** GPSISObject
 * All first level Objects should extend this class. 
 * 
 * @author Vijendra Patel
 * @version 2
 *
 */
import mapper.*;

public abstract class GPSISObject {
	protected int id;
	
	protected static RoomDMO roomTbl;
	protected static CareProgrammeDMO careProgrammeTbl;
//  protected static PatientDMO patientTbl;
//	protected static AppointmentDMO appointmentTbl
//	etc.
	
	public int getId()
	{
		return this.id;
	}
}

/**
 * End of File: GPSISObject.java
 * Location: gpsis/framework
 */