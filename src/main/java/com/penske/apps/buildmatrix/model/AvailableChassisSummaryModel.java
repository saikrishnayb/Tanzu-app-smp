package com.penske.apps.buildmatrix.model;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.smccore.base.util.UnitRangeBuilder;

public class AvailableChassisSummaryModel {
	
	private Set<List<AvailableChassis>> groupedAvailableUnits;
	private Set<List<AvailableChassis>> groupedExcludedUnits;
	
	public AvailableChassisSummaryModel(List<AvailableChassis> availableChassis, List<String> excludedUnits) {
		Map<String, AvailableChassis> availableChassisByUnitNumber = availableChassis.stream()
				.collect(toMap(AvailableChassis::getUnitNumber, ac->ac));
		List<AvailableChassis> excludedChassis = new ArrayList<>();
		for(String unitNumber: excludedUnits) {
			AvailableChassis excludedUnit = availableChassisByUnitNumber.get(unitNumber);
			if(excludedUnit == null)
				continue;
			excludedChassis.add(excludedUnit);
		}
		List<AvailableChassis> actualAvailableChassis = availableChassis.stream()
				.filter(ac-> !excludedUnits.contains(ac.getUnitNumber()))
				.collect(toList());
		
		this.groupedAvailableUnits = new UnitRangeBuilder<AvailableChassis>().byUnitNumber().build(actualAvailableChassis);
		this.groupedExcludedUnits = new UnitRangeBuilder<AvailableChassis>().byUnitNumber().build(excludedChassis);
	}
	
	public Set<List<AvailableChassis>> getGroupedAvailableUnits() {
		return groupedAvailableUnits;
	}
	public void setGroupedAvailableUnits(Set<List<AvailableChassis>> groupedAvailableUnits) {
		this.groupedAvailableUnits = groupedAvailableUnits;
	}
	public Set<List<AvailableChassis>> getGroupedExcludedUnits() {
		return groupedExcludedUnits;
	}
	public void setGroupedExcludedUnits(Set<List<AvailableChassis>> groupedExcludedUnits) {
		this.groupedExcludedUnits = groupedExcludedUnits;
	}
	
}
