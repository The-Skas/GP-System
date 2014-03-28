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
import object.TrainingDay;
import object.Patient;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

// access the tables with TrainingDay and Training Days

public class TrainingDayDMO extends GPSISDataMapper<TrainingDay> {
	
    private TrainingDayDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static TrainingDayDMO instance; // singleton 

    public static TrainingDayDMO getInstance() // make it a singleton
    {
        if(instance == null)
        {
            instance = new TrainingDayDMO("TrainingDay");
        }
        return instance;
    }
    

	@Override
	public List<TrainingDay> getAllByProperties(SQLBuilder query)
			throws EmptyResultSetException 
	{
		List<TrainingDay> trainingDay = new ArrayList<>();
		ResultSet res;
		try {
			res = GPSISDataMapper.getResultSet(query, this.tableName);
			while(res.next())
	        {
	        	trainingDay.add(new TrainingDay(res.getInt("id"),res.getDate("date")));
	        }			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!trainingDay.isEmpty())
			return trainingDay;
		else
			throw new EmptyResultSetException();
	}

	@Override
	public TrainingDay getByProperties(SQLBuilder query)
			throws EmptyResultSetException {
		try {
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
			if (res.next()) {
				return new TrainingDay(res.getInt("id"),res.getDate("date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new EmptyResultSetException();
	}

	@Override
	public void put(TrainingDay o) {
	       String date = new SimpleDateFormat("yyyy-MM-dd").format(o.getDate());
	       
	       SQLBuilder sql = new SQLBuilder("id", "=", ""+o.getId())
	       							  .SET("date", "=", ""+date);
	       
	       try {
	            putHelper(sql, this.tableName, o);
	        } catch (SQLException ex) {
	            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
	        }

	}

	
}
