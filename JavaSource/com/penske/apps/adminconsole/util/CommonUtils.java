package com.penske.apps.adminconsole.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.DateType;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;


/**
 * CommonUtils class
 * @author  502174985 @created 19-JAN-2016 9:11:47 AM
 *
 */
public class CommonUtils {
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	
	public static String getCompnentCheckSum(List<Components> compList){
		if(compList !=null && !compList.isEmpty()){
			List<String> componentNameList=new ArrayList<String>();
			for (Components components : compList) {
				 StringBuffer key = new StringBuffer();
				 key.append(components.getComponentId());
				 key.append(components.isViewable());
				 key.append(components.isEditable());
				 key.append(components.isRequired());
				 key.append(components.isDispOtherPO());
				 key.append(components.isExcel());
				componentNameList.add(key.toString());
			}
			Collections.sort(componentNameList);
			MessageDigest md1;
			try {
		        ByteArrayOutputStream b = new ByteArrayOutputStream();
		        ObjectOutputStream o = new ObjectOutputStream(b);
		        o.writeObject(componentNameList);
		        
		        md1 = MessageDigest.getInstance("MD5"); // parasoft-suppress SECURITY.WSC.ICA "We don't use this to store any sensitive data"

				byte[] thedigest = md1.digest(b.toByteArray());
				
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < thedigest.length; i++) {
		        	sb.append(Integer.toHexString(0xFF & thedigest[i]));
		        }

		        return  sb.toString();
				
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		}
		return null;
	}
	

	public static void buildHierarchy(Role topRole,Map<Integer, Role> roleMap) {
		Role role = topRole;
        List<Role> employees1 = findAllRoleByRoleId(role.getRoleId(),roleMap);
        role.setSubRoles(employees1);
        if (employees1.size() == 0) {
            return;
        }

        for (Role role1 : employees1) {
            buildHierarchy(role1,roleMap);
        }
    }
	
	private static  List<Role> findAllRoleByRoleId(int roleId,Map<Integer, Role> roleMap) {
        List<Role> sameBaseRole = new ArrayList<Role>();
        for (Role e : roleMap.values()) {
            if (e.getBaseRoleId() == roleId) {
                sameBaseRole.add(e);
            }
        }
        return sameBaseRole;
    }
	
	public static void getCommonErrorAjaxResponse(HttpServletResponse response,String errorMsg) throws Exception{
			if(errorMsg ==null || ( errorMsg!=null && errorMsg.isEmpty())){
				errorMsg="Error in processing the last request.";
			}
		 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,errorMsg);
		 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	     response.getWriter().write(errorMsg);
	     response.flushBuffer();
	}
	
	
	public static boolean validUserID(String str) {

		if ((str.length() < 3) || (str.length() > 100)) {
			return false;	
		} else if ((str.indexOf("\'") != -1) || (str.indexOf(",") != -1) || (str.indexOf("\"") != -1) 
			|| (str.indexOf("\\") != -1) || (str.indexOf(" ") != -1)) {
			return false;				
		}

		 return true;					
	}
	
	public static boolean hasAccess(String secFunction,UserContext userRuleModel){
			if(userRuleModel!=null){
				Map<String, Map<String, String>> userRuleMap =userRuleModel.getTabSecFunctionMap();	
				if(userRuleMap != null){
					
					Map<String,String> secFunctions=userRuleMap.get(ApplicationConstants.TAB);
					
					if (userRuleMap.containsKey(ApplicationConstants.TAB)&&secFunctions.containsKey(secFunction)&&secFunctions!=null){
						{
							
							return true;
						}
					}
				}    
		}
			return false;
	}
	
	public static void sortDateType(List<DateType> dateType){
		Collections.sort(dateType, new Comparator<DateType>() {
		    @Override
		    public int compare(DateType lhs, DateType rhs) {
		        return lhs.getDateTypeDesc().compareTo(rhs.getDateTypeDesc());
		    }
		});
	}
	
	public static void populateRoleDept(User user){
		//int id=102;
		Role role=user.getRole();
		if(role !=null){
			if(role.getRoleId()<1){
				role.setRoleId(102);
			}
		}else{
			role=new Role();
			role.setRoleId(102);
		}
		user.setRole(role);
		if(user.getOrgId()<1){
			user.setOrgId(83);
		}
	}
}
