package com.penske.apps.adminconsole.model;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Role {

    public static final Comparator<Role> ROLE_NAME_ASC = new RoleNameComparator();
    public static final Comparator<Role> ORG_NAME_ASC_ROLE_NAME_ASC = new OrgNameRoleNameComparator();

    private String roleName;
    private String baseRoleName;
    private int baseRoleId;
    private String oem;
    private int roleId;
    private String status;
    private List<String> vendor;
    private List<Tab> tabs;
    private List<Role> subRoles;
    private String createdBy;
    private String  modifiedBy;
    private String roleDescription;
    private String orgName;

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
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

    // Getters
    public String getRoleName() {
        return roleName;
    }

    public String getBaseRoleName() {
        return baseRoleName;
    }

    public int getBaseRoleId() {
        return baseRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getVendor() {
        return vendor;
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public List<Role> getSubRoles() {
        return subRoles;
    }

    // Setters
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setBaseRoleName(String baseRoleName) {
        this.baseRoleName = baseRoleName;
    }

    public void setBaseRoleId(int baseRoleId) {
        this.baseRoleId = baseRoleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVendor(List<String> vendor) {
        this.vendor = vendor;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public void setSubRoles(List<Role> subRoles) {
        this.subRoles = subRoles;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /* Comparators ****************************/
    private static class RoleNameComparator implements Comparator<Role> {

        @Override
        public int compare(Role role, Role otherRole) {

            String roleName = role.getRoleName();
            String otherRoleName = otherRole.getRoleName();

            if (roleName == otherRoleName) return 0;// This takes care of nulls
            return roleName == null ? 1 : roleName.compareToIgnoreCase(otherRoleName);
        }
    }

    private static class OrgNameRoleNameComparator implements Comparator<Role> {

        @Override
        public int compare(Role role, Role otherRole) {

            String orgName = role.getOrgName();
            String otherOrgName = otherRole.getOrgName();

            boolean sameOrgName = StringUtils.equalsIgnoreCase(orgName, otherOrgName);

            if (!sameOrgName) {
                return orgName == null ? 1 : orgName.compareToIgnoreCase(otherOrgName);
            } else {

                String roleName = role.getRoleName();
                String otherRoleName = otherRole.getRoleName();

                if (roleName == otherRoleName) return 0;// This takes care of nulls
                return roleName == null ? 1 : roleName.compareToIgnoreCase(otherRoleName);

            }

        }
    }

}
