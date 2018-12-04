package com.penske.apps.suppliermgmt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;

/*******************************************************************************
 *
 * @Author : 502299699
 * @Version : 1.0
 * @Date Created: May 15, 2015
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : User context object to hold user details and store in session.
 * @History :
 *
 ******************************************************************************/
public class UserContext implements Serializable {

	private static final long serialVersionUID = 7853812056722969776L;
	
	private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String extension;
    private String status;
    private Integer roleId;
    private int userType;
    private String userSSO;
    private int userDept;
    private String userDeptName;
    private int orgId;
    private List<VendorLocation> associatedVendorList;
    private List<Integer> associatedVendorIds = new ArrayList<Integer>();
    private List<Buddies> associatedBuddyList;
    private Map<String, Map<String, String>> tabSecFunctionMap;
    private Set<SecurityFunction> securityFunctions;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = StringUtils.trimToEmpty(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = StringUtils.trimToEmpty(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserSSO() {
        return userSSO;
    }

    public void setUserSSO(String userSSO) {
        this.userSSO = userSSO;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public List<VendorLocation> getAssociatedVendorList() {
        return associatedVendorList;
    }

    public List<Buddies> getAssociatedBuddyList() {
        return associatedBuddyList;
    }

    public void setAssociatedBuddyList(List<Buddies> associatedBuddyList) {
        this.associatedBuddyList = associatedBuddyList;
    }

    public Map<String, Map<String, String>> getTabSecFunctionMap() {
        return tabSecFunctionMap;
    }

    public void setTabSecFunctionMap(Map<String, Map<String, String>> tabSecFunctionMap) {
        this.tabSecFunctionMap = tabSecFunctionMap;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserDept() {
        return userDept;
    }

    public void setUserDept(int userDept) {
        this.userDept = userDept;
    }

    public Set<SecurityFunction> getSecurityFunctions() {
        return securityFunctions;
    }

    public void setSecurityFunctions(Set<SecurityFunction> securityFunctions) {
        this.securityFunctions = securityFunctions;
    }

	public List<Integer> getAssociatedVendorIds() {
		return associatedVendorIds;
	}
    
    /* Modified Accessors */
    public boolean isVisibleToPenske() {
        return this.userType == 1;
    }

    public boolean isVendorUser()
    {
    	return this.userType == 2;
    }

    public void setAssociatedVendorList(List<VendorLocation> associatedVendorList) {
        this.associatedVendorList = associatedVendorList;
        if(associatedVendorList == null)
        	associatedVendorIds = new ArrayList<Integer>();
        else
        {
        	for(VendorLocation vendorLoc : associatedVendorList)
        		associatedVendorIds.add(vendorLoc.getVendorId());
        }
    }

}
