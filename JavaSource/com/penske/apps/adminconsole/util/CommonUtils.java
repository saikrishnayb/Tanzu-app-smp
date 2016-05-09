package com.penske.apps.adminconsole.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.suppliermgmt.model.UserContext;


/**
 * CommonUtils class
 * @author  502174985 @created 19-JAN-2016 9:11:47 AM
 *
 */
public class CommonUtils {
	
	public static String getCompnentCheckSum(List<Components> compList){
		if(compList !=null && !compList.isEmpty()){
			List<String> componentNameList=new ArrayList<String>();
			for (Components components : compList) {
				componentNameList.add(components.getComponentName());
			}
			Collections.sort(componentNameList);
			MessageDigest md1;
			try {
		        ByteArrayOutputStream b = new ByteArrayOutputStream();
		        ObjectOutputStream o = new ObjectOutputStream(b);
		        o.writeObject(componentNameList);
		        
		        md1 = MessageDigest.getInstance("MD5");

				byte[] thedigest = md1.digest(b.toByteArray());
				
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < thedigest.length; i++) {
		        	sb.append(Integer.toHexString(0xFF & thedigest[i]));
		        }

		        return  sb.toString();
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
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

		if ((str.length() < 3) || (str.length() > 30)) {
			return false;	
		} else if ((str.indexOf("\'") != -1) || (str.indexOf(",") != -1) || (str.indexOf("\"") != -1) 
			|| (str.indexOf("\\") != -1) || (str.indexOf(" ") != -1)) {
			return false;				
		}

		 return true;					
	}
	
	public static boolean hasAccess(String secFunction,HttpSession session){
			UserContext userRuleModel =   (UserContext) session.getAttribute(ApplicationConstants.USER_MODEL);
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
	
}
