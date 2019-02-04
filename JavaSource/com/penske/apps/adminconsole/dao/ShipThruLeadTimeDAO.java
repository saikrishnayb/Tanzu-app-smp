package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.domain.ShipThruLeadTime;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;

public interface ShipThruLeadTimeDAO {

    @NonVendorQuery
    public List<ShipThruLeadTime> getShipThruLeadTimes();
}
