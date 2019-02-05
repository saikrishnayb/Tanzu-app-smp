package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.ShipThruLeadTimeDAO;
import com.penske.apps.suppliermgmt.domain.ShipThruLeadTime;

@Service
public class DefaultShipThruLeadTimeService implements ShipThruLeadTimeService {

    @Autowired
    private ShipThruLeadTimeDAO shipThruLeadTimeDAO;
    
    @Override
    public List<ShipThruLeadTime> getShipThruLeadTimes() {
        return shipThruLeadTimeDAO.getShipThruLeadTimes();
    }

}
