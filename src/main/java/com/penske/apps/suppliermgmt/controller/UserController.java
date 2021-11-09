package com.penske.apps.suppliermgmt.controller;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserDepartment;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.smccore.base.service.UserService;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.OrgFilter;
import com.penske.apps.suppliermgmt.model.VendorFilter;
import com.penske.apps.suppliermgmt.service.SuppliermgmtUserService;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;

@Version1Controller
@RequestMapping(value = "/userController")
public class UserController extends BaseController {

    @Autowired
    private SuppliermgmtUserService suppliermgmtUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    
    @RequestMapping(value = "/getUsersList", method = {RequestMethod.GET, RequestMethod.POST })
    protected  ModelAndView getUsersList() {
        ModelAndView model=new ModelAndView();
        
        try
        {
        	User user = sessionBean.getUser();
        	List<User> activeUsers = userService.getUsersWithoutSecurityFunctions(UserType.PENSKE, null);
        	Map<UserDepartment, List<User>> partitionedUsers = activeUsers.stream()
        		.collect(groupingBy(u -> u.getUserDepartment()));
        	
        	List<User> purchasingUsers = partitionedUsers.get(UserDepartment.SUPPLY_SPECIALIST);
        	List<User> planningUsers = partitionedUsers.get(UserDepartment.PLANNING_ANALYST);
        	
            List<Buddies> existingBuddyList=suppliermgmtUserService.getExistingBuddiesList(user.getSso());
            List<String> existingBuddySsoList=new ArrayList<String>();

            populateExistingBuddyUserList(existingBuddySsoList,existingBuddyList);

            model.addObject("loggedInUserSso",user.getSso());
            model.addObject("existingBuddySsoList",existingBuddySsoList);
            model.addObject("planningUsersList", planningUsers);
            model.addObject("purchasingUsersList", purchasingUsers);

        }catch(Exception e){
            model=handleException(e);
        }
        model.setViewName("app-container/modal/buddy");
        return model;
    }

    /**
     * Controller to delete selected workflow requests
     * @Params buddyId
     * @return void
     * @Excepton Exception
     */
    @RequestMapping(value = "/deleteBuddyList", method = {RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String deleteBuddyList(@RequestParam("newBuddies")List<String> newBuddyArray,@RequestParam("existingBuddyList")List<String> existingBuddyList,HttpServletResponse response) throws Exception
    {

        User user= sessionBean.getUser();
        List<Buddies> newBuddyList=new ArrayList<Buddies>();
        String loggedInUserSso=user.getSso();
        List<Buddies> existingBuddies=new ArrayList<Buddies>();
        try{

            if(existingBuddyList!=null&&!existingBuddyList.isEmpty())
            {
                suppliermgmtUserService.deleteBuddyList(loggedInUserSso);
            }
            List<LabelValue> deptDetailList=suppliermgmtUserService.getDeptDetailList();
            List<User> activeUsers = userService.getUsersWithoutSecurityFunctions(UserType.PENSKE, null);
            populateNewBuddyUserList(newBuddyList,activeUsers,deptDetailList,loggedInUserSso,newBuddyArray);

            addBuddyList(newBuddyList,loggedInUserSso);
            existingBuddies =suppliermgmtUserService.getExistingBuddiesList(user.getSso());
            
        }catch(Exception e){
            handleAjaxException(e, response);
        }
        return existingBuddies.size()>1?"inline-block":"none";
    }

    @VendorAllowed
    @RequestMapping(value="/getTermsAndCondition")
    public @ResponseBody String getTermsAndCondition(HttpServletResponse response){
        String terms = null;
        try{
            terms = suppliermgmtUserService.getTermsAndCondition();
        }catch(Exception e){
            handleAjaxException(e, response);
        }
        return terms;
    }

    @SmcSecurity(securityFunction = SecurityFunction.VENDOR_FILTER)
    @RequestMapping(value = "/get-vendor-filter-modal-content")
    @ResponseBody
    public ModelAndView getVendorFilterModalContent() {

        ModelAndView modelAndView = new ModelAndView("app-container/modal/vendor-filter-modal");

        List<OrgFilter> allOrgFilters = suppliermgmtUserService.getAllOrgFilters();

        modelAndView.addObject("allOrgFilters", allOrgFilters);

        return modelAndView;

    }

    @SmcSecurity(securityFunction = SecurityFunction.VENDOR_FILTER)
    @RequestMapping(value = "/get-organization-vendor-filters")
    @ResponseBody
    public List<VendorFilter> getOrganizationVendorFilters(@RequestParam("organizationId") int organizationId) {
        return suppliermgmtUserService.getAllVendorFilters(organizationId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.VENDOR_FILTER)
    @RequestMapping(value = "/save-user-vendor-selections", method = {RequestMethod.POST })
    @ResponseBody
    public boolean saveUserVendorFilters(@RequestParam("vendorIds") List<Integer> vendorIds) {
        suppliermgmtUserService.saveUserVendorFilterSelections(vendorIds);
        return vendorIds.size() > 0;
    }
    
    @SmcSecurity(securityFunction = SecurityFunction.VENDOR_FILTER)
    @RequestMapping(value = "/toggle-vendor-filter", method = {RequestMethod.POST })
    @ResponseBody
    public void toggleVendorFilter() {
        suppliermgmtUserService.toggleVendorFilter();
        
    }

    //***** HELPER METHODS *****//
    private void populateNewBuddyUserList(List<Buddies> newBuddyList, List<User> usersList,List<LabelValue> deptDetailList, String loggedInUserSso, List<String> newBuddyArray) 
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
					buddy.setUserDept(u.getUserDepartment().getDepartmentUserTitle());
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

	private void populateExistingBuddyUserList(List<String> existingBuddySsoList,List<Buddies> existingBuddyList) 
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

	  /**
     * Controller to add buddyList
     * @Params buddyId
     * @return void
     * @Excepton Exception
     */
    private void addBuddyList(List<Buddies> newBuddyList,String loggedInUserSso) {
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
                suppliermgmtUserService.addBuddyBasedOnselectionType(loggedInUser, loggedInUserSso);
                if(!newRandomBuddyList.isEmpty()){
                    suppliermgmtUserService.addBuddyList(newRandomBuddyList, loggedInUserSso);
                }

            }else{
                suppliermgmtUserService.addBuddyList(newBuddyList, loggedInUserSso);
            }
        }catch(Exception e){
            handleException(e);
        }
    }
}
