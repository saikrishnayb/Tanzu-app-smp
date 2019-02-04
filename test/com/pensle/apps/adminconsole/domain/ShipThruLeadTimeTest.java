package com.pensle.apps.adminconsole.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.penske.apps.adminconsole.domain.ShipThruLeadTime;
import com.penske.apps.adminconsole.enums.PoCategoryType;

public class ShipThruLeadTimeTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldCreateValidShipThruLeadTime() {
        new ShipThruLeadTime(PoCategoryType.BODY, 1);
    }

    @Test
    public void shouldNotCreateShipThruLeadTimeWith0DayLead() {
        
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("leadDays should be greater than 0");
        new ShipThruLeadTime(PoCategoryType.BODY, -1);
    }

    @Test
    public void shouldNotCreateShipThruLeadTimeWithLessThan0DayLead() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("leadDays should be greater than 0");
        new ShipThruLeadTime(PoCategoryType.BODY, 0);
    }

    @Test()
    public void shouldNotCreateShipThruLeadTimeWithNullPoCateogry() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("poCategory can not be null");
        new ShipThruLeadTime(null, 2);

    }
}
