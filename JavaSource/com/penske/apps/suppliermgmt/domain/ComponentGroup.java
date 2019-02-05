package com.penske.apps.suppliermgmt.domain;


public class ComponentGroup {

    private int groupId;
    private String groupName;
    private int groupSequence;
    private String groupNumber;
    private int sectionId;
    
    //Mybatis Only
    protected ComponentGroup(){};
    
    public int getGroupId() {
        return groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public int getGroupSequence() {
        return groupSequence;
    }
    public String getGroupNumber() {
        return groupNumber;
    }
    public int getSectionId() {
        return sectionId;
    }

    @Override
    public String toString() {

        return "ComponentGroup [groupId=" + groupId + ", groupName=" + groupName + ", groupSequence=" + groupSequence + ", groupNumber=" + groupNumber + ", sectionId=" + sectionId + "]";
    }
    
}
