package mapper;

import java.util.Set;

import object.CareProgramme;
import framework.GPSISDataMapper;

public class CareProgrammeDMO extends GPSISDataMapper<CareProgramme> {
        private static CareProgrammeDMO instance;;
    
        public static CareProgrammeDMO getInstance() 
        {
            if(instance == null)
            {
                instance = new CareProgrammeDMO();
            }
            return instance;
        }

        private CareProgrammeDMO(){}
	@Override
	public Set<CareProgramme> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CareProgramme> getAllByProperties(SQLBuilder query) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CareProgramme getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
        
	@Override
	public CareProgramme getByProperties(SQLBuilder query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(CareProgramme o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(int id) {
		// TODO Auto-generated method stub
		
	}
	
}

/**
 * End of File: CareProgrammeDMO.java
 * Location: gpsis/mapper
 */