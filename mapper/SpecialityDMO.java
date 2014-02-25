/**
 * 
 */
package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import object.Speciality;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

/**
 * @author VJ
 *
 */
public class SpecialityDMO extends GPSISDataMapper<Speciality> {
	
	// stores the only instance of this DataMapper
	private static SpecialityDMO instance;
	
	/** getInstance
     * returns the only instance of the StaffMemberDMO
     * @return
     */
    public static SpecialityDMO getInstance() 
    {
        if(instance == null)
            instance = new SpecialityDMO("Speciality");
        return instance;
    }    
    
    /** SpecialityDMO Constructor 
	 * This is Private as part of a Singleton implementation.
	 * @param tableName
	 */
    private SpecialityDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getAllByProperties(mapper.SQLBuilder)
	 */
	@Override
	public List<Speciality> getAllByProperties(SQLBuilder query) throws EmptyResultSetException {
		List<Speciality> specialities = new ArrayList<>();
        
        try 
        {            
          ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
          while(res.next()) // While there's a StaffMember, create a the StaffMember object and add it to a Set
          {
        	  specialities.add(new Speciality(res.getInt("id"), res.getString("name")));
          }

      } 
      catch (SQLException e) 
      {
          e.printStackTrace();
      }
      if (specialities.isEmpty())
      	throw new EmptyResultSetException();
      else
      	return specialities;
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#getByProperties(mapper.SQLBuilder)
	 */
	@Override
	public Speciality getByProperties(SQLBuilder query) throws EmptyResultSetException {
		 try 
	        {
	            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
	            
	            if (res.next()) // if found, create the Speciality object
	            {
	            	return new Speciality(res.getInt("id"), res.getString("name"));
	            }

	        } 
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
	        // throw Exception because of empty result set
	        throw new EmptyResultSetException();
	}

	/* (non-Javadoc)
	 * @see framework.GPSISDataMapper#put(java.lang.Object)
	 */
	@Override
	public void put(Speciality o) {
		
		SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
        			.SET("name", "=", o.getName());
		try 
		{
		    putHelper(sql, this.tableName, o);
		} 
		catch (SQLException e) 
		{
			System.err.println(e.getMessage());
		}
	}

}
