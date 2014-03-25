package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import object.CalendarAppointment;
import object.Holidays;
import object.Patient;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

// access the tables with Holidays and Training Days

public class HolidaysDMO extends GPSISDataMapper<Holidays> {
	
    private HolidaysDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static HolidaysDMO instance; // singleton 

    public static HolidaysDMO getInstance() // make it a singleton
    {
        if(instance == null)
        {
            instance = new HolidaysDMO("Holidays");
        }
        return instance;
    }
    

	@Override
	public List<Holidays> getAllByProperties(SQLBuilder query)
			throws EmptyResultSetException 
	{
		List<Holidays> holidays = new ArrayList<>();
		ResultSet res;
		try {
			res = GPSISDataMapper.getResultSet(query, this.tableName);
			while(res.next())
	        {
	        	holidays.add(new Holidays(res.getInt("id"),res.getDate("date")));
	        }			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!holidays.isEmpty())
			return holidays;
		else
			throw new EmptyResultSetException();
	}

	@Override
	public Holidays getByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
			return new Holidays(res.getInt("id"),res.getDate("date"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new EmptyResultSetException();
	}

	@Override
	public void put(Holidays o) {
	       String date = new SimpleDateFormat("yyyy-MM-dd").format(o.getDate());
	       
	       SQLBuilder sql = new SQLBuilder("id", "=", ""+o.getId())
	       							  .SET("date", "=", ""+date);
	       
	       try {
	            putHelper(sql, this.tableName, o);
	        } catch (SQLException ex) {
	            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
	        }

	}
	
	
	/*
	public static void main(String [] args)
	{
		GPSISDataMapper.connectToDatabase();
		
		HolidaysDMO holiTbl=HolidaysDMO.getInstance();
		
		holiTbl.put(new Holidays(new Date()));
		
		try {
			System.out.println(holiTbl.getAll());
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				holiTbl.removeById(1);
				
				try {
					System.out.println(holiTbl.getAll());
				} catch (EmptyResultSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}
	*/
	
}
