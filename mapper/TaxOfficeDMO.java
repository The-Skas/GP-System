/** TaxOfficeDMO
 * Data Mapper for Tax Office Objects
 * 
 * @author Vijendra Patel (vp302)
 */
package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.TaxOffice;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class TaxOfficeDMO extends GPSISDataMapper<TaxOffice> {
	private static TaxOfficeDMO	instance;

	/** getInstance 
	 * returns the only instance of the TaxOfficeDMO
	 * 
	 * @return
	 */
	public static TaxOfficeDMO getInstance() {
		if (instance == null)
			instance = new TaxOfficeDMO();
		return instance;
	}

	/** TaxOfficeDMO Constructor 
	 * This is Private as part of a Singleton
	 * implementation.
	 */
	private TaxOfficeDMO() {
		this.tableName = "TaxOffice";
	}

	/** buildTaxOffice 
	 * builds a Staff Member object from the given Result Set.
	 * Used as a helper method in retrieving TaxOffices from the Database
	 * Returns the correct object type (Receptionist or MedicalTaxOffice) and
	 * includes their temp contract if needed
	 * 
	 * @param res
	 * @return a complete Staff Member
	 * @throws SQLException
	 */
	private TaxOffice buildTaxOffice(ResultSet res) throws SQLException {
		if (res != null) // if found, create a the TaxOffice object
		{
			return new TaxOffice(res.getInt("id"), res.getString("name"), res.getString("no_and_street"),
					res.getString("locality"), res.getString("town"), res.getString("postcode_area"),
					res.getString("postcode_district"));
		} else {
			System.err.println("EMPTY SET - No Tax Office Found matching the criteria");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public List<TaxOffice> getAllByProperties(SQLBuilder query) throws EmptyResultSetException {
		List<TaxOffice> taxOffices = new ArrayList<TaxOffice>();

		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
			while (res.next()) // While there's a TaxOffice, create a the
								// TaxOffice object and add it to a Set
			{
				taxOffices.add(this.buildTaxOffice(res));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (taxOffices.isEmpty())
			throw new EmptyResultSetException();
		else
			return taxOffices;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public TaxOffice getByProperties(SQLBuilder query) throws EmptyResultSetException {
		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);

			if (res.next()) // if found, create a the TaxOffice object
			{
				return this.buildTaxOffice(res);
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
	public void put(TaxOffice o) {
		SQLBuilder sql =
				new SQLBuilder("id", "=", "" + o.getId()).SET("name", "=", "" + o.getName())
						.SET("no_and_street", "=", "" + o.getNoAndStreet()).SET("locality", "=", "" + o.getLocality())
						.SET("town", "=", "" + o.getTown()).SET("postcode_area", "=", o.getPostcodeArea())
						.SET("postcode_district", "=", o.getPostcodeDistrict());
		try {
			putHelper(sql, this.tableName, o);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}
}

/**
 * End of File: TaxOfficeDMO.java 
 * Location: mapper
 */