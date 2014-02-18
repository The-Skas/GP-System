package mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import object.CareProgramme;
import framework.GPSISDataMapper;

public class CareProgrammeDMO extends GPSISDataMapper<CareProgramme> {
        private static CareProgrammeDMO instance;;
    
        public static CareProgrammeDMO getInstance() 
        {
            if(instance == null)
            {
                instance = new CareProgrammeDMO("CareProgramme");
            }
            return instance;
        }

        private CareProgrammeDMO(String tableName){
        	this.tableName = tableName;
        }

	@Override
	public List<CareProgramme> getAllByProperties(SQLBuilder query) {
		// TODO Auto-generated method stub
		List<CareProgramme> cps = new ArrayList<CareProgramme>();
		cps.add(new CareProgramme());
		return cps;
	}
        
	@Override
	public CareProgramme getByProperties(SQLBuilder query) {
		// TODO Auto-generated method stub
		CareProgramme cp = new CareProgramme();
		return cp.getTempCP();
	}

	@Override
	public void put(CareProgramme o) {
		// TODO Auto-generated method stub
		
	}
	
}

/**
 * End of File: CareProgrammeDMO.java
 * Location: gpsis/mapper
 */