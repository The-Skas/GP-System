/**
 * 
 */
package mapper;

import java.util.Set;

import object.Speciality;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

/**
 * @author VJ
 *
 */
public class SpecialityDMO extends GPSISDataMapper<Speciality> {

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public Set<Speciality> getAllByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public Speciality getByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#put(java.lang.Object)
	 */
	@Override
	public void put(Speciality o) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#removeById(int)
	 */
	@Override
	public void removeById(int id) {
		// TODO Auto-generated method stub

	}

}
