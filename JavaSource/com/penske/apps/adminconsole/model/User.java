package com.penske.apps.adminconsole.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;

public class User {
	private int userId;
	private String userName;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String extension;
	private String status;
	private UserType userType;
	private String description;
	private Role role;
	private Vendor vendor;
	private ImageFile initFile;
	private ImageFile signFile;
	private boolean hasSignFile;
	private boolean hasInitFile;
	private UserDept userDept;
	private String ssoId;
	private boolean deactivatible;
	private String createdBy;
	private String modifiedBy;
	private String signString;
	private String initString;
	private boolean ssoUserUpdated;
	private String phoneLdap;
	private String defaultPassword;
	private String gessouid;
	private int orgId;
	private int returnFlg;
	private String supportNumber;
	private String org;
	private boolean dailyOptIn;
	private Date lastLoginDate;
	private Date createdDate;

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public boolean getSsoUserUpdated() {
		return ssoUserUpdated;
	}

	public void setSsoUserUpdated(boolean ssoUserUpdated) {
		this.ssoUserUpdated = ssoUserUpdated;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getExtension() {
		return extension;
	}

	public String getStatus() {
		return status;
	}

	public UserType getUserType() {
		return userType;
	}

	public String getDescription() {
		return description;
	}

	public Role getRole() {
		return role;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public ImageFile getInitFile() {
		return initFile;
	}

	public ImageFile getSignFile() {
		return signFile;
	}

	public boolean isHasSignFile() {
		return hasSignFile;
	}

	public boolean isHasInitFile() {
		return hasInitFile;
	}

	public UserDept getUserDept() {
		return userDept;
	}

	public String getSsoId() {
		return ssoId;
	}

	// Setters
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		if (phone != null) {
			if (phone.length() > 10) {
				StringBuilder build = new StringBuilder();

				build.append(phone.substring(1, 4));
				build.append(phone.substring(5, 8));
				build.append(phone.substring(9, 13));

				phone = build.toString();
			} else if (phone.length() == 10) {
				StringBuilder build = new StringBuilder();

				build.append('(');
				build.append(phone.substring(0, 3));
				build.append(')');
				build.append(phone.substring(3, 6));
				build.append('-');
				build.append(phone.substring(6));

				phone = build.toString();
			}

			this.phone = phone;
		}
	}

	public void setPhoneFromSSOLookup(String phone) {
		if (phone != null) {
			/*if (phone.length() > 12 && phone.length() <=15) {
				StringBuilder build = new StringBuilder();
				build.append('(');
				build.append(phone.substring(3, 6));
				build.append(')');
				build.append(phone.substring(7, 11));
				build.append(phone.substring(11, 15));

				phone = build.toString();
			} else if (phone.length() > 10 && phone.length()!= 12) {
				StringBuilder build = new StringBuilder();

				build.append(phone.substring(0, 4));
				build.append(phone.substring(5, 8));
				build.append(phone.substring(9, 13));

				phone = build.toString();
			} else if (phone.length() == 12) {
				StringBuilder build = new StringBuilder();

				build.append('(');
				build.append(phone.substring(0, 3));
				build.append(')');
				build.append(phone.substring(4, 7));
				build.append('-');
				build.append(phone.substring(8));

				phone = build.toString();
			} 
			else if (phone.length() == 10) {
				StringBuilder build = new StringBuilder();

				build.append('(');
				build.append(phone.substring(0, 3));
				build.append(')');
				build.append(phone.substring(3, 6));
				build.append('-');
				build.append(phone.substring(6));

				phone = build.toString();
			}*/
			phone = phone.replaceAll("[^0-9]", "");
			if(phone.length()>10){
				phone=phone.substring(0, 10);
			}
			setPhone(phone);
			//this.phone = phone;
		}
	}

	public void setExtension(String extension) {
		if (extension.equalsIgnoreCase("null")) {
			extension = "";
		}

		this.extension = extension;
	}

	public void setStatus(int status) {
		if (status == 1)
			this.status = "Active";
		else
			this.status = "Inactive";
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public void setInitFile(ImageFile initFile) {
		this.initFile = initFile;
	}

	public void setSignFile(ImageFile signFile) {
		this.signFile = signFile;
	}

	public void setHasSignFile(boolean hasSignFile) {
		this.hasSignFile = hasSignFile;
	}

	public void setHasInitFile(boolean hasInitFile) {
		this.hasInitFile = hasInitFile;
	}

	public void setUserDept(UserDept userDept) {
		this.userDept = userDept;
	}

	public void setSsoId(String ssoId) {
		if (ssoId.equalsIgnoreCase("null")) {
			ssoId = "";
		}
		this.ssoId = ssoId;
	}
	public boolean getDeactivatible() {
		return deactivatible;
	}

	public void setDeactivatible(boolean deactivatible) {
		this.deactivatible = deactivatible;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	public String getSignString() {
		return signString;
	}

	public void setSignString(String signString) {
		this.signString = signString;
	}

	public String getInitString() {
		return initString;
	}

	public void setInitString(String initString) {
		this.initString = initString;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phone=" + phone + ", extension=" + extension + ", status="
				+ status + ", userType=" + userType + ", description="
				+ description + ", role=" + role + ", vendor=" + vendor
				+ ", initFile=" + initFile + ", signFile=" + signFile
				+ ", hasSignFile=" + hasSignFile + ", hasInitFile="
				+ hasInitFile + ", userDept=" + userDept + ", ssoId=" + ssoId
				+ ", deactivatible=" + deactivatible + "]";
	}

	// Validation Methods
	public boolean validateUserInfo() {

		/*if (StringUtils.isBlank(userName)) {
			return false;
		}*/
		 if (StringUtils.isBlank(firstName) || !isName(firstName)) {
			return false;
		}
		else if (StringUtils.isBlank(lastName) || !isName(lastName)) {
			return false;
		}
		/*else if (StringUtils.isBlank(phone)) {
			return false;
		}*/
		else if (StringUtils.isBlank(email)) {
			//|| !isEmail(email) --> need to implement in future for validate email against regex. 
			return false;
		}
		else if (role.getRoleId() <= 0) {
			return false;
		}
		else if (userType.getUserTypeId() <= 0) {
			return false;
		}
		else if (userType.getUserTypeId() == 1) {
			
			if (userDept.getUserDeptId() <= 0) {
				return false;
			}
			else if (StringUtils.isEmpty(ssoId)) {
				return false;
			}
			
			if (hasInitFile && initFile!=null) {
				
				String fileType = initFile.getName().substring(initFile.getName().length() - 4);
				
				if (!fileType.equalsIgnoreCase(".png") && !fileType.equalsIgnoreCase(".jpg")) {
					return false;
				}
			}

			if (hasSignFile && signFile!=null) {
				
				String fileType = signFile.getName().substring(signFile.getName().length() - 4);
				
				if (!fileType.equalsIgnoreCase(".png") && !fileType.equalsIgnoreCase(".jpg")) {
					return false;
				}
			}

		}
		else if (userType.getUserTypeId() == 2) {
			
			if (StringUtils.isEmpty(vendor.getVendorName())) {
				return false;
			}
		}

		return true;
	}

	/*private boolean isEmail(String email) {
		email=email.trim();
		return email.matches("([\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4})");
	}
*/
	private boolean isName(String name) {
		name=name.trim();
		return name.matches("(^[\\D\\s'.]+$)");
	}
	
	public boolean validateUserWithSSOData(User ssoUser){
		boolean isUpdated = false;
		if((ssoUser.getEmail() !=null && !ssoUser.getEmail().equalsIgnoreCase(this.email)) ||
			(ssoUser.getFirstName() !=null && !ssoUser.getFirstName().equalsIgnoreCase(this.firstName)) ||
			(ssoUser.getLastName() !=null && !ssoUser.getLastName().equalsIgnoreCase(this.lastName)) ||
			(ssoUser.getPhone() !=null && !ssoUser.getPhone().equalsIgnoreCase(this.phone))	|| 
			(ssoUser.getGessouid() !=null && !ssoUser.getGessouid().equalsIgnoreCase(this.gessouid))){
			isUpdated = true;
		}
		return isUpdated;
	}

	public String getPhoneLdap() {
		return phoneLdap;
	}

	public void setPhoneLdap(String phoneLdap) {
		if(phoneLdap !=null){
			phoneLdap = phoneLdap.replaceAll("[^0-9]", "");
			if(phoneLdap.length()>10){
				phoneLdap=phoneLdap.substring(0, 10);
			}
			this.phoneLdap = phoneLdap;
		}
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public String getGessouid() {
		return gessouid;
	}

	public void setGessouid(String gessouid) {
		this.gessouid = gessouid;
	}

	public int getReturnFlg() {
		return returnFlg;
	}

	public void setReturnFlg(int returnFlg) {
		this.returnFlg = returnFlg;
	}

	public String getSupportNumber() {
		return supportNumber;
	}

	public void setSupportNumber(String supportNumber) {
		this.supportNumber = supportNumber;
	}

	public boolean isDailyOptIn() {
		return dailyOptIn;
	}

	public void setDailyOptIn(boolean dailyOptIn) {
		this.dailyOptIn = dailyOptIn;
	}
	
	public void setDailyOptInFromChar(String dailyOptIn) {
		this.dailyOptIn = "Y".equals(dailyOptIn) ? true : false;
	}	
	
	public String getDailyOptInChar() {
		return this.dailyOptIn ? "Y" : "N";
	}		
	
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public String getFormattedLastLoginDate() {
		String formattedLastLoginDate = DateUtil.formatDateTimeUS(lastLoginDate);
		return formattedLastLoginDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getFormattedCreatedDate() {
		return DateUtil.formatDateTimeUS(createdDate);
	}
}
