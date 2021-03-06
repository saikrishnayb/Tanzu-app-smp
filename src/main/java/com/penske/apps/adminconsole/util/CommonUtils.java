package com.penske.apps.adminconsole.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.Role;


/**
 * CommonUtils class
 * @author  502174985 @created 19-JAN-2016 9:11:47 AM
 *
 */
public class CommonUtils {
	
	private static Logger logger = LogManager.getLogger(CommonUtils.class);
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
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
				 key.append(components.isForRules());
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

    /**
     * Invokes Jackson's {@link ObjectMapper#writeValueAsString(Object)} to deep serialize an object to a JSON string.
     * This method basically has no error handling. It just throws any generated IOException as a RuntimeException
     * @param object The object to serialize
     * @param escapeForJavascript True to escape JavaScript literals in the resulting JSON string; false to skip the escaping.
     *      This should be set to true if you are using the result in a call like $.parseJSON('${RESULT_STRING_HERE}')
     * @return The serialized string.
     * @throws RuntimeException If an IOException occurs while serializing. Since we're writing to Strings, this should happen very
     * infrequently
     */
	public static String serializeJson(Object objectToSerialize, boolean escapeForJavascript)
	{
		try {
			String jsonString = jsonMapper.writeValueAsString(objectToSerialize);
			if(escapeForJavascript && jsonString != null)
				return StringEscapeUtils.escapeEcmaScript(jsonString);
			else
				return jsonString;
		} catch(IOException ex) {
			RuntimeException ex2 = new RuntimeException(ex);
			ex2.setStackTrace(ex.getStackTrace());
			throw ex2;
		}
	}
}
