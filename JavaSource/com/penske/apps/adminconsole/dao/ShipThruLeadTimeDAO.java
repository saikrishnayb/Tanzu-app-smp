package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.domain.ShipThruLeadTime;

public interface ShipThruLeadTimeDAO {

    @NonVendorQuery
    public List<ShipThruLeadTime> getShipThruLeadTimes();
}
