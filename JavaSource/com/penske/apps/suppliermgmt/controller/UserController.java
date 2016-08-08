package com.penske.apps.suppliermgmt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.handler.UserHandler;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.UserService;



@Controller 
@RequestMapping(value = "/userController")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getUsersList", method = {RequestMethod.GET, RequestMethod.POST })
	protected  ModelAndView validateUser(HttpServletRequest request) {
		ModelAndView model=new ModelAndView();
		UserContext userContext = new UserContext();
		List<User> userList=new ArrayList<User>();
		try
		{
			
			userContext = getUserContext(request);
			userList=userService.getUserDetails();
			List<User> purchasingUsersList=new ArrayList<User>();
			List<User> planningUsersList=new ArrayList<User>();
			UserHandler.populateUserLists(userList,planningUsersList,purchasingUsersList);
			
			List<Buddies> existingBuddyList=userService.getExistingBuddiesList(userContext.getUserName());
			List<String> existingBuddySsoList=new ArrayList<String>();
			
			UserHandler.populateExistingBuddyUserList(existingBuddySsoList,existingBuddyList);
			
			model.addObject("loggedInUserSso",userContext.getUserSSO());
			model.addObject("existingBuddySsoList",existingBuddySsoList);
			model.addObject("planningUsersList", planningUsersList);
			model.addObject("purchasingUsersList", purchasingUsersList);
				 
		  }catch(Exception e){
			  model=handleException(e, request);
		  }
		model.setViewName("home/buddy");
		return model;
	}
	
	


	/**
	 * Controller to add buddyList
	 * @Params buddyId
	 * @return void
	 * @Excepton Exception
	 */
	public void addBuddyList(List<Buddies> newBuddyList,String loggedInUserSso,HttpServletRequest request) {	
		try {
				String selectionType=null;
				List<Buddies> newRandomBuddyList=new ArrayList<Buddies>();
				Buddies loggedInUser=new Buddies();
			Buddies buddy=null;
				if(newBuddyList!=null){
					
					for(Buddies buddylst:newBuddyList){
						
						
							buddy=new Buddies();
							buddy.setSso(loggedInUserSso);
							buddy.setBuddySso(buddylst.getBuddySso());
							buddy.setUserDept(buddylst.getUserDept());
							//if selection type is ALL
							if(StringUtils.equalsIgnoreCase(buddylst.getSelectionType(),ApplicationConstants.ALL_SELECTION_TYPE)){
								
								selectionType=ApplicationConstants.ALL_SELECTION_TYPE;
								buddy.setSelectionType(selectionType);
								
							}else if(StringUtils.equalsIgnoreCase(buddylst.getSelectionType(),ApplicationConstants.ALL_BUYER_SELECTION_TYPE)){
							//if selection type is based on depratment
								selectionType=ApplicationConstants.ALL_BUYER_SELECTION_TYPE;
								buddy.setSelectionType(selectionType);
							}else if(StringUtils.equalsIgnoreCase(buddylst.getSelectionType(),ApplicationConstants.ALL_PLANNING_SELECTION_TYPE)){
								selectionType=ApplicationConstants.ALL_PLANNING_SELECTION_TYPE;
								buddy.setSelectionType(selectionType);
							}else {	//if it is random
								//except the logged in user if we have selection Type
								if(buddylst.getBuddySso() != null && !buddylst.getBuddySso().contains(loggedInUserSso)){
									buddy.setSelectionType(null);
									newRandomBuddyList.add(buddy);
								}
							}
							
							//inserting logged in user with selection type
							if(buddylst.getBuddySso().contains(loggedInUserSso)){
								//buddy.setSelectionType(selectionType);
								loggedInUser=buddy;
							}
						
						
					}
					
				}
				
			if(StringUtils.equalsIgnoreCase(selectionType, ApplicationConstants.ALL_SELECTION_TYPE)||
					StringUtils.equalsIgnoreCase(selectionType, ApplicationConstants.ALL_BUYER_SELECTION_TYPE)||
					StringUtils.equalsIgnoreCase(selectionType, ApplicationConstants.ALL_PLANNING_SELECTION_TYPE)){
				loggedInUser.setSelectionType(selectionType);
				userService.addBuddyBasedOnselectionType(loggedInUser);
				if(!newRandomBuddyList.isEmpty()){
					userService.addBuddyList(newRandomBuddyList);
				}
				
			}else{
				userService.addBuddyList(newBuddyList);
			}
		}catch(Exception e){
		   handleException(e,request);
		}
	}

	/**
	 * Controller to delete selected workflow requests
	 * @Params buddyId
	 * @return void
	 * @Excepton Exception
	 */
	@RequestMapping(value = "/deleteBuddyList", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void deleteBuddyList(@RequestParam("newBuddies")List<String> newBuddyArray,@RequestParam("existingBuddyList")List<String> existingBuddyList,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{	//
		
		UserContext userContext= getUserContext(request);
		List<Buddies> newBuddyList=new ArrayList<Buddies>();
		String loggedInUserSso=userContext.getUserSSO();
		try{
			
			if(existingBuddyList!=null&&!existingBuddyList.isEmpty())
			{
				userService.deleteBuddyList(loggedInUserSso);
			}
			List<LabelValue> deptDetailList=userService.getDeptDetailList();
			List<User> usersList=userService.getUserDetails();
			UserHandler.populateNewBuddyUserList(newBuddyList,usersList,deptDetailList,loggedInUserSso,newBuddyArray);
			
			addBuddyList(newBuddyList,loggedInUserSso, request);
		}catch(Exception e){
			handleAjaxException(e, response, request);
		}
	}
	
	@RequestMapping(value="/getTermsAndCondition")
	public @ResponseBody String getTermsAndCondition(HttpServletResponse response,HttpServletRequest request){
		String terms = null;
		try{
			terms = userService.getTermsAndCondition();
		}catch(Exception e){
			handleAjaxException(e, response, request);
		}
		return terms;
	}
}
