/** TaxFormDMO
 * Data Mapper for Tax Forms
 * 
 * @author Vijendra Patel (vp302)
 */
package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import object.TaxForm;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class TaxFormDMO extends GPSISDataMapper<TaxForm> {
	private static TaxFormDMO	instance;

	/** getInstance 
	 * returns the only instance of the TaxFormDMO
	 * 
	 * @return
	 */
	public static TaxFormDMO getInstance() {
		if (instance == null)
			instance = new TaxFormDMO();
		return instance;
	}

	/** TaxFormDMO Constructor 
	 * This is Private as part of a Singleton
	 * implementation.
	 * 
	 * @param tableName
	 */
	private TaxFormDMO() {
		this.tableName = "TaxForm";
	}

	/** buildTaxForm
	 * @param res
	 * @return
	 * @throws EmptyResultSetException
	 */
	private TaxForm buildTaxForm(ResultSet res) throws EmptyResultSetException {
		if (res != null) // if found, create a the TaxForm object
		{
			try {
				return new TaxForm(res.getInt("id"), staffMemberDMO.getById(res.getInt("staff_member_id")),
						taxOfficeDMO.getById(res.getInt("tax_office_id")), res.getString("tax_number"),
						res.getDouble("salary"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("EMPTY SET - No Tax Form Found matching the criteria");
		}
		throw new EmptyResultSetException();
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public List<TaxForm> getAllByProperties(SQLBuilder query) throws EmptyResultSetException {
		List<TaxForm> taxForms = new ArrayList<TaxForm>();

		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
			while (res.next()) {
				taxForms.add(this.buildTaxForm(res));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (taxForms.isEmpty())
			throw new EmptyResultSetException();
		else
			return taxForms;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public TaxForm getByProperties(SQLBuilder query) throws EmptyResultSetException {
		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);

			if (res.next()) // if found, create a the TaxOffice object
			{
				return this.buildTaxForm(res);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new EmptyResultSetException();
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#put(java.lang.Object)
	 */
	@Override
	public void put(TaxForm o) {
		SQLBuilder sql =
				new SQLBuilder("id", "=", "" + o.getId()).SET("staff_member_id", "=", "" + o.getStaffMember().getId())
						.SET("tax_office_id", "=", "" + o.getTaxOffice().getId())
						.SET("tax_number", "=", "" + new String(o.getTaxNumber()))
						.SET("salary", "=", "" + o.getSalary());
		try {
			putHelper(sql, this.tableName, o);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

}

/**
 * End of File: TaxFormDMO.java 
 * Location: mapper
 */