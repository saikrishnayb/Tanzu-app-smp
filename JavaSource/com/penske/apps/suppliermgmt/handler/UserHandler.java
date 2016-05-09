package com.penske.apps.suppliermgmt.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;



/*******************************************************************************
*
* @Author 		: 502405417
* @Version 	    : 1.1
* @Date Created : May 14, 2015
* @Date Modified: 
* @Modified By  : 
* @Contact 	    :
* @Description  : This is the Handler class for User Operations.
* @History	    :
*
******************************************************************************/

public class UserHandler {

	public static void populateUserLists(List<User> userList, List<User> planningUsersList,
																						List<User> purchasingUsersList) 
	{
		for(User u:userList)
		{
			if(u.getUserDept()==ApplicationConstants.PURCHASE_DEPT_ID&&!purchasingUsersList.contains(u))
			{
				purchasingUsersList.add(u);
			}
			if(u.getUserDept()==ApplicationConstants.PLAN_DEPT_ID&&!planningUsersList.contains(u))
			{
				planningUsersList.add(u);
			}
		}
	
		
	}

	public static void populateExistingBuddyUserList(List<String> existingBuddySsoList,List<Buddies> existingBuddyList) 
	{
		if(existingBuddyList!=null&&!existingBuddyList.isEmpty())
		{
			for(Buddies b:existingBuddyList)
			{
				if(StringUtils.isNotBlank(b.getBuddySso()))
				{
					if(StringUtils.equalsIgnoreCase(b.getSelectionType(),ApplicationConstants.ALL_SELECTION_TYPE))
					{
						existingBuddySsoList.add(ApplicationConstants.ALL_CHECKBOX_ID);
						break;
					}
					else
					{
						if(!existingBuddySsoList.contains(ApplicationConstants.ALL_PLANNING_CHECKBOX_ID)
								&& StringUtils.equalsIgnoreCase(b.getSelectionType(),ApplicationConstants.ALL_PLANNING_SELECTION_TYPE))
						{
							existingBuddySsoList.add(ApplicationConstants.ALL_PLANNING_CHECKBOX_ID);
						}
						else if(!existingBuddySsoList.contains(ApplicationConstants.ALL_BUYER_CHECKBOX_ID)
								&&StringUtils.equalsIgnoreCase(b.getSelectionType(),ApplicationConstants.ALL_BUYER_SELECTION_TYPE))
						{
							existingBuddySsoList.add(ApplicationConstants.ALL_BUYER_CHECKBOX_ID);
						}
						existingBuddySsoList.add(b.getBuddySso());
					}
				}
			}
		}
		
		
	}

	public static void populateNewBuddyUserList(List<Buddies> newBuddyList,List<User> usersList,List<LabelValue> deptDetailList, 
												String loggedInUserSso, List<String> newBuddyArray) 
	{
		
		Map<Integer,String> userDeptMap=new HashMap<Integer,String>();
		for(LabelValue value:deptDetailList)
		{
			userDeptMap.put(value.getLabelId(), value.getLabelValue());
		}
		boolean allBuyerCheckBoxSelected=false,allPlanningCheckBoxSelected=false,randomCheckBoxesSelected=false;
		
		for(String buddyValue:newBuddyArray)
		{
			if(buddyValue.equalsIgnoreCase(ApplicationConstants.ALL_CHECKBOX_ID))
			{
				
				for(User u:usersList)
				{
					Buddies buddy=new Buddies();
					buddy.setBuddySso(u.getSso());
					buddy.setUserDept(userDeptMap.get(u.getUserDept()));
					buddy.setSso(loggedInUserSso);
					buddy.setSelectionType(ApplicationConstants.ALL_SELECTION_TYPE);
					newBuddyList.add(buddy);
				}
				break;
			}
			else if(buddyValue.equalsIgnoreCase(ApplicationConstants.ALL_PLANNING_CHECKBOX_ID))
			{
				allPlanningCheckBoxSelected=true;
				randomCheckBoxesSelected=false;
			}
			else if(buddyValue.equalsIgnoreCase(ApplicationConstants.ALL_BUYER_CHECKBOX_ID))
			{
				allBuyerCheckBoxSelected=true;
				randomCheckBoxesSelected=false;
			}
			else if(buddyValue.equalsIgnoreCase(ApplicationConstants.ALL_RANDOM_CHECKBOX_ID)){
				randomCheckBoxesSelected=true;
				allPlanningCheckBoxSelected=false;
				allBuyerCheckBoxSelected=false;
			}
			else 
			{
				Buddies buddy=new Buddies();
				buddy.setBuddySso(buddyValue.split("/")[0]);
				buddy.setUserDept(userDeptMap.get(Integer.parseInt(buddyValue.split("/")[1])));
				buddy.setSso(loggedInUserSso);
				
				//the below if/else is to add selection_type value if all planning /all buyers(purchasing) is selected
				if(allPlanningCheckBoxSelected)
				{
					buddy.setSelectionType(ApplicationConstants.ALL_PLANNING_SELECTION_TYPE);
				}
				else if(allBuyerCheckBoxSelected)
				{
					buddy.setSelectionType(ApplicationConstants.ALL_BUYER_SELECTION_TYPE);
				}
				else if(randomCheckBoxesSelected){
					buddy.setSelectionType(null);
				}
				
				newBuddyList.add(buddy);
			}
		}
		
	}
	

}
