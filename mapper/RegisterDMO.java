/** RegisterDMO
 * Data Mapper for Register Objects
 * 
 * @author Vijendra Patel (vp302)
 */
package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import object.Register;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class RegisterDMO extends GPSISDataMapper<Register> {
	
	private static RegisterDMO instance;
	
	/** getInstance
	 * @return the only instance of this Data Mapper
	 */
	public static RegisterDMO getInstance() {
		if (instance == null)
			instance = new RegisterDMO();
		return instance;
	}
	
	/** RegisterDMO Constructor
	 * set the table name to the Register table in the database
	 */
	private RegisterDMO() {
		this.tableName = "Register";
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public List<Register> getAllByProperties(SQLBuilder query) throws EmptyResultSetException {

		List<Register> register = new ArrayList<Register>();

		ResultSet resRegister;
		try {
			resRegister = getResultSet(query, "Register");

			while (resRegister.next()) {
				register.add(new Register(resRegister.getInt("id"), staffMemberDMO.getById(resRegister
						.getInt("staff_member_id")), resRegister.getDate("date"), resRegister.getInt("availability")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// this should never be reached... All associated Registers will be
			// removed if a StaffMember is removed
			e.printStackTrace();
		}

		if (register.isEmpty())
			throw new EmptyResultSetException();
		else
			return register;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public Register getByProperties(SQLBuilder query) throws EmptyResultSetException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#put(java.lang.Object)
	 */
	@Override
	public void put(Register o) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String date = fm.format(o.getDate().getTime());

		try {
			SQLBuilder sql =
					new SQLBuilder("staff_member_id", "=", "" + o.getStaffMember().getId()).SET("date", "=", date).SET(
							"availability", "=", "" + o.getAvailability());
			putHelper(sql, "Register", o);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

/**
 * End of File: RegisterDMO.java 
 * Location: mapper
 */