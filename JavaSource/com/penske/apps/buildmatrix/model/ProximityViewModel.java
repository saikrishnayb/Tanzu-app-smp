/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;

/**
 * Used to display what districts are what tier of importance for a given plant.
 */
public class ProximityViewModel
{
	/** Which proximities belong to each "tier" of importance. Lower-numbered tiers will be preferred by the rules engine to higher-numbered ones */
	private final Map<Integer, ProximityTierViewModel> tiers = new TreeMap<>();
	
	/**
	 * Creates a model that holds the different plant proximities by tier
	 * @param freightMileages The list of all the areas and districts under consideration
	 * @param proximities The list of all proximities under consideration
	 */
	public ProximityViewModel(List<FreightMileage> freightMileages, List<PlantProximity> proximities)
	{
		if(freightMileages == null)
			freightMileages = Collections.emptyList();
		if(proximities == null)
			proximities = Collections.emptyList();
		
		tiers.put(1, new ProximityTierViewModel(1, freightMileages));
		tiers.put(2, new ProximityTierViewModel(2, freightMileages));
		tiers.put(3, new ProximityTierViewModel(3, freightMileages));
		
		for(PlantProximity proximity : proximities)
		{
			int tier = proximity.getTier();
			ProximityTierViewModel tierModel = tiers.get(tier);
			if(tierModel == null)
				throw new IllegalArgumentException("Can not add plant proximity for tier " + tier + ". ProximityViewModel only supports the following tiers: " + StringUtils.join(tiers.keySet(), ", "));
			
			tierModel.addProximity(proximity);
		}
	}
	
	//***** HELPER CLASSES *****//
	/**
	 * Holds just the plant proximities for a given tier
	 */
	public static class ProximityTierViewModel
	{
		/** The tier of importance this model represents. Lower-numbered tiers are more important than higher-numbered ones. */
		private final int tier;
		/** The area numbers of all districts under consideration, keyed by their district number */
		private final Map<String, String> areasByDistrictNumber = new HashMap<>();
		/** The area / district objects under consideration, keyed by their area number */
		private final Map<String, ProximityAreaViewModel> areas = new TreeMap<>();
		
		/**
		 * Creates a new model to hold proximities for a given tier
		 * @param tier The tier this model is for
		 * @param freightMileages All the areas and districts under consideration
		 */
		private ProximityTierViewModel(int tier, List<FreightMileage> freightMileages)
		{
			this.tier = tier;
			for(FreightMileage freightMileage : freightMileages)
			{
				String area = freightMileage.getArea();
				this.areas.put(area, new ProximityAreaViewModel(freightMileage));
				
				for(String district : freightMileage.getDistricts())
					areasByDistrictNumber.put(district, area);
			}
		}
		
		/**
		 * Integrates a plant proximity into this model
		 * @param proximity The proximity to add. Its tier must match this model's tier, and it must be for one of the districts in this model.
		 * 	If not, an exception will be thrown.
		 */
		private void addProximity(PlantProximity proximity)
		{
			if(proximity.getTier() != tier)
				throw new IllegalArgumentException("Can not add plant proximity for " + proximity.getDistrict() + " to tier " + tier + ". Proximity belongs to tier " + proximity.getTier());
			
			//This optionally could throw an exception, but only if we knew that for every proximity entry, we had a matching district for it
			String areaNumber = areasByDistrictNumber.get(proximity.getDistrict());
			if(areaNumber == null)
				return;
			
			ProximityAreaViewModel area = this.areas.get(areaNumber);
			area.addProximity(proximity);
		}
	
		//***** MODIFIED ACCESSORS *****//
		public List<ProximityAreaViewModel> getAreas()
		{
			return Collections.unmodifiableList(new ArrayList<>(areas.values()));
		}

		//***** DEFAULT ACCESSORS *****//
		public int getTier()
		{
			return tier;
		}
	}
	
	/**
	 * Holds all the plant proximities for a given area and tier
	 */
	public static class ProximityAreaViewModel
	{
		/** The area and districts this model represents */
		private final FreightMileage freightMileage;
		/** A set of the district numbers in this area, for faster access */
		private final Set<String> districtsInArea = new HashSet<>();
		/** The plant proximities for districts in this area, keyed by district number */
		private final Map<String, PlantProximity> proximities = new TreeMap<>();
		
		/**
		 * Creates a new model to hold proximities for a given area and tier
		 * @param freightMileage
		 */
		private ProximityAreaViewModel(FreightMileage freightMileage)
		{
			this.freightMileage = freightMileage;
			for(String district : freightMileage.getDistricts())
			{
				this.districtsInArea.add(district);
				this.proximities.put(district, null);
			}
		}

		/**
		 * Integrates a plant proximity into this model
		 * @param proximity The proximity to add. Its district must be one of the districts in this area's {@link freightMileage} object, or an exception will be thrown.
		 */
		private void addProximity(PlantProximity proximity)
		{
			String district = proximity.getDistrict();
			if(!districtsInArea.contains(district))
				throw new IllegalArgumentException("Can not add plant proximity for " + district + " to area " + freightMileage.getArea() + ". That district is not part of that area.");
			proximities.put(district, proximity);
		}
		
		//***** MODIFIED ACCESSORS *****//
		public Map<String, PlantProximity> getProximities()
		{
			return Collections.unmodifiableMap(proximities);
		}
		
		//***** DEFAULT ACCESSORS *****//
		public FreightMileage getFreightMileage()
		{
			return freightMileage;
		}
	}

	//***** MODIFIED ACCESSORS *****//
	public List<ProximityTierViewModel> getTiers()
	{
		return Collections.unmodifiableList(new ArrayList<>(tiers.values()));
	}
}
