/** StaffMemberDMO
 * Data Mapper for Staff Members
 * 
 * @author Vijendra Patel (vp302)
 */
package mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import object.CalendarAppointment;
import object.MedicalStaffMember;
import object.Receptionist;
import object.ReferralObject;
import object.Register;
import object.Speciality;
import object.StaffMember;
import object.TaxForm;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class StaffMemberDMO extends GPSISDataMapper<StaffMember> {
	private static StaffMemberDMO instance;

	/** getInstance
	 * returns the only instance of the StaffMemberDMO
	 * 
	 * @return
	 */
	public static StaffMemberDMO getInstance() {
		if (instance == null)
			instance = new StaffMemberDMO();
		return instance;
	}

	/** StaffMemberDMO Constructor 
	 * This is Private as part of a Singleton
	 * implementation.
	 * 
	 * @param tableName
	 */
	private StaffMemberDMO() {
		this.tableName = "StaffMember";
	}

	/** addHoliday 
	 * create a new relation between the StaffMember and Register in
	 * StaffRegister, check if date exists in Register before creating
	 * 
	 * @param sM
	 * @param dateOfHol
	 */
	public void addHoliday(StaffMember sM, Date dateOfHol) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String holiday = fm.format(dateOfHol.getTime());

		try {
			SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + sM.getId())
					.SET("date", "=", holiday)
					.SET("availability", "=", "" + Register.HOLIDAY);
			putHelper(sql, "Register", new Register(sM, dateOfHol,
					Register.HOLIDAY));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/** addSpeciality
	 * creates a relationship between a given Speciality and StaffMember
	 * @param sM
	 * @param sp
	 */
	public void addSpeciality(StaffMember sM, Speciality sp) {
		try {
			SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""
					+ sM.getId()).SET("speciality_id", "=", "" + sp.getId());
			putHelper(sql, "StaffMemberSpeciality", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** buildStaffMember 
	 * builds a Staff Member object from the given Result Set.
	 * Used as a helper method in retrieving StaffMembers from the Database
	 * Returns the correct object type (Receptionist or MedicalStaffMember) and
	 * includes their temporary contract if needed
	 * 
	 * @param res
	 * @return a complete Staff Member
	 * @throws SQLException
	 * @throws EmptyResultSetException
	 */
	public StaffMember buildStaffMember(ResultSet res) throws SQLException,
			EmptyResultSetException {
		if (res != null) // if found, create a the StaffMember object
		{

			Calendar cal = Calendar.getInstance();
			Date endDate = null;
			if (res.getDate("end_date") != null) {
				cal.setTime(res.getDate("end_date"));
				endDate = cal.getTime();
			}

			cal.setTime(res.getDate("start_date"));
			Date startDate = cal.getTime();

			if (res.getString("role").equals("Receptionist")) {
				Receptionist r = new Receptionist(res.getInt("StaffMember.id"),
						res.getString("username"),
						res.getString("enc_password"),
						res.getString("StaffMember.first_name"),
						res.getString("StaffMember.last_name"),
						res.getBoolean("full_time"), startDate,
						res.getBoolean("office_manager"),
						res.getInt("holiday_allowance"));
				if (endDate != null)
					r.setTemporary(endDate);
				return r;
			} else {
				MedicalStaffMember mSM = new MedicalStaffMember(
						res.getInt("StaffMember.id"), res.getString("username"),
						res.getString("enc_password"),
						res.getString("StaffMember.first_name"),
						res.getString("StaffMember.last_name"),
						res.getBoolean("full_time"), startDate,
						res.getBoolean("office_manager"),
						res.getString("role"), res.getInt("holiday_allowance"));
				if (endDate != null)
					mSM.setTemporary(endDate);
				return mSM;
			}

		} else {
			// throw Exception because of Empty set
			throw new EmptyResultSetException();
		}
	}

	/** getAbsences 
	 * Returns a List of Dates that the Staff Member has been Absent
	 * in the past year
	 * 
	 * @param o
	 * @return
	 */
	public List<Date> getAbsences(StaffMember o) throws EmptyResultSetException {
		String sql = "SELECT * FROM Register WHERE staff_member_id = ? AND date > ?";

		HashSet<Date> registered = new HashSet<Date>();
		List<Date> absences = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		try {
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MONTH, -12); // the last 12 months
			Date oneYearAgo = start.getTime();
			PreparedStatement pS = dbConnection.prepareStatement(sql);
			pS.setInt(1, o.getId());
			pS.setDate(2, new java.sql.Date(oneYearAgo.getTime()));
			ResultSet resRegistered = pS.executeQuery();
			while (resRegistered.next()) {
				cal.setTime(resRegistered.getDate("date"));
				Date register = cal.getTime();
				registered.add(register);
			}

			if (o.getStartDate().before(oneYearAgo)) {
				start.setTime(oneYearAgo);
			} else {
				start.setTime(o.getStartDate());
			}

			Calendar end = Calendar.getInstance();
			end.setTime(new Date());

			/*
			 * loop through each date loop through each registered date
			 */

			for (Date date = start.getTime(); !start.after(end); start.add(
					Calendar.DATE, 1), date = start.getTime()) {
				Iterator<Date> iter = registered.iterator();
				boolean absent = true;
				while (iter.hasNext()) {
					Date registeredDate = iter.next();
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					if (fm.format(registeredDate.getTime()).equals(
							fm.format(date.getTime())))
						absent = false;
				}
				if (absent)
					absences.add(date);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (absences.isEmpty())
			throw new EmptyResultSetException();
		else
			return absences;

	}

	/** getAppointments
	 * returns a list of Appointments for a given Staff Member
	 * @param o
	 * @return
	 * @throws EmptyResultSetException
	 */
	public List<CalendarAppointment> getAppointments(StaffMember o)
			throws EmptyResultSetException {
		return calendarAppointmentDMO.getAllByProperties(new SQLBuilder(
				"StaffMember.id", "=", "" + o.getId()));
	}
	
	/** getReferrals
	 * returns a list of Referrals given by a Staff Member
	 * @param o
	 * @return
	 */
	public List<ReferralObject> getReferrals(StaffMember o) {
		return referralDMO.getAllByProperties(new SQLBuilder(
				"doctors_id", "=", "" + o.getId()));
	}

	/** getSpecialities
	 * returns a list of Specialities for a given Staff Member
	 * @param o
	 * @return
	 * @throws EmptyResultSetException
	 */
	public List<Speciality> getSpecialities(StaffMember o)
			throws EmptyResultSetException {
		List<Speciality> sMSpecialities = new ArrayList<Speciality>();
		try {

			ResultSet res = GPSISDataMapper
					.getResultSet(
							new SQLBuilder("staff_member_id", "=", ""
									+ o.getId()),
							"StaffMemberSpeciality JOIN Speciality ON StaffMemberSpeciality.speciality_id = Speciality.id");
			while (res.next()) {
				sMSpecialities.add(new Speciality(res.getInt("speciality_id"),
						res.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (sMSpecialities.isEmpty())
			throw new EmptyResultSetException();
		else
			return sMSpecialities;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public List<StaffMember> getAllByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		List<StaffMember> staffMembers = new ArrayList<>();

		try {
                        String sqlJoin = this.tableName + " left outer join TempStaffMember on StaffMember.id = TempStaffMember.staff_member_id";
			ResultSet res = GPSISDataMapper.getResultSet(query, sqlJoin);
			while (res.next()) // While there's a StaffMember, create a the
								// StaffMember object and add it to a Set
			{
				staffMembers.add(this.buildStaffMember(res));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (staffMembers.isEmpty())
			throw new EmptyResultSetException();
		else
			return staffMembers;
	}

	/** getHolidays 
	 * retrieves all of the TrainingDay for a Staff Member in the past
	 * year
	 * 
	 * @param
	 * @return
	 */
	public List<Date> getHolidays(StaffMember o) throws EmptyResultSetException {
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + o.getId())
				.AND("availability", "=", "0");
		List<Date> holidays = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		try {
			ResultSet resHolidays = GPSISDataMapper.getResultSet(sql,
					"Register");
			while (resHolidays.next()) {
				cal.setTime(resHolidays.getDate("date"));
				Date holiday = cal.getTime();

				holidays.add(holiday);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (holidays.isEmpty())
			throw new EmptyResultSetException();
		else
			return holidays;

	}

	/** getRegister
	 * returns the Register object that matches the given StaffMember and Date
	 * @param sM
	 * @param d
	 * @return
	 * @throws EmptyResultSetException
	 */
	public Register getRegister(StaffMember sM, Date d)
			throws EmptyResultSetException {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + sM.getId())
				.AND("date", "=", fm.format(d.getTime()));

		try {
			ResultSet resRegister = getResultSet(sql, "Register");
			if (resRegister.next()) {
				return new Register(resRegister.getInt("id"),
						this.getById(resRegister.getInt("staff_member_id")),
						resRegister.getDate("date"),
						resRegister.getInt("availability"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new EmptyResultSetException();

	}

	/** getAllRegisters
	 * returns all of the associated Register objects with the given Staff Member
	 * @param sM
	 * @return
	 * @throws EmptyResultSetException
	 */
	public List<Register> getAllRegisters(StaffMember sM)
			throws EmptyResultSetException {
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + sM.getId());
		List<Register> register = new ArrayList<Register>();

		try {
			ResultSet resRegister = getResultSet(sql, "Register");

			while (resRegister.next()) {
				register.add(new Register(resRegister.getInt("id"), this
						.getById(resRegister.getInt("staff_member_id")),
						resRegister.getDate("date"), resRegister
								.getInt("availability")));

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

	/** getTaxForms
	 * returns a list of the Tax Forms belonging to a given Staff Member
	 * @param sM
	 * @return
	 * @throws EmptyResultSetException
	 */
	public List<TaxForm> getTaxForms(StaffMember sM)
			throws EmptyResultSetException {
		SQLBuilder query = new SQLBuilder("staff_member_id", "=", ""
				+ sM.getId());

		return taxFormDMO.getAllByProperties(query);
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public StaffMember getByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		try {
                        String sqlJoin = this.tableName + 
                                " LEFT OUTER JOIN TempStaffMember on StaffMember.id = TempStaffMember.staff_member_id";
			ResultSet res = GPSISDataMapper.getResultSet(query, sqlJoin);

			if (res.next()) // if found, create a the StaffMember object
			{
				return this.buildStaffMember(res);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// throw Exception because of empty result set
		throw new EmptyResultSetException();
	}


	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#put(java.lang.Object)
	 */
	@Override
	public void put(StaffMember o) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String sD = fm.format(o.getStartDate().getTime());
		SQLBuilder sql = new SQLBuilder("id", "=", "" + o.getId())
				.SET("username", "=", "" + o.getUsername())
				.SET("enc_password", "=", o.getEncryptedPassword())
				.SET("first_name", "=", "" + o.getFirstName())
				.SET("last_name", "=", "" + o.getLastName())
				.SET("full_time", "=", "" + (o.isFullTime() ? 1 : 0))
				.SET("start_date", "=", sD)
				.SET("office_manager", "=", "" + (o.isOfficeManager() ? 1 : 0))
				.SET("role", "=", o.getRole())
				.SET("holiday_allowance", "=", "" + o.getHolidayAllowance());
		try {
			putHelper(sql, this.tableName, o);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/** register 
	 * registers attendance for today for a given Staff Member
	 * 
	 * @param sM
	 *            the Staff Member to register
	 */
	public void register(StaffMember sM) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String today = fm.format(new Date().getTime());
		// Check if user's already registered on Database

		try {
			SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""
					+ sM.getId()).AND("date", "=", today);
			ResultSet registerdCheckRes = GPSISDataMapper.getResultSet(sql,
					"Register");

			if (registerdCheckRes.next())
				System.out.println("Already Registered for Today!");
			else {
				// Register User for today :D
				sql = new SQLBuilder("staff_member_id",
						"=", "" + sM.getId()).SET("date", "=", today);
				new Register(sM, new Date(), Register.ALLDAY);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/** makeTemporary
	 * Makes a given Staff Member temporary. Should have a End Date set beforehand
	 */
	public void makeTemporary(StaffMember o) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String eD = fm.format(o.getEndDate().getTime());
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + o.getId())
				.SET("end_date", "=", eD);

		try {
			putHelper(sql, "TempStaffMember", o);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/** remove Speciality
	 * Severs the relationship between a given Staff Member and Speciality
	 * @param sM
	 * @param s
	 */
	public void removeSpeciality(StaffMember sM, Speciality s) {
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + sM.getId())
				.AND("speciality_id", "=", "" + s.getId());

		try {
			removeByPropertyHelper(sql, "StaffMemberSpeciality");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

/**
 * End of File: StaffMemberDMO.java 
 * Location: mapper
 */