package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.domain.ShipThruLeadTime;

@DBSmc
public interface ShipThruLeadTimeDAO {

    @NonVendorQuery
    public List<ShipThruLeadTime> getShipThruLeadTimes();
}
